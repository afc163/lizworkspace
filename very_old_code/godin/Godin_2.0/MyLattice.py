#!/bin/python
# coding: utf-8

""" Permutation
@author: Lizzie
@license: ...
@contract: shengyan1985@gmail.com
@see: ...
@version:0.1
"""

import os
import sys

class Permutation(object):
    """ 产生组合
    @todo: 
    """
    def __init__(self, total=10):
        """初始化
        @param total: 所有要排列的数字个数
        @type total: integer
        """
        self.totalNum = total
        
    def perm(self, i):
        """对于total个数选取i个数进行选组合
        @param i: 实际的数字个数
        @type i: integer
        @todo: 产生Pmn选排列，而不是组合，是一种组合对应有n!个排列
        """
        if i == 0:
            yield None
            return
        if i == self.totalNum:
            yield range(self.totalNum)
            return
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

class MyLattice(object):
    """形式概念格类
    some...
    """
    def __init__(self, obj, att):
        """初始化
        obj和att可以省略，所以主要有概念集合，底概念，顶概念，层次
        """
        self.conceptSet = []                    # 概念的集合
        self.layer = {}
        
        self.object_list = obj
        self.attribute_list = att
        
    def addConcept(self, newconcept):
        """增加新的概念到已有概念集中"""
        self.conceptSet.append(newconcept)
        
    def addEdge(self, parent_cpt, child_cpt):
        """增加新边到已有边集中并更新层次"""
        parent_cpt.child_list.append(child_cpt)
        child_cpt.parent_list.append(parent_cpt)
        
            
    def deleteEdge(self, parent_cpt, child_cpt):
        """删除边"""
        parent_cpt.child_list.remove(child_cpt)
        child_cpt.parent_list.remove(parent_cpt)
        
        #todo: 层次的更新?
                


    def __str__(self):
        selfstring = 'Concept------>>\n'
        for cpt in self.conceptSet:
            selfstring += 'Concept %d: {' % cpt.conceptID
            for obj in cpt.extent:
                selfstring += ' %s ' % self.object_list[obj]
            selfstring += '}, {'
            
            for i in cpt.intent:
                selfstring += ' %s ' % self.attribute_list[i]
            selfstring += '}\n    his children is: '

            for one in cpt.child_list:
                selfstring += ' %d ' % one.conceptID    
            selfstring += '\n    his parent is: '

            for one in cpt.parent_list:
                selfstring += ' %d ' % one.conceptID    
            selfstring += '\n'

        for onelayer_key in self.layer.keys():
            tmp = 'concept is:'
            for cpt in self.layer[onelayer_key]:
                tmp += '%d ' % cpt.conceptID
            selfstring += "layer %d: \n    %s\n" % (onelayer_key, tmp)
        return selfstring+str(TEST)
    
class Concept:
    """ 形式概念，id，外延，内涵，孩子列表，父列表，所在层号 """
    conceptNUM = 0

    def __init__(self, extent=set(), intent=set()):
        Concept.conceptNUM += 1
        self.conceptID = Concept.conceptNUM
        self.extent = extent
        self.intent = intent
        self.child_list = []
        self.parent_list = []
        self.layer_num = 0

    def addIntent(self, intentSet):
        self.intent = self.intent.union(intentSet)

    def addExtent(self, extentSet):
        self.extent = self.extent.union(extentSet)

    def __str__(self):
        str_child = 'his children is: '
        for one in self.child_list:
            str_child += '%d, ' % one.conceptID
        return 'Concept %d: (%s), (%s)\n%s\nhis layer is: %d' % (self.conceptID, self.extent, self.intent, str_child[:-2], self.layer_num)
        
from FormalContext import *

if __name__ == '__main__':
    
    fc = read_FC_file()
    
    dic = {}
    p = Permutation(fc.object_num)
    for i in range(fc.object_num+1):
        for j in p.perm(i):
            if not j:
                dic[0] = 'all'
            else:
                time = get_time(j)
    #for j in p.perm(4):
    #    print j