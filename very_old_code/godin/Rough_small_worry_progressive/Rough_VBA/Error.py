#!/usr/bin/python
#coding=utf-8

"""Error.py: 自定义错误类
@author: Lizzie
@license: ...
@contact: shengyan1985@gmail.com
@version: 0.2
"""

class FCError(Exception):
    """错误基类
    """
    pass

class ObjectError(FCError):
    """
    """
    def __init__(self, msg):
        self.error_msg = msg
    def __str__(self):
        return self.error_msg

class AttributeError(FCError):
    """
    """
    def __init__(self, msg):
        self.error_msg = msg
    def __str__(self):
        return self.error_msg

class BRError(FCError):
    """
    """
    def __init__(self, msg):
        self.error_msg = msg
    def __str__(self):
        return self.error_msg