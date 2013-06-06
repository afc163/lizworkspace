#!/usr/bin/python
#coding:utf-8

"""classification.py
分类器
@contract: shengyan1985@gmail.com
@version:0.1
"""

import os
import re
import collections
#from wordsegmentor import StopWordList
try:
    import cPickle as pickle
except:
    import pickle
from math import log

NEWSGROUPS = "/home/shengyan/workspace/corpus/NG/"
#NEWSGROUPS = "/home/shengyan/workspace/corpus/20_newsgroups/"
#REUTERS = "/home/shengyan/workspace/corpus/reuters21578/"
TDF_THRESHOLD = 0.05      # 去除频度最大/最小的5%个term
CF_THRESHOLD = 0.001      # 类别波动范围
CF_RATE = 0.9                    # 类别的大多数
DELTA = 0.01                      # 权值公式中的调节系数

# 预处理过程总体描述:
# 1, 从数据集NG中读取文档
# 2, 去除全局高频term
# 3, 保存到本地, 之后就可以脱离原来数据集

PATTERM = re.compile(r"[a-z]+")
def get_words(text):
    """获得text中文本单词
    """
    return PATTERM.findall(text.lower())

def get_NG():
    """读取NEWSGROUPS数据源, 获得:
    1, 一索引file_category: {文档名:[类别号]}
    2, 文档向量空间file_term: {文档名:[特征词]}
    3, 所有term集合tdf: {term:全局频数}, 经过去除停用词或去除zipf高频词
    4, DF: {term: frequence} 每个term的DF值
    5, TF: {term:{file:frequence}} term在某个文档中的频率
    所有结果都pickle到文件
    @return: None
    """
    file_category = {}                                # {文档名:[类别号]}
    file_term = {}                                     # {文档名:[特征词]}
    tdf = collections.defaultdict(lambda: 0) # term在整体词集中的频率
    term_count = 0                                  # 词集中所有词汇(不包括去除掉的)个数
    stop_word_list = StopWordList().words
    
    for r, d, f in os.walk(NEWSGROUPS):
        for ff in f:
            file_category[ff]=[r[r.rfind('/')+1:]]
            fp = open(os.path.join(r, ff))
            start_text = False                      # 文档头部或正文标志
            file_term[ff] = []
            for line in fp.readlines():
                if not line:                           # 跳过空行
                    continue
                if not start_text:                   # 表文档头部处理
                    if line.startswith("Newsgroups:sds "):                     # 获得该文档的类别号, 一个文档可以有多个类别
                        file_category[ff] = []
                        for c in line[12:].split(','):
                            file_category[ff].append(c.strip())
                            break
                    elif line.startswith("Lines: "):                                 # 标志正文开始
                        start_text = True
                else:                                                                       # 表文档正文, 获取特征词
                    for oneterm in get_words(line):
                        if oneterm in stop_word_list or len(oneterm) < 4:  # 在停用词表或单词数少于4个
                            continue
                        file_term[ff].append(oneterm)
                        tdf[oneterm] += 1
                        term_count += 1
    print 'get source ok'
    print len(tdf)
    # 去除全局高频数的term, 即根据zipf定律删掉高x%频数的term
    high_tdf = []                                                                        # 先记下要删掉的高频term
    for (k, f) in sorted(tdf.items(), key=lambda tdf:tdf[1])[- int(len(tdf)*TDF_THRESHOLD):]: #note: 如果是删除一定数量的频率相近的term, 不合算了...
        high_tdf.append(k)
        term_count -= tdf[k]                                                         # 同时减少有效term数量
        del tdf[k]
    # 在文档向量空间中真正删除, 并顺带计算term的DF
    DF = collections.defaultdict(lambda: 0)                                  # {term:df}文档频率, 即term在多少个文档中出现次数
    for onefile in file_term:
        for htdf in high_tdf:
            delete_flag = True
            while delete_flag:
                try:
                    file_term[onefile].remove(htdf)
                except ValueError,e:
                    delete_flag = False
        for oneterm in tdf:                                                          # 计算DF
            if oneterm in file_term[onefile]:
                DF[oneterm] += 1
    
    # 去除DF低的term
    low_df = []                                                                        # 先记下要删掉的低DF的term
    for (k, f) in sorted(DF.items(), key=lambda DF:DF[1])[- int(len(DF)*TDF_THRESHOLD):]:
        low_df.append(k)
        term_count -= tdf[k]                                                       # 同时减少有效term数量
        del tdf[k]
        del DF[k]
    print 'df ok'

    # 计算term的类别频率
    CF = {}                                                                             # {term:{category:frequence}}
    CF_count = collections.defaultdict(lambda: 0)                       # {category: total_term_num}
    for onefile in file_term:
        for ldf in low_df:                                                           # 删除文档中低频df的term
            delete_flag = True
            while delete_flag:
                try:
                    file_term[onefile].remove(ldf)
                except ValueError,e:
                    delete_flag = False
        
        category = file_category[onefile]
        for oneterm in file_term[onefile]:
            if CF.has_key(oneterm):
                for cate in category:
                    CF_count[cate] += 1
                    if CF[oneterm].has_key(cate):
                        CF[oneterm][cate] += 1
                    else:
                        CF[oneterm][cate] = 1
            else:
                CF[oneterm] = {}
                for cate in category:
                    CF_count[cate] += 1
                    CF[oneterm][cate] = 1
    for term in CF:
        for ct in CF[term]:                                                       # 得到term在各类别中的频率
            CF[term][ct] = CF[term][ct]*1.0/CF_count[ct]
    
    del_cf = []
    CF_min = int(len(CF_count)*CF_RATE)                               # 即是类间频率阈值
    for t in CF:
        if len(CF[t]) >= CF_min and _IsInThreshold(CF[t].values()):
            del_cf.append(t)                                                     # 记下要删除的这个term
            term_count -= tdf[t]                                                # 同时减少有效term数量
            del tdf[t]
            del DF[t]
    for t in del_cf:
        del CF[t]
    print 'cf ok'

    # 计算词频
    TF = {}
    for onefile in file_term:
        for lcf in del_cf:                                                         # 删除文档中cf频率不符合的term
            delete_flag = True
            while delete_flag:
                try:
                    file_term[onefile].remove(lcf)
                except ValueError,e:
                    delete_flag = False
        
        for oneterm in file_term[onefile]:                                 # 计算每个文档中term的词频TF
            if TF.has_key(oneterm):
                if TF[oneterm].has_key(onefile):
                    TF[oneterm][onefile] += 1                              # 仅仅是频数
                else:
                    TF[oneterm][onefile] = 1
            else:
                TF[oneterm] = {}
                TF[oneterm][onefile] = 1
    print 'tf ok'
    
    for k in tdf:                                     # 真正得到tdf
        tdf[k] = tdf[k]*1.0/term_count
    len_file = len(file_term)
    for k in DF:                                     # 真正得到文档频率 DF[oneterm]*1.0/len(file_term), 
        DF[k] = DF[k]*1.0/len_file
    for oneterm in TF:                            # 真正得到词频TF[oneterm][onefile] *1.0/len(file_term[onefile])
        for onefile in TF[oneterm]:             # 得到term在各类别中的频率
            TF[oneterm][onefile] = TF[oneterm][onefile]*1.0/len(file_term[onefile])
    del_file = []
    for i in file_term:                              # 删掉为空的文档,,因为数据集中可能存在这类的文档
        if len(file_term[i]) == 0:
            del_file.append(i)
    for i in del_file:
        del file_term[i]
    # 保存 file_category, file_term, (tdf, term_count), (TF, DF) 至本地
    pickle.dump(file_category, open('file_category.dump', 'w'))
    pickle.dump(file_term, open('file_term.dump', 'w'))
    tp = (dict(tdf), term_count)                                              # collections.defaultdict()这个不能被pickle
    pickle.dump(tp, open('tdf_termcount.dump', 'w'))
    tp = (TF, dict(DF))
    pickle.dump(tp, open('tf_df.dump', 'w'))
    tp = (CF, dict(CF_count))
    pickle.dump(tp, open('cf_cfcount.dump', 'w'))
    print 'Done'

def _IsInThreshold(cf=[]):
    """判断cf列表中的数值波动在一定范围之间, 利用数值的最大最小值之差是否满足给定的阈值
    @param cf: 待判断的数值列表
    @type cf: list
    @return boolean: 表示数值波动是否在一定范围内, 如果是返回True, 否则False
    """
    if not cf:
        return False
    tc_min = cf[0]
    tc_max = cf[0]
    for otc in cf[1:]:
        if otc < tc_min:
            tc_min = otc
            continue
        if otc > tc_max:
            tc_max = otc
    if tc_max-tc_min < CF_THRESHOLD:
        return True
    return False

def get_weight():
    """
    """
    file_category = pickle.load(open('file_category.dump'))
    file_term = pickle.load(open('file_term.dump'))
    (tdf, term_count) = pickle.load(open('tdf_termcount.dump'))
    (TF, DF) = pickle.load(open('tf_df.dump'))
    (CF, CF_count) = pickle.load(open('cf_cfcount.dump'))
    dvs_table = {} # {file:{term:weight}} 矩阵表示??
    for onefile in file_term:
        dvs_table[onefile] = {}
        sum = 0
        for oneterm in tdf:
            if oneterm in file_term[onefile]:  # 如果该文档有term, 则计算对应权值, 否则为0
                dvs_table[onefile][oneterm] = TF[oneterm][onefile]*log(1.0/DF[oneterm]+DELTA)*log(1.0/CF[oneterm][file_category[onefile][0]]+DELTA)
                sum += dvs_table[onefile][oneterm]**2
                print sum
            else:
                pass#dvs_table[onefile][oneterm] = 0
        sum = sum**0.5
        for oneterm in dvs_table[onefile]:
            dvs_table[onefile][oneterm] /= sum
    #print dvs_table
    pickle.dump(dvs_table, open('dvs_table.dump', 'w'))
    
def show_TF_DF():
    (TF, DF) = pickle.load(open('tf_df.dump'))
    print len(TF)
    print len(DF)
    
def get_RT():
    """读取REUTERS数据源
    """
    pass
    
def main():
    #get_NG()
    #show_TF_DF()
    get_weight()
    
if __name__ == "__main__":
    main()
