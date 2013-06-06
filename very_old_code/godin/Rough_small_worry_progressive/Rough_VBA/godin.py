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

if __name__ == '__main__':

    # load随机形式背景
    fc = load_FC()
    fp = open('result_%s_%s_%s' % (fc.object_num, fc.attribute_num, fc.oa_rate), 'w')
    # load组合
    
    b1 = [0.0, 0.3, 0.5, 0.7, 0.9]
    b2 = [0.0, 0.2, 0.4, 0.6, 0.8]
    
    # b1, b2 变化
    for b11 in b1:
        for b22 in b2:
            try:
                myLattice = Lattice(fc, b11, b22)
                before = time.time()
                for obj in range(fc.object_num):
                    myLattice.addOneObject([obj], fc.oa_value[obj])
                fp.write('b1=%s, b2=%s --> conceptnum is %s\n time: %s\n' % (b11, b22, len(myLattice.conceptSet), time.time()- before))
            except Exception, e:
                fp.write('\n\nException : %s\n\n'% e)
            finally:
                fp.flush()
    print 'end  b1, b2 变化'