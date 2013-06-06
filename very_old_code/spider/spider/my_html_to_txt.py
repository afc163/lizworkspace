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

import os
from html2txt import *
#import chardet
import sys
reload(sys)
sys.setdefaultencoding('utf8')

def execute_from_command_line():
    yahoo_dir = 'J:\\yahoo_data\\'
    yahoo_txt_file = open('J:\\yahoo_data\\all.txt', 'w')
    
    start = 1
    while 1:
        filename = yahoo_dir + str(start) + '.html'
        if os.path.isfile(filename):
            
            fp = open(filename, 'r')
            htmltxt = ''.join(fp.readlines())
            fp.close()
            
            #codedetect = chardet.detect(htmltxt)["encoding"]				#检测得到编码方式
            #print codedetect
	    #htmltxt = unicode(htmltxt, codedetect).encode('utf-8')
	
            #target_filename = yahoo_txt_dir + str(start) + '.txt'
            yahoo_txt_file.write(html2txt(htmltxt))
            print 'Success change html to txt'
            
            start += 1
        else:
            break
    yahoo_txt_file.close()

#ftp://ftp.cogsci.princeton.edu/pub/wordnet/2.0/WordNet-2.0.exe

if __name__ == '__main__':
    execute_from_command_line()