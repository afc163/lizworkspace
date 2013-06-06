#!/usr/bin/python
#coding:utf-8

"""数据流中的频繁项
需要更新密度D(x)和最近出现位置
"""

try:
    import cPickle as pickle
except:
    import pickle
import collections

Y = 0.98 # 衰减系数
E = 0.001 # 误差系数
WINDOWSIZE = long(1.0/E)
S = 0.005 # 支持度
DX = 1000 # 更新阈值, 超过这个数就更新窗口中记录

def get_file_data():
    """提供数据流,目前是从一个文件中读入文本,以后可以随机产生
    """
    fp = open("test")
    while 1:
       ch = fp.read(1)
       if not ch:
           break
       if ch.isalpha():
           yield ch

    """try:
        while 1:
            l = fp.next()
            print l
    except StopIteration:
        pass"""

def get_zipf_data():
    return pickle.load(open("zipf.data"))
        
def do_main():
    """主算法
    读取一个数据,更新密度和最近出现位置,在固定窗口大小中,将每次的窗口状态保存至文件中
    """
    t = -1
    windows = {}
    result = []
    
    #for ch in get_file_data():
    for ch in get_zipf_data():
        t += 1
        if windows.has_key(ch):
            # 窗口中已有ch,则直接更新这个,其他不做
            windows[ch][0] += 1
            windows[ch][1] = t
        else:
            # 如果不存在,则需要加入,加入新的记录,后考虑窗口是否已满,满的话要删除密度值最小的一个
            windows[ch] = [1, t]
            if len(windows.keys()) > WINDOWSIZE:
                # 找出当前窗口中密度最小的一个
                min = windows[ch][0]
                min_key = ch
                for i in windows:
                    if windows[i][0] < min:
                        min_key = i
                        min = windows[i][0]
                # 删除, 并将其他的项减去该密度
                min_item = windows.pop(min_key)
                for i in windows:
                    if i != ch:
                        windows[i][0] -= min_item[0]
                
        # 更新窗口中原有记录的密度值
        for i in windows:
            if i != ch:
                windows[i][0] *= Y
        
        # 保存当前时刻t的窗口状态,便于接下来的查询
        tempstring = pickle.dumps(windows)
        result.append(tempstring)
    
    pickle.dump(result, open('result', 'w'))

Support = S/(1-Y)

def query(t):
    """查询t时刻,窗口中的频繁项, off_line
    """
    print 'Support', Support
    result = pickle.load(open("result"))
    windows = pickle.loads(result[t])
    print t,windows
    for i in windows:
        current = E/(1-Y)*(Y**(t-windows[i][1]))
        print windows[i][0], current
        if windows[i][0]+current >= Support:
            print i

def do_fact():
    """计算实际数据流中的
    """
    result = []
    windows = collections.defaultdict(lambda: 0)
    data = get_zipf_data()
    print data
    for ch in data:
        windows[ch] += 1

        for x in windows:
            # 更新窗口中原有记录的密度值
            #if x != ch:
            windows[x] *= Y
        
        # 保存当前时刻t的窗口状态,便于接下来的查询
        tempstring = pickle.dumps(dict(windows))
        result.append(tempstring)
    
    pickle.dump(result, open('result_fact', 'w'))

def query_fact(t):
    """
    """
    result = pickle.load(open("result_fact"))
    windows = pickle.loads(result[t])
    print t,windows
    for x in windows:
        current = S*(1-Y**t)/(1-Y)
        print windows[x], current
        if windows[x] > current:
            print 'P', x
        else:
            print 'N', x
              
def do_main_improve():
    """主算法 改进
    读取一个数据,更新密度和最近出现位置,在固定窗口大小中
    """
    t = -1
    deltad = 0
    deltat = 0
    windows = {}
    result = {}
    
    for ch in streamdata():
        t += 1
        if windows.has_key(ch):
            # 窗口中已有ch,则直接更新这个,其他不做
            windows[ch][0] += Y**(-t)  # 这里改为D(X)+Y-t
            windows[ch][1] = t
        else:
            # 如果不存在,则需要加入,加入新的记录,后考虑窗口是否已满,满的话要删除密度值最小的一个
            windows[ch] = [Y**(-t), t]  # 这里1改为Y**(-t)
            if len(windows.keys()) > WINDOWSIZE:
                # 找出当前窗口中密度最小的一个, python字典中主要是没有顺序, 所以用冒泡排序不能.
                min = windows[ch][0]
                min_key = ch
                for i in windows:
                    if windows[i][0] < min:
                        min_key = i
                        min = windows[i][0]
                # 删除, 并将累计deltad
                min_item = windows.pop(min_key)
                # 改进之处
                deltad += min_item[0]
        
        deltat += 1
        if windows[ch][0] > DX:
            # 更新窗口中原有记录的密度值
            for i in windows:
                if i != ch:
                    windows[i][0] = (windows[i][0]-deltad)*(Y**deltat)
            deltat = 0
            deltad = 0
            
        # 保存当前时刻t的窗口状态,便于接下来的查询
        result[t] = windows
    try:
        pickle.dump(result, open('result', 'w'))
    except:
        print "Pickle Result Failed"
    print windows
    print t


def query_improve(t):
    """查询t时刻,窗口中的频繁项
    """
    try:
        result = pickle.load(open("result"))
        if result.has_key(t):
            windows = result[t]
            print windows
            for i in windows:
                current = E/(1-Y)*(Y**(t-windows[i][1]))
                print windows[i][0], current
                if windows[i][0] + current >= Support:
                    print i
        else:
            raise
    except Exception, e:
        print e
        
if __name__ == "__main__":
    do_fact()
    query_fact(145)
    #query(99)
    #do_main_improve()
    
    
