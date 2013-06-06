#!/usr/bin/python
#coding:utf-8

def ifthenelse(a, b, c):
    if a:
        return b
    else:
        return c

def is_ver(major, minor):
    from sys import version_info
    ver = version_info
    return ifthenelse(ver[0]>major or (ver[0]==major and ver[1]>=minor), True, False)

def is_ver_23():
    return is_ver(2, 3)
