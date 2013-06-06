#!/usr/bin/python
#coding=utf-8
# Filename : godin.py
import BinaryRelation
import Lattice

if __name__ == '__main__':
	
	# create random binary formal context
	attribute_num = 10
	object_num = 10
	object_attribute_rate = 0.6
	BR = BinaryRelation.BinaryRelation(object_num, attribute_num, object_attribute_rate)
	#print BR

	myLattice = Lattice.Lattice(BR)
	del BR
	myLattice.doGodinAlgorithm()
	print myLattice
	#import hotshot, hotshot.stats
	#prof = hotshot.Profile("lattice.prof", 1)
	#prof.runcall(myLattice.doGodinAlgorithm)
	#prof.close()
	#stats = hotshot.stats.load("lattice.prof")
	#stats.strip_dirs()
	#stats.sort_stats('time', 'calls')
	#stats.print_stats(20)