#!/usr/bin/python
#coding=utf-8
import sys
from omniORB import CORBA
import messenger

#从message.ior文件读取服务对象的IOR
input_handle = open("message.ior", "r")
ior = input_handle.read()
input_handle.close()

orb = CORBA.ORB_init(sys.argv, CORBA.ORB_ID)

#从IOR获取对象，导入到自己地址空间中
obj = orb.string_to_object(ior)
mo = obj._narrow(messenger.SecretMessage)

result = mo.get_message()
print result
