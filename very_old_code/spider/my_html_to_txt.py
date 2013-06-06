#!/bin/python
# coding: utf-8

""" My Html To Txt
@author: shengyan
@license: ...
@contract: shengyan1985@gmail.com
@see: ...
@version:0.1
@todo: html page to text
"""

from optparse import OptionParser
import pickle
import re
import os
from html2txt import *
import chardet
import sys
reload(sys)
sys.setdefaultencoding('utf8')

yahoo_dir = '/media/VBA/yahoo_data/'   ######change
yahoo_txt = yahoo_dir + 'txt/all.txt'

def html_to_txt():
    """将多个html文件合并为一个txt文件，统一编码为utf-8 or ascii
    """
    ft = open(yahoo_txt, 'w')
    start = 1
    while 1:
        filename = yahoo_dir + str(start) + '.html'
        if os.path.isfile(filename):
            
            fp = open(filename, 'r')
            htmltxt = ''.join(fp.readlines())
            if not htmltxt or not len(htmltxt):
                continue
            fp.close()
            
            codedetect = chardet.detect(htmltxt)["encoding"]				#检测得到修改之前的编码方式
            print codedetect
	    if not codedetect in ['utf-8', 'ascii']:
	        htmltxt = unicode(htmltxt, codedetect).encode('utf-8')
	        codedetect = chardet.detect(htmltxt)["encoding"]				#检测得到修改之后的编码方式
                print 'change', codedetect
            
            #target_filename = yahoo_txt_dir + str(start) + '.txt'
            #ft = open(target_filename, 'w')
            ft.write(html2txt(htmltxt))
            
            print 'Success change html to txt %s' % start
            
            start += 1
        else:
            break
    ft.close()

stopwords_list = ['http', 'www', 'none']

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
    pickle.dump(wf_dict, open(yahoo_dir + 'txt/' + 'wf.dump', 'w'))

def search_wf(aword):
    """查找某个word的词频
    """
    try:
        wf_dict = pickle.load(open(yahoo_dir + 'txt/' + 'wf.dump'))
        if wf_dict.has_key(aword):
            return wf_dict[aword]
        else:
            return None
    except Exception, e:
        print e

def execute_from_command_line(argv=None):
    if argv is None:
        argv = sys.argv
    # Parse the command-line arguments.
    parser = OptionParser(usage="""my_html_to_txt.py [options] xxx""")
    parser.add_option('-s', '--search', help='Search keywords frequence', dest='keyword')
    parser.add_option('-c', '--create', help='Create a txt file from many html', action='store_true', dest='create')
    parser.add_option('-f', '--frequence', help='Get Words Frequence', action='store_true', dest='frequence')
    #........

    options, args = parser.parse_args(argv[1:])
    if options.create:
        html_to_txt()
        
    if options.frequence:
        get_words_frequnce()
    
    if options.keyword:
        print search_wf(options.keyword)
        
if __name__ == '__main__':
    execute_from_command_line()
    
    