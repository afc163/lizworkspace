#!/usr/bin/python
#coding:utf-8

"""获取amarok正在播放的音乐名字, 并通过XMPP不断传送到特定帐号作为状态显示
依赖于: 
1) XMPPPy
2) ...
@author: shengyan1985@gmail.com
"""

import xmpp
import sys
import os
import time

# 消息回调函数
def messageCB(cnx, msg): #X
    # 显示消息发送者和内容
    print "Sender: " + str(msg.getFrom())
    print "Content: " + str(msg.getBody())
    # 将消息又返回给发送者
    cnx.send(xmpp.Message(str(msg.getFrom()), str(msg.getBody())))

def get_amarok_title():
    """使用dcop获得amarok当前播放的歌曲名字
    """
    try:
        pipe = os.popen('dcop amarok player title')
        if not pipe:
            return ''
        title = pipe.readlines()[0].strip()
        pipe.close()
        return title
    except:
        return ''

def presenceHandler(cnx, msg=''):
    """发送presence显示为状态信息
    Presence标准协议: http://xmpp.org/rfcs/rfc3922.html
    """
    st = get_amarok_title()
    print st
    pr = xmpp.Presence(show='music', status=st)
    cnx.send(pr)
        
def main(usr, pwd):
    """主函数
    """
    # 创建client对象, 设置host
    cnx = xmpp.Client('gmail.com', debug=[])
    # 连接到google的服务器, connect连接到指定的jabber服务器, 失败则抛出IOError
    cnx.connect(server=('talk.google.com', 443))
    # 用户身份认证
    cnx.auth(usr, pwd, 'Liz')
    # 告诉gtalk服务器用户已经上线
    cnx.sendInitPresence()
    # 设置, 这边设置的话, 就只能在别人状态变化时运行presenceHandler
    #cnx.RegisterHandler('presence', presenceHandler)
    
    print 'Ctrl+C to exit'
    # 循环处理消息,如果网络断开则结束循环
    while True:
        if cnx.Process(1) == None:
            print 'Lost connection.'
            break
        time.sleep(100) # 间隔100秒后再处理
        presenceHandler(cnx)
        
    # 主动结束程序
    while True:
        time.sleep(1)

def get_usage():
    usage = """
        %prog user password
        """
    return usage
    
if __name__ == "__main__":
    argv = sys.argv
    if len(argv) < 3:
        print get_usage()
        sys.exit(0)
    try:
        main(argv[1], argv[2]);
    except KeyboardInterrupt:
        print 'exit the program!'
    except Exception,e:
        print e
        