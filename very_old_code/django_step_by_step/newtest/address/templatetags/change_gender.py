# coding=utf-8
from django import template #导入模块

register = template.Library() #生成register对象，用来注册filter

#@register.filter(name='change_gender')
def change_gender(value):
	if value == 'M':
		return '男'
	else:
		return '女'

register.filter('change_gender', change_gender)
