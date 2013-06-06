#!/usr/bin/python
#coding:utf-8
# $Id: xsend.py,v 1.8 2006/10/06 12:30:42 normanr Exp $

"""用来向特定帐号发送消息
"""

import sys,os,xmpp,time

if len(sys.argv) < 2:
    print "Syntax: xsend JID text"
    sys.exit(0)

tojid=sys.argv[1]
text=' '.join(sys.argv[2:])

# 从~/.xsend中获得jid
jidparams={}
if os.access(os.environ['HOME']+'/.xsend',os.R_OK):
    for ln in open(os.environ['HOME']+'/.xsend').readlines():
        if not ln[0] in ('#',';'):
            key,val=ln.strip().split('=',1)
            jidparams[key.lower()]=val
for mandatory in ['jid','password']:
    if mandatory not in jidparams.keys():
        open(os.environ['HOME']+'/.xsend','w').write('#Uncomment fields before use and type in correct credentials.\n#JID=romeo@montague.net/resource (/resource is optional)\n#PASSWORD=juliet\n')
        print 'Please point ~/.xsend config file to valid JID for sending messages.'
        sys.exit(0)

jid=xmpp.protocol.JID(jidparams['jid'])
cl=xmpp.Client(jid.getDomain(),debug=[])

con=cl.connect() # 建立连接
if not con:
    print 'could not connect!'
    sys.exit()
print 'connected with',con
auth=cl.auth(jid.getNode(),jidparams['password'],resource=jid.getResource()) # 验证
if not auth:
    print 'could not authenticate!'
    sys.exit()
print 'authenticated using',auth

cl.SendInitPresence(requestRoster=0) # 这个可以注释掉
id=cl.send(xmpp.protocol.Message(tojid,text)) #发送消息
print 'sent message with id',id

time.sleep(1)   # some older servers will not send the message if you disconnect immediately after sending

cl.disconnect()
