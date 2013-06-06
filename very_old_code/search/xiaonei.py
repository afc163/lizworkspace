#!/usr/bin/env python
#coding=utf-8

""" someone的一个设置Cookie的例子
"""

import urllib2
import httplib
import os

def xiaonei(url):
    httplib.HTTPConnection.debuglevel = 1 
    request = urllib2.Request(url)
    
    opener = urllib2.build_opener()
    
    request.add_header('Cookie','__utmz=123456789.1234567890.1.1.utmccn=(direct)|utmcsr=(direct)|utmcmd=(none); showrelation${requestScope.hostid}=0; id=123456789; wh_email=rms@gnu.com; wh_key=312345678924e8c1234567894631ae63; mop_uniq_ckid=10.252.252.255_1202708445_1030545018; __utma=123456789.1892817461.1202705880.1202734124.1202821653.4; __utma=157501179.1128220497.1202705984.1202705984.1202823196.2; __utmz=157501179.1202823196.2.2.utmccn=(referral)|utmcsr=xiaonei.com|utmcct=/getuser.do|utmcmd=referral; XNESSESSIONID=abcM149LSk-KJ8yq3VnGr; __utmb=123456789; __utmc=123456789; userid=123456789; univid=9007; gender=1; univyear=2004; hostid=123456789; __utmb=123456789; __utmc=123456789; societyguester=70e2789beb6d8b53fbc6455f60871bfb')
    request.add_header('Referer',url)
    request.add_header('User-Agent','Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.8.1.12) Gecko/20080201 Firefox/2.0.0.12')
                                     
    content = opener.open(request).read()
    f = open(os.path.join(os.getcwd(),'content.html'),'w')
    f.write(content)
    f.close()


if __name__ == '__main__':
    xiaonei('http://xiaonei.com/GetUser3.do?id=xxxxxxxxx')
