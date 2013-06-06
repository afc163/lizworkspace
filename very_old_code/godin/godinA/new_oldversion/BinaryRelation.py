#!/usr/bin/python
#coding=utf-8
#Filename : BinaryRelation.py
import random

class BinaryRelation:
	def __init__(self, object_num=7, attribute_num=10, object_attribute_rate=0.3):
		'''init the BinaryRelation'''
		self.attribute_list = ['a_%d' % att for att in range(attribute_num)]
		self.object_list = ['o_%d' % obj for obj in range(object_num)]
		
		# create random values
		self.value_list = []
		num = int(attribute_num * object_attribute_rate)
		for oneline in range(object_num):
			oneline_value = []
			for i in range(attribute_num):
				oneline_value.append(0)
			random_index = [random.randint(0, attribute_num-1) for one in range(num)]
			for j in range(num):
				oneline_value[random_index[j]] = 1
			#0101010  ----> an integer
			#value = 0;
			#for i in oneline_value:
			#	value = value*2 + i
			#self.value_list.append(value)
			tp = [ i for i in range(attribute_num) if oneline_value[i] == 1 ]
			self.value_list.append(tp)

	def __str__(self):
		'''Get this BinaryRelation Information
		
		contains: object inform
		attribute inform
		value inform'''
		return 'object_list is %s\nattribute_list is %s\nvalue_list is\n%s' % (self.object_list, self.attribute_list, self.value_list)

