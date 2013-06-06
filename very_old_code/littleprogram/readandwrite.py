#!/usr/bin/python
#coding=utf-8
import threading
import time

class Writer(threading.Thread):
	def __init__(self):
		threading.Thread.__init__(self, name='writer')
		self.isStop = 0
		self.filename = 'test.mp3'
		self.file = open(self.filename, 'w')

	def stop(self):
		self.isStop = 1
		self.file.close()

	def run(self):
		i = 0
		while 1:
			if self.isStop:
				break
			self.file.write('hi%i\n' % i)
			i += 1
			time.sleep(.005)

class Reader(threading.Thread):
	def __init__(self):
		threading.Thread.__init__(self, name='reader')
		self.isStop = 0
		self.filename = 'test.mp3'
		self.file = open(self.filename)
		self.out = open('outtest', 'w')

	def stop(self):
		self.isStop = 1
		self.file.close()
		self.out.close()

	def run(self):
		while 1:
			if self.isStop:
				break
			oneline = self.file.readline()
			self.out.write(oneline)
			time.sleep(.005)

if __name__ == '__main__':
	#writer = Writer()
	#writer.setDaemon(1)
	#print 'Writer start.........'
	#writer.start()

	#reader = Reader()
	#reader.setDaemon(1)
	#print 'Reader start.........'
	#reader.start()

	#while 1:
	#	pass

	#print 'stop'
	#writer.stop()
	#reader.stop()
	try:
		one = None
		two = None
		one = file('test.mp3', 'w')
		one.write('something\n')
		#one.close()
		two = file('test.mp3')
		string = two.readline()
		print string
	except Exception, e:
		print e
	finally:
		if one:
			one.close()
		if two:
			two.close()

