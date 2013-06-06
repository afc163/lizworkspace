#!/usr/bin/python
# Filename : creatFile.py

import os
import time

target_dir = '/home/shengyan/workspace/someWork/tt'
time_place = time.strftime('%Y%m%d')
now_time = time.strftime('%H : %M : %S')

comment = raw_input('Enter a File Name -->')
if len(comment) == 0:
	target = target_dir + 'default.' + time_place
else:
	target = target_dir + os.sep + comment.replace(' ', '_') + '.' + time_place

# new_file = " echo 'some example....no meaning' > " + target
new_file = " echo %s at %s > %s" % (target, now_time, target)
ls_file = 'ls ' + target_dir
if os.system(new_file) == 0:
	os.system(ls_file)
else:
	print 'Creat Files Failed!'

print '\nDone\n'
