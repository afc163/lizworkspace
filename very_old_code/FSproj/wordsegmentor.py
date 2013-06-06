#!/usr/bin/python
#coding=utf-8

""" wordsegmentor.py
@contract: shengyan1985@gmail.com
@version:0.1
"""

import os
from optparse import OptionParser
import re
import collections

import sys
reload(sys)
sys.setdefaultencoding('utf8')

try:
    import cPickle as pickle
except:
    import pickle

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

PATTERM = re.compile(r"[a-z]+")
def get_words(text):
    """获得text中文本单词
    """
    return PATTERM.findall(text.lower())

def words_filter(sourceword, stopwordlist):
        """去停用词
        @param sourceword: 待过滤的词
        @type sourceword: list
        @param stopwordlist: 停用词表
        @type stopwordlist: list
        @return: 返回过滤后的词表
        """
        purewords = []
        if sourceword and stopwordlist:
            for w in sourceword:
                if w in stopwordlist:
                    while sourceword.count(w):
                        sourceword.remove(w)
            
                purewords.append(w)
        return purewords

from wordnet import *
import Stemmer

def words_filter1(sourceword):
    """根据WordNet过滤, 这个是没有用停用词过滤的,而是对Wordnet词典进行查找,找到就加入,没有找到的话先还原下,然后再查,仍然没有则丢弃
    """
    stemmer = Stemmer.Stemmer('english')
    #print stemmer.stemWords(sourceword)  直接用stemmer,有些词不对,,,,
    #return words_filter(stemmer.stemWords(sourceword), StopWordList().words)
    stopwordlist = StopWordList().words
    
    purewords = []
    for oneword in sourceword:
        
        for d in Dictionaries:
            part = d.pos
            try:
                getWord(oneword, part)
                purewords.append(oneword)
                # 只要查到一种就退出
                break
            except:
                pass
        else:
            # 这里再次用stemmer试探还原词干,,,比如说查找dogs
            oneword = stemmer.stemWord(oneword)
            for d in Dictionaries:
                part = d.pos
                try:
                    getWord(oneword, part)
                    purewords.append(oneword)
                    break
                except:
                    pass
            else:
                # 还原后还是找不到,那就没办法了...如果这个词不在停用词表中的话,那还是保存起来
                pass
                #if oneword not in stopwordlist:
                #    purewords.append(oneword)
    return purewords
    return words_filter(purewords, StopWordList().words)
    
def get_words_frequnce(features=None):
    """记录词频,从1开始,没有出现的词记为1,避免0值,出现一次就加1
    """
    model = collections.defaultdict(lambda: 1)
    for f in features:
        model[f] += 1
    return model

def search_words_frequnce(aword):
    """查找某个word的词频
    """
    return
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
    
    parser = OptionParser(usage=get_usage(), version=get_version())
    parser.add_option('-s', '--search', help='搜索给定单词的词频', dest='keyword')
    parser.add_option('-w', '--word', help='提取一个文本中的单词', dest='filename')
    parser.add_option('-f', '--frequence', help='统计文本中的词频', action='store_true', dest='frequence')
    parser.add_option('-l', '--load', help='加载停用词表', dest='loadswl')

    options, args = parser.parse_args(argv[1:])
    if options.filename:
        #print get_words_frequnce(words_filter(get_words(file(options.filename).read()), StopWordList().words))
        print get_words_frequnce(words_filter1(get_words(file(options.filename).read())))
        
    if options.loadswl:
        print StopWordList(options.loadswl)
        
    if options.frequence:
        pass
    
    if options.keyword:
        pass
        
if __name__ == "__main__":
    execute_from_command_line()
