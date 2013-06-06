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
    # 从文件中读入
    #fc = read_FC_file()

    #fc = FormalContext()
    # 随机生成形式背景
    #fc.randomize(10, 10, 0.3)
    
    fc = load_FC()

    myLattice = Lattice(fc, 0, 0)
    myLattice.All()
    print myLattice