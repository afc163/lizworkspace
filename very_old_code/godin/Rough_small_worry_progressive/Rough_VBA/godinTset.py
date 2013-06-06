#!/usr/bin/python
#coding=utf-8

"""godin.py: 主入口调用脚本
@author: Lizzie
@license: ...
@contact: shengyan1985@gmail.com
@version: 0.2
"""

from FormalContext import *
from Lattice import Lattice
import pickle
import time

if __name__ == '__main__':
    
    fp = open('result', 'w')
    # load随机形式背景
    fc = load_FC()
    # load组合
    perm_obj = pickle.load(open('permutation_%s.dump' % fc.object_num))
    perm_att = pickle.load(open('permutation_%s.dump' % fc.attribute_num))
    
    b1 = [0.0, 0.3, 0.5, 0.7, 0.9]
    b2 = [0.0, 0.2, 0.4, 0.6, 0.8]
    
    # b1=0, b2 变化
    for b in b2:
        try:
            myLattice = Lattice(fc, 0, b, perm_obj, perm_att)
            before = time.time()
            myLattice.All()
            fp.write('b1=%s, b2=%s --> conceptnum is %s\n time: %s\n' % (0, b, len(myLattice.conceptSet), time.time()- before))
        except Exception, e:
            fp.write('\n\nException : %s\n\n'% e)
        finally:
            fp.flush()
    
    print 'end  b1=0, b2'
    # b2=0, b1 变化
    for b in b1:
        try:
            myLattice = Lattice(fc, b, 0, perm_obj, perm_att)
            before = time.time()
            myLattice.All()
            fp.write('b1=%s, b2=%s --> conceptnum is %s\n time: %s\n' % (b, 0, len(myLattice.conceptSet), time.time()- before))
        except Exception, e:
            fp.write('\n\nException : %s\n\n'% e)
        finally:
            fp.flush()
    
    print 'end  b2=0, b1'
    # b1, b2 变化
    for b11 in b1:
        for b22 in b2:
            try:
                myLattice = Lattice(fc, b11, b22, perm_obj, perm_att)
                before = time.time()
                myLattice.All()
                fp.write('b1=%s, b2=%s --> conceptnum is %s\n time: %s\n' % (b11, b22, len(myLattice.conceptSet), time.time()- before))
            except Exception, e:
                fp.write('\n\nException : %s\n\n'% e)
            finally:
                fp.flush()
    print 'end  b1, b2 变化'