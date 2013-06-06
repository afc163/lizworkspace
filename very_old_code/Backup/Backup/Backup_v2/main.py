#!/usr/bin/python
#coding:utf-8

"""客户端入口程序
    @version: 0.1
    @author: lizzie
    @contact: shengyan1985@gmail.com
    @todo: 
"""

import os
from BackupClient import BackupClient
import socket
import sys
from optparse import OptionParser
from head import STOP_LIST, INDEX_FILE, WATCH_FILE

def Backup(one, flag='C'):
    """备份某个文件，某个文件符合以下几个条件就放弃备份：
    1，临时文件（以~结尾）
    2，后缀名符合STOP_LIST=['.pyc', '.lrc', '.tmp', '.out', ...]
    3，svn版本文件，也就是路径中包含.svn的文件
    4，.so文件，链接文件类型，错误的链接文件产生异常
    @param one: 文件名
    @param flag: 'C'表示覆盖，'U'表示更新，'D'表示删除，C和U的区别在于。。。
    """
    if one.endswith('~') or '.svn' in one:
        return True
    dotindex = one.rfind('.')
    if dotindex:
        dotname = one[dotindex:]
        if dotname in STOP_LIST:
            return True
    try:
        BackupClient(one, flag).Do()
    except Exception,e:
        try:
            BackupClient(one, flag).Do()
        except socket.error,(en, em):
            print en, em
            #todo: 判断多种socket错误或者直接输出提示后退出程序
            if en == 111: # 这里的数字可以用errno代替
                print em, 'and exit the program'
                exit(-1)
        except Exception,e:
            print type(e), e
            # 这里的错误一般是OS I/O读取错误
            print '备份文件 %s 失败！' % one
            return False
    return True  
                
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

def get_Index():
    """读取WATCH_FILE文件，其中包含所监控的路径和文件，返回所有存在路径或文件的列表。
    @return: 存在绝对路径文件项目
    """
    if os.path.isfile(WATCH_FILE):
        fp = open(WATCH_FILE)
        while 1:
            line = fp.readline()
            if not line:
                break
            line = os.path.abspath(line.strip())
            if os.path.exists(line):
                yield line
            else:
                print '警告: %s 不存在！' % line
        fp.close()

def get_Complete(opt, value, parser, *args, **kwargs):
    """用于全部拷贝
    """
    for item in get_Index():
        print '正在处理 ... ', item
        backup_All(item)

def get_Update(opt, value, parser, *args, **kwargs):
    """获得更新，删除的文件，删除仅是给文件名加上delete标记
    """
    try:
        update_file_list = []
        delete_file_list = []
        fp = open(INDEX_FILE)
        while 1:
            line = fp.readline()
            if not line:
                break
            fn = line[2:].strip()
            #@note: 由于INDEX_FILE文件中记录的问题，导致很多重复文件都被记录下来，所以这里要处理这些重复并判断是否确切存在
            if line.startswith("U:"):
                if os.path.isfile(fn) and fn not in update_file_list:
                    update_file_list.append(fn)
            elif line.startswith("D:"):
                if fn not in delete_file_list:
                    delete_file_list.append(fn)
        fp.close()
        # 处理了更新INDEX后，若备份失败时，还需将记录重新写回INDEX中
        fp = open(INDEX_FILE, 'w')
        for one in update_file_list:
            print '更新文件: ', one
            if not Backup(one, 'U'):
                fp.write('U:%s\n' % one)
                fp.flush()
        for one in delete_file_list:
            print '删除文件: ', one
            if not Backup(one, 'D'):
                fp.write('D:%s\n' % one)
                fp.flush()
        fp.close()
        print '更新结束'
    except Exception,e:
        print e

def get_usage():
    usage = """
        %prog [options]
        """
    return usage

def get_version():
    version = """
        %prog 0.1
        """
    return version    

def main():
    argv = sys.argv
    parser = OptionParser(usage=get_usage(), version=get_version())
    parser.add_option("-u", "--update",
                        help="Update Files",
                        action="callback", 
                        callback=get_Update)
    parser.add_option("-c", "--complete",
                        help="Completely Copy",
                        action="callback", 
                        callback=get_Complete)

    (options, args) = parser.parse_args(argv[1:])
    #todo: 输出提示信息
    
if __name__ == '__main__':
    main()
