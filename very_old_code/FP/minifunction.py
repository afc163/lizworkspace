#!/usr/bin/python
#coding:utf-8

import operator
import types
from miniutil import ifthenelse

class unary_function(object):
    def __init__(self, arg, result):
	    if type(arg) is not types.TypeType:
		    self.argument_type = type(arg)
		else:
		    self.argument_type = arg

	    if type(result) is not types.TypeType:
		    self.result_type = type(result)
		else:
		    self.result_type = result

class binary__function:
    def __init__(self, arg1, arg2, result):
	    if type(arg1) is not types.TypeType:
		    self.first_argument_type = type(arg1)
		else:
		    self.first_argument_type = arg1

	    if type(arg2) is not types.TypeType:
		    self.second_argument_type = type(arg2)
		else:
		    self.second_argument_type = arg2

		if type(result) is not types.TypeType:
		    self.result_type = type(result)
	    else:
		    self.result_type = result

