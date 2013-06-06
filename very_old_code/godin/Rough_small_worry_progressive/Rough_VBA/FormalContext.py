#!/usr/bin/python
#coding=utf-8

"""FormalContext.py: 形式背景及相关操作
@author: Lizzie
@license: ...
@contact: shengyan1985@gmail.com
@version: 0.2

@attiontion:仍在继续完善中
@bug:
@warning:
"""

from Error import *

class FormalContext(object):
    """形式背景类
        some...
    """
    def __init__(self):
        """初始化
        """
        # 对象列表，保存其名字
        self.object_list = None
        # 对象个数
        self.object_num = 0
        # 属性列表，保存其名字
        self.attribute_list = None
        # 属性个数
        self.attribute_num = 0
        # 二元关系，即0,1
        self.br_list = None
        # 对象属性率
        self.oa_rate = 0
        # 对象属性值表，本质上和二元关系一样
        self.oa_value = {}
        # 属性对象值表，本质上和二元关系一样
        self.ao_value = {}
    
    def add_object_list(self, object_list=None):  ## 以下四个函数可否合并为两个?
        """增加对象列表，一组对象
        @param object_list: 对象列表，保存所有对象
        @type object_list: list
        """
        if object_list:
            self.object_list = object_list
            self.object_num = len(self.object_list)
        else:
            raise ObjectError, 'Object list is None!'
        
    def add_one_object(self, obj=None):
        """增加对象，一对象
        @param obj: 某个对象
        @type obj: object
        """
        if obj:
            if isinstance(self.object_list, list):
                self.object_list.append(obj)
                self.object_num += 1
            else:
                self.object_list = [obj]
        else:
            raise ObjectError, 'Object is None!'

    def add_attribute_list(self, attribute_list=None):
        """增加属性列表，一组属性
        @param attribute_list: 属性列表，保存所有属性
        @type attribute_list: list
        """
        if attribute_list:
            self.attribute_list = attribute_list
            self.attribute_num = len(self.attribute_list)
        else:
            raise AttributeError, 'Attribute list is None!'
        
    def add_one_attribute(self, att=None):
        """增加属性，一个属性
        @param att: 某个属性
        @type att: attribute
        """
        if obj:
            if isinstance(self.attribute_list, list):
                self.attribute_list.append(att)
                self.attribute_num += 1
            else:
                self.attribute_list = [att]
                self.attribute_num = 1
        else:
            raise AttributeError, 'Attribute is None!'
    
    def set_oa_rate(self, oar=1):
        """设置对象属性比率
        @param oar: 对象属性比率，默认为1
        @type oar: float
        """
        self.oa_rate = oar
    
    def add_br_list(self, br_list=None):
        """
        @param br: 对应二元关系，二值背景
        @typr br: BinaryRelation
        """
        if br_list:
            # 判断br的有效性
            
            if len(br_list) <> self.object_num:
                raise BRError, 'Binary Relation Row Error!'
            for col in br_list:
                if len(col) <> self.attribute_num:
                    # todo:定位到具体某行
                    raise BRError, 'Binary Relation Col Error!'
            self.br_list = br_list
            self.br_to_dict()
        else:
            raise BRError, 'Binary Relation is None!'
    
    def br_to_dict(self):
        """将二值形式背景变为对象属性值形式
        """
        self.oa_value = [[] for i in xrange(self.object_num) ]
        self.ao_value = [[] for i in xrange(self.attribute_num) ]
        for i in xrange(self.object_num):
            for j in xrange(self.attribute_num):
                if self.br_list[i][j]:
                    self.oa_value[i].append(j)
                    self.ao_value[j].append(i)
        
    def randomize(self, object_num=0, attribute_num=0, object_attribute_rate=1):
        """随机生成形式背景，包括对象，属性及二元关系
        @param object_num: 指定对象个数
        @type object_num: integer
        @param attribute_num: 指定属性个数
        @type attribute_num: integer
        @param object_attribute_rate: 指定对象属性比率
        @type object_attribute_rate: integer
        """
        import random
        
        # 生成随机对象，属性值
        self.attribute_list = ['a%d' % att for att in xrange(attribute_num)]
        self.object_list = ['o%d' % obj for obj in xrange(object_num)]
        self.object_num = object_num
        self.attribute_num = attribute_num
        self.oa_rate = object_attribute_rate
        
        # 生成随机BR
        self.br_list = []
        num = int(attribute_num * object_attribute_rate)
        
        for oneline in xrange(object_num):
            # 设置了oa_rate，每行不可能为全1，但全0还是有可能的
            oneline_value = [0]*attribute_num
            random_index = [random.randint(0, attribute_num-1) for one in xrange(num)]
            for j in xrange(num):
                oneline_value[random_index[j]] = 1
            # oneline_value为01010...
            self.br_list.append(oneline_value)
        
        # 生成oa_value和ao_value
        self.br_to_dict()

    def __str__(self):
        """输出形式背景信息
        """
        return 'object_num is %s\nobject_list is %s\natribute_num is %s\nattribute_list is %s\nbr_list is\n%s\noa_value is\n%s\nao_value is\n%s' % (self.object_num, self.object_list, self.attribute_num, self.attribute_list, self.br_list, self.oa_value, self.ao_value)

## 从这里开始，关于FC的读出和写入文件的各种方法
def save_FC(fc, filename='br_relations'):
    """将当前形式背景保存下来
    @param fc: 形式背景对象
    @type fc: FormalContext
    @param filename: 保存入文件的名字，默认为当前文件夹下的br_relations
    @type filename: string
    """
    import pickle
    try:
        pickle.dump(fc, open(filename, "w"))
        print 'save Formal Context to %s correctly!\n' % filename
    except Exception,e:
        print e

def load_FC(filename='br_relations'):
    """从文件中load已存形式背景对象
    @param filename: 文件的名字，默认为当前文件夹下的br_relations
    @type filename: string
    @return: 形式背景对象FormalContext
    @rtype: FormalContext
    """
    import pickle
    try:
        return pickle.load(open(filename))
    except Exception,e:
        print e

def read_FC_file(filename='FormalContext'):
    """从文件中读入形式背景，文件是有一定格式的
    @param filename: 文件名
    @type filename: string
    @return: FormalContxt
    """
    fcFile = file(filename, 'r')
    fc = FormalContext()
    try:
        oneLine = fcFile.readline()
        if len(oneLine) != 0:
            fc.add_object_list(oneLine.split())
        oneLine = fcFile.readline()
        if len(oneLine) != 0:
            fc.add_attribute_list(oneLine.split())
        
        value_list = []
        for index in fc.object_list:
            value_list.append([int(i) for i in fcFile.readline().split()])
        fc.add_br_list(value_list)
        return fc
    except Exception,e:
        print e
        print "Type Error in File %s" % filename
    finally:
        fcFile.close()