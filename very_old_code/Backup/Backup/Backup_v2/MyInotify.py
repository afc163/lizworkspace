#!/usr/bin/python
#coding:utf-8

"""检测监控路径的更新，并将其更新信息记录至INDEX_FILE文件
    @note: 需要pyinotify模块
    @version: 0.1
    @author: lizzie
    @contact: shengyan1985@gmail.com
    @todo: 文件系统其他检测事件待处理，文件的重命名现在无法处理
"""

import os, sys
from pyinotify import WatchManager, Notifier, ProcessEvent, EventsCodes
from head import INDEX_FILE, WATCH_FILE

MASK = EventsCodes.IN_CLOSE_WRITE|EventsCodes.IN_DELETE|EventsCodes.IN_CREATE#|EventsCodes.IN_ATTRIB|EventsCodes.IN_MODIFY|EventsCodes.IN_MOVE_SELF#|EventsCodes.IN_MOVED_FROM|EventsCodes.IN_MOVED_TO

# 追加方式打开，此记录所有文件变化，主要是更新和删除
IFP = open(INDEX_FILE, 'a')

class MyProcessEvent(ProcessEvent):
    """事件处理，并写入INDEX_FILE，不处理隐藏文件和临时文件
    """
    """def process_IN_ATTRIB(self, event):
        # 路径不检测，只取文件
        if event.name.startswith('.') or event.name.endswith('~'):
            pass
        else:
            fn = os.path.join(event.path, event.name)
            IFP.write('U:%s\n' % fn)
            IFP.flush()
            print 'ATTRIB event: ' + fn
            """
    def process_IN_CLOSE_WRITE(self, event):
        """写关闭事件，记录格式为U:somefile(绝对路径)
        """
        if event.name.startswith('.') or event.name.endswith('~'):
            pass
        else:
            if os.path.isfile(event.path):
                fn = event.path
            else:
                fn = os.path.join(event.path, event.name)
            IFP.write('U:%s\n' % fn)
            IFP.flush()
            
    def process_IN_CREATE(self, event):
        """对于CREATE事件，只获取，不记录，因为对于一个新建的文件同时也有写关闭事件发生
        """
        pass
            
    def process_IN_DELETE(self, event):
        """删除事件，记录格式为D:somefile(绝对路径)
        """
        if event.name.startswith('.') or event.name.endswith('~'):
            pass
        else:
            fn = os.path.join(event.path, event.name)
            IFP.write('D:%s\n' % fn)
            IFP.flush()
            
    """def process_IN_MODIFY(self, event):
        if event.name.startswith('.') or event.name.endswith('~'):
            pass
        else:
            fn = os.path.join(event.path, event.name)
            IFP.write('M:%s\n' % fn)
            IFP.flush()
            print 'MODIFY event: ' + fn
            
    def process_IN_MOVE_SELF(self, event):
        if event.name.startswith('.') or event.name.endswith('~'):
            pass
        else:
            fn = os.path.join(event.path, event.name)
            IFP.write('M:%s\n' % fn)
            IFP.flush()
            print 'MOVE_SELF event: ' + fn
    def process_IN_MOVED_TO(self, event):
        if event.name.startswith('.') or event.name.endswith('~'):
            pass
        else:
            fn = os.path.join(event.path, event.name)
            IFP.write('M:%s\n' % fn)
            IFP.flush()
            print 'MOVED_TO event: ' + fn
    def process_IN_MOVED_FROM(self, event):
        if event.name.startswith('.') or event.name.endswith('~'):
            pass
        else:
            fn = os.path.join(event.path, event.name)
            IFP.write('M:%s\n' % fn)
            IFP.flush()
            print 'MOVED_FROM event: ' + fn
    """
def Monitor(path_list):
    """主监控程序
    """
    wm = WatchManager()
    notifier = Notifier(wm, MyProcessEvent())
    
    for path in path_list:
        print '增加监控路径: %s' % path
        wm.add_watch(os.path.abspath(path), MASK, rec=True, auto_add=True)# 递归并自动监控新建的路径
    print 'Watching... \nPress Ctrl+C exit'
    try:
        while 1:
            notifier.process_events()
            if notifier.check_events():
                notifier.read_events()
    except KeyboardInterrupt:
        notifier.stop()
        return

def get_Index():
    """读取WATCH_FILE，其中包含所监控的路径和文件，返回所有存在路径或文件的列表。
    @return: 存在绝对路径文件项目
    """
    result = []
    if os.path.isfile(WATCH_FILE):
        fp = open(WATCH_FILE)
        while 1:
            #@note 如果是一个文件，监控有问题？因为inotify似乎只能监控一路径，对于光光一个文件就不行了。但事实上客户端上有可能出现单个文件的。
            line = fp.readline()
            if not line:
                break
            line = os.path.abspath(line.strip())
            if os.path.exists(line):
                result.append(line)
            else:
                print '警告: %s 不存在！' % line
    return result

if __name__ == '__main__':
    Monitor(get_Index())
