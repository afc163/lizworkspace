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
from datetime import datetime

DIR = '/media/VBA/web_data/'
LOG = None

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
        self.urlNum = 0
        self.htmlFileQueue = []
        self.patterm = r'href=["\'](?P<url>%s[^ "\']+)["\'][ >]' % self.startURL #(.jpg)(.gif)(.png)

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
                    #if '?' in one:
                    #    continue
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
            pass

    def downloadURL(self, url):
        """下载给定url的页面，保存至本地文件
        @param url: 指定url
        @type url: string
        @todo: ...
        """
        global DIR
        global LOG
        
        print url
        
        try:
            #尝试3次打开链接
            fp = urllib2.urlopen(url)
        except:
            try:
                fp = urllib2.urlopen(url)
            except:
                try:
                    fp = urllib2.urlopen(url)
                except:
                    #打开链接失败，输出log，并退出
                    LOG.write('='*20)
                    LOG.write('\n无法下载: %s\n' % url)
                    return 0
       
        self.allURL.append(url)
        self.urlNum += 1
        #打开文件，新建，将url对应页面保存
        filename = DIR + str(self.urlNum) + '.html'
        op = open(filename, 'wb')
        while 1:
            s = fp.read()
            if not s:
                break
            op.write(s)
        fp.close()
        op.close()
        self.htmlFileQueue.append(filename)
        LOG.write('='*10)
        LOG.write('\n正确获取%s并保存!\n' % url)
        #print '正确获取%s并保存!' % url
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
    
    slash_index = options.url[7:].find('/')
    web_site = options.url[7:7+slash_index]
    
    global DIR
    DIR = DIR + web_site + os.sep
    print DIR
    if not os.path.isdir(DIR):
        os.mkdir(DIR)
    global LOG
    LOG = open(DIR + 'log.txt', 'w')
    
    LOG.write(datetime.ctime(datetime.now())+'\n')
    spider = Spider(options.url)

    try:
        spider.analyze()
    except Exception,e:
        LOG.write('='*20)
        LOG.write('\nSpider Exit by Exception %s\n\n' % e)
    LOG.write('='*10)
    LOG.write('\nGet All URLs is\n')
    LOG.write('\n'.join(spider.allURL))
    LOG.write('\nAll Url Number: %d\n' % spider.urlNum)
    LOG.write('='*10)
    LOG.write('\nSpider Over!\n')
    LOG.write(datetime.ctime(datetime.now())+'\n')
    LOG.close()
    print 'Done!'

if __name__ == '__main__':
    execute_from_command_line()