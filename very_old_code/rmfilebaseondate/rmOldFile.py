#!/usr/bin/python
# Filename : rmOldFile.py
# Remove files 10 days ago
# command [options] options: somedirs or current dir


import os
import re
import datetime
import sys

def removefiles(pathname):
	global patterm
	global deltaDay

	fileList = os.listdir(pathname)
	for oneFile in fileList:
		print oneFile
		# first match the filename
		if re.search(patterm, oneFile) != None:
			# the name of this file is correct
			dlist = re.search('\d{8}$', oneFile).group()
			year = int(dlist[:4])
			#if year < MINYEAR or year > MAXYEAR:
			#	continue
			month = int(dlist[4:6])
			#if month < 1 or month > 12:
			#	continue
			day = int(dlist[6:])
			#if day < 1 or day > MAXDAY:
			#	continue
			try:
				fileDate = datetime.date(year, month, day)
			except ValueError:
				continue
			if fileDate < fileDate.today() - deltaDay:
				# remove this file
				removeCommand = 'rm -i ' + pathname + os.sep + oneFile
				os.system(removeCommand)

# Start Here
deltaDay = datetime.timedelta(10)
patterm = "^.*\.\d{8}$"

if len(sys.argv) != 1 :   # has some dirs
	for onePath in sys.argv[1:]:
		fullPathName = os.path.abspath(onePath)
		if os.path.exists(fullPathName):
			print 'exists %s' % fullPathName
			removefiles(fullPathName)
		else:
			print 'not exists %s' % fullPathName

else:                     # current dir
	pathName = os.path.dirname(sys.argv[0])
	fullPathName = os.path.abspath(pathName)
	print 'Full Path Name is %s:' % fullPathName
	removefiles(fullPathName)

print '\nDone\n' 
