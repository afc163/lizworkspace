#!/bin/python
#coding:utf-8

import random
import time

def tail_w():
    file = open("test", "a")
    while 1:
        afloat = random.random()
        print afloat
        file.writelines('lines: %s\n' % afloat)
        file.flush()
        time.sleep(2)

if __name__ == '__main__':
    tail_w()