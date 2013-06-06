#!/usr/bin/python
#coding=utf-8
# Filename : godin.py
import BinaryRelation
import Lattice
import time
import sys
from optparse import OptionParser

def timer(fn, * args):
	'''Time the application of fn to args.Return(result, seconds).'''  # don't understand
	import time
	start = time.clock()
	return fn(*args), time.clock() - start


# Start From Here...............
if __name__ == '__main__':
	# Parser the command-line arguments.
	parser = OptionParser(usage="""godin.py [options] something""")
	parser.add_option('--version', help='Show Version Information')
	options, args = parser.parse_args(sys.argv[1:])
	
	
	# create random binary formal context
	attribute_num = 20
	object_num = 100
	object_attribute_rate = 0.3
	BR = BinaryRelation.BinaryRelation(object_num, attribute_num, object_attribute_rate)
	print BR
	# init Lattice
	myLattice = Lattice.Lattice(BR)
	del BR
	
	start = time.time()
	myLattice.doGodinAlgorithm()
	usetime = time.time() - start
	print myLattice
	print 'the time is %.10f' % usetime
	
	print '\nDone\n'
