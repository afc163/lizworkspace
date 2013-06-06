#/usr/bin/python
#coding:utf-8

"""测试正则式和BeautifulSoup
"""

import re

pt = re.compile(r'<h3 class="r".*?href="(?P<url>.*?)".*?>(?P<head>.*?)</a>.*?<div class="s">(?P<summary>.*?)<br')

def _html2txt(s):
        '''去掉 html 标记
        '''
        return re.sub(r'<[^>]+>', '', s)
def rr():
    for (url, head, summary) in pt.findall(open('search.html').read()):
        print _html2txt(head), _html2txt(summary)

from BeautifulSoup import BeautifulSoup
def soup():
    soup = BeautifulSoup(open('lookup.htm').read())
    for i in soup.findAll(text="Searched for&nbsp;"):#'td', {"class":"grey"}):
        #if 'Searched' not in str(i):
        #    continue
        print i
        print i.findNext('td').a.renderContents()#for i in soup.findAll('li', {"class":"g"}):
        #print i.h3.a["href"]
        #print _html2txt(i.h3.renderContents())
        #d  = i.div.renderContents()
        #print _html2txt(d[:d.find("<br")])
        #print 
        
URLPT = re.compile(r'max=(?P<num>\d+)')
def rere():
    # 获取url, 直接使用正则
    url = URLPT.findall(open('lookup.htm').read())[0]
    print "http://www.google.com/history/lookup?hl=en&max=%s" % url
    
if __name__ == "__main__":
    #soup()
    rere()

# <html>
#  <head>
#   <title>
#    Page title
#   </title>
#  </head>
#  <body>
#   <p id="firstpara" align="center">
#    This is paragraph
#    <b>
#     one
#    </b>
#    .
#   </p>
#   <p id="secondpara" align="blah">
#    This is paragraph
#    <b>
#     two
#    </b>
#    .
#   </p>
#  </body>
# </html>