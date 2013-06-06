#!/usr/bin/python
#coding=utf-8

not1 = lambda f: lambda x, f=f : not f(x)
not2 = lambda f: lambda x, y, f=f: not f(x, y)
bind1st = lambda f, x: lambda y, p=f: p(x, y)
bind2nd = lambda f, x:lambda y, p=f: p(y, x)
