#!/usr/bin/python
#coding=utf-8
import random

class SeqDB():
    def __init__(self, attribute_num=20, cus_num=40, object_attribute_rate=0.3):
        self.attribute_list = ['a_%d' % att for att in range(attribute_num)]
        #self.cus_list = ['o_%d' % obj for obj in range(cus_num)]
        self.object_list = []
        self.attribute_weight = {}
        for i in range(attribute_num):
            self.attribute_weight[i] = random.randint(0, 10)
        self.Seq_DB = {}
        
        #createRandomDB
        num = int(attribute_num * object_attribute_rate)
        for i in range(cus_num):       # all customer number
            self.Seq_DB[i] = []
            times = random.randint(1, 5)    #one custom has times sequence
            for j in range(times):          #随机函数产生不同也不同
                oneline = []
                for k in range(attribute_num):
                    oneline.append(0)
                random_index = [random.randint(0, attribute_num-1) for one in range(num)]
                for k in range(num):
                    oneline[random_index[k]] = 1
                tp = [ k for k in range(attribute_num) if oneline[k] == 1 ]
                self.Seq_DB[i].append(tp)
                #random_num = random.randint(1, num)
                #for k in range(random_num):
                #    index = random.randint(0, attribute_num-1)
                    #if not self.att_info[self.att_list[index]] in oneline:
                    #    oneline.append(self.att_info[self.att_list[index]])'a_%d' % 
                #    if not index in oneline:
                #        oneline.append(index)

                #random_index = [random.randint(0, self.att_num-1) for one in range(num)]
                #for k in random_index:
                    #if not self.att_list[k] in oneline:
                        #oneline.append(self.att_list[k])
                    #if not self.att_info[self.att_list[k]] in oneline:
                    #    oneline.append(self.att_info[self.att_list[k]])#这边可以省略att_list
                #self.object_list.append('obj_%d_%d' % (i, j))
                self.object_list.append('obj_%d' % i)
                #self.Seq_DB[i].append(oneline)
    
    def __str__(self):
        str = 'the Sequence DB is:\n'
        for i in self.Seq_DB:
            str += 'cid is %d:\n----his sequence\n' % i
            for j in self.Seq_DB[i]:
                str += '%s\n' % j
        return str
