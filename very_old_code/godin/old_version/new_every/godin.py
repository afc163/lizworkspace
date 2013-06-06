#!/usr/bin/python
#coding=utf-8
# Filename : godin.py
import BinaryRelation
import Lattice
import re, time

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
		br.value_list = []#可省，因为BR中设了默认值
		for index in br.object_list:
			oneLine = brFile.readline()
			temp = pt.split(oneLine)
 			temp.remove('')
			br.value_list.append(temp)
		return br
	except:
		print "ERROR in BinaryRelation File"
	finally:
		brFile.close()

if __name__ == '__main__':
	
	# create random binary formal context
	attribute_num = 20
	object_num = 100
	object_attribute_rate = 0.3
	BR = BinaryRelation.BinaryRelation(object_num, attribute_num, object_attribute_rate)
	#print BR


	#BR = readBinaryRelation()
	# init Lattice
	myLattice = Lattice.Lattice(BR)
	del BR
	#start = time.time()
	#myLattice.doGodinAlgorithm()
	#for oneobject in BR.object_list:
	#    Lattice
	#save and output
	#usetime = time.time() - start

	#print myLattice
	#print 'the time is %.10f' % usetime
	#print '\nDone\n'
	#myLattice.doGodinAlgorithm()
	import hotshot, hotshot.stats
	prof = hotshot.Profile("lattice.prof", 1)
	prof.runcall(myLattice.doGodinAlgorithm)
	prof.close()
	print myLattice
	stats = hotshot.stats.load("lattice.prof")
	stats.strip_dirs()
	stats.sort_stats('time', 'calls')
	stats.print_stats(20)
