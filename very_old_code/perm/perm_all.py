#coding=utf-8
#
#关于这个问题的精彩讨论参见这里
#  http://groups.google.com/group/python-cn/browse_thread/thread/4d9eda8e422a6cf8
#
#猜数字游戏8步以内的求解程序的一部分
#用来生成四位不重复数字(0-9)的所有组合，比如8765, 9876, 0123

#用的是最直接的方法，4重for循环：
#by realfun
def init_set():
    ret = []
    for i in range(0, 10):
        for j in range(0, 10):
            for k in range(0, 10):
                for l in range(0, 10):
                    if i != j and i != k and i != l and j != k and j != l and k != l:
                        ret.append((i, j, k, l))
    return ret

#改进的4重for，减少判断语句被调用的次数
#by Leo Jay
def init_set2():
    ret = []
    for i in range(0, 10):
        for j in range(0, 10):
            if i == j: continue
            for k in range(0, 10):
                if j == k: continue
                for l in range(0, 10):
                    if k == l: continue
                    if i != k and i != l and j != l:
                        ret.append((i, j, k, l))
    return ret

#引入set，消除所有的判断语句
#by realfun
def init_set3():
    s1 = set(range(0, 10))
    ret = []
    for i in s1:
        s2 = s1.copy()
        s2.remove(i)
        for j in s2:
            s3 = s2.copy()
            s3.remove(j)
            for k in s3:
                s4 = s3.copy()
                s4.remove(k)
                for l in s4:
                    ret.append((i, j, k, l))
    return ret

#by Nick Cen
def perm(items, n=None):
    if n is None:
        n = len(items)
    for i in range(len(items)):
        v = items[i:i+1]
        if n == 1:
            yield v
        else:
            rest = items[:i] + items[i+1:]
            for p in perm(rest, n-1):
                yield v + p

#by Nick Cen
#递归的方法消除判断语句
def init_set4():
    ret = []
    for i in perm(range(0,10),4):
        ret.append(i)
    return ret

#展开版，采用init_set3生成，已知程序里面最快的
#灭掉了所有的判断语句，同时用展开的方法灭掉了set，目前已知最快的
#应该不能更快了，因为这个版本已经没有多余的操作了
#by Leo Jay
def init_set5():
    ret = []
    for i in xrange(0, 10):
       for j in xrange(i+1, 10):
           for k in xrange(j+1, 10):
               for l in xrange(k+1, 10):
                   ret.append((i, j, k, l))
                   ret.append((i, j, l, k))
                   ret.append((i, k, j, l))
                   ret.append((i, k, l, j))
                   ret.append((i, l, j, k))
                   ret.append((i, l, k, j))
                   ret.append((j, i, k, l))
                   ret.append((j, i, l, k))
                   ret.append((j, k, i, l))
                   ret.append((j, k, l, i))
                   ret.append((j, l, i, k))
                   ret.append((j, l, k, i))
                   ret.append((k, i, j, l))
                   ret.append((k, i, l, j))
                   ret.append((k, j, i, l))
                   ret.append((k, j, l, i))
                   ret.append((k, l, i, j))
                   ret.append((k, l, j, i))
                   ret.append((l, i, j, k))
                   ret.append((l, i, k, j))
                   ret.append((l, j, i, k))
                   ret.append((l, j, k, i))
                   ret.append((l, k, i, j))
                   ret.append((l, k, j, i))
    return ret

#limodou: 得到一个灵感，可以写一个程序生成的程序，用来生成上面的代码。
#Leo Jay: 嘿嘿，实际上这段代码就是自动生成的
def gen_fun5():
    def init_set3():
       s1 = set(range(0, 4))
       ret = []
       for i in s1:
           s2 = s1.copy()
           s2.remove(i)
           for j in s2:
               s3 = s2.copy()
               s3.remove(j)
               for k in s3:
                   s4 = s3.copy()
                   s4.remove(k)
                   for l in s4:

                       ret.append((i, j, k, l))
       return ret

    c = ['i', 'j', 'k', 'l']
    for i, j, k, l in init_set3():
       print 'ret.append((%s, %s, %s, %s))' % (c[i], c[j], c[k], c[l])
 
# by me
class Permutation(object):
    """ 产生组合
    @todo: 
    """
    def __init__(self, total=10):
        """初始化
        @param total: 所有要排列的数字个数
        @type total: integer
        """
        self.totalNum = total
        self.result = []
        
    def perm(self, i=4):
        """对于total个数选取i个数进行选组合
        @param i: 实际的数字个数
        @type i: integer
        @todo: 产生Pmn选排列，而不是组合，是一种组合对应有n!个排列
        """
        self.result = []
        first = [] # 初始的组合
        end = [] # 最终的组合
        for j in range(i):
            first.append(j)
            end.append(self.totalNum-i+j)
    
        all = 0 # 组合的个数
        change = i-1 # 待改变的位置
        while 1:
            #for k in range(i):
            #    print first # 输出当前first组合，这里储存并可以进一步生成排列
            # print first
            
            self.result.append(first)# ...

            all += 24
            if first[change] <> end[change]:
                first[change] += 1
            else:
                change -= 1
                if change<0:
                    break
                first[change] += 1
                newchange = change
                for h in range(change+1, i):
                    first[h] = first[h-1]+1
                    if first[h] <> end[h]:
                        newchange = h
                change = newchange
        return all
    def perm2(self, i=4):
        """对于totalNum个数选取i个数进行选排列1...totalNum 选i个
        @param i: 实际的数字个数
        @type i: integer
        """
        first = [] # 初始的排列
        end = [] # 最终的排列，即 n, n-1, ..., n-m+1
        u = [1 for ii in xrange(self.totalNum)] # 辅助标志数组 1为未使用，0为使用过
        for j in xrange(i):
            first.append(j+1)
            end.append(self.totalNum-j) # -1
        # 排列个数
        all = 1
        #print first
        # 初态递增到终态为止
        while first <> end:
            for j in xrange(i):
                u[first[j]-1] = 0
            
            f = self.totalNum
            # 找未使用过的最大整数
            while u[f-1] <> 1 :
                f -= 1
            k = i
            h = -1
            # 找最右可修改元素
            while h == -1:
                k -= 1
                u[first[k]-1] = 1
                if first[k] < f:
                    # 找满足first[k] < j <= totalNum且u[j] =1的最小下标j
                    j = first[k]
                    for jj in xrange(first[k]+1, self.totalNum+1):
                        if u[jj-1]:
                            j = jj
                            break
                    h = k
                    first[h] = j
                    u[first[h]-1] = 0
                else:
                    f = first[k]
            # 修改first[h]之右的元素
            for ka in xrange(1, i-h):
                kk = 0
                s = -1
                for s in xrange(self.totalNum):
                    if u[s]:
                        kk += 1
                        if kk == ka:
                            break
                first[h+ka] = s+1
            for kb in xrange(h):
                u[first[kb]-1] = 1
            # 产生输出
            #print first
            all += 1
        return all
#下面的代码用来测试性能
import time

def timing(f, n):
    print  "times",
    r = range(n)
    t1 = time.clock()
    for i in r:
        f()
    t2 = time.clock()
    print round(t2-t1, 3)

#直接的四重for
#timing(init_set, 1000)
#init_set 1000 times 5.624

##四重for里面每层都加入判断，减少不必要的for
#timing(init_set2, 1000)
#init_set2 1000 times 4.815

#引入set灭掉所有的判断语句(if语句)
#timing(init_set3, 1000)
#init_set3 1000 times 3.189

#用递归灭掉所有的判断语句（递归调用开销大啊）
#timing(init_set4, 1000)
#init_set4 1000 times 17.03

#灭掉了所有的判断语句，同时用展开的方法灭掉了set，目前已知最快的
#应该不能更快了，因为这个版本已经没有多余的操作了
timing(init_set5, 10)
#init_set5 1000 times 2.024

p = Permutation(10)
timing(p.perm2, 10)