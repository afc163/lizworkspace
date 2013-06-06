#!/home/shengyan/Python3.0/bin/python3.0
#coding:utf-8

import sys
import array
import tempfile
import heapq

# 断言: 判断有符号整型所占的字节数是否是4
assert array.array("i").itemsize == 4

def intsfromfile(f):
    """从文件中读取整数
    """
    while True:
        a = array.array("i")
        # 一次性从文件中读入1000个整数
        a.fromstring(f.read(4000))
        if not a:
            break
        for x in a:
            yield x

fp = open("randomdata", 'br')
result = open("result", 'bw')

iters = []
while True:
    a = array.array("i")
    a.fromstring(fp.read(4000))##?sys.stdin.buffer
    if not a:
        break
    f = tempfile.TemporaryFile()
    # 将排序结果保存到临时文件夹中
    array.array("i", sorted(a)).tofile(f)
    # 文件指针置为开始处
    f.seek(0)
    # iters包含排好序的1000个数字
    iters.append(intsfromfile(f))

a = array.array("i")
for x in heapq.merge(*iters):
    a.append(x)
    # 如果超过1000个,将她保存至buffer中,并清空a
    if len(a) >= 1000:
        a.tofile(result)#sys.stdout.buffer
        del a[:]

# 剩余a的输出
if a:
    a.tofile(result)#sys.stdout.buffer
    