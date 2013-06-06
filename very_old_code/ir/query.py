#!/usr/bin/python
#coding:utf-8

"""fca.py
生成子形式背景, 概念格和等价对象集
"""

import os,sys
from FormalContext import *
from Lattice import Lattice
import pickle
from datetime import datetime

import pp

SIM = 0.5
SIM2 = 0.6

def getSimilarity(kw, cpt):
    des = cpt.intent
    try:
        a = 1.0/(1+cpt.layer_num)
    except:
        a = 1.0/2
    return len(des.intersection(kw))*1.0/(1+a*len(kw-des)+(1-a)*(des-kw))
    
def query(keywords, i):
    myLattice = pickle.load(open('lattice-%' % i))
    result = []
    print 'start %s at %s' % (i, datetime.now())
    for cpt in myLattice.conceptSet:
        sim = getSimilarity(keywords, cpt)
        print 'sim ', sim
        if sim>SIM:
            result.append(cpt)
    print 'end %s at %s' % (i, datetime.now())
    return result
    
part = 5
ppservers = ()

job_server = pp.Server(part, ppservers=ppservers)

print "Startgins pp with", job_server.get_ncpus(), "workers"

if not isinstance(keywords, list):
    keywords = set([keywords])
else:
    keywords = set(keywords)
results = []
for i in xrange(0, part):
    f = job_server.submit(query, (keywords, i))
    results.append(f)

pickle.dump(results, open('results', "w"))
job_server.print_stats()


all_cpt = []
for res in results:
    all_cpt.extend(res)

finall = []
for cpt in all_cpt:
    for cpt2 in all_cpt:
        if cpt==cpt2:
            continue
        newconcept_intent = cpt.intent.union(cpt2.intent)
        newconcept_extent = cpt.extent.intersection(cpt2.extent)
        sim = getSimilarity(keywords, newconcept_intent)
        print 't sim:', sim
        
        if sim>SIM2:
            finall.append(new_extent)

pickle.dump(finall, open('finall', "w"))
print 'all done'


