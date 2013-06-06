#!/usr/bin/python
#coding:utf-8

"""
来自: http://www.voidspace.org.uk/python/articles/cookielib.shtml 上的例子
"""

import os.path
import urllib2

# 要保存的cookies所在文件名
COOKIEFILE = 'cookies.lwp'

cj = None
ClientCookie = None
cookielib = None

try:
    # 看cookielib是否可用
    import cookielib
except ImportError:
    try:
        # cookielib不可用的话, 尝试ClentCookie
        import ClientCookie
    except ImportError:
        # 如果ClientCookie也不可用
        urllopen = urllib2.urlopen
        Request = urllib2.Request
    else:
        # ClientCookie导入,
        urlopen = ClientCookie.urlopen
        Request = ClientCookie.Request
        cj = ClientCookie.LWPCookieJar()
else:
    urlopen = urllib2.urlopen
    Request = urllib2.Request
    cj = cookielib.LWPCookieJar()
    
if cj is not None:
    # 也就是上述成功导入ClientCookie或cookielib
    if os.path.isfile(COOKIEFILE):
        # 已经存在cookie文件了, 则load进来
        cj.load(COOKIEFILE)
    if cookielib is not None:
        # 如果使用cookielib, 需获得HTTPCookieProcessor, 并安装opener
        opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
        urllib2.install_opener(opener)
    else:
        # 如果使用ClientCookie, 同样
        opener = ClientCookie.build_opener(ClientCookie.HTTPCookieProcessor(cj))
        CLientCookie.install_opener(opener)


theurl = 'http://www.google.com/history/'

# 如果是POST类型请求, 应使用urllib.urlencod(somedict)
txdata = None

# 假装浏览器, a user agent
txheaders = {'User-Agent':'Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.4) Gecko/20061201 Firefox/2.0.0.6 (Ubuntu-feisty)'}

try:
    # 创建一个请求对象
    req = Request(theurl, txdata, txheaders)
    # 打开
    handle = urlopen(req)
except IOError, e:
    print 'Failed to open "%s".' % theurl
    if hasattr(e, 'code'):
        print 'failed with error code - %s.' % e.code
    elif hasattr(e, 'reason'):
        print "The error object has the following 'reason' attribute :"
        print e.reason
        print "This usually means the server doesn't exist,",
        print "is down, or we don't have an internet connection."
    sys.exit()
else:
    print 'The Headers of the Page:'
    print handle.info()
    # handle.read() returns the page
    # handle.geturl() returns the true url of the page fetched
    # (in case urlopen has followed any redirects, which it sometimes does)
print
if cj is None:
    print "We don't have a cookie library available - sorry."
    print "I can't show you any cookies."
else:
    print 'These are the cookies we have received so far :'
    for index, cookie in enumerate(cj):
        print index, '  :  ', cookie
    cj.save(COOKIEFILE)                     # 保存cookie
    