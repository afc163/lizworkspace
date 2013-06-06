#!/usr/bin/python
#coding=utf-8

class BSet():
    def __init__(self, value = 0):
        self.value = value
        self.base = []
        #i = 1
        #for j in range(32):
        #    self.base.append(i)
        #    i = i * 2
        #self.base.reverse()
        
    def __str__(self):
        return '%s' % self.changeToList()
    
    def union(self, other_set):
        return BSet(self.value | other_set.value)
    
    def intersection(self, other_set):
        return BSet(self.value & other_set.value)
    
    def isNone(self):
        if self.value:
            return False
        return True
    
    def isEqual(self, other_set):
        if self.value == other_set.value:
            return True
        return False
    
    def contains(self, other_set):
        inter = self.intersection(other_set)
        if inter.isEqual(other_set):
            return True
        return False
    
    def changeToList(self):#X
        va = self.value
        l = []
        while va:
            l.append(va % 2)   # reverse
            va = va / 2
        return l
    
    def getLength(self):
        num = 0
        va = self.value
        #while va:
        #    if va % 2 == 1:
        #        num += 1
        #    va = va / 2
        
        base = 1
        for i in range(20):
            if base & va:
                pass
            else:
                num += 1
            base = base << 1    
        #for i in range(32):
        #    if va > self.base[i]:
        #        pass
        #    else:
        #        num += 1
        #        va -= self.base[i]
        #for i in self.changeToList():
        #    if i:
        #        num += 1
        return num
    
