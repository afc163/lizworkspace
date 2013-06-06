import sys
from optparse import OptionParser
import urllib2
import re

def downloadURL(url, filename):
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

def getURL(url):
    try:
        fp = urllib2.urlopen(url)
    except:
        print 'get url: %s exception' % url
        return []
    pattern = re.compile('http://sports.sina.com.cn/[^\>]+.shtml')
    while 1:
        s = fp.read()
        if not s:
            break
        urls = pattern.findall(s)
    fp.close()
    return urls

def spider(startURL, times):
    urls = []
    urls.append(startURL)
    i = 0
    while 1:
        if i > times:
            break
        if len(urls) > 0:
            url = urls.pop(0)
            print 'in spider: ', url, len(urls)
            downloadURL(url, str(i)+'.html')
            i = i + 1
            if len(urls) < times:
                urllist = getURL(url)
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
    parser = OptionParser(usage="""spider.py [options] init_url times""")
    parser.add_option('--version', help='Show Version Information')
    parser.add_option('-u', '--url', help='Give one URL, Default: http://sports.sina.com.cn', default='http://sports.sina.com.cn')
    parser.add_option('-t', '--time', help='Give Time, Default: 10', default='10')
    #........

    options, args = parser.parse_args(argv[1:])
    #check url is valid or not
    spider(options.url, options.time)

if __name__ == '__main__':
    execute_from_command_line()