#!/bin/python
# coding: utf-8

""" My Spider
@author: shengyan
@contract: shengyan1985@gmail.com
@version:0.1
@todo: 多线程实现，抓取多个网页，并分析网页
"""

import os
import sys
from optparse import OptionParser
import urllib2
import re
from datetime import datetime
import chardet

DIR = '/home/shengyan/workspace/spider/'
LOG = None

class Spider(object):
    """ 
    @todo: Multi-Thread
    """
    def __init__(self, startURL):
        """初始化
        @param startURL: 初始给定的URL
        @type startURL: string
        """
        if startURL[-1] <> '/':
            startURL += '/'
        self.startURL = startURL
        # 保存所有抓取的链接
        self.allURL = []
        self.urlNum = 0
        self.htmlFileQueue = []
        self.patterm = r'<a(?:.*?)href=["\']?(?P<url>[^ >"\']+)["\']?(?:[^>]*?)>(?P<name>[^<]*?)</a>'
        #self.patterm = r'<a(?:.*?)href=(?P<url>[^ >]+)(?:[^>]*?)>(?P<name>[^<]*?)</a>'

    def analyzeOne(self, filename):
        """分析一个页面上面的连接
        """
        fp = open(filename, 'r')
        pt = re.compile(self.patterm)
        while 1:
            s = fp.readline()
            if not s:
                break
            un = pt.findall(s)
            for (url, name) in un:
                if url in self.allURL:
                    continue
                else:
                    print url, name    # 这里直接输出到屏幕，也可保存起来
                    #self.downloadURL(url)  # 这里不再继续抓取
        fp.close()
            
    def analyze(self):
        """分析初始页面，得到符合的url，等等
        """
        if self.downloadURL(self.startURL):
            # 初始URL是正确的，并且能够保存至本地文件
            #分析首页，提取首页url和名字
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
        
        print 'download...... ', url
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
        
        page = fp.read()                                            #读取内容
        fp.close()
        codedetect = chardet.detect(page)["encoding"]               #检测得到编码方式
        if codedetect <> 'utf-8':                                   #是否是utf-8
            try:
                page = unicode(page, codedetect)                    #不是的话，则尝试转换
                page = page.encode('utf-8')
            except:
                print u"bad unicode encode try!"
                return 0
        op.write('%s' % page)
        op.close()
        self.htmlFileQueue.append(filename)
        LOG.write('='*10)
        LOG.write('\n正确获取%s并保存!\n' % url)
        return 1

def execute_from_command_line(argv=None):
    if argv is None:
        argv = sys.argv
    # Parse the command-line arguments.
    parser = OptionParser(usage="""hao123_spider.py [options] init_url""")
    parser.add_option('--version', help='Show Version Information')
    parser.add_option('-u', '--url', help='Give one URL, Default: http://hao123.com/', default='http://hao123.com/')

    options, args = parser.parse_args(argv[1:])
    
    slash_index = options.url[7:].find('/')
    web_site = options.url[7:7+slash_index]
    
    global DIR
    DIR = DIR + web_site + os.sep
    print 'save in ', DIR
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
        return -1
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