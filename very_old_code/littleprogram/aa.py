#!/usr/bin/env python
#coding=utf-8

def shownumber(num=None):
    def get_nl(num):
        i = 0        nl = []        for j in xrange(len(num)-1, -1, -1):            if i==2:                i = 0                nl.append(num[j:j+3])            else:                i += 1        if i <> j:            nl.append(num[j:i])        nl.reverse()
        return ','.join(nl)
    if not num:
        return '0.00'
    if type(num) == type(0.00):
        num = str(num)
        dot_index = num.find('.')
        if len(num[dot_index+1:])==1:
            num += '0'
        elif len(num[dot_index+1:])>2:
            num = num[:dot_index+3]
        return get_nl(num[:dot_index])+num[dot_index:]
    elif type(num) == type(0):
        return get_nl(str(num))+'.00'
    return num


print shownumber(1433534553.1)
print shownumber(143353453.1)
print shownumber(1433553.1)
print shownumber(143353.1)
print shownumber(14553.1)
print shownumber(1453.1)
print shownumber(153.1)
print shownumber(13.1)
print shownumber(3.112)
print shownumber(13)
