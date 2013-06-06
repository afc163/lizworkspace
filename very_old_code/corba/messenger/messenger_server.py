#!/usr/bin/python
#coding=utf-8
#以Skeleton类为基类，定义服务类的实现
import messenger, messenger__POA

class OurSecretMessage(messenger__POA.SecretMessage):
	def __init__(self, secret_message):
		self.secret_message = secret_message

	def get_message(self):
		return self.secret_message

#创建服务对象实例
ourMessage = OurSecretMessage("Hello, omniORBpy!")

#初始化ORB、POA、POA_MANAGER对象
import sys
from omniORB import CORBA

orb = CORBA.ORB_init(sys.argv, CORBA.ORB_ID)
poa = orb.resolve_initial_references("RootPOA")
poa_manager = poa._get_the_POAManager()
poa_manager.activate()

#激活对象，对象的类从messenger_POA中继承了激活的方法_this()
message_obj = ourMessage._this()

#将对象引用字符串IOR写入message.ior文件
output_handle = open("message.ior", "w")
string_ior = orb.object_to_string(message_obj)
output_handle.write(string_ior)
output_handle.close()

#启动ORB
orb.run()
