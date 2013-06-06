#!/usr/bin/python
#coding:utf-8

"""公共头文件
    @version: 0.1
    @author: lizzie
    @contact: shengyan1985@gmail.com
"""

# 服务器端备份文件目标目录
DESTDIR = '/home/shengyan/backup'
# 客户端不处理的文件后缀名
STOP_LIST = ['.pyc', '.lrc', '.tmp', '.out']
# 更新文件列表，即是Myinotify.py和main.py同时访问更新的文件，可以不存在
INDEX_FILE = '/home/shengyan/workspace/Backup_v2/index'
# 需要备份的文件目录或文件名，必须存在
WATCH_FILE = '/home/shengyan/workspace/Backup_v2/watch_file'
# 使用端口
PORT = 7878
# 服务器端IP
SERVERIP = '192.168.0.57'

