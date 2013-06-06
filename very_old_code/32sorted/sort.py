#!/usr/bin/python
import sys, array

# 数组类,第一个参数是指明数组元素的类型,后面是一个元素列表,也可以是其他,从标准输入读入
a = array.array("i", [65,68,67])#sys.stdin.buffer.read())
a = list(a)
a.sort()
a = array.array("i", a)
# 保存到文件,元素为二进制,写到文件就解析成asc了
a.tofile(sys.stdout)#open("tt",'w'))
