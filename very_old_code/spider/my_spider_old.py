#!/bin/python
# coding: utf-8

"""
@author: shengyan
@license: oo
@contract: shengyan1985@gmail.com
@version:0.1
@todo: ...
"""


import sys
from optparse import OptionParser
import urllib2
import re

ALL_URL = list()

class Spider(object):
    def __init__(self, startURL, times):
        self.startURL = startURL
        self.times = times

    def downloadURL(self, url, filename):
        '''download one page'''
        try:
            fp = urllib2.urlopen(url)
        except:
            print 'download exception %s' % url
            return 0
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

    def spider(self, startURL, times):
        urls = []
        urls.append(startURL)
        i = 0
        while 1:
            if i > times:
                break
            if len(urls) > 0:
                url = urls.pop(0)
                print 'in spider: ', url, len(urls)
                self.downloadURL(url, str(i)+'.html')
                i = i + 1
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
    parser.add_option('-u', '--url', help='Give one URL, Default: http://www.vbarter.cn', default='http://www.vbarter.cn')
    parser.add_option('-t', '--time', help='Give Time, Default: 10', default='10')
    #........

    options, args = parser.parse_args(argv[1:])
    #check url is valid or not
    spider = Spider(options.url, options.time)

if __name__ == '__main__':
    execute_from_command_line()

