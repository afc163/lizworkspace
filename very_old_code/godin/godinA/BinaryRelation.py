#!/usr/bin/python
#coding=utf-8
#Filename : BinaryRelation.py
import random

class BinaryRelation:
	def __init__(self, object_num=7, attribute_num=10, object_attribute_rate=0.3):
		'''init the BinaryRelation'''
		# create attribute info
		#for att in range(attribute_num):
		#    attribute_list.append('attribute_%d' % att)
		self.attribute_list = ['a_%d' % att for att in range(attribute_num)]
		# create object info
		#for obj in range(object_num):
		#    object_list.append('object_%d' % obj)
		self.object_list = ['o_%d' % obj for obj in range(object_num)]
		
		# create random values
		def add(x, y):
			return x + y
		
		def sum(seq):
			def add(x, y):
				return x + y
			return reduce(add, seq, 0) # 0 is starting value
		self.value_list = []
		for oneline in range(object_num):
			oneline_sum = 0
			oneline_value = []
			while oneline_sum == 0 or oneline_sum == attribute_num:
				oneline_value = [random.randint(0, 1) for one in range(attribute_num)]
				oneline_sum = reduce(add, oneline_value) # reduce, map, filter
				# oneline_sum = sum(oneline_value)
			self.value_list.append(oneline_value)
		# end init

	def __str__(self):
		'''Get this BinaryRelation Information
		
		contains: object inform
		attribute inform
		value inform'''
		return 'object_list is %s\nattribute_list is %s\nvalue_list is\n%s' % (self.object_list, self.attribute_list, self.value_list)

