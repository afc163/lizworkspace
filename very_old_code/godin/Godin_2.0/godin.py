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
    
    fc = FormalContext()
    # 随机生成形式背景
    fc.randomize(1000, 20, 0.2)
    
    # 从文件中读入
    fc1 = read_FC_file()
    

    myLattice = Lattice(fc.object_list, fc.attribute_list)
    for obj in range(fc.object_num):
        myLattice.addOneObject([obj], fc.oa_value[obj])
    #print myLattice
    #str = myLattice.generate_dot_file()
    #print str
    #try:
    #    to_file = file('/home/shengyan/workspace/godin/new/dot/show', 'wa')
    #    to_file.write('BR-----------\n%s\n\n' % BR)
    #    to_file.write('Lattice-----------\n%s\n\n' % myLattice)
    #    dot_file = file('/home/shengyan/workspace/godin/new/dot/show_godin.dot', 'wa')
    #    dot_file.write('graph G {\n%s}' % str)
    #except IOError, e:
    #    print e
    #finally:
    #    dot_file.close()
    #import hotshot, hotshot.stats
    #prof = hotshot.Profile("lattice.prof", 1)
    #for obj in range(object_num):
    #    prof.runcall(myLattice.addOneObject, [obj], BR.value_list[obj])
    #prof.close()
    #print myLattice
    #stats = hotshot.stats.load("lattice.prof")
    #stats.strip_dirs()
    #stats.sort_stats('time', 'calls')
    #stats.print_stats(20)
