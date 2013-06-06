#!/bin/python
# coding: utf-8

""" My Spider
@author: shengyan
@license: ...
@contract: shengyan1985@gmail.com
@see: ...
@version:0.1
@todo: get all pages in yahoo travel
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
        self.allURL = []
        self.htmlFileQueue = []
        self.patterm = r'href=["\'](?P<url>%s[^ "\']+)["\']>' % self.startURL #(.jpg)(.gif)(.png)
        
        if os.path.isdir('/media/VBA/yahoo_data'):
            pass
        else:
            os.mkdir('/media/VBA/yahoo_data')

    def analyzeOne(self, filename):
        """
        """
        fp = open(filename, 'r')
        pt = re.compile(self.patterm)
        while 1:
            s = fp.readline()
            if not s:
                break
            url = pt.findall(s)
            if url:
                for one in url:
                    if '?' in one:
                        continue
                    if one in self.allURL:
                        continue
                    self.downloadURL(one)
        fp.close()
            
    def analyze(self):
        """分析初始页面，得到符合的url，等等
        """
        if self.downloadURL(self.startURL):
            # 初始URL是正确的，并且能够保存至本地文件
            #todo: 分析首页   
            while len(self.htmlFileQueue):     
                startFilename = self.htmlFileQueue[0]
                del self.htmlFileQueue[0]
                self.analyzeOne(startFilename)
        else:
            pass#print '无法下载%s' % self.startURL

    def downloadURL(self, url):
        """下载给定url的页面，保存至本地文件
        @param url: 指定url
        @type url: string
        @todo: ...
        """
        try:
            #尝试打开链接
            fp = urllib2.urlopen(url)
        except:
            try:
                fp = urllib2.urlopen(url)
            except:
                try:
                    fp = urllib2.urlopen(url)
                except:
                    #打开链接失败，输出log，并退出
                    #todo: print information to log and not exit
                    print 'ERROR: 无法下载 %s' % url
                    return 0
       
        self.allURL.append(url)
        #打开文件，新建，将url对应页面保存
        filename = '/media/VBA/yahoo_data' + os.sep + str(len(self.allURL)) + '.html'
        op = open(filename, 'wb')
        while 1:
            s = fp.read()
            if not s:
                break
            op.write(s)
        fp.close()
        op.close()
        self.htmlFileQueue.append(filename)
        print '正确获取%s并保存!' % url
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
    print spider.allURL
    print 'Number: ', len(spider.allURL)

if __name__ == '__main__':
    execute_from_command_line()