#! /usr/bin/python
#  -*- coding: utf-8 -*-
#  Author : caijunjie
#  Mail   : caijunjie@baidu.com


"""

"""

__revision__ = '0.1'

import os
import sys
import re
#from cjj_op import backup_nsop 
#from batchtools import backup_nsop 
from graph import Graph
from optparse import OptionParser

FILETYPE = {0:'BASH', 1:'PERL', 2:'PYTHON', 3:'C', 4:'CONF', 5:'TXT', 6:'DICT'}

class RelationFiles(object):
    
    def __init__(self, file):
        """
        """
        try:
            self.file_content=open(file).readlines()
            
            #设计需要，RelationFiles类只接收有绝对路径的文件
            #记录当前所走的路径，保存当前路径为file的路径
            self.current_dir=[]   
            #dir_tag标记，用于指示下一次改变路径是新增还是修改，0代表修改，1代表新增
            self.dir_tag=[]
            self.dir_tag.append(0)
            #file中包涵的子脚本和子程序列表
            self.child_files={}
            self.file=file
            self.line_num=0
            if file.startswith('/'):
                self.current_dir.append(file[:file.rfind('/')+1])
        except IOError, e:
            print e
            raise
    def cd_handle(self, line):
        """
            handle with "cd ./some" "cd /some" "cd $some" "cd ~/some" "cd ../some"
            "cd some" "cd "x
        """
        teams=line.split(" ")
        dir=teams[1]
        current_dir=""
        if dir.find('$') != -1:
        #如果cd 后面跟的是变量名，即路径存放在变量中，搜索策略
            current_dir = self.doll_handle(dir)
        else:
            current_dir = self.get_abspath(dir)

        if self.dir_tag[-1]==0:
            self.current_dir[-1]=current_dir
        elif self.dir_tag[-1]==1:
            self.current_dir.append(current_dir)
            self.dir_tag[-1]=0
    
    def get_abspath(self, dir):
        """
        """
        if dir.startswith('./'):
            current_dir=self.current_dir[-1]
            current_dir=current_dir[:current_dir.rfind('/')+1]
            dir=dir.replace("./",current_dir)
        elif dir.startswith('/') or dir.startswith('ftp://'):
            pass
        elif dir.startswith('~'):
            dir=dir.replace('~','/home/'+os.getlogin())
        elif dir.startswith('../'):
            current_dir=self.current_dir[-1][:-1]
            temp_dir=dir
            while temp_dir.startswith('../'):
                current_dir=current_dir[:current_dir.rfind('/')]
                temp_dir=temp_dir[3:]
            dir=current_dir+'/'+temp_dir
        elif dir is None:
            dir = os.getcwd()
        else:
            dir=self.current_dir[-1] + dir
        if dir[-1] != '/':
            return dir+'/'
        else:
            return dir     
    
    def doll_handle(self, variable):
        #路径可能是有多个$组成，目前不支持模块内重名变量
        teams = variable.split('/')
        #teams=variable.split("$")
        refh=open(self.file).readlines()
        num=0
        doll_current_dir = variable
        for line in refh:
            line=line.strip()
            num=num+1
            if num==self.line_num:
                break
            eq_index=line.find('=')
            if eq_index>0:
                #if '$' + line[:eq_index] in teams:
                #    doll_current_dir.replace('$' + line[:eq_index], line[eq_index+2:len(line)-1])
                for i in range(len(teams)) :
                    if teams[i] == '$' + line[:eq_index] or teams[i] == '${' + line[:eq_index] + '}':
                        teams[i] = line[eq_index+2:len(line)-1]
                        if teams[i][-1] == '/':
                            teams[i] = teams[i][:-1]
        doll_current_dir = '/'.join(teams)
        #print 'shengyan....', doll_current_dir
        return self.get_abspath(doll_current_dir)
    
    def find_relation(self):
        """
        find_relation寻找当前file调用的程序、脚本、配置等等文件
        """
        for line in self.file_content:
            line=line.strip()            
            self.line_num=self.line_num+1
            if line.startswith('#') or line=="":
                continue
            
            #get current file's directory
            if line.startswith('cd '):
                self.cd_handle(line)
                continue
            
            if line.startswith('if ') or line[0:len(line)+1]=='do':
                if self.dir_tag[-1]==1:
                    self.dir_tag.append(1)
                elif self.dir_tag[-1]==0:
                    self.dir_tag[-1]=1
                continue
            if line[0:len(line)+1]=='fi' or line[0:len(line)+1]=='done':
                if self.dir_tag[-1]==1:
                    self.dir_tag[-1]=0
                    if len(self.dir_tag)>1:
                        self.dir_tag.pop()
                elif self.dir_tag[-1]==0:
                    try:
                        self.current_dir.pop()
                    except IndexError,e:
                        print e, self.current_dir, self.dir_tag, line
                        sys.exit(0)
                continue
            
            pt_file = re.compile(r'(?P<file>[$:{}.~/a-zA-Z0-9_-]*\.(sh|py|pl|txt|dict|conf))\b')
            teams = pt_file.findall(line)
            for (one, onetype) in teams:
                #若文件名中包含$var变量名和路径混合
                if one.find('$') != -1:
                    #print '111111111111111', (one, onetype),'\n'
                    self.add_child(self.doll_handle(one)[:-1])
                else:
                    self.add_child(self.get_abspath(one)[:-1])
                
    def add_child(self, file):
        """
        add one child file name：先判断文件类型和是否已经包含此文件，然后保存
        """
        filename = file[file.rfind('/')+1:]
        dot_index = filename.rfind('.')
        if dot_index != -1:
            type = filename[filename.rfind('.'):]
        else:
            type = '.c'
        if self.child_files.has_key(type):
            self.child_files[type].append(file)
        else:
            self.child_files[type] = [file]
        
    def get_child_files(self):
        return self.child_files

def get_absolute_path(path):#X
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
        dir=os.path.abspath('pr.py')
        dir=dir[:dir.rfind('/')+1]
        return dir + path

def directory_handle(dir):
    """
    directory_handle目录处理：递归寻找子目录及当前目录下的所有文件
    """
    for root, dirs, files in os.walk(dir):
        sub_files = [ os.path.join(root, name) for name in files ]
        sub_files.sort()
        for file in sub_files:
            if file.endswith('.sh'):
                file_handle(file)

def file_handle(file):
    """
    file_handle文件处理：分析shell脚本
    """
    global fr_graph
    if not fr_graph.contains(file):###是否已经包含shell脚本
        fr_graph.add_node(file)           ###加入新的节点
    if not fr_graph.isHandled(file):             ###是否已经处理了
        try:
            file_relation = RelationFiles(file) ###
            file_relation.find_relation()       ###分析shell脚本
            children = file_relation.get_child_files()####边关系直接可以在类里做
            if not children:
                return
            del file_relation
            for (child_type, child) in children.items():
                for one in child:
                    fr_graph.add_node(one)
                    fr_graph.add_edge(file, one, child_type)
            for sh_child in children['.sh']:
                file_handle(sh_child)
        except IOError,e:
            pass

def get_usage():
    usage = """
        %prog [options] action [applist]:
        action: file relations
        """
    return usage

def directory_option(option, opt, value, parser):
    """directory_option"""
    #dir = get_absolute_path(value)
    dir = os.path.abspath(value)###判断目录是否存在
    directory_handle(dir)

def file_option(option, opt, value, parser):
    """file_option"""
    #file_abspath = get_absolute_path(value)[:-1]
    file_abspath = os.path.abspath(value)
    if file_abspath.endswith('.sh'):
        file_handle(file_abspath)

def read_conf_files(confile):
    """
    read the content from the configure_file,and transform it as a file 
    with abspath
    """    
    try:
        content=open(confile).readlines()
        files=[]
        for line in content:
            line=line.strip()
            if line.startswith('#'):
                continue
            elif line.startswith('/') or line.startswith('./') or line.startswith('~'):
                #files.append(get_absolute_path(line))
                files.append(os.path.abspath(line))
        return files
    except IOError,e:
        print e, "\nconfigure file: %s not exist\n" % confile
        sys.exit(0)
def configure_option(option, opt, value, parser):
    """configure_option"""
    try:
        dir_or_file=read_conf_files(value)
        for d_or_f in dir_or_file:
            d_or_f=d_or_f.strip()
            if os.path.isdir(d_or_f):
                directory_handle(d_or_f)
            elif os.path.isfile(d_or_f) and d_or_f.endswith('.sh'):
                file_handle(d_or_f)
            else:
                print "this is undefined file/dir"
    except IOError,e:
        print e
        sys.exit(0)
    
def do_main(argv=None):
    """
    main
    """
    if argv is None:
        argv = sys.argv
    parser = OptionParser(usage=get_usage())
    parser.add_option("-d", "--dictory",
                        action="callback",
                        type="string",
                        help="查看一个目录下的文件依赖关系",
                        metavar="string",
                        callback=directory_option)
    parser.add_option("-f", "--file",
                        action="callback",
                        type="string",
                        help="查看某个脚本的依赖关系",
                        metavar="FILE",
                        callback=file_option)
    parser.add_option("-c", "--configure",
                        action="callback",
                        type="string",
                        help="read configuration from FILE",
                        metavar="FILE",
                        callback=configure_option)
    parser.add_option("-y", "--yyy",
                        action="store",
                        type="string",
                        help="use settings in section SEC from config file",
                        metavar="SEC",
                        dest="section")

    (options, args) = parser.parse_args(argv[1:])
    #print options, args
    # 如果没有指定文件和目录，就默认是当前目录
    #if not (options.dictory or options.file):
    #    print options.dictory, options.file
    #    dir=os.path.abspath('pr.py')
    #    dir=dir[:dir.rfind('/')+1]
    #     directory_handle(dir)

if __name__ == "__main__" :
    fr_graph = Graph()
    do_main()
    print fr_graph