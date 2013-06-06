#!/usr/bin/python
#coding:utf-8

# 服务器端备份文件目标目录
DESTDIR = '/home/shengyan/backup2'
# 客户端不处理的文件后缀名
STOP_LIST = ['.pyc', '.lrc', '.tmp', '.out']
# 更新文件列表，即是Myinotify.py和main.py同时访问更新的文件
INDEX_FILE = '/home/shengyan/workspace/Backup/index'
# 需要备份的文件目录或文件名
WATCH_FILE = '/home/shengyan/workspace/Backup/watch_file'