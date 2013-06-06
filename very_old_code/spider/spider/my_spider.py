#!/bin/python
# coding: utf-8

""" My Spider
@author: shengyan
@license: ...
@contract: shengyan1985@gmail.com
@see: ...
@version:0.1
@todo: ...
"""

import os
import sys
from optparse import OptionParser
import urllib2
import re
from threading import Thread
from threading import RLock


class Spider(object):
    """ 爬虫类，主要是抓取符合一定条件的网页
    @todo: Multi-Thread
    """
    def __init__(self, startURL):
        """初始化
        @param startURL: 初始给定的URL，只要符合这个URL表达式就要抓取下来
        @type startURL: string
        """
        if startURL[-1] <> '/':
            startURL += '/'
        self.startURL = startURL
        # 保存所有抓取的链接
        self.allURL = [startURL]

    def analyze(self):
        """分析初始页面，得到符合的url，等等
        """
        if os.path.isdir('data'):
            pass
        else:
            os.mkdir('data')
        startFilename = self.startURL[7:]
        #todo: 改善文件名规则
        startFilename = 'data' + os.sep + startFilename.replace('/', '_')
        
        if self.downloadURL(self.startURL, startFilename):
            # 初始URL是正确的，并且能够保存至本地文件
            #todo: 分析首页
            print '正确获取%s' % self.startURL
            
            fp = open(startFilename, 'r')
            pattern = re.compile(r'href=["\'](?P<url>%s[^ "\']+)["\']>' % self.startURL)
            while 1:
                s = fp.readline()
                if not s:
                    break
                url = pattern.findall(s)
                if url:
                    for one in url:
                        if '?' in one:
                            continue
                        self.allURL.append(one)
                        onefilename = one[7:]
                        #todo: 改善文件名规则
                        onefilename = 'data' + os.sep + onefilename.replace('/', '_')
                        #self.downloadURL(one, onefilename)
                        print onefilename
            fp.close()
        else:
            print '无法下载%s' % self.startURL

    def downloadURL(self, url, filename):
        """下载给定url的页面，保存至本地文件
        @param url: 指定url
        @type url: string
        @param filename: 保存url为filename的文件
        @type filename: string
        @todo: ...
        """
        try:
            #尝试打开链接
            fp = urllib2.urlopen(url)
        except:
            #打开链接失败，输出log，并退出
            #todo: print information to log and not exit
            return 0
        #打开文件，新建，将url对应页面保存
        op = open(filename, 'wb')
        while 1:
            s = fp.read()
            if not s:
                break
            op.write(s)
        fp.close()
        op.close()
        return 1

    def getURL(self, url):
        """获取url对应页面中的url路径
        @param url: 指定url
        @type url: string
        @todo: ...
        """        
        try:
            fp = urllib2.urlopen(url)
        except:
            print 'get url: %s exception' % url
            return []
        #pattern = re.compile('http://www.vbarter.cn/[^\>]+.shtml')
        pattern = re.compile('<a href="(?P<url>http://[^ "]+)".*>')
        while 1:
            s = fp.read()
            if not s:
                break
            #urls = pattern.findall(s)
            urls = pattern.findall(s)
        fp.close()
        return urls

    def spider(self, startURL):
        urls = []
        urls.append(startURL)
        i = 0
        while len(urls) > 0:
            url = urls.pop(0)
            print 'in spider: ', url, len(urls)
            self.downloadURL(url, str(i)+'.html')
            if len(urls) < times:
                urllist = self.getURL(url)
                print 'test...', urllist
                for url in urllist:
                    if urls.count(url) == 0:
                        urls.append(url)
            else:
                break
        return 1

def execute_from_command_line(argv=None):
    if argv is None:
        argv = sys.argv
    # Parse the command-line arguments.
    parser = OptionParser(usage="""my_spider.py [options] init_url times""")
    parser.add_option('--version', help='Show Version Information')
    parser.add_option('-u', '--url', help='Give one URL, Default: http://lizziesky.blogspot.com/', default='http://lizziesky.blogspot.com/')
    #parser.add_option('-t', '--time', help='Give Time, Default: 10', default='10')
    #........

    options, args = parser.parse_args(argv[1:])

    spider = Spider(options.url)
    spider.analyze()
    print 'all post: ', len(spider.allURL)

if __name__ == '__main__':
    execute_from_command_line()

