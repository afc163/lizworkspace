#!/usr/bin/python
# -*- coding: UTF-8 -*-

"""抓取 google 搜索结果
@author: shengyan1985@gmail.com
@license: LGPL
@note: 改自web_google.py
"""

import re
import urllib2

## 设定 urllib2 的 User-Agent
opener = urllib2.build_opener()
opener.addheaders = [('User-Agent','Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.4) Gecko/20061201 Firefox/2.0.0.6 (Ubuntu-feisty)')]

## 搜索 URL ， 需要 %(key,page)
search_url = 'http://www.google.com/search?q=%s&num=100&complete=1&hl=zen&newwindow=1&client=firefox&rls=com.ubuntu:en-US:official&start=%s&sa=N'

def _html2txt(s):
        '''去掉 html 标记
        '''
        return re.sub(r'<[^>]+>', '', s)

def _gethtml(key, page=0):
        '''抓取 google 搜索结果页面
        return 源码
        '''
        key = urllib2.quote(key)
        page = 100*int(page)
        url = search_url%(key, page)
        try:
                return opener.open(url).read()
        except:
                return ''

def _dorev(s):
        '''分析抓回的 google 搜索结果页面 
        return [[url, title, summary],]
        '''
        if not s: return []
        rev=[]
        pt = re.compile(r'<h3 class="?r"?>(?P<head>(?s).*?)</h3><div class="?s"?>(?P<summary>(?s).*?)</div>')
        for (head, summary) in pt.findall(s):
            rev.append(['test', _html2txt(head), _html2txt(summary)])
        return rev

def getrev(key):
        '''获取搜索结果
        return [[url, title, summary],]
        '''
        return _dorev(_gethtml(key))

if __name__=="__main__":
    import sys
    for url, title, summary in getrev(' '.join(sys.argv[1:])):
        print 'title:', title
        print 'url:', url
        print 'summary:', summary
        print