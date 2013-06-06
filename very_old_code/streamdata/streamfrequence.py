#!/usr/bin/python
#coding:utf-8

"""数据流中的频繁项
需要更新密度D(x)和最近出现位置,在t时刻找出频繁项
"""

try:
    import cPickle as pickle
except:
    import pickle
import collections
import time

Y = 0.99 # 衰减系数
E = 0.0005 # 误差系数
WINDOWSIZE = long(1.0/E)
S = 0.005 # 支持度
DX = 10000 # 更新阈值, 超过这个数就更新窗口中记录
TLIST = [19999, 39999, 59999, 79999, 99999] # 检查点列表
Support = S/(1-Y)

def get_zipf_data():
    return pickle.load(open("zipf_0.5_1000000.data"))

def do_main():
    """主算法
    读取一个数据,更新密度和最近出现位置,在固定窗口大小中,将TLIST时刻的窗口状态保存
    """
    t = -1
    windows = {}  #{数据项:[密度,出现位置]}
    result = {}      #{时刻t:[频繁项]}
    
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
                for x in windows:
                    if windows[x][0] < min:
                        min_key = x
                        min = windows[x][0]
                # 删除, 并将所有项减去该密度
                min_item = windows.pop(min_key)
                for x in windows:
                    windows[x][0] -= min_item[0]
                
        # 更新窗口中所有记录的密度值
        for x in windows:
            windows[x][0] *= Y
        
        # 时刻t的窗口状态,并获取频繁项
        ##if t in TLIST:
        ##    result[t] = []
        ##    for x in windows:
        ##        current = E/(1-Y)*(Y**(t-windows[x][1]))
        ##        if windows[x][0]+current >= Support:
        ##            result[t].append(x) ###??"%d" % 
                    
    ##pickle.dump(result, open('result', 'w'))
    #print result

def do_main_improve():
    """主算法 改进
    读取一个数据,更新密度和最近出现位置,在固定窗口大小中,将TLIST时刻的窗口状态保存
    """
    t = -1
    deltad = 0
    deltat = 0
    windows = {}  #{数据项:[密度,出现位置]}
    result = {}      #{时刻t:[频繁项]}

    for ch in get_zipf_data():
        t += 1
        if windows.has_key(ch):
            # 窗口中已有ch,则直接更新这个,其他不做
            windows[ch][0] += Y**(-deltat)  # 这里改为D(X)+Y**-deltat
            windows[ch][1] = t
        else:
            # 如果不存在,则需要加入,加入新的记录,后考虑窗口是否已满,满的话要删除密度值最小的一个
            windows[ch] = [Y**(-deltat), t]  # 这里改为Y**(-deltat)
            #print 'new', windows[ch][0]
            if len(windows.keys()) > WINDOWSIZE:
                # 找出当前窗口中密度最小的一个, python字典中主要是没有顺序, 所以用冒泡排序不能.
                min = windows[ch][0]
                min_key = ch
                for x in windows:
                    if windows[x][0] < min:
                        min_key = x
                        min = windows[x][0]
                # 删除, 并将累计deltad
                min_item = windows.pop(min_key)
                # 改进之处
                deltad += min_item[0]
        
        deltat += 1
        flag = 0
        for x in windows:
            if windows[x][0] > DX:
                flag = 1
                #print t
        if flag:
            # 更新窗口中所有记录的密度值
            for x in windows:
                windows[x][0] = (windows[x][0]-deltad)*(Y**deltat)
            deltat = 0
            deltad = 0
            
        # 时刻t的窗口状态,并获取频繁项
        ##if t in TLIST:
        ##    result[t] = []
        ##    #print windows
        ##    for x in windows:
        ##        current = E/(1-Y)*(Y**(t-windows[x][1]))
        ##        w = (windows[x][0] - deltad)*(Y**deltat)
        ##        if w+current >= Support:
        ##            result[t].append(x) ###??"%d" % 
    ##pickle.dump(result, open('result_improve', 'w'))
    #print result

def do_fact():
    """计算实际数据流中的
    """
    result = {} #{时刻t:[频繁项]}
    windows = collections.defaultdict(lambda: 0)  # {数据:密度}
    t = -1
    for ch in get_zipf_data():
        t += 1
        windows[ch] += 1
        for x in windows:
            # 更新窗口中所有记录的密度值
            windows[x] *= Y
        
        # 保存当前时刻t的窗口状态,并计算频繁项, 将时刻t的P'集合保留下来
        if t in TLIST:
            result[t] = []
            for x in windows:
                current = S*(1-Y**t)/(1-Y)
                if windows[x] > current:
                    result[t].append(x)
    pickle.dump(result, open('result_fact', 'w'))
    #print result

def do_EC():
    """EC算法
    """
    def hasZero(windows):
        for x in windows:
            if windows[x][0] == 0:
                return True
        return False
        
    windows = {}  #{数据项:[密度,df]}
    result = {}      #{时刻t:[频繁项]}
    t = -1
    for ch in get_zipf_data():
        t += 1
        if windows.has_key(ch):
            windows[ch][0] += 1
        else:
            if len(windows.keys()) >= WINDOWSIZE:
                while not hasZero(windows):
                    for x in windows:
                        windows[x][0] -= 1
                        windows[x][1] += 1
                del_item = []
                for x in windows:
                    if windows[x][0] == 0:
                        del_item.append(x)
                for d in del_item:
                    del windows[d]
            windows[ch] = [1, 0]
            
        # 时刻t的窗口状态,并获取频繁项
        if t in TLIST:
            result[t] = []
            for x in windows:
                if windows[x][0]+windows[x][1] >= (S-E)*WINDOWSIZE:
                    result[t].append(x) ###??"%d" % 
                    
    pickle.dump(result, open('result_ec', 'w'))
    print result
    
def analysis():
    """分析结果数据
    """
    truedata = pickle.load(open('result_fact'))
    data = pickle.load(open('result'))
    improvedata = pickle.load(open('result_improve'))
    #ecdata = pickle.load(open('result_ec'))
    r = {} #{时刻t:(recall, precision)}
    r_improve = {}
    r_ec = {}
    for t in TLIST:
        p1 = set(truedata[t])
        p = set(data[t])
        p_improve = set(improvedata[t])
        p_ec = set(ecdata[t])
        r[t] = (len(p1.intersection(p))*1.0/len(p1), len(p1.intersection(p))*1.0/len(p))
        r_improve[t] = (len(p1.intersection(p_improve))*1.0/len(p1), len(p1.intersection(p_improve))*1.0/len(p_improve))
        r_ec[t] = (len(p1.intersection(p_ec))*1.0/len(p1), len(p1.intersection(p_ec))*1.0/len(p_ec))
        
    print "算法1", r
    print "算法2", r_improve
    print "EC算法", r_ec
    
if __name__ == "__main__":
    #do_fact()
    s = time.time()
    do_main()
    print 'time 1', time.time()-s
    s = time.time()
    do_main_improve()
    print 'time 2', time.time()-s
    #do_EC()
    #analysis()
    
