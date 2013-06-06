#!/usr/bin/python
#coding:utf-8

import os
from BackupClient import BackupClient
import socket

STOP_LIST = ['.pyc', '.lrc', '.tmp', '.out']
FL = open('flog', 'w')

def Backup(one):
    """备份某个文件，某个文件符合以下几个条件就放弃备份：
    1，临时文件（以~结尾）
    2，后缀名符合stoplist=['.pyc', '.lrc', '.tmp', '.out', ...]
    3，svn版本文件，也就是路径中包含.svn的文件
    4，.so文件，链接文件类型，错误的链接文件产生异常
    """
    if one.endswith('~') or '.svn' in one:
        return
    dotindex = one.rfind('.')
    if dotindex:
        dotname = one[dotindex:]
        if dotname in STOP_LIST:
            return
    try:
        BackupClient(one).Do()
    except Exception,e:
        try:
            BackupClient(one).Do()
        except socket.error,(en, em):
            print (en, em)
            if en == 111:
                print em, 'and exit the program'
                exit(-1)
        except Exception,e:
            print type(e), e
            print 'Back up file %s failed!' % one
            FL.write('Back up file %s failed!\n' % one)
                
def backup_All(index):
    """备份index项目
    @param index: 列表中项目是文件或目录(绝对路径)
    @type index: string
    @return: 返回True(正确备份) or False(备份失败有异常)
    """
    if os.path.isfile(index): # 为一文件时
        Backup(index)
    else:  # 为一目录时
        for r, d, f in os.walk(index):
            files = [ os.path.join(r, name) for name in f ]
            files.sort()
            for afile in files:
                Backup(afile)

def get_Index(filename='config'):
    """读取config文件，其中包含所监控的路径和文件，返回所有存在路径或文件的列表。
    @param filename: config文件名，默认为config
    @type filename: string
    @return: 存在绝对路径文件项目
    """
    if os.path.isfile(filename):
        fp = open(filename)
        while 1:
            line = fp.readline().strip()
            if not line:
                break
            line = os.path.abspath(line.strip())
            if os.path.exists(line):
                yield line
            else:
                print 'Warning: %s is not exist!'

if __name__ == '__main__':
    for item in get_Index():
        print 'handling ... ', item
        backup_All(item)
