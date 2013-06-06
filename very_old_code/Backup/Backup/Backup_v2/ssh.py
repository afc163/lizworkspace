#!/usr/bin/python
#coding:utf-8

"""远程启动服务脚本
    @version: 0.1
    @author: lizzie
    @contact: shengyan1985@gmail.com
"""

import os
import sys
from socket import *
localPortNo = 8000
maxTries = 10

def createTCPSocketSSH(remoteHostname, remotePort=22, localPort=-1):
    if localPort == -1:
        localPort = localPortNo
        localPortNo += 1
    tryNo = 1
    while 1:
        command = "ssh -f -g -A -X -N -T -L%d:localhost:%d %s\n" % (localPort, remotePort, remoteHostname)
        result = os.system(command)
        if result == 0:
            break
        localPort = localPort + 1
        tryNo = tryNo + 1
        if tryNo == maxTries:
            os.exit(1)
    s = socket(AF_INET, SOCK_STREAM)
    s.connect(("localhost", localPort))
    return s

def ssh_Connect():
    pass
if __name__ == '__main__':
    ssh_Connect()
