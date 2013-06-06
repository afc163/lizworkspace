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
        #self.totalNum = len(all)
        self.totalNum = all
        #self.all = all
        
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
#TT = 3
#if TT == 1:
#    Begin = 4
#    result = []
#    for i in xrange(1, Begin):
#        result.append([])
#        for j in Permutation(i).perm_all():
#            result[i-1].append(tuple(j))
#        print result[i-1]

#    pickle.dump(result, open('permutation.dump', 'w'))
#    print 'done'
#elif TT == 2:
#    Num = 100
#    result = []
#    for j in Permutation(Num).perm_all():
#        result.append(tuple(j))
#        print j
#    pickle.dump(result, open('permutation_100.dump', 'w'))
#    print 'done'
    #for i in pickle.load(open('permutation_100.dump')):
    #    print i
#elif TT == 3:
#    tmp = pickle.load(open('permutation.dump'))
#    Next = 16
#    for i in xrange(len(tmp), Next):
#        print i
#        tmp.append([])
#        for j in Permutation(i+1).perm_all():
#            tmp[i].append(tuple(j))
#    pickle.dump(tmp, open('permutation.dump', 'w'))
#    print 'done'
    
#for i in pickle.load(open('permutation_16.dump')):
#    print i
