#!/usr/bin/python
#coding:utf-8

"""服务器端入口程序
    @version: 0.1
    @author: lizzie
    @contact: shengyan1985@gmail.com
    @todo: 
"""

from socket import *
import os
from datetime import datetime
import time
import threading

from head import DESTDIR, PORT, SERVERIP

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
        self.destIP = ''#SERVERIP
        self.destPort = PORT
        
        timestring = time.strftime("%a%b%d%H%M%S%Y", time.strptime(time.ctime()))
        self.logFile = open('log_'+timestring, 'w')
        try:
            # 创建Socket对象，绑定并监听
            self.server = socket(AF_INET, SOCK_STREAM)
            self.server.bind((self.destIP, self.destPort))
            self.server.listen(5)
            self.logFile.write('%s\n创建Socket，绑定并监听 IP:%s Port:%s\n\n' % (datetime.ctime(datetime.now()), self.destIP, self.destPort))
        except Exception,e:
            print e
            exit(-1)
            
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
                # 如果标记是C，并且文件存在并大小不为0字节，则放弃传送，用于complete
                if flag == 'C' and os.path.isfile(absfn) and os.stat(absfn).st_size:
                    client.send('PASS')
                    print '忽略: ', absfn
                    return
                # 否则如果文件不存在或为0字节，或者有更新标记，则传送数据
                elif flag == 'C' or flag == 'U':
                    client.send('STAR')
                    f = open(absfn, 'wb')
                    while 1:
                        rdata = client.recv(1024)
                        if not rdata:
                            break
                        f.write(rdata)
                    self.logFile.write('%s\n保存一个文件：%s\n\n' % (datetime.ctime(datetime.now()), absfn))
                    print '保存: %s\n' % absfn
                    f.close()
                # 删除操作，不是真正的删除，只是重命名delete
                elif flag == 'D':
                    client.send('DELT')
                    if os.path.exists(absfn):
                        print '删除: %s' % absfn
                        self.logFile.write('%s\n删除一个文件：%s\n\n' % (datetime.ctime(datetime.now()), absfn))
                        os.system('mv %s %s_delete' % (absfn, absfn))
                else:
                    pass
            client.close()
            self.logFile.write('%s\n关闭客户端来自ip: %s\n\n' % (datetime.ctime(datetime.now()), addr))

    def Do(self):
        print 'Press Ctrl+C Stop...'
        while 1:
            try:
                client, addr = self.server.accept()
                self.logFile.write('%s\n连接一个客户端来自ip: %s\n\n' % (datetime.ctime(datetime.now()), addr))
                # 多个线程
                t = threading.Thread(target=self.start, name='server', args=(client, addr))
                t.setDaemon(1)
                t.start()
            except KeyboardInterrupt:
                # Ctrl+C关闭连接
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
    BackupServer(DESTDIR).Do()
