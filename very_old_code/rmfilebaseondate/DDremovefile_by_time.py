#!/usr/bin/python
# Filename : removefiles_by_time.py
# Author: 
# Date:
# Remove files 10 days ago
# command [options] options: somedirs or current dir

import os
import re
import datetime
import sys

class RmFile:
	def __init__(self, time, pathlist):
		'''init : two members'''
		self.delta_day = datetime.timedelta(time)
		self.pathlist = pathlist
		self.rm_filelist = []

	def do(self):
		'''do remove'''
		if len(self.pathlist) != 1 :   # has some dirs
			for onepath in self.pathlist[1:]:
				full_pathname = os.path.abspath(onepath)
				if os.path.exists(full_pathname):
					print 'The Processing Dir is : %s\n' % full_pathname
					self.removefiles(full_pathname)
				else:
					print '\nERROR: not exists %s\n' % full_pathname
		else:                     # current dir
			pathname = os.path.dirname(self.pathlist[0])
			full_pathname = os.path.abspath(pathname)
			print 'The Processing Dir is : %s\n' % full_pathname
			self.removefiles(full_pathname)

		
		if len(self.rm_filelist) != 0:
			for items in self.rm_filelist:
				print items

			yesorno = raw_input('\nAre you sure to remove these files:y/n\n')
			if yesorno == 'y' or yesorno == 'Y':
				for items in self.rm_filelist:
					remove_command = 'rm ' + items
					if os.system(remove_command) == 0:
						pass
					else:
						print '\nERROR: remove this files: %s\n' % items
				print '\nComplete in this Dir and Leave.......\n'
			else:
				pass
		else:
			print '\nIn %s has no file must remove\n' % pathname	

	def removefiles(self, pathname):
		''' remove some files in %s''' % pathname
		
		for root, dirs, files in os.walk(pathname):
			filelist = [ os.path.join(root, name) for name in files ]
			filelist.sort()

			for onefile in filelist:
				# first match the filename
				if re.search("^.*\.\d{8}$", onefile) != None:
					# the name of this file is correct
					dlist = re.search('\d{8}$', onefile).group()
					year = int(dlist[:4])
					month = int(dlist[4:6])
					day = int(dlist[6:])

					try:
						filedate = datetime.date(year, month, day)
					except ValueError:
						continue

					if filedate < filedate.today() - self.delta_day:
						# add this file to removeList
						if self.rm_filelist.__contains__(onefile) == False:
							self.rm_filelist.append(onefile)
				else:
					pass

	#def rmone(self, node):
		
	def doall(self):
		'''removefiles from console to others'''
		nodelistfile = file('/usr/local/bin/nodelist')
		while True:
			one_node = nodelistfile.readln()
			if len(one_node) == 0:
				break
			if os.system('/usr/bin/rsh ' + one_node) == 0:
				# connect
				print 'connect %s' % one_node
				#rmone(one_node)
			else:
				print 'not connect %s' % one_node
		nodelistfile.close()
		#console
		#rmone()

# Start Here
if __name__ == "__main__":
	
	# argv------------------
	rmfile = RmFile(10, sys.argv)
	rmfile.doall()

	print '\nDone\n' 
