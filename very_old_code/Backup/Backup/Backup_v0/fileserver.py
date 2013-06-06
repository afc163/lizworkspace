#!/usr/bin/python
#coding:utf-8

from socket import *
import os

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
            server.listen(1)
            try:
                client, addr = server.accept()
                filename = client.recv(32)
                file = open(filename, 'wb')
                print filename
                #file = os.open('avv', os.O_WRONLY|os.O_CREAT|os.O_EXCL)
                while 1:
                    data = client.recv(1024)
                    if not data:
                        break
                    file.write(data)
                    #os.write(file, data)
                file.close()
                #os.close(file)
                client.close()
            except Exception, e:
                print 'Exception: %s' % e
            # 关闭连接
            server.close()
        except Exception,e:
            print 'Exception: %s' % e
            
        
if __name__ == '__main__':
    mc = MyServer()
    mc.Do()
