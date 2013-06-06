#coding=utf-8
from django.shortcuts import render_to_response

address = [
{'name':'shengyan', 'address':'shengyan195@gmail.com'},
{'name':'wangshu', 'address':'wangshu@163.com'}
]

def index(request):
	return render_to_response('list.html', {'address':address})
