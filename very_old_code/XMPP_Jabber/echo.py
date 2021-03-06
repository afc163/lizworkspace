#! /usr/bin/env python
# encoding=UTF-8

"""
要运行这个例子需要两点:
    1,系统需要安装Python和xmpppy.
    2,要有两个gtalk帐号,并且这两个帐号要互相加为好友,可以用GTalk客户端做这件事情.
"""
import xmpp
import time

# 消息回调函数
def messageCB(cnx, msg):
    # 显示消息发送者和内容
    print "Sender: " + str(msg.getFrom())
    print "Content: " + str(msg.getBody())
    # 将消息又返回给发送者
    cnx.send(xmpp.Message(str(msg.getFrom()), str(msg.getBody())))

if __name__ == '__main__':
    # 给实例的gtalk帐号和密码
    login = 'shengyan1985'
    pwd = '...'
    # 创建client对象
    cnx = xmpp.Client('gmail.com', debug=[])
    # 连接到google的服务器
    cnx.connect(server=('talk.google.com', 443))
    # 用户身份认证
    cnx.auth(login, pwd, 'UDPonNAT')
    # 告诉gtalk服务器用户已经上线
    cnx.sendInitPresence()
    # 设置消息回调函数
    cnx.RegisterHandler('message', messageCB)
    # 循环处理消息,如果网络断开则结束循环
    while True:
        if cnx.Process(1) == None:
            print 'Lost connection.'
            break
    # 无用,方便windows命令窗口调试
    while True:
        time.sleep(1)
