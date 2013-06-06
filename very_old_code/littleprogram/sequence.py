#!/usr/bin/python
#coding:utf-8

"""sequence.py
Aprori算法
"""

ALL = []
RESULT = {}
THRESHOLD = 3

def aprori():
    """
    """
    fp = open('example')
    for line in fp.readlines():
        linelist = line.split(' ')
        tmplist = []
        for l in linelist:
            l = l.strip()
            tmplist.append(l)
            if RESULT.has_key(l):
                RESULT[l] += 1
            else:
                RESULT[l] = 1
        ALL.append(tmplist)

def item1():
    """
    """
    for k in RESULT.keys():
        if RESULT[k] < THRESHOLD:
            del RESULT[k]
    print RESULT


if __name__ == "__main__":
    aprori()
    item1()
    
    