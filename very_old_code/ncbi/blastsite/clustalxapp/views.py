#coding:utf-8
from django.shortcuts import render_to_response
from django.http import HttpResponseRedirect
import os

def show(request):
    """
    """
    try:
        stdin, stdout, stderr = os.popen3(os.path.abspath(os.path.abspath(__file__)+"/../../../clustalx-2.0.10/clustalx2"))
        #print stdout.readlines(), stderr.readlines()
        return HttpResponseRedirect("/main/")
        return render_to_response("clustalx.html", {"result":stdout.readlines()+stderr.readlines()})
    except Exception,e:
        return render_to_response("clustalx.html", {"result":e})
    
    

