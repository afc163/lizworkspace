import thread
import urllib2
import re
import sys
import time
import random
import traceback
import os
import Queue

#lock for synchronization of data update and print out
safe            = thread.allocate_lock()


#struct for restore base page and result page
dataQueue       = Queue.Queue()
resultQueue     = Queue.Queue()

proxylist       = open('proxy2.txt', 'r').read().split('\n')

urls2fetch      = open('urls2fetch.txt','r').read().split('\n')
currentindex    = 0

proxystart      = random.randint(0, len(proxylist) - 1)

pagestart       = 0

pageend         = 100

basefinish     = 0
basefail       = 0
finished        = 0

final           = 0

itemfinish      = 0

pattern4mail    = """
                (
		[a-zA-Z0-9_]+
		(?:[\-\+\.][a-zA-Z0-9_]+)*
		@
		[a-zA-Z0-9_]+
		(?:[\-\.][a-zA-Z0-9_]+)*
		\.
		[a-zA-Z0-9_]+
		(?:[\-\.][a-zA-Z0-9_]+)*
		)
                """

patternUrlBase  = """
                http://www.indeed.com/q\-
                (
                [^\-]+
                (?:\-
                [^\-]+)*
                )
                \-jobs.html             
                """

pattern         = """
		<
		h2
		\s+
		class=jobtitle
		>
		<
		a
		\s+
		href
		=
		"
		([^"]+)
		"
		\s+
		onmousedown
		=
		"
		return
		\s+
		rclk
		\(
		[^\)]+
		\)
		;
		"
		\s+
		title
		=
		"
		(?:[^"]+)
		"
		>
		((?:[^<]*?<b>(?:[^<]+)</b>[^<]*?)+)
		</a>
		\s+
		(?:-\s+<span\s+class=new>(new)</span>)?
		</h2>
		\s+
		<span\s+class="company">
		([^<]+)
		</span>
		\s+
		-
		\s+
		<span\s+class="location">
		([^<]+)
		</span>
		\s+
		(?:<a\s+rel="nofollow"\s+style="color:\#666"\s+href="
		([^"]+)
		"
		>
		([^<]+)
		</a>\s+)?
		<table\s+cellpadding=0\s+cellspacing=0\s+border=0><tr><td\s+class=snip>
		\s*
		<span\s+class=summary>
		(
		(?:.*?(?:<b>.*?</b>)?.*?)+
		)
		</span>
		([^<]*)
		<br>
		\s+
		<span\s+class=source>([^<]+)</span>
		&nbsp;-&nbsp;
		<span\s+class=date>([^<]+)</span>
		"""


def producer():
    global pagestart, pageend, finished, final, currentindex, urls2fetch
    while 1:
        safe.acquire()
        #task finished,quit
        #if pagestart >= pageend:
        if currentindex > (len(urls2fetch) - 1):
            safe.release()
            break
        #for testing only
        #if currentindex > 6:
         #   safe.release()
          #  break
        if pagestart >=pageend:
            currentindex += 1
            pagestart    =  0
        #update the global viable pagestart


        tmp         = pagestart
        pagestart   = pagestart + 1

        safe.release()
        #get the base page into the dataQueue, and it MUST work very hard!
        getBasePage(tmp, currentindex)

        
        


def consumer():
    global dataQueue, finished
    while 1:
        time.sleep(0.1)
        try:
            item = dataQueue.get(block=False)
        except Queue.Empty:
            pass
        else:
            #further fetch the data and restore in the resultQueue,and it MUST work very hard!
            getNextPage(item)

            #update the global viable finished
            safe.acquire()
            
            finished = finished + 1

            print 'finished base page:%d' %(finished, )
            
            safe.release()
            

def getUrlBase(url):
    global patternUrlBase
    regex       = re.compile(patternUrlBase, re.DOTALL|re.VERBOSE|re.IGNORECASE)
    jobtitle    = regex.findall(url)[0].replace('-','+')
    urlbase     = 'http://www.indeed.com/jobs?q=' + jobtitle
    return urlbase

def getBasePage(pagenumber, urlindex):
    global proxylist, proxystart, dataQueue, pagestart, basefinish, basefail, urls2fetch
    onepiece        = urls2fetch[urlindex]

    colon           = onepiece.index(':')
    semicolon       = onepiece.index(';')
    
    titleofclass    = onepiece[:colon]
    subtitle        = onepiece[colon + 1:semicolon]
    url2fetch_t     = onepiece[semicolon + 1:]
    url             = getUrlBase(url2fetch_t) + '&start=' + str(pagenumber * 10)

    count   = 0
    
    while 1:
        if count > 10:
            safe.acquire()
            basefail = basefail + 1
            print 'basefailed =%d, urlindex=%d' %(basefail, urlindex)
            safe.release()
            break
        #update the global viable proxystart
        safe.acquire()
        
        proxy       = proxylist[proxystart]
        proxystart  = proxystart + 1
        
        if proxystart   == len(proxylist):
            proxystart  = 0
            
        safe.release()
        
        try:
            proxy_handler   = urllib2.ProxyHandler({'http':'http://' + proxy})
            opener          = urllib2.build_opener(proxy_handler)
            htmlSource      = opener.open(url).read()
            
            #print and update synchronization
            safe.acquire()
            
            regex = re.compile(pattern, re.DOTALL|re.VERBOSE|re.IGNORECASE)
            items = regex.findall(htmlSource)  #[{}]!!!

            
            for item in items:
                template = []
                for each in item:
                    template.append(each)
                template.append(titleofclass)
                template.append(subtitle)
                while 1:
                    if not dataQueue.full():
                        dataQueue.put(template)
                        break
                    else:
                        time.sleep(0.2)
            
            
            basefinish = basefinish + 1
            print 'basefinish = %d, urlindex=%d' %(basefinish, urlindex)
            
            safe.release()
            break
           #""" except urllib2.HTTPError:
            #    self.suc = 0
               # break
           # except urllib2.URLError:
              #  self.suc = 0
              #  break
           # except httplib.BadStatusLine:
            #    self.suc = 0
              #  break"""
        except:
            safe.acquire()
            count = count + 1
            print 'try fail count =%d' %(count,)
            traceback.print_exc()
            safe.release()
            pass

def getNextPage(item):
    global proxylist, proxystart, pattern4mail, resultQueue, itemfinish, finished

    packet = []
    
    count = 0
    while 1:
        if count > 3:
            packet.append(('http://www.indeed.com' + item[0], None))
            break
            
        safe.acquire()
            
        proxy       = proxylist[proxystart]
        proxystart  = proxystart + 1
        
        if proxystart   == len(proxylist):
            proxystart  = 0
            
        safe.release()

        try:
            proxy_handler   = urllib2.ProxyHandler({'http':'http://' + proxy})
            opener          = urllib2.build_opener(proxy_handler)
            response        = opener.open('http://www.indeed.com' + item[0])
            url             = response.geturl()
            infosource      = response.read()
            regex           = re.compile(pattern4mail, re.DOTALL|re.VERBOSE|re.IGNORECASE)
            emails          = regex.findall(infosource)

            safe.acquire()

            itemfinish = itemfinish + 1
            print 'succeed,at itemfinish = %d' %(itemfinish, )
                
            safe.release()
            packet.append((url, emails))
            break

        except:
            count = count + 1
                
            safe.acquire()
            print 'fail,at itemfinish:%d, try time:%d of 3' %(itemfinish + 1, count)
            traceback.print_exc()
                
            safe.release()
            pass

    while 1:
        if not resultQueue.full():
            resultQueue.put((item, packet[0]))
            break
        else:
            time.sleep(0.2)
    
                
def printresult(item, url, emails):
    title       = item[1].replace('<b>', '').replace('</b>', '')
    summary     = item[7].replace('<b>', '').replace('</b>', '')
    
    print '<Url><![CDATA['  + url       + ']]></Url>\n'
    print '<Title>'         + title     + '</Title>\n'
    print '<Isnew>',
    if item[2] == '':
        print 'no',
    else:
        print 'yes',
    print '</Isnew>\n'
    print '<Company>'       + item[3]   + '</Company>\n'

    print '<Class>'         + item[11]  + '</Class>\n'
    print '<Subclass>'      + item[12]  + '</Subclass>\n'
    if item[4].rfind(',')   != -1:
        index               = item[4].index(',')
        (city, state)       = (item[4][:index], item[4][index + 1:])
        print '<City>'      + city      + '</City>\n'
        print '<State>'     + state     + '</State>\n'
    else:
        print '<Location>'  + item[4]   + '</Location>\n'
    if item[5] != '':
        print '<Locationplus_url><![CDATA['     + item[5]   + ']]></Locationplus_url>\n'
        print '<Locationplus_info><![CDATA['    + item[6]   + ']]></Locationplus_info>\n'
    print '<Summary>'       + summary   + '</Summary>\n'
    print '<Salary_detail>',
    if item[8] == '':
        print 'no',
    else:
        print item[8],
    print '</Salary_detail>\n'
    print '<Source>'        + item[9] + '</Source>\n'
    print '<Date>'          + item[10] + '</Date>\n'
    if emails  == []:
        print '<Email>no</Email>\n'
    else:
        if emails is None:
            print '<Email>unconnectable!</Email>\n'
        else:
            for each in emails:
                print '<Email>' + each + '</Email>\n'


"""def initial():
    global pagestart
    pagestart       = 0
"""





if __name__ == '__main__':

    print 'Total page to crawl per url:%d' %(pageend, )
    
    for i in range(100):
        thread.start_new_thread(producer, ())
    for i in range(100):
        thread.start_new_thread(consumer, ())
    
    
    start = time.time()

    itemfinishsave = itemfinish
    fileno     = 0

    #it loops to check whether the fulfilled no is to certain degree,if yes then restore,until the total no is satisfied
    while 1:
        time.sleep(60)
        safe.acquire()
        print 'basefinish,basefail,finished,itemfinish:%d,%d,%d,%d' %(basefinish,basefail,finished,itemfinish)
        safe.release()
        if (itemfinish > itemfinishsave + (pageend - 10) * 10) or resultQueue.full():
            safe.acquire()
            
            itemfinishsave = itemfinish
            end         = time.time()
            savesock    = open('result_' + str(fileno) + '.xml', 'w')
            saveout     = sys.stdout
            sys.stdout  = savesock
            print '<?xml version="1.0" encoding="utf-8" ?>'
            print '<Resultset>'
            i           = 1
            while not resultQueue.empty():
                result  = resultQueue.get()
                item    = result[0]
                packet  = result[1]
                url     = packet[0]
                emails  = packet[1]
                print '<Roundset>'
                print '<NO>%d</NO>' %(i, )
                i       = i + 1
                printresult(item, url, emails)
                print '</Roundset>'

            print '</Resultset>'
            sys.stdout  = saveout
            savesock.close()
            print 'Total time cost:%s' %(str(end - start), )

            #restart timing
            start = time.time()
            
            """if finished > (pageend - 10) * 5:
                final   = 1
                safe.release()
                break
"""
           
            safe.release()
            
            if final:
                break
            fileno      += 1
