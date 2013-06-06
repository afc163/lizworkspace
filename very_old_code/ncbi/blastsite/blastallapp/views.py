#coding:utf-8
from django.shortcuts import render_to_response
from django.http import HttpResponse
import os

QUERYDIR = "/tmp/blastquery/"
DBDIR = "/usr/local/blast/wwwblast/db/"

def show(request):
    """显示所有可用站点
    """
    return render_to_response("blastall.html")
    
def query(request):
    """使用blastall工具查询并返回结果
    """
    program = request.POST.get("program",None)
    database = request.POST.get("database",None)
    sequence = request.POST.get("sequence",None)
    seqfile = request.FILES.get("seqfile", None)
    if type(sequence) == type(''):
        sequence = sequence.strip()
    if not sequence and seqfile is None:
        return render_to_response("blastall_result.html", {"result": "No Input Sequence"})
    if not program:
        program = "blastp"
    if not database:
        database = "testdb"
    # 先把查询的序列保存到文件中
    filename = 'defaultquery'
    content = sequence
    if seqfile:
        filename = seqfile["filename"]
        content = seqfile["content"]
    filename = QUERYDIR + filename
    try:
        open(filename, 'w').write(content)
    except IOError, e:
        return render_to_response("blastall_result.html", {"result": e})
    blastall = "blastall -p "+program+" -d "+DBDIR+database+" -i "+filename
    stdin, stdout, stderr = os.popen3(blastall)
    result = "<br/>".join(stdout.readlines())
    return render_to_response("blastall_result.html", {'result': result})
    
