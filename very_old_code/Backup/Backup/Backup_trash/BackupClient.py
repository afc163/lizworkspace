#!/usr/bin/python
#coding:utf-8

from socket import *
import os

class BackupClient(object):
    """客户端
    """
    def __init__(self, sourcefilename, flag='C'):
        """备份指定文件
        @param sourcefilename: 指定备份文件名字
        """
        self.source = sourcefilename
        self.flag = flag
        self.destIP = '192.168.0.57'
        self.destPort = 7878
        try:
            # 创建Socket对象
            self.client = socket(AF_INET, SOCK_STREAM)
            self.client.connect((self.destIP, self.destPort))
        except Exception,e:
            raise
            
    def Do(self):
        """连接并尝试发送文件数据，先发送文件名，然后发送文件数据
        """
        try:
            # 先发送文件名，文件更新时间，再发送文件内容
            head = 'FILENAME:%sFLAG:%sEND' % (self.source, self.flag)
            head = head+'#'*(512-len(head))
            # 头信息中512长度，发送后等待接受字符串
            self.client.send(head)
            rdata = self.client.recv(4)
            # 接受到的若是'PASS'表示已经存在目的文件，不更新，直接返回
            if rdata == 'PASS' or rdata == 'DELT':
                pass
            elif rdata == 'STAR':
                # 否则传送文件数据
                file = open(self.source, 'rb')
                while 1:
                    data = file.readline()
                    if not data:
                       break
                    self.client.send(data)
                file.close()
            else:
                pass
            self.client.close()
        #except OSError,e:
        #    # os读取错误
        #    print 'OSERROR',e
        #    raise
        except Exception,e:
            #print 'Exception: %s' % e
            raise
    def DoD(self):
        """测试resv_into，sendall
        """
        try:
            # 先发送文件名，文件更新时间，再发送文件内容
            self.client.send(self.source)
            file = open(self.source, 'rb')
            while 1:
                data = file.readline()
                if not data:
                   break
                self.client.send(data)
            file.close()
            self.client.close()
        except Exception,e:
            #print 'Exception: %s' % e
            raise        
if __name__ == '__main__':
    mc = BackupClient(os.path.abspath('aa'))
    mc.DoD()
