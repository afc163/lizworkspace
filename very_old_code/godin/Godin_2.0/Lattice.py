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
TEST = 0
class Lattice(object):
    """形式概念格类
    some...
    """
    def __init__(self, obj, att):
        """初始化
        obj和att可以省略，所以主要有概念集合，底概念，顶概念，层次
        """
        self.conceptSet = []                    # 概念的集合
        self.supConcept = Concept()
        self.addConcept(self.supConcept)
        self.infConcept = Concept()
        self.addConcept(self.infConcept)
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
        
        if parent_cpt.layer_num >= child_cpt.layer_num + 1: #don't change laynum
            pass
        else:
            if parent_cpt in self.layer[parent_cpt.layer_num]: #remove old layer
                self.layer[parent_cpt.layer_num].remove(parent_cpt)
            parent_cpt.layer_num = child_cpt.layer_num + 1
            if self.layer.has_key(parent_cpt.layer_num):
                self.layer[parent_cpt.layer_num].append(parent_cpt)
            else:
                self.layer[parent_cpt.layer_num] = [parent_cpt]
            self.updateLayer(parent_cpt)
    
    def updateLayer(self, child_cpt):
        """递归更新层次号
        @note: 可否不用递归"""
        global TEST
        TEST+= 1
        for parent_cpt in child_cpt.parent_list:
            if parent_cpt.layer_num >= child_cpt.layer_num + 1: #don't change laynum
                pass
            else:
                if parent_cpt in self.layer[parent_cpt.layer_num]: #remove old layer
                    self.layer[parent_cpt.layer_num].remove(parent_cpt)
                parent_cpt.layer_num = child_cpt.layer_num + 1
                if self.layer.has_key(parent_cpt.layer_num):
                    self.layer[parent_cpt.layer_num].append(parent_cpt)
                else:
                    self.layer[parent_cpt.layer_num] = [parent_cpt]
                self.updateLayer(parent_cpt)
            
    def deleteEdge(self, parent_cpt, child_cpt):
        """删除边"""
        parent_cpt.child_list.remove(child_cpt)
        child_cpt.parent_list.remove(parent_cpt)
        
        #todo: 层次的更新?

    def SelectAndClassifyPairs(self, fxSet):
        def search(H):
            visited_flag.append(H.conceptID)
            i = len(H.intent)
            if C.has_key(i):
                C[i].append(H)
            else:
                C[i] = [H]
            for Hd in H.child_list:
                if not Hd.conceptID in visited_flag:
                    search(Hd)
        
        visited_flag = []
        visited_flag.append(self.infConcept.conceptID)
        C = {}
        C[len(self.infConcept.intent)] = [self.infConcept]
        
        for P in self.infConcept.child_list:
            print P
            if P.intent.intersection(fxSet) :
                search(P)
        print C
        return C
                
    def addOneObject(self, oneObject, oneattribute):
        """增加一个对象"""
        xSet = set(oneObject)                                     # the set of oneobject's extent
        fxSet = set(oneattribute)    
        if not self.supConcept.extent and not self.supConcept.intent:  # 当一开始格为空时进入该分支，能否提到初始化中?
            self.supConcept.addExtent(xSet)            # replace supConcept use object
            self.supConcept.addIntent(fxSet)
            self.infConcept.addExtent(xSet)             # add this object to infConcept这里的inf，一直不怎么懂初始化。。。
            return True

        if not fxSet <= self.supConcept.intent:
            if not self.supConcept.extent:            # supConcept's extent is none
                self.supConcept.addIntent(fxSet)    # add this object's intent to supConcept's
            else:
                H = Concept(set(), self.supConcept.intent.union(fxSet))    # creat new concept: {},{supConcept.intent+fxSet}
                self.addConcept(H)
                H.layer_num = 0
                self.layer[0] = [H]
                
                #self.supConcept.layer_num = 1###?删掉 保留
                #self.layer[1] = [self.supConcept]###?
                #self.infConcept.layer_num = 2###?
                #self.layer[2] = [self.infConcept]###?
                self.addEdge(self.supConcept, H)        # creat new edge from supConcept to this new
                self.addEdge(self.infConcept, self.supConcept)###
                self.supConcept = H                        # change supConcept with this new concept
        
        #C = self.SelectAndClassifyPairs(fxSet)
        C = {}
        for onecpt in self.conceptSet:        # consider all concept, get different length of intent
            i = len(onecpt.intent)
            if C.has_key(i):
                C[i].append(onecpt)
            else:
                C[i] = [onecpt]
        
        CAnother = {}                          # 初始化所有更新格节点和所有新生成的格节点的集合为空
        C_list = C.keys()
        C_list.sort()
        for i in C_list:                          # 从intent的数目由小到大考虑各个概念
            for H in C[i]:
                if H.intent <= fxSet:        # 这个概念的intent包含于fxSet，需要修改这个概念
                    H.addExtent(xSet)     # 加入对象xSet，相应的CAnother也得加入
                    if CAnother.has_key(i):
                        CAnother[i].append(H)
                    else:
                        CAnother[i] = [H]
                if H.intent == fxSet:                                         # 若这个概念的intent＝fxSet，exit algorithm
                    return True
                else:                                                                 # 这个概念的intent不包含于fxSet，old concept
                    intSet = H.intent.intersection(fxSet)        # 得到这个概念的intent和fxSet的交集
                    len_intSet = len(intSet)
                    if len_intSet == 0:
                        continue
                    flaglist = 0 # 存在标志，要表达出：在更新概念CAnother且intent的势为交集势的概念中不存在一更新概念的intent＝这个交集
                    if CAnother.has_key(len_intSet):
                        flaglist = len([H1 for H1 in CAnother[len_intSet] if H1.intent == intSet])
                    if not flaglist:                                                   # not exisit, show that H is a generator
                        Hn = Concept(H.extent.union(xSet), intSet)    # new concept:extent+xSet, intSet
                        self.addConcept(Hn)
                        if CAnother.has_key(len_intSet):           # add this new concept to CAnother
                            CAnother[len_intSet].append(Hn)
                        else:
                            CAnother[len_intSet] = [Hn]

                        self.addEdge(Hn, H)                               # add new edge: from this new concept to current
                        jlist = [j for j in xrange(len_intSet) if CAnother.has_key(j)]    # 修改边
                        for j in jlist:                                                 # 找更新概念中
                            for Ha in CAnother[j]:
                                if Ha.intent <= intSet:                      # Ha is a potential parent of Hn 
                                    parent = True
                                    for Hd in Ha.child_list:
                                        if Hd.intent <= intSet:              # 只要有一个更新概念的孩子的intent包含于这个交集，就不是父节点
                                            parent = False
                                            break
                                    if parent:                                      # 是父节点
                                        if H in Ha.child_list:               # 当前的概念是不是他的孩子，是的话，删除边
                                            self.deleteEdge(Ha, H)     # eliminate edge Ha->H
                                        self.addEdge(Ha, Hn)          # 增加父节点到这个新概念的边
                        if intSet == fxSet:                                   # 找到内涵完全相同的
                            #print intSet
                            return True

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
    
    def generate_dot_file(self):
        node_string = ''
        edge_string = ''
        for cpt in self.conceptSet:
            node_string += 'c%d[label = "%d: ' % (cpt.conceptID, cpt.conceptID)
            for obj in cpt.extent:
                node_string += '%s ' % self.object_list[obj]
            node_string += '/'
            for i in cpt.intent:
                node_string += '%s ' % self.attribute_list[i]
            node_string = node_string[:-1] + '"];\n'

            for one in cpt.child_list:
                edge_string += 'c%d -- c%d;\n' % (cpt.conceptID, one.conceptID)
        return node_string + edge_string

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