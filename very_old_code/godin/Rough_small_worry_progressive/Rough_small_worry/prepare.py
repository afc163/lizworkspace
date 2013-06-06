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
if not os.path.exists('permutation_%s.dump' % OBJNUM):
    result = []
    for j in Permutation(OBJNUM).perm_all():
        result.append(tuple(j))
    pickle.dump(result, open('permutation_%s.dump' % OBJNUM, 'w'))
if not os.path.exists('permutation_%s.dump' % ATTNUM):
    result = []
    for j in Permutation(ATTNUM).perm_all():
        result.append(tuple(j))
    pickle.dump(result, open('permutation_%s.dump' % ATTNUM, 'w'))
print 'OK'
