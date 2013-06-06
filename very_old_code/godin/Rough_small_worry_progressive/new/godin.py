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
import time

if __name__ == '__main__':

    # load随机形式背景
    #fc = load_FC()
    fc = read_FC_file()
    
    b1 = [0.0, 0.3, 0.5, 0.7, 0.9]
    b2 = [0.0, 0.2, 0.4, 0.6, 0.8]
    
    myLattice = Lattice(fc, 0.5, 0.4)
    before = time.time()
    for obj in range(fc.object_num):
        myLattice.addOneObject([obj], fc.oa_value[obj])
    print myLattice