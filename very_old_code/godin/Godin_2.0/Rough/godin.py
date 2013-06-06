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
    
    fc1 = FormalContext()
    # 随机生成形式背景
    fc1.randomize(20, 10, 0.3)
    
    # 从文件中读入
    #fc1 = read_FC_file()
    

    myLattice = Lattice(fc1.object_list, fc1.attribute_list, 0.5, 0.4)
    for obj in range(fc1.object_num):
        myLattice.addOneObject([obj], fc1.oa_value[obj])
    print myLattice