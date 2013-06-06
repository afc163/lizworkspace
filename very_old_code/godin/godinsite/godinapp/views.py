# Create your views here.
from django.http import HttpResponse
from django.http import HttpResponseRedirect
from django.shortcuts import render_to_response
import os

def show(request):
    """
    """
    return render_to_response("show.html")

def start(request):
    searchname=request.POST.get("searchname", None)
    if searchname is None:
        searchname = ''
    else:
        searchname = searchname.strip()
    try:
        if searchname is None or searchname == '':
            return HttpResponseRedirect("/godin/")
        import os
        if os.system("python /home/shengyan/workspace/lizworkspace/godin/new/godin.py") == 0:
            if os.system("dot -Tpng /home/shengyan/workspace/lizworkspace/godin/new/dot/show_godin.dot -o /home/shengyan/workspace/lizworkspace/godin/godinsite/media/img/"+searchname+".png") == 0:
                picture_path = searchname+".png"
                return render_to_response("show.html", {"pic":picture_path})
            else:
                raise
        else:
            raise
    except Exception, e:
        print "In Function Start: ", e
        return HttpResponse("Generate PNG Error")
