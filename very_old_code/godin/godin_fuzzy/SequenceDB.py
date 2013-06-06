#!/usr/bin/python
#coding=utf-8
import random

class SeqDB():
    def __init__(self, attribute_num=20, cus_num=40, object_attribute_rate=0.3):
        self.attribute_list = ['a_%d' % att for att in range(attribute_num)]
        #self.cus_list = ['o_%d' % obj for obj in range(cus_num)]
        self.object_list = []
        self.Seq_DB = {}
        self.attribute_weight = []      #generate the weight of attribute\ O_value\ Y_value
        self.O_value = []
        self.Y_value = []
        for i in range(attribute_num):  #len(self.attribute_list)
            self.attribute_weight.append(random.randint(1, 10))    
            #x = random.randint(0, 9)
            #self.O_value.append(x*0.1)
            #self.Y_value.append(random.randint(x, 10)*0.1)
            self.O_value.append(0.5)
            self.Y_value.append(0.6)
        #createRandomDB
        num = int(attribute_num * object_attribute_rate)
        for i in range(cus_num):            #all customer number
            self.Seq_DB[i] = []
            times = random.randint(1, 1)    #one custom has times sequence
            ttp = []
            for j in range(times):          #随机函数产生不同也不同
                oneline = []
                for k in range(attribute_num):
                    oneline.append(0)
                random_index = [random.randint(0, attribute_num-1) for one in range(num)]
                for k in range(num):
                    oneline[random_index[k]] = 1
                tp = [ k for k in range(attribute_num) if oneline[k] == 1 ]
                #self.Seq_DB[i].append(tp)
                for k in tp:
                    ttp.append(k)
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
            self.Seq_DB[i] = ttp
            self.object_list.append('obj_%d' % i)
                #self.Seq_DB[i].append(oneline)
    
    def generate_formalcontext1(self):
        import fpformat
        for cid in self.Seq_DB:
            oneline = {}
            sum = 0             #get the sum of one sequence
            for event_index in range(len(self.Seq_DB[cid])):
                for e in self.Seq_DB[cid][event_index]:
                    if oneline.has_key(e):
                        #oneline[e][1].append(event_index)
                        oneline[e].append(event_index)
                    else:
                        #oneline[e] = []
                        #oneline[e].append(0)
                        #oneline[e].append([event_index])
                        oneline[e] = [event_index]
                    sum += self.attribute_weight[e]
            for o in oneline.keys():
                #value = self.attribute_weight[o]*len(oneline[o][1])*1.0/sum
                value = self.attribute_weight[o]*len(oneline[o])*1.0/sum
                #value = self.attribute_weight[o]*len(oneline[o])*1.0/max  fpformat.fix(
                if value < self.O_value[o] or value > self.Y_value[o]:
                    del oneline[o]
                else:
                    oneline[o].append(value)
            max = 0
            for i in oneline:
                if max < oneline[i][-1]:
                    max = oneline[i][-1]
            for j in oneline:
                oneline[j][-1] = oneline[j][-1]/max
            del self.Seq_DB[cid]
            self.Seq_DB[cid] = oneline
    def generate_formalcontext(self):
        import fpformat
        for cid in self.Seq_DB:
            sum = 0             #get the sum of one sequence
            oneline = []
            for e in self.Seq_DB[cid]:
                sum += self.attribute_weight[e]
            for o in self.Seq_DB[cid]:
                value = self.attribute_weight[o]*self.Seq_DB[cid].count(o)*1.0/sum
                #value = self.attribute_weight[o]*len(oneline[o])*1.0/max  fpformat.fix(
                if value < self.O_value[o] or value > self.Y_value[o]:
                    #pass
                    oneline.append([o, value])
                else:
                    oneline.append([o, value])
            max = 0
            for (i, w) in oneline:
                if max < w:
                    max = w
            for j in range(len(oneline)):
                oneline[j][1] = oneline[j][1]/max
            del self.Seq_DB[cid]
            self.Seq_DB[cid] = oneline

    def MaxCommSS(self, seq1, seq2):
        '''the Max Common of two Sequence'''
        print seq1, seq2
        for item in seq1:
            pass
    def __str__(self):
        str = 'the Sequence DB is:\n'
        for i in self.Seq_DB:
            str += 'cid is %s:\n----his sequence\n' % self.object_list[i]
            if repr(type(self.Seq_DB[i])) == "<type 'list'>":
                for j in self.Seq_DB[i]:
                    if repr(type(j)) == "<type 'list'>":
                        str += ' %d=%.3f, ' % (j[0], j[1])
                    else:
                        str += '%s\n' % j
            else:
                for j in self.Seq_DB[i]:
                    str += '%s: ' % self.attribute_list[j]
                    for k in self.Seq_DB[i][j]:
                        str += '%s, ' % k
                    str = str[:-2] + '\n'
        return str
