# coding=utf-8
# Create your views here.
from django.http import HttpResponse

def input(request):
	input = request.REQUEST["input"]
	return HttpResponse('<p>You input is "%s"</p>' % input)

def json(request):
	a = {'head':('Name', 'Telphone'), 'body':[(u'张三', '1111'), (u'李斯', '2222')]}
	import simplejson
	return HttpResponse(simplejson.dumps(a))

