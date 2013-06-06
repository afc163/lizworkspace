#!/bin/python
# coding: utf-8

""" Permutation
@author: shengyan
@license: ...
@contract: shengyan1985@gmail.com
@see: ...
@version:0.1
"""

import os
import sys
import pickle

class Permutation(object):
    """ 产生组合
    """
    def __init__(self, all=[]):
        """初始化
        @param total: 所有要组合的数字个数
        @type total: integer
        """
        self.totalNum = all
        
    def perm(self, i):
        """对于totalNum个数选取i个数进行选组合
        @param i: 实际的数字个数
        @type i: integer
        """
        first = [] # 初始的组合
        end = [] # 最终的组合
        for j in range(i):
            first.append(j)
            end.append(self.totalNum-i+j)
    
        change = i-1 # 待改变的位置
        while 1:
            yield first
            if first[change] <> end[change]:
                first[change] += 1
            else:
                change -= 1
                if change<0:
                    break
                first[change] += 1
                newchange = change
                for h in range(change+1, i):
                    first[h] = first[h-1]+1
                    if first[h] <> end[h]:
                        newchange = h
                change = newchange
        
    def perm_all(self):
        """产生全组合，不包括空集但包括全集
        """
        for i in xrange(1, self.totalNum):
            for t in self.perm(i):
                yield t
        yield range(self.totalNum)