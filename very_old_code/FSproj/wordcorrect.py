#!/usr/bin/python
#coding=utf-8

""" wordcoorect.py
@author: shengyan
@license: ...
@contract: shengyan1985@gmail.com
@see: 怎样写一个拼写检查器 http://blog.youxu.info/spell-correct.html
@version:0.1
"""

import re
import collections
# collections介绍: http://www.python.org/doc/2.5.2/lib/module-collections.html

PATTERM = re.compile(r"[a-z]+")

def words(text):
    return PATTERM.findall(text.lower())

def train(features):
    model = collections.defaultdict(lambda: 1)
    for f in features:
        model[f] += 1
    return model

NWORDS = train(words(file('browncorpus.txt').read()))

alphabet = 'abcdegfhijklmnopqrstuvwxyz'

def edits1(word):
    """与word编辑距离为1的集合
    """
    n = len(word)
    return set([word[0:i]+word[i+1:] for i in range(n)] +                               # deletion
                   [word[0:i]+word[i+1]+word[i]+word[i+2:] for i in range(n-1)] + # transposition
                   [word[0:i]+c+word[i+1:] for i in range(n) for c in alphabet] +  # alteration
                   [word[0:i]+c+word[i:] for i in range(n+1) for c in alphabet])    # insertion

def edits2(word):
    """与word编辑距离为2的集合
    """
    return set(e2 for e1 in edits1(word) for e2 in edits1(e1))

def known_edits2(word):
    return set(e2 for e1 in edits1(word) for e2 in edits1(e1) if e2 in NWORDS)
    
def known(words):
    return set(w for w in words if w in NWORDS)

def correct(word):
    candidates = known([word]) or known(edits1(word)) or known_edits2(word) or [word]
    # 返回候选集中出现频率最大的词
    return max(candidates, key= lambda w: NWORDS[w])
    
    
print correct('pyhton')