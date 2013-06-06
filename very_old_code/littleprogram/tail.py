#!/bin/python
#coding:utf-8

import time
import urllib

def tail_f():
    file = urllib.urlopen(r"file:/home/shengyan/workspace/littleprogram/test")
    #file = urllib.urlopen("http://www.baidu.com/")
    while 1:
        #where = file.tell()
        content = file.readline()
        if not content:
            time.sleep(1)
            #file.seek(where)
        else:
            print content

if __name__ == '__main__':
    tail_f()
