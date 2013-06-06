#!/usr/bin/python
#coding:utf-8

"""reuters_clean.py
标准测试数据集reuters21578预处理脚本
"""

import os, sys
import re
from wordsegmentor import StopWordList

REUTERS = "./reuters21578"

PATTERM = re.compile(r"[a-z]+")
BODY = re.compile(r"<BODY>(?P<body>(?s).*?)</BODY>")

def get_words(text):
    return PATTERM.findall(text.lower())


def get_data():
    """提取正文, 分词, 去停用词, 保存入文件
    """
    stop_word_list = StopWordList().words
    index = 0
    for r, d, f in os.walk(REUTERS):
        for ff in f:
            for bd in BODY.findall(open(os.path.join(r, ff)).read()):
                allterm = []
                for oneterm in get_words(bd[:-4]):
                    if oneterm in stop_word_list or len(oneterm)<4:
                        continue
                    allterm.append(oneterm)
                if allterm:
                    index += 1
                    #open("reuters_clean/body-%s.txt" % index, 'w').write(bd[:-4])
                    nodedir = "reuters_term_%s" % (index%5)
                    if not os.path.isdir(nodedir):
                        os.mkdir(nodedir)
                    open("%s/body-%s.txt" % (nodedir, index), 'w').write(' '.join(allterm))
get_data()



