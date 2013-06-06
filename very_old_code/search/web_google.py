#!/usr/bin/python
# -*- coding: UTF-8 -*-
"""抓取 google 搜索结果
@version: $Id$
@author: U{Jiahua Huang <jhuangjiahua@gmail.com>} 
@license: LGPL
@see: www.hiweed.com
"""

import re
import urllib2

## 设定 urllib2 的 User-Agent
opener = urllib2.build_opener()
opener.addheaders = [('User-Agent','Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.4) Gecko/20061201 Firefox/2.0.0.6 (Ubuntu-feisty)')]

## 搜索 URL ， 需要 %(key,page)
search_url = 'http://www.google.cn/search?q=%s&num=100&complete=1&hl=zh-CN&newwindow=1&client=firefox&rls=com.ubuntu:en-US:official&start=%s&sa=N'

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
            print 'jo'
            rev.append(['test', _html2txt(head), _html2txt(summary)])

        """###s1 = s.split(r'<h2 class=r>')[1:]
        s1 = s.split('<h3 class=r>')[1:]
        print s1
        for i in s1:
                ###if i.rfind('<span class="f">文件格式:</span>')>-1:
                ###        continue
                #url = i.split('"')[1]
                url = re.findall('href=".*?"', i)[0][6:-1]
                ###title = _html2txt(i.split('</h2>')[0])
                title = _html2txt(i.split('</h3>')[0])
                try:
                        ###summary = _html2txt(re.findall(r'<td.*?<br>', i)[0]).replace('"','').replace("'","")
                        summary = _html2txt(re.findall(r'<div class="s">.*?<br/>', i)[0]).replace('"','').replace("'","")
                except:
                        continue
                rev.append([url, title, summary])
                """
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



