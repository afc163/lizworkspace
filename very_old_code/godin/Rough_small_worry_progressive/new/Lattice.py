#!/usr/bin/python
#coding=utf-8

"""Lattice.py: 形式概念格及其相关操作
@author: Lizzie
@license: ...
@contact: shengyan1985@gmail.com
@version: 0.2

@attiontion:仍在继续完善中
@bug:
@warning:
"""

from Error import *
#from CMN import Permutation
import pickle

class Lattice(object):
    """形式概念格类
    some...
    """
    def __init__(self, fc, b1, b2):
        """初始化
        obj和att可以省略，所以主要有概念集合，底概念，顶概念，层次
        """
        self.conceptSet = []                    # 概念的集合
        self.supConcept = Concept()
        self.addConcept(self.supConcept)
        self.infConcept = Concept()
        self.addConcept(self.infConcept)

        self.formalcontext = fc
        self.b1 = b1
        self.b2 = b2
        self.perm = {}

    def addConcept(self, newconcept):
        """增加新的概念到已有概念集中"""
        self.conceptSet.append(newconcept)

    def get_power(self, AB):
        """给出幂集
        """
        AB = list(AB)
        len_AB = len(AB)
        tmp_perm = None
        if self.perm.has_key(len_AB):
            tmp_perm = self.perm[len_AB]
        else:
            tmp_perm = pickle.load(open('permutation_%s.dump' % len_AB))
            self.perm[len_AB] = tmp_perm
        for i in tmp_perm:
            tmp = []
            for j in i:
                tmp.append(AB[j])
            yield tmp
    
    def get_Hn_Concept(self, A, intSet, fxintSet):
        """增加精度系数，b1, b2
        """
        for mex in self.get_power(A):
            tempmex = list(mex)
            mex = set(mex)
            mexs = set(self.formalcontext.oa_value[tempmex.pop()])
            for i in tempmex:
                mexs = mexs.intersection(set(self.formalcontext.oa_value[i]))
                if not mexs:
                    break
            if not mexs:
                continue
            Co = 1- len(mexs.intersection(intSet))*1.0/max(len(mexs), len(intSet))
            if Co > self.b1:
                continue
            Ca = 1- len(mex.intersection(fxintSet))*1.0/max(len(mex), len(fxintSet))
            if Ca > self.b2:
                continue
            yield Concept(mex, intSet)
    
    def delete_cpt(self, cpt):
        """删除某个概念，调整边即父子关系
        """
        self.conceptSet.remove(cpt)
        #print 'delete ', cpt.conceptID
        
    def addOneObject(self, oneObject, oneattribute):
        """增加一个对象"""
        print oneObject, oneattribute
        xSet = set(oneObject)                                     # the set of oneobject's extent
        fxSet = set(oneattribute)
        if not self.supConcept.extent and not self.supConcept.intent:  # 当一开始格为空时进入该分支，能否提到初始化中?
            self.supConcept.addExtent(xSet)            # replace supConcept use object
            self.supConcept.addIntent(fxSet)
        else:
            if not fxSet <= self.supConcept.intent:
                if not self.supConcept.extent:               # supConcept's extent is none
                    self.supConcept.addIntent(fxSet)    # add this object's intent to supConcept's
                else:
                    H1 = Concept(set(), self.supConcept.intent.union(fxSet))    # creat new concept: {},{supConcept.intent+fxSet}
                    self.addConcept(H1)
                    self.supConcept = H1                        # change supConcept with this new concept
        
        H = self.infConcept
        if H.intent <= fxSet:        # 这个概念的intent包含于fxSet，需要修改这个概念
            H.addExtent(xSet)        # 加入对象xSet，相应的CAnother也得加入
        if H.intent == fxSet:                                         # 若这个概念的intent＝fxSet，exit algorithm
            return True
        else:                                                         # 这个概念的intent不包含于fxSet，old concept
            for intSet in self.get_power(fxSet): # 修改了这里，增大了intSet
                tmpintSet = list(intSet)
                intSet = set(intSet)
                fxintSet = set(self.formalcontext.ao_value[tmpintSet.pop()])
                for i in tmpintSet:
                    fxintSet = fxintSet.intersection(set(self.formalcontext.ao_value[i]))
                    if not fxintSet:
                        break
                if not fxintSet:
                    continue
                len_intSet = len(intSet)
                for Hn in self.get_Hn_Concept(H.extent, intSet, fxintSet):
                    # 删掉属性冗余概念和对象冗余概念
                    for cpt in self.conceptSet:
                        if not cpt.intent or not cpt.extent:
                            continue
                        if cpt.intent == Hn.intent and cpt.extent <= Hn.extent:
                            self.delete_cpt(cpt)
                            continue
                        if cpt.extent == Hn.extent and cpt.intent<= Hn.intent:
                            self.delete_cpt(cpt)
                            continue
                        if cpt.extent <= Hn.extent and cpt.intent<= Hn.intent:
                            self.delete_cpt(cpt)
                        
                    
                    self.addConcept(Hn)
                    if intSet == fxSet:                                  # 找到内涵完全相同的
                        return True

    def __str__(self):
        selfstring = 'Concept------>>\n'
        for cpt in self.conceptSet:
            selfstring += 'Concept %d: {' % cpt.conceptID
            for obj in cpt.extent:
                selfstring += ' %s ' % self.formalcontext.object_list[obj]
            selfstring += '}, {'
            
            for i in cpt.intent:
                selfstring += ' %s ' % self.formalcontext.attribute_list[i]
            selfstring += '}\n'#    his children is: '

        return selfstring+str(len(self.conceptSet))
    
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