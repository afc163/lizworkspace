#!/usr/bin/python
#coding=utf-8
import random

class SeqDB():
    def __init__(self, att_num=20, cus_num=40, object_attribute_rate=0.3):
        self.Seq_DB = {}
        self.att_num = att_num
        self.cus_num = cus_num
        self.object_attribute_rate=object_attribute_rate
        
        #################
        #self.att_info = {'a':2, 'b':4, 'c':5, 'd':6, 'e':7, 'f':3, 'g':8, 'h':10}
        #self.att_list = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h']
        self.att_list = ['a_%d' % att for att in range(att_num)]
        #self.att_info = {'a':2, 'b':4, 'c':5, 'd':6, 'e':7, 'f':3, 'g':8, 'h':10}
        self.att_info = {}
        for a_d in self.att_list:
            self.att_info[a_d] = random.randint(0, 10)

    def createRandomDB(self):
        num = int(self.att_num * self.object_attribute_rate)
        for i in range(self.cus_num):       # all customer number
            self.Seq_DB[i] = []
            times = random.randint(1, 5)    #one custom has times sequence
            for j in range(times):
                oneline = []
                random_num = random.randint(1, num)
                for k in range(random_num):
                    index = random.randint(0, self.att_num-1)
                    #if not self.att_info[self.att_list[index]] in oneline:
                    #    oneline.append(self.att_info[self.att_list[index]])'a_%d' % 
                    if not index in oneline:
                        oneline.append(index)

                #random_index = [random.randint(0, self.att_num-1) for one in range(num)]
                #for k in random_index:
                    #if not self.att_list[k] in oneline:
                        #oneline.append(self.att_list[k])
                    #if not self.att_info[self.att_list[k]] in oneline:
                    #    oneline.append(self.att_info[self.att_list[k]])#这边可以省略att_list
                self.Seq_DB[i].append(oneline)
    
    def __str__(self):
        str = 'the Sequence DB is:\n'
        for i in self.Seq_DB:
            str += 'cid is %d:\n----his sequence\n' % i
            for j in self.Seq_DB[i]:
                str += '%s\n' % j
        return str
