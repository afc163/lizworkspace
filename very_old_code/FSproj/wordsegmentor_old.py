#!/usr/bin/python
#coding=utf-8

""" wordsegmentor.py
@author: shengyan
@license: ...
@contract: shengyan1985@gmail.com
@see: ...
@version:0.1
"""

import os
from optparse import OptionParser
import re

import sys
reload(sys)
sys.setdefaultencoding('utf8')

try:
    import cPickle as pickle
except:
    import pickle

PATTERM = re.compile(r"[a-z]+")

class WordSegmentor(object):
    """分词类
    提取一个文本中的所有符合PATTERM的单词, 并保存至文件
    @todo: 提取一个文本中的单词
    """
    def __init__(self, filename):
        """初始化
        @param filename: 给定待处理的文件名
        @type filename: string
        @return : 无返回
        """
        self.filename = filename
        self.wordlist = []             #@note: 用于保存分词结果
    
    def analysis(self):
        """根据模式提取文本中单词, 并保存单词
        @return : 分析成功与否, 过程出错返回[], 否则返回self.wordlist
        @rtype: list
        """
        try:
            fp = open(self.filename, 'r')
            
            while True:
                line = fp.readline()
                if len(line)==0:
                    break;
                #@note: 提取一行中的单词(全部转换为小写), 并添加至self.wordlist
                relist = PATTERM.findall(line.lower())
                if relist:
                    self.wordlist += relist
            return self.wordlist
        except Exception, e:
            print e
            return []
            
    def __str__(self):
        """字符串输出
        """
        return "File Name is %s\n%s" % (self.filename, '\t'.join(self.wordlist))


class StopWordList(object):
    """停用词表
    """
    def __init__(self, sourcefilename=None):
        """初始化
        读取文件并提取其中停用词
        @param sourcefilename: 停用词所在文件, 如果不提供,则默认从stopwordlist.dump中读取,如果提供,表示需要从其中加载停用词表并转换为dump文件,里面是一个list对象
        @type sourcefilename: string
        """
        try:
            fp = open(sourcefilename)    # try中读取sourcefilename并dump为一个list
            self.words = []
            for line in fp.readlines():
                #line = line.strip()
                if line.endswith('\n'):
                    self.words.append(line[:-1])
                else:
                    self.words.append(line)
            pickle.dump(self.words, open('stopwordlist.dump', 'w'))
        except Exception, e:  # 如果上述出错, 尝试load一个stopwordlist,若还是出错,则置为空.
            #print e
            try:
                self.words = pickle.load(open('stopwordlist.dump'))
            except Exception, e:
                self.words = []
                print e
        
    def __str__(self):
        return "Stop Word List:\n%s\nStop Word Number: %d" % ('\t'.join(self.words), len(self.words))


class WordsFilter(object):
    """过滤词
    主要有去停用词, 词干还原
    """
    def __init__(self, sourceword, stopwordlist):
        """
        @param sourceword: 待过滤的词
        @type sourceword: list
        @param stopwordlist: 停用词表
        @type stopwordlist: list
        @return: 返回过滤后的词表
        """
        self.purewords = []
        if sourceword and stopwordlist:
            self.purewords = []
            for w in sourceword:
                if w in stopwordlist:
                    while sourceword.count(w):
                        sourceword.remove(w)
            
                self.purewords.append(w)
    
    def __str__(self):
        return '\t'.join(self.purewords)
        
def get_words_frequnce():
    """将文件中去掉无用的东西，如链接等，然后记录词频
    """
    pt = re.compile(r'\b\w*\b')
    ft = open(yahoo_txt, 'r')
    wf_dict = {}
    all_words = 0
    
    while 1:
        line = ft.readline()
        if not line:
            break
        line = line.strip()
        word_list = pt.findall(line)
        for w in word_list:
            if w in stopwords_list:
                continue
            if wf_dict.has_key(w):
                wf_dict[w] += 1
            else:
                wf_dict[w] = 1
            all_words += 1
            print w
    ft.close()
    
    for k in wf_dict.keys():
        wf_dict[k] = wf_dict[k]*1.0/all_words
    #print wf_dict
    print all_words
    pickle.dump(wf_dict, open(yahoo_dir + 'txt\\' + 'wf.dump', 'w'))

def search_wf(aword):
    """查找某个word的词频
    """
    try:
        wf_dict = pickle.load(open(yahoo_dir + 'txt\\' + 'wf.dump'))
        if wf_dict.has_key(aword):
            return wf_dict[aword]
        else:
            return None
    except Exception, e:
        print e

def get_usage():
    usage = """
        %prog [options] xxx
        """
    return usage

def get_version():
    version = """
        %prog 1.0
        """
    return version

def execute_from_command_line(argv=None):
    if argv is None:
        argv = sys.argv
    # Parse the command-line arguments.
    parser = OptionParser(usage=get_usage(), version=get_version())
    parser.add_option('-s', '--search', help='搜索给定单词的词频', dest='keyword')
    parser.add_option('-w', '--word', help='提取一个文本中的单词', dest='filename')
    parser.add_option('-f', '--frequence', help='统计文本中的词频', action='store_true', dest='frequence')
    parser.add_option('-l', '--load', help='加载停用词表', dest='loadswl')

    options, args = parser.parse_args(argv[1:])
    if options.filename:
        t = WordSegmentor(options.filename).analysis()
        if t:
            print t
        print 'After\n'
        print WordsFilter(t, StopWordList().words)
        
    if options.loadswl:
        print StopWordList(options.loadswl)
        
    if options.frequence:
        get_words_frequnce()
    
    if options.keyword:
        print search_wf(options.keyword)
        
if __name__ == "__main__":
    execute_from_command_line()
