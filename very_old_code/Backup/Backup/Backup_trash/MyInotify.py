#!/usr/bin/python
#coding:utf-8

"""检测监控路径的更新，并将其更新信息记录至index文件
    @note: 需要pyinotify模块
    @version: 0.1
    @author: lizzie
"""

import os, sys
from pyinotify import WatchManager, Notifier, ProcessEvent, EventsCodes

MASK = EventsCodes.IN_CLOSE_WRITE|EventsCodes.IN_DELETE|EventsCodes.IN_CREATE#|EventsCodes.IN_ATTRIB|EventsCodes.IN_MODIFY|EventsCodes.IN_MOVE_SELF#|EventsCodes.IN_MOVED_FROM|EventsCodes.IN_MOVED_TO

INDEX_FILE = open('/home/shengyan/index', 'a')
WATCH_FILE = '/home/shengyan/workspace/config'

class MyProcessEvent(ProcessEvent):
    """def process_IN_ATTRIB(self, event):
        # 路径不检测，只取文件
        if event.name.startswith('.') or event.name.endswith('~'):
            pass
        else:
            fn = os.path.join(event.path, event.name)
            INDEX_FILE.write('U:%s\n' % fn)
            INDEX_FILE.flush()
            print 'ATTRIB event: ' + fn
            """
    def process_IN_CLOSE_WRITE(self, event):
        if event.name.startswith('.') or event.name.endswith('~'):
            pass
        else:
            if os.path.isfile(event.path):
                fn = event.path
            else:
                fn = os.path.join(event.path, event.name)
            INDEX_FILE.write('U:%s\n' % fn)
            INDEX_FILE.flush()
            
    def process_IN_CREATE(self, event):
        pass
            
    def process_IN_DELETE(self, event):
        if event.name.startswith('.') or event.name.endswith('~'):
            pass
        else:
            fn = os.path.join(event.path, event.name)
            INDEX_FILE.write('D:%s\n' % fn)
            INDEX_FILE.flush()
            
    """def process_IN_MODIFY(self, event):
        if event.name.startswith('.') or event.name.endswith('~'):
            pass
        else:
            fn = os.path.join(event.path, event.name)
            INDEX_FILE.write('M:%s\n' % fn)
            INDEX_FILE.flush()
            print 'MODIFY event: ' + fn
            
    def process_IN_MOVE_SELF(self, event):
        if event.name.startswith('.') or event.name.endswith('~'):
            pass
        else:
            fn = os.path.join(event.path, event.name)
            INDEX_FILE.write('M:%s\n' % fn)
            INDEX_FILE.flush()
            print 'MOVE_SELF event: ' + fn
    def process_IN_MOVED_TO(self, event):
        if event.name.startswith('.') or event.name.endswith('~'):
            pass
        else:
            fn = os.path.join(event.path, event.name)
            INDEX_FILE.write('M:%s\n' % fn)
            INDEX_FILE.flush()
            print 'MOVED_TO event: ' + fn
    def process_IN_MOVED_FROM(self, event):
        if event.name.startswith('.') or event.name.endswith('~'):
            pass
        else:
            fn = os.path.join(event.path, event.name)
            INDEX_FILE.write('M:%s\n' % fn)
            INDEX_FILE.flush()
            print 'MOVED_FROM event: ' + fn
    """
def Monitor(path_list):
    wm = WatchManager()
    notifier = Notifier(wm, MyProcessEvent())
    
    for path in path_list:
        print '增加监控路径: %s' % path
        wm.add_watch(os.path.abspath(path), MASK, rec=True, auto_add=True)

    try:
        while 1:
            notifier.process_events()
            if notifier.check_events():
                notifier.read_events()
    except KeyboardInterrupt:
        notifier.stop()
        return

def get_Index():
    """读取config文件，其中包含所监控的路径和文件，返回所有存在路径或文件的列表。
    @return: 存在绝对路径文件项目
    """
    result = []
    if os.path.isfile(WATCH_FILE):
        fp = open(WATCH_FILE)
        while 1:
            line = fp.readline()    # 如果是一个文件，监控有问题？
            if not line:
                break
            line = os.path.abspath(line.strip())
            if os.path.exists(line):
                result.append(line)
            else:
                print 'Warning: %s is not exist!' % line
    return result

if __name__ == '__main__':
    Monitor(get_Index())
