#!/usr/bin/env python
#coding=utf-8

import os
def get_absolute_path(path):
    """
    get absolute path
    """
    #path=path.strip()
    if not path.endswith('/'):
        path = path + '/'
    if path.startswith('/'):
        return path
    elif path.startswith('./'):
        dir=os.path.abspath('pr.py')
        dir=dir[:dir.rfind('/')+1]
        return path.replace('./',dir)
    elif path.startswith('~'):
        dir='/home/'+os.getlogin()
        return path.replace('~',dir)
    elif path.startswith('../'):
        temp=path
        current_dir = os.path.abspath('pr.py')
        while temp.startswith('../'):
            current_dir=current_dir[:current_dir.rfind('/')]
            temp=temp[3:]
        return current_dir + '/' + temp
    else:
        #dir=os.path.abspath('pr.py')
        #dir=dir[:dir.rfind('/')+1]
        #return dir + path
        return os.path.abspath(path)
print os.path.abspath('.')
print os.path.abspath('..')
print os.path.abspath('./')
print os.path.abspath('../')
print os.path.abspath('../../a/')
print os.path.abspath('../../a.sh')

print '..................\n'
print get_absolute_path('.')
print get_absolute_path('./')
print get_absolute_path('..')
print get_absolute_path('../')
print get_absolute_path('~')
print get_absolute_path('~/')
print get_absolute_path('.a')
print get_absolute_path('.')


class Enum:
    def __init__(self, **entries):
        self.__dict__.update(entries)
        
    def __repr__(self):
            args = ['%s=%s' % (k, repr(v)) for (k,v) in vars(self).items()]
            return 'Enum(%s)' % ', '.join(args)

FILETYPE = Enum(BASH=0, PERL=1, PYTHON=2, C=3, CONF=4, TXT=5, DICT=6)
print vars(FILETYPE).items()

