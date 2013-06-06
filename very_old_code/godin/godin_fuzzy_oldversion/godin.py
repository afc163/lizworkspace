#!/usr/bin/python
#coding=utf-8
# Filename : godin.py
import BinaryRelation
import Lattice
import re
import time
import SequenceDB

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
	seq_DB = SequenceDB.SeqDB(20, 100, 0.3)
	print seq_DB
	# init Lattice
	#myLattice = Lattice.Lattice(seq_DB.object_list, seq_DB.attribute_list)	
	
	#start = time.time()
	#for obj in seq_DB.Seq_DB:
	#	event_list = seq_DB.Seq_DB[obj]
	#	for eid in range(len(event_list)):
	#		oneEvent = event_list[eid]
	#		print 'CID : %d\n EID is %d: %s\n' % (obj, eid, oneEvent)
	#		myLattice.addOneObject((obj, eid), oneEvent)
			
	#myLattice.doGodinAlgorithm()
	#usetime = time.time() - start

	#print myLattice
	#print 'the time is %.10f' % usetime
	#print '\nDone\n'

	#myLattice.doGodinAlgorithm()
	#import hotshot, hotshot.stats
	#prof = hotshot.Profile("lattice.prof", 1)
	#for obj in seq_DB.Seq_DB:
	#	event_list = seq_DB.Seq_DB[obj]
	#	for eid in range(len(event_list)):
	#		oneEvent = event_list[eid]
			#print 'CID : %d\n EID is %d: %s\n' % (obj, eid, oneEvent)
	#		prof.runcall(myLattice.addOneObject, [(obj, eid)], oneEvent)
	
	#prof.close()
	#print myLattice
	#stats = hotshot.stats.load("lattice.prof")
	#stats.strip_dirs()
	#stats.sort_stats('time', 'calls')
	#stats.print_stats(20)
