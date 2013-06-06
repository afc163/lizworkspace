#!/usr/bin/python
#coding:utf-8

"""fca.py
生成子形式背景, 概念格和等价对象集
"""

import os,sys
from FormalContext import *
from Lattice import Lattice
import pickle

DATABASE = "reuters_term_%s"

def genContext(idx, fc):
    for r,d,f in os.walk(DATABASE % idx):
        for ff in f:
            fatt = open(os.path.join(r, ff)).read().split()
            for att in fatt:
                if not fc.attribute_list or att not in fc.attribute_list:
                    fc.add_one_attribute(att)
            fc.add_one_object(ff)
            fc.add_one_object_att(ff, fatt)
    print fc
    return fc


if __name__ == "__main__":
    for i in xrange(0,5):
        fc = genContext(i, FormalContext())
        save_FC(fc, filename="br-%s" % i)
        myLattice = Lattice(fc.object_list, fc.attribute_list)
        for obj in range(fc.object_num):
            myLattice.addOneObject([obj], fc.oa_value[obj])
        try:
            pickle.dump(myLattice, open("lattice-%s" % i, "w"))
            print 'save Formal Context to %s correctly!\n' % filename
        except Exception,e:
            print e

