#!/bin/python
# coding: utf-8

from FormalContext import *
from CMN import *
import os

OBJNUM = 20
ATTNUM = 20
OA_RATE = 0.3

# 生成随机形式背景
fc = FormalContext()
fc.randomize(OBJNUM, ATTNUM, OA_RATE)
save_FC(fc)

# 准备组合
NUM = max(OBJNUM, ATTNUM)
for i in range(1, NUM+1):
    if not os.path.exists('permutation_%s.dump' % i):
        result = []
        for j in Permutation(i).perm_all():
            result.append(tuple(j))
        pickle.dump(result, open('permutation_%s.dump' % i, 'w'))
print 'OK'
