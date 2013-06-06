#!/usr/bin/python
#coding:utf-8

"""web_history.py
抓取google webhistory页面, 首先在登录页面获得cookies, 然后依次抓取所有web history页面保存至本地, 最后分析这些页面提取关键词, 根据关键词更新UIM
"""

import cookielib, urllib2, urllib

#COOKIEFILE = 'cookies.lwp'  # 之前尝试使用cookies文件加载, 后来直接改掉.
#cj = cookielib.LWPCookieJar()
#cj.load(COOKIEFILE)

cj = cookielib.CookieJar()
opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
urllib2.install_opener(opener)

opener.addheaders = [('User-Agent','Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.4) Gecko/20061201 Firefox/2.0.0.6 (Ubuntu-feisty)')]
url_login = 'https://www.google.com/accounts/ServiceLoginAuth?service=hist'
body = (('Email','shengyan1985@gmail.com'), ('Passwd','...'))
req1 = opener.open(url_login,urllib.urlencode(body))  #这时，cookie已经进来了。

print 'The Headers of the Page:'
print req1.info()
open("googlewebhistorylogin.html", 'w').write(req1.read()) # the page
print req1.geturl() # the true url of the page fetched
    
url_post = 'http://www.google.com/history/?hl=en'
req2 = opener.open(url_post)

print 'The Headers of the Page:'
print req2.info()
open("googlewebhistory.html", 'w').write(req2.read())
print req2.geturl() # the true url of the page fetched

print cj








"""
'SID':'DQAAALYAAAC39zuCf5kuCvKJy4y2wqFJ0jMhSyfjb7JLoPSsGUSKv3UABkUXI5kCx5pEzi-j7U26m-a3DDWBdOnWm2yM0SHbJ2HHS5FPQihLKe8oYR4K8RAAE-1dVlx0_5-1_BjBctJtO_YRvjSK2UENYb5q15NTDiCBC8XWYm806ayBtdXrnzha5RcI9lXHETSxSD3gWcdfSzguwP_gU302RIsDGcV01cBSSwoQpTjdDjrngjl5KXrd6zQodVChFU1qe51j9F4'
'SS':'DQAAALcAAACJKjJIXkeUMzPY7eHwMXyYbr3WS23_1Hxs9Z2PykQI50RkAszZiSuAfSVHuYeeicHEU20EbrV69lqN94VVW9OM5k19GjSVhA3IYP9QRmX0LCwJEgH0qUYDCskysAW2eUO-tbb4hGOtNbTHpeqdnX5yK7_d_DVanmj9bVgz6b69V2aQNav4nOsrDpwaqyrE8ikGidHv6f2z4kl9zR3RMuMIy_6VeTaHhW7sY4jGTKY7oXwy-pL6oDkpV1TM8m3652s'
'PREF':'ID=db5981fbf883255a:LD=en:CR=2:TM=1227521100:LM=1227677010:DV=AA:GM=1:IG=1:S=Sl0NNFzRDF2LFNnQ'
'TZ':'-480'
'S':'photos_html=DHR50BQIyMPgwqoPzR6LiQ'
'NID':'17=Rb7Euf9bCacRxFEFpM6zG1u6a6ZdSXNhenA9LzdiXthkoP8rLW2gr-iVNWTt6PUdAiOxQL1zmNFoGpDYO1bc1MuydExK5FEPu56t1yd2DFY5cwxIIhIRrovwrwIYhRkL'
'rememberme':'true'
"""

"""
shengyan@LIZZIE:~/lizworkspace/search$ python web_history.py 
The Headers of the Page:
Content-Type: text/html; charset=UTF-8
Cache-control: no-cache, no-store
Pragma: no-cache
Expires: Mon, 01-Jan-1990 00:00:00 GMT
Set-Cookie: LSID=EXPIRED;Domain=.google.com;Path=/;Expires=Mon, 01-Jan-1990 00:00:00 GMT
Set-Cookie: LSID=EXPIRED;Path=/;Expires=Mon, 01-Jan-1990 00:00:00 GMT
Set-Cookie: LSID=EXPIRED;Domain=www.google.com;Path=/accounts;Expires=Mon, 01-Jan-1990 00:00:00 GMT
Set-Cookie: LSID=cl|s.CN:DQAAALUAAAD7cSYg1mVCsEly-thIoznG4WxOVq52pOOyZcwGd2myZZpKZD73UZsKmCGeKobwSptZ1HbH2v1MeETsH12I3ers39qp8RGazMEwQCr-RQu6PjtZIp_mUcVEfsZNQUyg1e5B2tURSZL1Pq0RKNtSFqnovVScJK5JzERZXu5RGYmW17NWmxTp4tz5kNAmP14Io_W9pgwdUN4zLucpvqrKpY3a0N5CSd6RcJpOPYsYhEYhV3aSuPRvYjNQ96iRRySWgMk;Path=/accounts;Secure
Date: Wed, 26 Nov 2008 08:59:21 GMT
X-Content-Type-Options: nosniff
Content-Length: 1448
Server: GFE/1.3
Connection: Close

https://www.google.com/accounts/CheckCookie?service=hist&chtml=LoginDoneHtml
The Headers of the Page:
Pragma: no-cache
Cache-Control: private, no-cache, no-cache="Set-Cookie", proxy-revalidate
Expires: Fri, 04 Aug 1978 12:00:00 GMT
Content-Type: text/html; charset=UTF-8
Set-Cookie: PREF=ID=10c7043d3c6ee30b:TM=1227689962:LM=1227689962:DV=AA:S=BdnPHx8rJM1JfsJS; expires=Fri, 26-Nov-2010 08:59:22 GMT; path=/; domain=.google.com
Date: Wed, 26 Nov 2008 08:59:22 GMT
Server: Search-History HTTP Server
Transfer-Encoding: chunked
Connection: Close

http://www.google.com/history/?hl=en
<cookielib.CookieJar[<Cookie PREF=ID=10c7043d3c6ee30b:TM=1227689962:LM=1227689962:DV=AA:S=BdnPHx8rJM1JfsJS for .google.com/>, <Cookie SID=DQAAALQAAAD0u4ifpKNe1FOBnyMaZd5aQbUwsXpIDFl_oFBzjOqghCqQqfd-B71kbVTKdmqyuMU4KpoJig05npTK_WtH4F9cQ9kSoeDIztGyCxOpCYYUxdmBmPQeMGxzF5hbUd7wmBohQfaOXl9gJBt2pZtPw-BwOeEeMiQcKXNdXy1ydqnR-4ldfUggdXX36gDpuyPhUgOkAg4-vL2HKEPGRa1Lu0pKSkZONOuQxI5GxaSyd9Zk9KMMBjHwX3ShnhChoyatFuM for .google.com/>, <Cookie CAL=DQAAALYAAAD7cSYg1mVCsEly-thIoznG4WxOVq52pOOyZcwGd2myZZpKZD73UZsKmCGeKobwSptZ1HbH2v1MeETsH12I3ers39qp8RGazMEwQCr-RQu6PjtZIp_mUcVEfsZNQUyg1e5o7qfC74EinBzwtETQJ_XZiqgOuzO2BqgUywWwRmLejkmSfQS53Sousfvu34MA3Tm80etUq7qXEYaz1KkVd6NFBHl4NeLcZzD89chS6_2fbQDoZl6IgXJZr3jg-DUuEvE for .www.google.com/calendar>, <Cookie GAUSR=shengyan1985@gmail.com for www.google.com/accounts>, <Cookie LSID=cl|s.CN:DQAAALUAAAD7cSYg1mVCsEly-thIoznG4WxOVq52pOOyZcwGd2myZZpKZD73UZsKmCGeKobwSptZ1HbH2v1MeETsH12I3ers39qp8RGazMEwQCr-RQu6PjtZIp_mUcVEfsZNQUyg1e5B2tURSZL1Pq0RKNtSFqnovVScJK5JzERZXu5RGYmW17NWmxTp4tz5kNAmP14Io_W9pgwdUN4zLucpvqrKpY3a0N5CSd6RcJpOPYsYhEYhV3aSuPRvYjNQ96iRRySWgMk for www.google.com/accounts>]>

"""