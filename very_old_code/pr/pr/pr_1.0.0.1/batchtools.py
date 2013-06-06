#! /usr/bin/python
#  -*- coding: utf-8 -*-
#  Author : caijunjie
#  Mail   : caijunjie@baidu.com


"""

"""


import os


class backup_nsop(object):
    
    """
    """
    
    def __init__(self,relation):
        self.relation={}
        self.relation=relation
        self.dirs_list=[]
        self.file_list=[]
        
    def mkdirs(self):
        try:
            fh=open("1.txt",'wa')
            for keys in self.relation.keys():
                temp=keys.strip()
                temp=temp[:temp.rfind('/')+1]
                if temp in self.dirs_list:
                    pass
                else:
                    self.dirs_list.append(temp)
                    if os.path.exists("."+temp)==False:
                        os.system("mkdir -p .%s" % temp)
                        
                if keys.strip() not in self.file_list and os.path.exists("."+keys.strip())==False and keys.startswith('/home'):
                    os.system("cp %s %s" % (keys.strip(),"."+keys.strip()))
                    self.file_list.append(keys.strip())
                for item in self.relation[keys]:
                    temp=item.strip()
                    temp=temp[:temp.rfind('/')+1]
                    if temp in self.dirs_list:
                        pass
                    else:
                        self.dirs_list.append(temp)
                        if temp.startswith('/home') and os.path.exists("."+temp)==False:
                            os.system("mkdir -p .%s" % temp)
                    if item.strip() not in self.file_list and os.path.exists("."+item.strip())==False and item.startswith('/home'):
                        os.system("cp %s %s" % (item.strip(),"."+item.strip()))
                        self.file_list.append(item.strip())
            
        except IOError,e:
            print e
            raise
    
    
    
