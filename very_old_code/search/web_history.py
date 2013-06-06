#!/usr/bin/python
#coding:utf-8

"""web_history.py
抓取google webhistory页面, 首先在登录页面获得cookies, 然后依次抓取所有web history页面保存至本地, 最后分析这些页面提取关键词, 根据关键词更新UIM
"""

import sys, os
import cookielib, urllib2, urllib
import re
from BeautifulSoup import BeautifulSoup
import time
try:
    import cPickle as pickle
except:
    import pickle

HISTORY_PAGE = "history_page" # 用于存放抓下来的历史页面目录
HISTORY_KEYWORDS = pickle.load(open('HISTORY_KEYWORDS')) # 存放历史搜索关键词
HISTORY_URL = "http://www.google.com/history/lookup?hl=en&max=%s" # 基本路径
URLPT = re.compile(r'lookup\?hl=en&max=(?P<num>\d+)')
UPDATE = False  # 为False时, 表只抓web历史首页

try:
    # 登录获取cookies
    cj = cookielib.CookieJar()
    opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
    urllib2.install_opener(opener)

    opener.addheaders = [('User-Agent','Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.4) Gecko/20061201 Firefox/2.0.0.6 (Ubuntu-feisty)')]
    url_login = 'https://www.google.com/accounts/ServiceLoginAuth?service=hist'
    body = (('Email','shengyan1985@gmail.com'), ('Passwd','...')) # 密码改掉!
    reqlogin = opener.open(url_login,urllib.urlencode(body))  #这时，cookie已经进来了。

    print 'The Headers of the Login Page:'
    print reqlogin.info()
except:
    sys.exit(-1)

def analysis_page(htmlpage):
    """分析一个页面, 获得该页面上的历史关键词和接下来的历史页面url
    @param htmlpage: 需要分析的页面
    @type htmlpage: string
    @return: 该页面上的接下来历史页面的url
    """
    # 获取关键词
    soup = BeautifulSoup(htmlpage)
    for i in soup.findAll(text="Searched for&nbsp;"):
       HISTORY_KEYWORDS.append(i.findNext('td').a.renderContents())
    
    # 获取url, 直接使用正则, 没有把url保存下来.
    url = URLPT.findall(htmlpage)
    print url
    if url:
        return HISTORY_URL % url[0]
    return ''
    
def down_page(url):
    """抓取url页面, 并保存至本地, 可以不用存储.
    @param url: 链接名 
    @type url: string
    @return: 返回页面string流, 如果抓取不下来, 则为''
    """
    try:
        req = opener.open(url)
        print 'get url: ', req.geturl()
        hid = url.find("max=")
        if hid < 0:
            hid = 0
        else:
            hid = url[hid+4:]
        reqstring = req.read()
        open(os.path.join(HISTORY_PAGE, "webhistory_%s.html" % hid), 'w').write(reqstring)
        print 'download ok'
        return reqstring
    except Exception,e:
        print e
        return ''

# 抓取web history页面, 
# 首先从初始页面开始, 之后的链接形如: 
# http://www.google.com/history/lookup?hl=en&max=1227700022545618
# 这个链接是在<td class="bl"><a href="./lookup?hl=en&max=1221653117260016">Older ›</a></td>中
html = down_page('http://www.google.com/history/?hl=en&max=1196672908518040') # 初始链接
while html:
    new_url = analysis_page(html)
    if UPDATE and new_url:
        html = down_page(new_url)
    else:
        break
    #time.sleep(5)

pickle.dump(HISTORY_KEYWORDS, open("HISTORY_KEYWORDS", 'w'))
#print 'ALL HISTORY_KEYWORDS:'
#for i in HISTORY_KEYWORDS:
#    print i, ' ',
