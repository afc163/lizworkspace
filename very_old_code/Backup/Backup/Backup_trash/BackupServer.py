#!/usr/bin/python
#coding:utf-8

from socket import *
import os
from datetime import datetime
import time
import threading

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
        try:
            # 创建Socket对象，绑定并监听
            self.server = socket(AF_INET, SOCK_STREAM)
            self.server.bind((self.destIP, self.destPort))
            self.server.listen(5)
            self.logFile.write('%s\n创建Socket对象，绑定并监听 IP:%s Port:%s\n\n' % (datetime.ctime(datetime.now()), self.destIP, self.destPort))
        except Exception,e:
            self.logFile.write('%s\nRaise Exception %s\n\n' % (datetime.ctime(datetime.now()), e))
            raise

    def start(self, client, addr):
            rdata = client.recv(512)
            if rdata.startswith('FILENAME:'):
                findex = rdata.rfind('FLAG:')
                eindex = rdata.rfind('END')
                    
                filename = rdata[9:findex]
                flag = rdata[findex+5:eindex]
                    
                destabspath = self.destDir+os.path.dirname(filename)
                if not os.path.isdir(destabspath):
                    os.makedirs(destabspath)
                absfn = os.path.abspath(destabspath+os.sep+os.path.basename(filename))
                # 如果已经存在该文件，判断是否?
                if flag == 'C' and os.path.isfile(absfn) and os.stat(absfn).st_size:
                    client.send('PASS')
                    print 'pass: ', absfn
                    return
                elif flag == 'C' or flag == 'U':
                    print 'start'
                    client.send('STAR')
                    file = open(absfn, 'wb')
                    while 1:
                        rdata = client.recv(1024)
                        if not rdata:
                            break
                        file.write(rdata)
                    self.logFile.write('%s\n保存一个文件：%s\n\n' % (datetime.ctime(datetime.now()), absfn))
                    print 'save file: %s\n' % absfn
                    file.close()
                    client.close()
                    self.logFile.write('%s\n关闭客户端来自ip: %s\n\n' % (datetime.ctime(datetime.now()), addr))
                elif flag == 'D':
                    client.send('DELT')
                    if os.path.exists(absfn):
                        print 'delete file: %s' % absfn
                        os.system('mv %s %s_delete' % (absfn, absfn))
                else:
                    pass
    def Do(self):
        while 1:
            try:
                client, addr = self.server.accept()
                self.logFile.write('%s\n连接一个客户端来自ip: %s\n\n' % (datetime.ctime(datetime.now()), addr))
                # 多个线程
                t = threading.Thread(target=self.start, name='server', args=(client, addr))
                t.setDaemon(1)
                t.start()
            except KeyboardInterrupt:
                # 关闭连接
                self.server.close()
                self.logFile.write('%s\n关闭服务器端\n\n' % datetime.ctime(datetime.now()))
                self.logFile.close()
                exit(0)
            except Exception, e:
                print 'Exception: %s' % e
                # 关闭连接
                self.server.close()
                self.logFile.write('%s\n关闭服务器端\n\n' % datetime.ctime(datetime.now()))
                self.logFile.close()
        
if __name__ == '__main__':
    mc = BackupServer('/home/shengyan/backup2')
    mc.Do()
