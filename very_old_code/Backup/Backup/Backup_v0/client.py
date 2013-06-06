#!/usr/bin/python
#coding:utf-8

from socket import *

class MyClient(object):
    """客户端
    """
    def __init__(self):
        pass
    def Do(self):
        try:
            # 创建Socket对象
            client = socket(AF_INET, SOCK_STREAM)
            client.connect(('192.168.0.57', 7878))
            client.send('hi~')
            # 接收数据后关闭连接
            rdata = client.recv(1024)
            client.close()
            print 'receive date: %s' % rdata
        except Exception,e:
            print 'Exception: %s' % e
            
        
if __name__ == '__main__':
    mc = MyClient()
    mc.Do()
