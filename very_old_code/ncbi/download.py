#!/usr/bin/python
#  -*- coding: utf-8 -*-
#  Author : liz
#  Mail   : shengyan1985@gmail.com

"""
自动下载搜索NCBI上特定数据库上包含关键词的结果列表
"""

import sys
import re
import httplib2, urllib, urllib2
import stackless
import stacklesssocket
stacklesssocket.install()
import Queue
import os
from ConfigParser import ConfigParser
try:
    import cPickle as pickle
except:
    import pickle
import time

SEARCH_URL = "http://www.ncbi.nlm.nih.gov/sites/entrez"
ITEM_URL = "http://www.ncbi.nlm.nih.gov/entrez/viewer.fcgi?db=%s&val=%s"
RESULT = []
RESULT_FILE = "result"
HTML_DIR = os.path.abspath('html')#os.path.join(os.path.dirname(__file__), 'html')
if not os.path.isdir(HTML_DIR):
    os.mkdir(HTML_DIR)
TXT_DIR = os.path.abspath('txt')#os.path.join(os.path.dirname(__file__), 'txt')
if not os.path.isdir(TXT_DIR):
    os.mkdir(TXT_DIR)

# 读取config.ini中变量
CONFIG = ConfigParser()
CONFIG.read("config.ini")
DB = CONFIG.get("search", "db")
TERM = CONFIG.get("search", "term")
PAGESIZE = CONFIG.get("var", "pagesize")
THREAD_NUM = int(CONFIG.get("var", "threadnum"))

APT = re.compile(r'href="/entrez/viewer.fcgi\?db=(?:\w+)&amp;id=(?P<id>\d+)">(?P<itemname>\w+)<')
PAGENUMPT = re.compile(r'\s(?P<id>\d+)<a name="EntrezSystem2\.PEntrez\.Nuccore\.Sequence_ResultsPanel\.Pager\.Next"\s')
TXTPT = re.compile(r'<pre class="genbank">(.+)</pre>')

def analysis_page(htmlpage, get_page_num=False):
    """分析页面, 获得该页面上的搜索条目并保存至RESULT, 可选获得搜索结果页面数
    """
    for line in htmlpage.split('\n'):
        for (idd, itemname) in APT.findall(line):
           RESULT.append((idd, itemname))
    
    if get_page_num:
         for p in PAGENUMPT.findall(htmlpage, re.S):
            return int(p)
    return 0

def try_download(h, pagenumber):
    pfn = os.path.join(HTML_DIR, "%s_page%s.html" % (TERM, pagenumber))
    if os.path.isfile(pfn):
        print "Get %s" % pfn
        return open(pfn).read()
    
    params = urllib.urlencode({"EntrezSystem2.PEntrez.DbConnector.Db":DB,
                                "EntrezSystem2.PEntrez.DbConnector.TermToSearch":TERM,
                                "EntrezSystem2.PEntrez.Nuccore.SearchBar.Db":DB,
                                "EntrezSystem2.PEntrez.Nuccore.SearchBar.Term":TERM,
                                "EntrezSystem2.PEntrez.Nuccore.CommandTab.LimitsActive":"false",
                                "EntrezSystem2.PEntrez.Nuccore.Sequence_ResultsPanel.Sequence_DisplayBar.PageSize":PAGESIZE,
                                "EntrezSystem2.PEntrez.Nuccore.Sequence_ResultsPanel.Sequence_DisplayBar.sPageSize":PAGESIZE,
                                "EntrezSystem2.PEntrez.Nuccore.Sequence_ResultsPanel.HistoryDisplay.Cmd":"PageChanged",
                                "EntrezSystem2.PEntrez.Nuccore.Sequence_ResultsPanel.Pager.CurrPage":pagenumber,
                                })
    headers = {"Content-Type":"application/x-www-form-urlencoded", 
                "Connection":"Keep-Alive", 
                "Referer":SEARCH_URL}
    try:
        resp, content = h.request(SEARCH_URL, method="POST", body=params, headers=headers)
        if resp.status == 200:
            open(pfn, 'w').write(content)
            print 'Download %s Page %s OK' % (SEARCH_URL, pagenumber)
            return content
        else:
            raise
    except Exception,e:
        try:
            resp, content = h.request(SEARCH_URL, method="POST", body=params, headers=headers)
            if resp.status == 200:
                open(pfn, 'w').write(content)
                print 'Download %s Page %s OK' % (SEARCH_URL, pagenumber)
                return content
            else:
                raise
        except Exception,e:
            try:
                resp, content = h.request(SEARCH_URL, method="POST", body=params, headers=headers)
                if resp.status == 200:
                    open(pfn, 'w').write(content)
                    print 'Download %s Page %s OK' % (SEARCH_URL, pagenumber)
                    return content
                else:
                    raise
            except Exception,e:
                print 'Download %s Page %s Wrong: %s' % (SEARCH_URL, pagenumber, e)
                return ''

def ag(s, e):
    h = httplib2.Http()
    for pagenumber in xrange(s, e+1):
        analysis_page(try_download(h, pagenumber))

def download_item(idd, name):
    """下载各个结果条目
    """
    def down():
        f = urllib2.urlopen(ITEM_URL % (DB, idd)).read()
        open(os.path.join(HTML_DIR, "%s_%s.html" % (idd, name)), 'w').write(f)
        for p in re.findall(r'<pre class="genbank">(.+)</pre>', f, re.S):
            open(os.path.join(TXT_DIR, "%s_%s.txt" % (idd, name)), 'w').write(p)
        print "save %s ok" % ITEM_URL % (DB, idd)
    try:
        tfn = os.path.join(TXT_DIR, "%s_%s.txt" % (idd, name))
        if not os.path.isfile(tfn):
            down()
        else:
            print "Get %s" % tfn
    except Exception,e:
        try:
            down()
        except Exception,e:
            try:
                down()
            except Exception,e:
                print "download %s error: %s" % (ITEM_URL % (DB, idd), e)

def download_item_all(s, e):
    for i in range(s, e+1):
        download_item(RESULT[i][0], RESULT[i][1])
    
def download_search_page():
    """不断抓取搜索页面, 直到全部抓完.
    """
    global RESULT
    try:
        RESULT = pickle.load(open(RESULT_FILE))
    except Exception,e:
        total_page_num = analysis_page(try_download(httplib2.Http(), 1), True)
        if total_page_num:
            print 'total page ', total_page_num
        
        step = (total_page_num-1-1)/THREAD_NUM + 1
        start = 2
        end = start + step
        for p in xrange(0, THREAD_NUM):
            if end > total_page_num:
                end = total_page_num
            stackless.tasklet(ag)(start, end,)
            start = end + 1
            end = start + step
        stackless.run()
        
        # 保存RESULT
        pickle.dump(RESULT, open(RESULT_FILE, 'w'))

    total_item_num = len(RESULT)
    print "\nObtain %s search results and now, downloading all\n" % total_item_num
    step = (total_item_num-1)/THREAD_NUM + 1
    start = 0
    end = start + step
    for p in xrange(0, THREAD_NUM):
        if end >= total_item_num:
            end = total_item_num-1
        stackless.tasklet(download_item_all)(start, end,)
        start = end + 1
        end = start + step
    stackless.run()

        
if __name__ == "__main__" :
     stime = time.time()
     download_search_page()
     print "Total Time: ", time.time()-stime
     sys.exit(0)

