#!/usr/bin/env python
# -*- coding: utf-8 -*-
############################################################################
# Python-Qt XMPP Status Bot for amaroK
# (c) 2007 Dawid Ciezarkiewicz <dawid.ciezarkiewicz@asn.pl>
# (c) 2007 Alexander Schaber   <web@alexanderschaber.de>
#
# Depends on: Python 2.2, PyQt, xmpppy
############################################################################
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License, or
# (at your option) any later version.
#
############################################################################

import ConfigParser
import os
import sys
import threading
import signal
import re
import codecs
from time import sleep

try:
	from qt import *
except:
	os.popen("kdialog --sorry 'PyQt (Qt bindings for Python) is required for this script.'")
	raise

try:
	import xmpp
except:
	os.popen("kdialog --sorry 'xmpppy is required for this script.'")
	raise

# globals
encoding = "utf-8"
config_filename = "xmppstatusbotrc"

class ConfigDialog(QDialog):
	"""
	Configuration widget class which is used to set up the bot.
	"""

	def __init__(self):
		QDialog.__init__(self)
		self.setWFlags( Qt.WDestructiveClose )
		self.setCaption( "XMPP Status Bot" )

		jid = "bot@server.org"
		resource = "amarokbot"
		delay = "5"
		rc = "false"
		rcsecret = ""

		try:
			config = ConfigParser.ConfigParser()
			config.read(config_filename)
			jid      = config.get("General", "jid")
			resource = config.get("General", "res")
			delay    = config.get("General", "delay")
			rc       = config.get("RemoteControl", "enable")
			rcsecret = config.get("RemoteControl", "secret")
		except:
			pass

		self.lay = QHBoxLayout(self)
		self.vbox = QVBox(self)
		self.lay.addWidget(self.vbox)

		self.l1 = QLabel(QString("bot jid"), self.vbox )
		self.le_jid = QLineEdit( QString(jid), self.vbox )
		self.l2 = QLabel(QString("bot resource"), self.vbox )
		self.le_res = QLineEdit( QString(resource), self.vbox )
		self.l4 = QLabel(QString("minimum delay"), self.vbox )
		self.le_delay = QLineEdit(QString(delay), self.vbox )

		self.cb_rc = QCheckBox("enable remote control?", self.vbox)
		if rc == "true":
			self.cb_rc.setChecked(1)
		else:
			self.cb_rc.setChecked(0)

		self.l5 = QLabel(QString("remote secret"), self.vbox )
		self.le_rcsecret = QLineEdit(QString(rcsecret), self.vbox )

		self.hbox = QHBox(self.vbox)
		self.cancel = QPushButton(self.hbox)
		self.cancel.setText("Cancel")

		self.ok = QPushButton(self.hbox)
		self.ok.setText("OK")
		self.ok.setDefault(True)

		self.connect(self.ok,     SIGNAL("clicked()"), self.save)
		self.connect(self.cancel, SIGNAL("clicked()"), self, SLOT("reject()"))

		self.adjustSize()

	def save(self):
		""" Saves configuration to file """

		self.file = file(config_filename, 'w' )

		self.config = ConfigParser.ConfigParser()
		self.config.add_section("General")
		self.config.add_section("RemoteControl")
		self.config.set("General", "jid",  self.le_jid.text())
		self.config.set("General", "res",  self.le_res.text())
		self.config.set("General", "delay", self.le_delay.text())
		self.config.set("RemoteControl", "secret", self.le_rcsecret.text())
		if self.cb_rc.isChecked():
			self.config.set("RemoteControl", "enable", "true")
		else:
			self.config.set("RemoteControl", "enable", "false")
		self.config.write(self.file)
		self.file.close()

		self.accept()

class Notification( QCustomEvent ):
	__super_init = QCustomEvent.__init__
	def __init__(self, str):
		self.__super_init(QCustomEvent.User + 1)
		self.string = str


class XmppStatusBot( QApplication ):
	"""
	The main application class. This also sets up the Qt event loop 
	"""

	def __init__(self, args):
		"""
		Initializes the class, sets up a dcop tunneled connection to
		kwalletd. Finally calls L{readSettings} to finish of the
		setup for the bot.
		"""
		QApplication.__init__(self, args)
		self.conn = None
		self.was_first_connected = False
		self.timer = None
		self.kwalletd_handle = os.popen("dcop kded kwalletd open kdewallet 0").readlines()[0].strip()
		if os.popen("dcop kded kwalletd hasFolder " + self.kwalletd_handle + " amarok").readlines()[0].strip() == "false":
			os.popen("dcop kded kwalletd createFolder " + self.kwalletd_handle + " amarok")

		# Start separate thread for reading data from stdin
		self.stdinReader = threading.Thread(target = self.readStdin)
		self.stdinReader.start()
		self.xmppLooper = threading.Thread(target = self.xmppLoop)
		self.xmppLooper.start()

		self.readSettings()

	def readSettings( self ):
		"""
		Parses configuration file (L{config_filename}). Calls 
		L{xmppReconnect} after successfull config read to get the
		bot off the ground.
		"""

		try:
			config = ConfigParser.ConfigParser()
			config.read(config_filename)
			self.bot_jid  = config.get("General", "jid")
			self.bot_pass = os.popen("dcop kded kwalletd readPassword " + self.kwalletd_handle + " amarok " + self.bot_jid).readlines()[0].strip()
			self.bot_res  = config.get("General", "res")
			self.bot_delay = float(config.get("General", "delay"))
			self.rc_enable = config.get("RemoteControl", "enable")
			self.secret = config.get("RemoteControl", "secret")

		except:
			self.userNotify("could not read settings")
			return

		self.xmppReconnect();



	####################################################################
	# Stdin-Reader Thread
	####################################################################

	def readStdin( self ):
		"""
		Reads incoming notifications from stdin.
		"""

		while True:
			# Read data from stdin. Will block until data arrives.
			line = sys.stdin.readline()

			if line:
				qApp.postEvent( self, Notification(line) )
			else:
				break

	####################################################################
	# XMPP loop Thread
	####################################################################

	def xmppLoop(self):
		"""
		This loops while script is running and processes incomming /
		outgoing messages.
		"""

		while 1:
			if self.was_first_connected:
				if self.conn and self.conn.isConnected():
					self.conn.Process(1)
				else:
					self.userNotify("reconnecting")
					self.xmppReconnect()
					sleep(10)
			else:
				sleep(1)

	####################################################################
	# XMPP Handling
	####################################################################

	def xmppReconnect(self):
		"""
		Uses xmpppy in order to connect to the jabber server and sets
		up the user presence.
		"""

		jid = xmpp.JID(self.bot_jid)
		self.conn = xmpp.Client(jid.getDomain())
		res = self.conn.connect()
		self.authed = []
		# make an option?
		# self.authed = [jid]

		if not res:
			self.was_first_connected = True
			self.userNotify("could not connect to server")
			return

		if res <> "tls":
			self.userNotify("warning - no tls connection")

		res = self.conn.auth(jid.getNode(), self.bot_pass, self.bot_res)

		if not res:
			self.was_first_connected = True
			self.userNotify("not authorized: " + self.bot_pass)
			password = os.popen("kdialog --password \"Password for " + self.bot_jid + "\"").readlines()[0].strip()
			if os.popen("dcop kded kwalletd hasFolder " + self.kwalletd_handle + " amarok").readlines()[0].strip() == "false":
				os.popen("dcop kded kwalletd createFolder " + self.kwalletd_handle + " amarok")
			os.popen("dcop kded kwalletd writePassword " + self.kwalletd_handle + " amarok " + self.bot_jid + " " + password)
			self.readSettings()
			return

		if res <> "sasl":
			user.userNotify("warning - no sasl auth")

		self.was_first_connected = True
		self.userNotify("connected as: " + self.bot_jid)
		self.xmppPresence("just started")
		if self.rc_enable == "true":
			self.conn.RegisterHandler('message',self.xmppMessage)

	def xmppMessage(self, con, event):
		text = event.getBody()
		srcjid = event.getFrom()
		if not self.rc_enable == "true":
			return

		if text.find(' ')+1:
			command,args=text.split(' ',1)
		else:
			command,args=text,''

		reply = ""
		if command == "auth":
			if args == self.secret:
				reply = "Nice to meet you."
				self.authed.append(srcjid);
			else:
				reply = "Sorry. Can't let you in."

		elif command == "help":
			reply = "http://dpc.wikidot.com/lab:xmppstatusbot-help"

		elif srcjid in self.authed:
			if command == "play":
				os.popen("dcop amarok player play")
			elif command == "pause":
				os.popen("dcop amarok player pause")
			elif command == "next":
				os.popen("dcop amarok player next")
			elif command == "prev":
				os.popen("dcop amarok player prev")
			elif command == "stop":
				os.popen("dcop amarok player stop")
			else:
				reply = "I don't understand you. Use 'help'."
		else:
			reply = "I don't know you. Authenticate or say 'help'."

		if reply <> "":
			self.conn.send(xmpp.Message(srcjid,body=reply,typ='chat'))

	def xmppPresence(self, st):
		"""
		Little helper to for easy presence setting
		
		@type  st: string
		@param st: New presence string
		"""
		if self.rc_enable == "true":
			presence = xmpp.Presence(status = st, show = 'xa', priority = '1')
		else:
			presence = xmpp.Presence(status = st, show = 'xa', priority = '-1')
		self.conn.send(presence)

	####################################################################
	# Notification Handling
	####################################################################

	def customEvent( self, notification ):
		"""
		Handles notifications and sets a timer for a presence update.
		
		@type  notification: String
		@param notification: Line read from stdin, which comes from 
					  amaroK.
		"""

		string = QString(notification.string)
		if self.timer != None:
			self.timer.cancel()
			self.timer = None

		if string.contains( "configure" ):
			self.configure()

		if string.contains( "engineStateChange: play" ):
			self.timer = threading.Timer(self.bot_delay, self.engineStatePlay)
			self.timer.setDaemon(1)
			self.timer.start()

		if string.contains( "trackChange" ):
			self.timer = threading.Timer(self.bot_delay, self.engineStatePlay)
			self.timer.setDaemon(1)
			self.timer.start()

		if string.contains( "engineStateChange: idle" ):
			self.engineStateIdle()

		if string.contains( "engineStateChange: pause" ):
			self.engineStateIdle()

		if string.contains( "engineStateChange: empty" ):
			self.engineStateIdle()

	def userNotify(self, msg):
		"""
		Helper function to show a nice in application popup Message
		in amaroK using it's dcop interface.

		@type  msg: String
		@param msg: Text shown in amaroK's popup.
		"""

		print msg
		os.popen("dcop amarok playlist popupMessage '" + msg + "'")

	# Notification callbacks. Implement these functions to react to
	# specific notification events from amaroK:

	def configure( self ):
		"""
		Called when user clicks on configure, pops up the
		configuration widget.
		"""

		self.dia = ConfigDialog()
		self.dia.show()
		self.connect(self.dia, SIGNAL("destroyed()"), self.readSettings )

	def engineStatePlay( self ):
		"""
		Called when Engine state changes it's state to play or there
		is a track change.
		"""

		track = os.popen("dcop amarok player track").readlines()[0].strip()
		title = unicode(os.popen("dcop amarok player title").readlines()[0].strip(), 'utf_8')
		artist = unicode(os.popen("dcop amarok player artist").readlines()[0].strip(), 'utf_8')
		album = unicode(os.popen("dcop amarok player album").readlines()[0].strip(), 'utf_8')
		msg = u"playing"

		if not title == "":
			msg = msg + " \"" + title + "\""
		if not artist == "":
			msg = msg + " by " + artist
		if not track == "":
			msg = msg + " track " + track
		if not album == "":
			msg = msg + " from \"" + album + "\""
		self.xmppPresence(msg.encode(encoding));

	def engineStateIdle( self ):
		"""
		Called when Engine state changes to idle or pause.
		"""

		msg = "idle"
		self.xmppPresence(msg.encode(encoding));

############################################################################

def main( ):
	app = XmppStatusBot( sys.argv )
	app.exec_loop()

def onStop(signum, stackframe):
	"""
	Called when script is stopped by user or amaroK quits.
	"""
	sys.exit(1)

if __name__ == "__main__":
	mainapp = threading.Thread(target=main)
	mainapp.start()
	signal.signal(15, onStop)
	# necessary for signal catching
	while 1: sleep(120)
