#!/usr/bin/python
#coding: utf-8

"""get.py
多关系贝叶斯分类
"""

import os
import sys
try:
    import cPickle as pickle
except:
    import pickle
import collections
from numpy.linalg import svd, LinAlgError
from numpy import array


DATABASE = "./berka/"  # 原始数据库表所在目录
DATABASE_NEW = "./berka_new/"  # 预处理后的数据库表所在目录

def get_tables():
    """预处理, 从存放数据表目录中读入各个表, 格式为tablename.dump, 内部为一字典, {主键:[属性值]}, 其中一个键值为'attribute'的为特殊情况, 其值为[各个属性名]
    """
    for r, d, files in os.walk(DATABASE):
        for f in files:
            database = {}
            for line in open(os.path.join(r, f)).readlines():
                line = line.strip()
                if line.startswith('"'):
                    database['attribute'] = [att[1:-1] for att in line.split(';')]
                else: 
                    item = []
                    for i in line.split(';'):
                        if i.startswith('"'):
                            item.append(i[1:-1])
                        else:
                            item.append(i)
                    database[item[0]] = item[1:]
            pickle.dump(database, open(DATABASE_NEW+'%s.dump' % f[:f.find('.')], 'w'))
            print 'get table %s ok' % f[:f.find('.')]

all_tables = ['loan', 'order', 'account', 'district', 'client', 'disp', 'card']#'trans', 
def get_all_tables():
    """获得所有表的名字, all_tables中, 因为外键和主键的引用约束, 所以需要调整位置
    """
    all_tables = []
    for r, d, files in os.walk(DATABASE_NEW):
        for f in files:
            all_tables.append(f[:f.find('.')])
    print all_tables
    
def gen_tree():
    """生成表之间的连接树, 类似于表的自然连接操作, 得到两两之间的连接, 存放于relation_table中
    """
    def join_two(upper, downer):
        """连接两个表, 关系保存在relation_table中
        @param upper: 相对上一层的表
        @type upper: dict
        @param downer: 相对下一层的表
        @type downer: dict
        @return: dict {id:[id,]}
        """
        for i in set(upper['attribute']).intersection(set(downer['attribute'])):
            if 'id' in i:  # 寻找有效的连接属性
                join_attribute = i
                break
        join_attribute_position_up = upper['attribute'].index(join_attribute)   # 找到连接属性在2个表中位置
        join_attribute_position_dw = downer['attribute'].index(join_attribute)

        level = collections.defaultdict(lambda: [])
        for up in upper:
            if join_attribute_position_up == 0:
                first = up
            else:
                first = upper[up][join_attribute_position_up-1]
            for dw in downer:
                if join_attribute_position_dw == 0:
                    second = dw
                else:
                    second = downer[dw][join_attribute_position_dw-1]
                if first == second:
                    level[up].append(dw)
        return dict(level)
        
    relation_table = []  # 层次关系, 相邻两层的关系, 每层用{id:[id,]}表示, 共有len(all_tables)-1层

    upper = pickle.load(open(DATABASE_NEW+'%s.dump' % all_tables[0]))
    for onetable in all_tables[1:]:
        downer = pickle.load(open(DATABASE_NEW+'%s.dump' % onetable))
        relation_table.append(join_two(upper, downer))
        upper = downer
    pickle.dump(relation_table, open('relation_table.dump', 'w'))
    
def read_relation():
    r = pickle.load(open('relation_table.dump'))
    for i in r:
        for j in i:
            if len(i[j])>2:
                print i[j]

def join_all():
    """读取relation_table, 生成所有连接后的记录, 分别由各自id组成, 得到一个join_table.dump, 里面是一个二维矩阵, 每个元素为对应表的id, 顺序为all_tables中的顺序
    """
    def depth_travel(level, root):
        """以root为根节点的先根遍历树
        @param level: 代表当前所在relation_table的层次号, 用于定位relation_table, 也用于表示当前层次指针
        @type level: integer
        @param root: 代表当前树根
        @type root: string
        """
        level_element[level] = root
        try:
            for r in relation_table[level][root]:
                depth_travel(level+1, r)
        except IndexError, e:  # 到达树节点, 需回到上一层
            join_table.append([])   # 不能直接join_table.append(level_element), 因为又是引用问题
            for i in level_element[:level+1]:
                join_table[-1].append(i)
        except KeyError, e:
            join_table.append([])
            for i in level_element[:level+1]:
                join_table[-1].append(i)
            
    relation_table = pickle.load(open('relation_table.dump'))
    level_element = [0 for i in range(len(all_tables))]
    join_table = []
    for root in relation_table[0]:
        depth_travel(0, root)
    #for i in join_table:
    #    print i
    pickle.dump(join_table, open("join_table.dump", 'w'))

def expand_all_tables():
    """根据join_table扩展每个表为矩阵形式, 以便进行奇异值分解
    """
    join_table = pickle.load(open("join_table.dump"))
    stringvalue = {} # 保存字符串属性及其数值代号{'string':id}
    for tableindex in range(len(all_tables)):
        table = pickle.load(open(DATABASE_NEW+'%s.dump' % all_tables[tableindex]))
        new_table = []
        for j in join_table:
            try:
                table_id = j[tableindex]        # join_table中每行对应的表id, 如果没有则直接跳过, 处理下一行
                tmp = list([table_id]+table[table_id])
                for i in xrange(len(tmp)):          # 将新加入的一行元素转成数值类型
                    try:
                        tmp[i] = float(tmp[i])    # 尝试转成float型, 如果不是当成字符串, 字符串转成对应数值代号
                    except ValueError, e:
                        if stringvalue.has_key(tmp[i]):
                            tmp[i] = stringvalue[tmp[i]]
                        else:
                            stringvalue[tmp[i]] = len(stringvalue) + 1
                            tmp[i] = len(stringvalue) + 1
                need_save = False
                if not new_table:
                    new_table.append([])
                    for i in tmp:
                        new_table[-1].append(i)
                    continue
                for i in xrange(len(new_table[-1])):
                    if tmp[i] != new_table[-1][i]:
                        need_save = True
                        break
                if need_save:
                    new_table.append([])
                    for i in tmp:
                        new_table[-1].append(i)
            except IndexError,e:
                pass
            except KeyError, e:
                pass
        pickle.dump(new_table, open(DATABASE_NEW+'%s_new.dump' % all_tables[tableindex], 'w'))
    pickle.dump(stringvalue, open(DATABASE_NEW+'stringvalue.dump', 'w'))
    print 'expand_all_tables ok'
    
def SVD():
    """对各个矩阵进行奇异值分解, 使用numpy中的svd
    """
    for tableindex in range(len(all_tables)):
        try:
            a = array(pickle.load(open(DATABASE_NEW+'%s_new.dump' % all_tables[tableindex])))
            u,s,vh = svd(a)
        except LinAlgError,e:
            print all_tables[tableindex], 'svd error'
            break
        except Exception,e:
            print e
            print a 
        
if __name__ == "__main__":
    #get_tables()
    #gen_tree()
    #read_relation()
    #join_all()
    expand_all_tables()
    #SVD()
    