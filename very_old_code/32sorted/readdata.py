#!/usr/bin/python
import array
# 测试读取result中的数据, 这些数据是排好序的

fp = open("result")
while True:
    a = array.array("i")
    a.fromstring(fp.read(4000))
    if not a:
        break
    for x in a:
        print x
fp.close()