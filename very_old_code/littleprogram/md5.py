#!/usr/bin/python
#coding=utf-8
import os, hashlib, binascii

def Test(path_1, path_2):
   f1 = file(os.path.join(path_1, os.listdir(path_1)[0]) ,'rb')
   f2 = file(os.path.join(path_2, os.listdir(path_2)[0]) ,'rb')
   m1 = hashlib.md5()
   m1.update(f1.read())
   md1 = m1.digest()
   print binascii.b2a_hex(md1)
   m2 = hashlib.md5()
   m2.update(f2.read())
   md2 = m1.digest()
   print binascii.b2a_hex(md2)
   print md1 == md2
   print f1,f2
Test("/home/shengyan/CTest", "/home/shengyan/workspace")


