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

class Lattice(object):
    """形式概念格类
    some...
    """
    def __init__(self, fc, b1, b2, perm_obj, perm_att):
        """初始化
        obj和att可以省略，所以主要有概念集合，底概念，顶概念，层次
        """
        self.conceptSet = []                    # 概念的集合
        #self.addConcept(Concept(set(), set(range(fc.attribute_num))))
        #self.addConcept(Concept(set(range(fc.object_num)), set()))
        
        self.formalcontext = fc
        self.b1 = b1
        self.b2 = b2
        
        self.att_powerset = perm_att#self.tmp_perm[self.formalcontext.attribute_num-1]
        self.obj_powerset = perm_obj#self.tmp_perm[self.formalcontext.object_num-1]
        
        self.exclude_att = []
        self.exclude_obj = []
        
    def addConcept(self, newconcept):
        """增加新的概念到已有概念集中"""
        self.conceptSet.append(newconcept)

    def delete_cpt(self, cpt):
        """删除某个概念，调整边即父子关系
        """
        self.conceptSet.remove(cpt)
        
    def get_Hn_Concept(self, intSet, fxintSet):
        """增加精度系数，b1, b2
        """
        for subo in self.obj_powerset:
            subo = set(subo)
            flag = 0
            for e in self.exclude_obj:
                if e < subo:
                    flag = 1
                    break
            if flag:
                continue
            tempsubo = list(subo)
            fxsubo = set(self.formalcontext.oa_value[tempsubo.pop()])
            for i in tempsubo:
                fxsubo = fxsubo.intersection(set(self.formalcontext.oa_value[i]))
                if not fxsubo:
                    break
            if not fxsubo:
                if set(subo) not in self.exclude_obj:
                    self.exclude_obj.append(set(subo))
                continue
                
            Co = 1- len(fxsubo.intersection(intSet))*1.0/max(len(fxsubo), len(intSet))
            if Co > self.b1:
                continue
            Ca = 1- len(subo.intersection(fxintSet))*1.0/max(len(subo), len(fxintSet))
            if Ca > self.b2:
                continue
            yield Concept(subo, intSet)
            print 'subo', subo, 'intSet', intSet
            
    def All(self):
            for suba in self.att_powerset: # 修改了这里，增大了intSet
                suba = set(suba)
                flag = 0
                for e in self.exclude_att:
                    if e < suba:
                        flag = 1
                        break
                if flag:
                    continue
                tmpsuba = list(suba)
                fxsuba = set(self.formalcontext.ao_value[tmpsuba.pop()])
                for i in tmpsuba:
                    fxsuba = fxsuba.intersection(set(self.formalcontext.ao_value[i]))
                    if not fxsuba:
                        break
                if not fxsuba:
                    if set(suba) not in self.exclude_att:
                        self.exclude_att.append(set(suba))
                    continue
                
                for Hn in self.get_Hn_Concept(suba, fxsuba):
                    # 删掉属性冗余概念和对象冗余概念
                    for cpt in self.conceptSet:
                        if not cpt.intent or not cpt.extent:
                            print 'dd'
                            continue
                        if cpt.intent == Hn.intent and cpt.extent <= Hn.extent:
                            print 'db'
                            self.delete_cpt(cpt)
                            continue
                        if cpt.extent == Hn.extent and cpt.intent<= Hn.intent:
                            print 'de'
                            self.delete_cpt(cpt)
                            continue
                        if cpt.extent <= Hn.extent and cpt.intent<= Hn.intent:
                            self.delete_cpt(cpt)
                            print 'dg'
                    self.addConcept(Hn)
                    
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