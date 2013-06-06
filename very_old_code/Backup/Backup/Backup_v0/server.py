#!/usr/bin/python
#coding:utf-8

from socket import *

class MyServer(object):
    """服务器端
    """
    def __init__(self):
        pass
    def Do(self):
        try:
            # 创建Socket对象，绑定并监听
            server = socket(AF_INET, SOCK_STREAM)
            server.bind(('', 7878))
            server.listen(2)
            while 1:
                try:
                    client, addr = server.accept()
                    data = client.recv(1024)
                    print data
                    client.send('>>%s' % data)
                    client.close()
                except Exception, e:
                    print 'Exception: %s' % e
                    break
            # 关闭连接
            server.close()
        except Exception,e:
            print 'Exception: %s' % e
            
        
if __name__ == '__main__':
    mc = MyServer()
    mc.Do()
