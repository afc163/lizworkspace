#!/usr/bin/python
#coding:utf-8

from socket import *
import os

class FileClient(object):
    """客户端
    """
    def __init__(self):
        pass
    def Do(self):
        try:
            # 创建Socket对象
            client = socket(AF_INET, SOCK_STREAM)
            client.connect(('192.168.0.57', 7878))
            file = open('tt.mp3', 'rb')
            #file = os.open('client.py', os.O_RDONLY|os.O_EXCL)
            while 1:
                data = file.readline(1024)
                #data = os.read(file, 1024)
                if not data:
                    break
                client.send(data)
            file.close()
            #os.close(file)
            client.close()
        except Exception,e:
            print 'Exception: %s' % e
            
        
if __name__ == '__main__':
    mc = FileClient()
    mc.Do()
