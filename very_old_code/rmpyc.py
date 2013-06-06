#! /usr/bin/python
#  -*- coding: utf-8 -*-
#  Author : caijunjie
#  Mail   : caijunjie@baidu.com

import os

"""

删除pyc文件

"""

__revision__ = '0.1'


if __name__ == "__main__" :
    
    for dirpath,dirnames,filenames in os.walk("."):
        for filename in filenames:
            if filename.endswith(".pyc") or filename.endswith("~"):
                file = os.path.join(dirpath,filename)
                os.remove(file)
