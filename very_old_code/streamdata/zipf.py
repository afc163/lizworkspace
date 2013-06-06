#!/usr/bin/python
#coding:utf-8

import random
import collections
from numpy.random import zipf
import sys

try:
    import cPickle as pickle
except:
    import pickle
    
def zipff(a=1.0, number=1000):
    sum = 0.0
    for i in xrange(0,number):
        sum += 1.0/((i+2)**a)
    print 'a'
    sums = [ 1.0*sum/(2**a) ]
    for i in xrange(1, number):
        sums.append(sums[-1]+1.0*sum/((i+2)**a))
    print 'b'
    result = []
    nn = max(sums)
    for j in xrange(0,number):
        r = random.uniform(0,nn)
        idx  = 0
        
        while (r > sums[idx]):
            if idx>=number-1:
                break
            else:
                idx += 1
        result.append(int(idx))
        print idx
    return result

def generat_zipf(a, n):
    """生成zipf分布随机数，保存至本地
    @param a: a参数，P(r) = C/(r**a)中的a，大于1
    @param n: 产生随机数的个数
    """
    try:
        randomdata = []
        element = {}
        for i in zipff(float(a), int(n)):
        #for i in zipf(float(a), int(n)):
            if not element.has_key(i):
                element[i] = len(element)
            randomdata.append(element[i])
        #print randomdata
        pickle.dump(randomdata, open('zipf.data', 'w'))
    except:
        raise

def show():
    #test = collections.defaultdict(lambda: 0)
    #test = dict()
    #for i in zipf(1.25, 1000):
    #    if test.has_key(i):
    #        test[i] += 1
    #    else:
    #        test[i] = 1
    #test1 =[tuple((item, key)) for key,item in test.items()]
    #print test
    
    test = collections.defaultdict(lambda: 0)
    data = pickle.load(open('zipf.data'))
    for i in data:
       test[i] += 1
    print data
    print sorted(test.items(), key=lambda test:test[1])
    
if __name__ == "__main__":
    if len(sys.argv)>1:
        generat_zipf(sys.argv[1], sys.argv[2])
    else:
        #print "Usage: zipf.py 1.25 100\n"
        show()
