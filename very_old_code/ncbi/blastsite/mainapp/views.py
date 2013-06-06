#coding:utf-8
from django.shortcuts import render_to_response
from django.http import HttpResponse
import os
import MySQLdb

class MysqlExcuter(object):
    def __init__(self, host='localhost', db='Influenza_A_seq', user='root', passwd='lizzie1985', unix_socket="/tmp/mysql.sock"):
        self.host = host
        self.db = db
        self.user = user
        self.passwd = passwd
        try:
            self.connect = MySQLdb.connect(host=self.host, 
                                            db=self.db, 
                                            user=self.user, 
                                            passwd=self.passwd,
                                            )
            self.cursor = self.connect.cursor()
        except Exception, e:
            raise
    
    def do_excute(self, statment, paramter=()):
        try:
            self.cursor.execute(statment, paramter)
            self.result = self.cursor.fetchall()
        except Exception, e:
            raise
    
    def close_connect(self):
        try:
            self.cursor.close()
            self.connect.close()
        except Exception, e:
            raise

def list_all(request):
    """显示所有可用站点
    """
    return render_to_response("index.html")

def _add(name, seq):
    result = False
    try:
        excute = None
        excute = MysqlExcuter()
        excute.cursor.execute("insert into test(name, seq) values(%(name)s, %(seq)s)", dict(name=name, seq=seq))
        result = True
    except Exception, e:
        print e
        raise
    finally:
        if excute:
            excute.close_connect()
        return result
        
def add(request):
    """增加一条新的序列
    """
    if request.method == "POST":
        name = request.POST.get("final_name", None)
        seq = request.POST.get("seq", None)
        if name and seq:
            if _add(name.strip(), seq.strip()):
                return render_to_response("add.html", {'msg': '插入成功'})
            else:
                return render_to_response("add.html", {'msg': '插入失败, 请重新尝试'})
        else:
            return render_to_response("add.html", {'msg': '序列名字或内容为空, 请重新插入'})
    elif request.method == "GET":
        return render_to_response("add.html")
    else:
        pass

def _search(searchname):
    try:
        excute = None
        excute = MysqlExcuter()
        excute.do_excute("select * from test where name REGEXP %s", searchname)
        return excute.result
    except Exception, e:
        raise
    finally:
        if excute:
            excute.close_connect()

def query(request):
    """查询处理
    """
    if request.method == "POST":
        query_type = request.POST.get("query_type", None)
        keyword = request.POST.get("keyword", None)
        if keyword:
            keyword = keyword.strip()
        searchstring = None
        if query_type == "year":
            searchstring = "%s, Influenza A virus" % keyword[-2:]
        elif query_type == "code":
            searchstring = "/%s/.*, Influenza A virus" % keyword
        elif query_type == "segment":
            searchstring = "segment %s " % keyword
        elif query_type == "place":
            if len(keyword) == 2:
                searchstring = "/%s/.*/.*, Influenza A virus" % keyword  # 分离地简写
            else:
                searchstring = ", Influenza A virus\(A/Quail/%s/" % keyword  # 完整分离地名
        else:
            pass
        if searchstring:
            result = _search(searchstring)
            string = ''
            for i in result:
                string += "%s\n%s\n\n" % i
            filename = "query_%s.txt" % keyword
            #open(os.path.abspath(os.path.abspath(__file__)+"/../../media/files/%s" % filename), 'w').write(string)
            open("/tmp/%s" % filename, 'w').write(string)
            return render_to_response("query.html", {
                                      'query_type':query_type,
                                      'result': result,
                                      'keyword': keyword, 
                                      'filename': filename,})
    elif request.method == "GET":
        return render_to_response("query.html", {'query_type':'code'})
    else:
        pass

def download(request, filename):
    response = HttpResponse(mimetype='text/plain')
    response['Content-Disposition'] = 'attachment; filename=%s' % filename
    
    response.write(open("/tmp/%s" % filename).read())
    return response

if __name__ == "__main__" :
    try:
        excute = MysqlExcuter()
        excute.do_excute("select * from test")
        for r in excute.result:
            print r
    except Exception, e:
        print e
    finally:
        excute.close_connect()

