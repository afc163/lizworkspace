#!/usr/bin/python
# Filename : rmFiles.py
# Author: 
# Date:
# Remove files 10 days ago
# command [options] options: somedirs or current dir

import os
import re
import datetime
import sys

def removefiles(pathname):
	''' remove the files in this path'''
	global deltaDay

	fileList = os.listdir(pathname)
	for oneFile in fileList:
		# first match the filename
		if re.search("^.*\.\d{8}$", oneFile) != None:
			# the name of this file is correct
			dlist = re.search('\d{8}$', oneFile).group()
                        year = int(dlist[:4])
			month = int(dlist[4:6])
			day = int(dlist[6:])

			try:
				fileDate = datetime.date(year, month, day)
			except ValueError:
				continue

			if fileDate < fileDate.today() - deltaDay:
				# remove this file
				removeCommand = 'rm -i ' + pathname + os.sep + oneFile
				if os.system(removeCommand) == 0:
					print 'Already remove: %s' % pathname + os.sep + oneFile
	print 'Complete in this Dir and Leave.......\n'

# Start Here

if __name__ == "__main__":

    deltaDay = datetime.timedelta(10)

    if len(sys.argv) != 1 :   # has some dirs
        for onePath in sys.argv[1:]:
	    fullPathName = os.path.abspath(onePath)
	    if os.path.exists(fullPathName):
	        print '\nThe Dir is : %s\n' % fullPathName
		removefiles(fullPathName)
	    else:
		print '\nERROR: not exists %s\n' % fullPathName

    else:                     # current dir
	pathName = os.path.dirname(sys.argv[0])
	fullPathName = os.path.abspath(pathName)
	print '\nThe Dir is : %s\n' % fullPathName
	removefiles(fullPathName)

    print '\nDone\n' 
