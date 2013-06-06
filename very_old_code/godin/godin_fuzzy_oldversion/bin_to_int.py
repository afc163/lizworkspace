#!/usr/bin/python
#coding=utf-8
import random
import time
def bin_to_int(bin_list):
    pass

def int_to_bin(va):
    l = []
    while va:
        l.append(va % 2)   # reverse
        va = va / 2
    return l

def getLength(va):
    num = 0
    while va:
        if va % 2 == 1:
            num += 1
        va = va / 2
    print num
    return num

if __name__ == '__main__':
    import hotshot, hotshot.stats
    prof = hotshot.Profile('bin_to_int', 1)
    start_time = time.time()
    for i in range(50000):
        prof.runcall(getLength, random.randint(1, 999999))
    use_time = time.time() - start_time
    print 'the time is %.10f' % use_time
    prof.close()
    stats = hotshot.stats.load("bin_int.prof")
    stats.strip_dirs()
    stats.sort_stats('time', 'calls')
    stats.print_stats(20)
