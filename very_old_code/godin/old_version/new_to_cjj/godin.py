#!/usr/bin/python
#coding=utf-8
# Filename : godin.py
import BinaryRelation
import Lattice
import re

def readBinaryRelation():
	''' input BinaryRelation -----OK'''
	brFile = file('binary_relation', 'r')
	br = BinaryRelation.BinaryRelation()
	pt = re.compile(r'\W+')
	try:
		oneLine = brFile.readline()#............是空行要跳过
 		if len(oneLine) != 0:
			br.object_list = pt.split(oneLine)
 			br.object_list.remove('')
		oneLine = brFile.readline()
		if len(oneLine) != 0:
			br.attribute_list = pt.split(oneLine)
			br.attribute_list.remove('')
		br.value_list = []
		for index in br.object_list:
			oneLine = brFile.readline()
			temp = pt.split(oneLine)
 			temp.remove('')
			#tp = []
			#for i in temp:
			#    tp.append(int(i))
			tp = [ i for i in range(len(temp)) if int(temp[i]) != 0 ]
			#0101010  ----> an integer
			#value = 0;
			#for i in tp:
			#	value = value*2 + i			
			#br.value_list.append(value)
			br.value_list.append(tp)
		return br
	except:
		print "ERROR in BinaryRelation File"
	finally:
		brFile.close()

if __name__ == '__main__':
	
	# create random binary formal context
	attribute_num = 20
	object_num = 1000
	object_attribute_rate = 0.3
	BR = BinaryRelation.BinaryRelation(object_num, attribute_num, object_attribute_rate)
	#BR = readBinaryRelation()
	print BR

	myLattice = Lattice.Lattice(BR.object_list, BR.attribute_list)
	#for obj in range(object_num):
	#    myLattice.addOneObject([obj], BR.value_list[obj])
	#print myLattice
	
	import hotshot, hotshot.stats
	prof = hotshot.Profile("lattice.prof", 1)
	for obj in range(object_num):
		prof.runcall(myLattice.addOneObject, [obj], BR.value_list[obj])
	prof.close()
	print myLattice
	stats = hotshot.stats.load("lattice.prof")
	stats.strip_dirs()
	stats.sort_stats('time', 'calls')
	stats.print_stats(20)
