#!/usr/bin/python
#coding=utf-8
# Filename : godin.py
import BinaryRelation
import Lattice

if __name__ == '__main__':
	
	# create random binary formal context
	attribute_num = 20
	object_num = 100
	object_attribute_rate = 0.3
	BR = BinaryRelation.BinaryRelation(object_num, attribute_num, object_attribute_rate)
	print BR

	#from timeit import Timer
	# init Lattice
	#t1 = Timer("Lattice.Lattice(BR)", "import Lattice, BinaryRelation")
	#myLattice = Lattice.Lattice(BR)
	#print "Init Lattice Time:".ljust(20), t1.timeit(1000)
	#del BR
	#t2 = Timer("myLattice.doGodinAlgorithm()", "import Lattice")
	#myLattice.doGodinAlgorithm()
	#print "GodinAlorithm Time:".ljust(20), t2.timeit(1)
	#print myLattice

	#import profile, pstats
	#myLattice = Lattice.Lattice(BR)
	#del BR
	#profile.run("myLattice.doGodinAlgorithm()", "lattice.prof")
	#p = pstats.Stats("lattice.prof")
	#p.sort_stats("time").print_stats()

	myLattice = Lattice.Lattice(BR)
	del BR
	import hotshot, hotshot.stats
	prof = hotshot.Profile("lattice.prof", 1)
	prof.runcall(myLattice.doGodinAlgorithm)
	prof.close()
	stats = hotshot.stats.load("lattice.prof")
	stats.strip_dirs()
	stats.sort_stats('time', 'calls')
	stats.print_stats(20)

	#from timeit import Timer
	#t1 = Timer("attribute_num = 20\nobject_num = 100\nobject_attribute_rate = 0.3\nBR = BinaryRelation.BinaryRelation(object_num, attribute_num, object_attribute_rate)\nmyLattice = Lattice.Lattice(BR)\nmyLattice.doGodinAlgorithm()\nprint myLattice", "import Lattice, BinaryRelation")
	#attribute_num = 20
	#object_num = 10
	#object_attribute_rate = 0.3
	#BR = BinaryRelation.BinaryRelation(object_num, attribute_num, object_attribute_rate)
	
	#t1 = Timer("myLattice = Lattice.Lattice('%s')\nmyLattice.doGodinAlgorithm()" % BR, "import Lattice, BinaryRelation")
	#print t1.timeit(1)
	#print myLattice

