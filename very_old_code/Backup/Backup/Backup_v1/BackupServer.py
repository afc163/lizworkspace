#!/usr/bin/python
#coding:utf-8

from socket import *
import os
from datetime import datetime
import time

class BackupServer(object):
    """服务器端
    """
    def __init__(self, destDir):
        """
        @param destDir: 目标备份文件夹，源文件按照原路径组织进该目标文件夹，不以'/'结尾的绝对路径，当值为''时表示路径和对方一致，此时有点危险，会把两机子上的同名文件覆盖掉。
        """
        if destDir.endswith('/'):
            destDir = destDir[:-1]
        self.destDir = destDir
        self.destIP = ''#'192.168.0.57'
        self.destPort = 7878
        
        timestring = time.strftime("%a_%b_%d_%H_%M_%S_%Y", time.strptime(time.ctime()))
        self.logFile = open('log_'+timestring, 'w')
        # indexFile保存源文件到目的文件的一一对应关系
        self.indexFile = open('index_'+timestring, 'w')
        try:
            # 创建Socket对象，绑定并监听
            self.server = socket(AF_INET, SOCK_STREAM)
            self.server.bind((self.destIP, self.destPort))
            self.server.listen(5)
            self.logFile.write('%s\n创建Socket对象，绑定并监听 IP:%s Port:%s\n\n' % (datetime.ctime(datetime.now()), self.destIP, self.destPort))
        except Exception,e:
            self.logFile.write('%s\nRaise Exception %s\n\n' % (datetime.ctime(datetime.now()), e))
            raise
            
    def Do(self):
        while 1:
            try:
                client, addr = self.server.accept()
                #todo: 多个线程
                self.logFile.write('%s\n连接一个客户端来自ip: %s\n\n' % (datetime.ctime(datetime.now()), addr))
                rdata = client.recv(512)
                if rdata.startswith('FILENAME:'):
                    tindex = rdata.rfind('TIME:')
                    eindex = rdata.rfind('END')
                    
                    filename = rdata[9:tindex]
                    mtime = float(rdata[tindex+5:eindex])
                    
                    destabspath = self.destDir+os.path.dirname(filename)
                    if not os.path.isdir(destabspath):
                        os.makedirs(destabspath)
                    self.absfn = os.path.abspath(destabspath+os.sep+os.path.basename(filename))
                    # 如果已经存在该文件，判断是否更新，是的话覆盖，不是的话，放弃？这边是有问题的
                    if os.path.isfile(self.absfn):
                        client.send('PASS')
                        print 'pass', self.absfn
                        continue
                        mmtime = os.stat(self.absfn).st_mtime
                        print mmtime, mtime
                        if mmtime == mtime:
                            client.send('PASS')
                            self.logFile.write('%s\n存在未更新文件：%s\n\n' % (datetime.ctime(datetime.now()), self.absfn))
                            continue
                    print 'start'
                    client.send('STAR')
                    self.file = open(self.absfn, 'wb')
                    while 1:
                        rdata = client.recv(1024)
                        if not rdata:
                            break
                        self.file.write(rdata)
                    self.logFile.write('%s\n保存一个文件：%s\n\n' % (datetime.ctime(datetime.now()), self.absfn))
                    self.indexFile.write('%s->%s\n' % (filename, self.absfn))
                    print 'save file: %s\n' % self.absfn
                    self.file.close()
                    client.close()
                    self.logFile.write('%s\n关闭客户端来自ip: %s\n\n' % (datetime.ctime(datetime.now()), addr))
            except KeyboardInterrupt:
                # 关闭连接
                self.server.close()
                self.logFile.write('%s\n关闭服务器端\n\n' % datetime.ctime(datetime.now()))
                self.logFile.close()
                self.indexFile.close()
                exit(0)
            except Exception, e:
                print 'Exception: %s' % e
                # 关闭连接
                self.server.close()
                self.logFile.write('%s\n关闭服务器端\n\n' % datetime.ctime(datetime.now()))
                self.logFile.close()
                self.indexFile.close()
        
if __name__ == '__main__':
    mc = BackupServer('/home/shengyan/backup')
    mc.Do()
