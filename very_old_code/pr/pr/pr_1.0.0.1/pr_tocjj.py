#! /usr/bin/python
#  -*- coding: utf-8 -*-
#  Author : caijunjie
#  Mail   : caijunjie@baidu.com


"""

"""

__revision__ = '0.1'

import os
import sys
#from cjj_op import backup_nsop 
#from batchtools import backup_nsop 
from optparse import OptionParser

FILETYPE = {'.sh':'BASH', '.pl':'PERL', '.py':'PYTHON', '.conf':'CONF', '.txt':'TXT', '.dict':'DICT', '.c':'C'}#FTP 
    
class RelationFiles(object):
    
    def __init__(self, file, args=[], type_list=None):
        """
        initalize and do some prepare work
        """
        try:
            self.file_content = open(file).readlines()
            
            self.current_dir = []                   #save current path list 
            self.dir_tag = []                       #dir_tag
            self.dir_tag.append(0)
            
            self.child_files = {}                   #{'type':{'filename':{'exist':True/False,'linenumber':[], 'args':[], 'info':'someinfo'}}}
            self.file = file
            self.line_num = 0
            if file.startswith('/'):
                self.current_dir.append(file[:file.rfind('/')+1])
            
            self.all_path = [self.current_dir[0]]   #['pathname']
            self.variable_dic = {}
            
            doll_sharp = len(args)+1                #filename args_list: $# $0 ${1}....
            self.variable_dic['#'] = doll_sharp
            self.variable_dic['0'] = file[file.rfind('/')+1:]
            for i in range(len(args)):
                self.variable_dic[str(i+1)] = args[i]
            
            self.type_list = type_list
        except IOError, e:
            #print e  'Not Exist Bash Script File: %s\n' % file
            raise

    def cd_handle(self, line):
        """
            handle with "cd ./some" "cd /some" "cd $some" "cd ~/some" "cd ../some"
            "cd some" "cd"
        """
        teams = line.split(' ')
        try:
            dir = teams[1]
        except:
            dir = ''
        current_dir = self.get_abspath(dir)
        if self.dir_tag[-1] == 0:                   #modify or add new current directory
            self.current_dir[-1] = current_dir
        elif self.dir_tag[-1] == 1:
            self.current_dir.append(current_dir)
            self.dir_tag[-1] = 0
    
    def get_abspath(self, dir):
        """
        get absolute path: ./ ../ ~/ / ~ --->/somepath/
        """
        if dir.startswith('./'):
            current_dir = self.current_dir[-1]
            current_dir = current_dir[:current_dir.rfind('/')+1]
            dir = dir.replace("./", current_dir)
        elif dir.startswith('ftp://'):
            if dir[6] == '.' or dir[6] == '~':
                dir = self.get_abspath(dir[6:])
            else:
                dir = dir[6:]
            index = dir.find('/')
            dir = 'hostname: ' + dir[:index] + '\tdirectory: ' + dir[index:]
        elif dir.startswith('http://'):
            if dir[7] == '.' or dir[7] == '~':
                dir = self.get_abspath(dir[7:])
            else:
                dir = dir[7:]
            index = dir.rfind('/')
            dir = 'hostname: ' + dir[:index] + '\tdirectory: ' + dir[index:]
        elif dir.startswith('/'):
            pass
        elif dir.startswith('~'):
            dir = dir.replace('~', '/home/'+os.getlogin())
        elif dir.startswith('../'):
            current_dir = self.current_dir[-1][:-1]
            temp_dir = dir
            while temp_dir.startswith('../'):
                current_dir = current_dir[:current_dir.rfind('/')]
                temp_dir = temp_dir[3:]
            dir = current_dir+'/'+temp_dir
        elif dir == '':                         #empty path
            dir = '/home/'+os.getlogin()
        else:                                   #such as: cd somepath
            dir = self.current_dir[-1] + dir    #such as: cd somepath
        if dir.find('/./') != -1:               #check some special cases
            dir = dir.replace('/./', '/')
        elif dir.find('//') != -1:
            dir = dir.replace('//', '/')
        dot_dot_index = dir.find('/../')
        while dot_dot_index != -1:
            first_path = dir[:dir[:dot_dot_index].rfind('/')]
            second_path = dir[dot_dot_index+4:]
            dir = first_path+'/'+second_path
            dot_dot_index = dir.find('/../')
        if dir[-1] != '/':                      #dir always ends with '/'
            return dir+'/'
        else:
            return dir
    
    def doll_handle(self, doll_variable):
        """
        $: handle path or filename contais $variable
        """
        for (n, v) in self.variable_dic.items():
            if '$'+n in doll_variable:
                doll_variable = doll_variable.replace('$'+n, v)
            elif '${'+n in doll_variable:
                doll_variable = doll_variable.replace('${'+n+'}', v)
        return doll_variable
    
    def variable_handle(self, var):
        """
        get variable: name:value
        """
        eq_index = var.find('=')
        name = var[:eq_index]
        value = var[eq_index+1:]
        if value.startswith('`'):
            items = value[1:-1].split(' ')
            for i in items:
                if i.startswith('"') or i.startswith("'"):
                    i = i[1:-1]
                if i == '' or i.startswith('-'):
                    continue
                file_path = self.get_abspath(i)[:-1]
                if os.path.isfile(file_path):
                    self.dot_slash_handle(file_path+value[value.find(i)+len(i):-1]) #case a=`python pr.py -d mp3`
                    return
            return
        if value.startswith('$('):
            items = value[2:-1].split(' ')
            for i in items:
                if i.startswith('"') or i.startswith("'"):
                    i = i[1:-1]
                if i == '' or i.startswith('-'):
                    continue
                file_path = self.get_abspath(i)[:-1]
                if os.path.isfile(file_path):
                    self.dot_slash_handle(file_path+value[value.find(i)+len(i):-1])
                    return
            return
        #if value.startswith('"') or value.startswith("'"):
        #    value = value[1:-1]
        #self.variable_dic[name] = value
        #print name, '=', value
        ######################################################################
        if '$' in value or ';' in value:
            return
        if value.startswith('"') or value.startswith("'"):
            value = value[1:-1]
            path = self.get_abspath(value)
            if os.path.isdir(path):
                if not path in self.all_path:
                    self.all_path.append(path)
            elif os.path.isfile(path[:-1]):
                self.dot_slash_handle(path[:-1])
        self.variable_dic[name] = value
        print name, '=', value
        ######################################################################
        
    def wget_handle(self, line):
        """
        wget line, we want to get ftp/http path
        """
        if self.type_list is None or 'FTP/HTTP' in self.type_list:
            ftp_index = line.find('ftp://')     #get hostname and download path
            if ftp_index == -1:
                ftp_index = line.find('http://')
            if ftp_index == -1:                 #path error, so skip
                return
            else:
                if line.endswith('"'):          #case wget xxx "ftp://www.xxx.com/home/xxx"
                    line = line[:-1]
                ftp_str = line[ftp_index:]
        
            ftp_str = self.get_abspath(ftp_str)[:-1]
            self.add_child(ftp_str, type='FTP/HTTP')
        
    def dot_slash_handle(self, line):
        """
        get ./a(.sh|.py|.pl|.conf)
        """
        teams = line.split(' ')
        child_pro = teams[0]
        child_pro = self.get_abspath(child_pro)[:-1]
        file_name = child_pro.split('/')[-1]
        dot_index = file_name.find('.')
        file_type = '.c'
        if dot_index != -1:
            file_type = file_name[dot_index:]
        if FILETYPE.has_key(file_type):
            file_type = FILETYPE[file_type]
        else:
            return                      #unknown file type
        if self.type_list is None or file_type in self.type_list:
            args_list = []
            for a in teams[1:]:         #get args... ignor -o -i
                if a.startswith('"') or a.startswith("'"):
                    a = a[1:-1]
                if a == '' or a.startswith('-'):
                    pass
                elif a in ['>', '<']:   #case : sh some.sh -o option > /tmp
                    break
                elif os.path.isdir(a):
                    args_list.append(self.get_abspath(a))
                elif os.path.isfile(a):
                    args_list.append(self.get_abspath(a)[:-1])
                else:
                    args_list.append(a)
            self.add_child(child_pro, type=file_type, args=args_list)            

    def sh_perl_python_handle(self, line):
        """
        sh xxx file.sh
        """
        teams = line.split(' ')
        for i in range(len(teams)):
            dot_index = teams[i].rfind('.')     #find first .sh or .py or .pl
            if dot_index != -1:
                file_type = FILETYPE[teams[i][dot_index:]]
                if self.type_list is None or file_type in self.type_list:
                    args_list = []
                    for a in teams[i+1:]:       #get args... ignor -o -i
                        if a.startswith('"') or a.startswith("'"):
                            a = a[1:-1]
                        if a == '' or a.startswith('-'):
                            pass
                        elif a in ['>', '<']:
                            break
                        elif os.path.isdir(a):
                                args_list.append(self.get_abspath(a))
                        elif os.path.isfile(a):
                            args_list.append(self.get_abspath(a)[:-1])
                        else:
                            args_list.append(a)                                                
                    self.add_child(self.get_abspath(teams[i])[:-1], type=file_type, args=args_list)
                break
                
    def conf_handle(self, line):
        """
        one line contains some conf files
        """
        for one in line.split(' '):
            if one.endswith('.conf'):
                self.add_child(self.get_abspath(one)[:-1], type='CONF')
        
    def add_child(self, file, type=None, args=[]):
        """
        add one child file name: file exist or not, get file type, save child file
        """
        if type in ['C', 'PYTHON', 'BASH', 'PERL', 'CONF']: #{'type':{'filename':{'exist':True/False,'linenumber':[], 'args':[], 'info':'someinfo'}}}
            exists_sign = True      #check that file is exist or not
            if not os.path.isfile(file):
                exists_sign = False
            md5value = None         #get md5 value
            if exists_sign and type != 'CONF':
                try:
                    md5value = os.popen('md5sum '+file).readlines()[0].split(' ')[0]
                except:
                    pass
            
            if self.child_files.has_key(type):
                if not file in self.child_files[type].keys():
                    self.child_files[type][file] = {'exist':exists_sign, 'linenumber':[self.line_num]}
                    if args:
                        self.child_files[type][file]['args'] = [args]
                    if md5value:
                        self.child_files[type][file]['md5'] = md5value
                else:
                    self.child_files[type][file]['linenumber'].append(self.line_num)
                    if args:#####
                        if self.child_files[type][file].has_key('args'):
                            self.child_files[type][file]['args'].append(args)
                        else:
                            self.child_files[type][file]['args'] = [args]
            else:
                self.child_files[type] = {file:{'exist':exists_sign, 'linenumber':[self.line_num]}}
                if args:
                    self.child_files[type][file]['args'] = [args]
                if md5value:
                    self.child_files[type][file]['md5'] = md5value
        elif type in ['TXT', 'DICT']:
            if self.child_files.has_key(type):
                if not file in self.child_files[type].keys():
                    self.child_files[type][file] = {}
            else:
                self.child_files[type] = {file:{}}
        elif type in ['FTP/HTTP']:
            filename = file[file.rfind('/')+1:]         #get file type
            dot_index = filename.rfind('.')
            if dot_index != -1:
                try:
                    type = FILETYPE[filename[filename.rfind('.'):]]
                except KeyError, e:
                    #print 'Unknown Type File: %s\n' % filename
                    pass
            if type == 'FTP/HTTP':
                type_str = 'FTP/HTTP'
            else:
                type_str = "FTP/HTTP-->"+type
            
            if self.child_files.has_key(type):
                if not file in self.child_files[type].keys():
                    self.child_files[type][file] = {'info':type_str}
                else:
                    pass
            else:
                self.child_files[type] = {file:{'info':type_str}}
        
        path = file[:file.rfind('/')+1]                 #save to self.all_path
        if os.path.isdir(path):
            if not path in self.all_path:
                self.all_path.append(path)
        
    def find_relation(self):
        """
        find_relation: find c, py, pl, txt...files that current file called
        """
        for line in self.file_content:
            line=line.strip()
            self.line_num=self.line_num+1
            if line.startswith('#') or line=="" or line.startswith('echo '):
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
            
            if '$' in line:
                line = self.doll_handle(line)
            if line.startswith('wget '):
                self.wget_handle(line)
            elif '=' in line:
                self.variable_handle(line)
            elif (self.type_list is None or 'CONF' in self.type_list) and '.conf' in line:
                self.conf_handle(line)
            elif '$' not in line:
                if line.startswith('cd ') or line == "cd":      #get current path
                    self.cd_handle(line)
                elif line.startswith('./') or line.startswith('/') or line.startswith('../') or line.startswith('~/'):
                    self.dot_slash_handle(line)
                elif line.startswith('sh ') or line.startswith('python ') or line.startswith('perl '):
                    self.sh_perl_python_handle(line)
            
    def get_all_path(self):
        return self.all_path
    def get_child_files(self):
        return self.child_files
    def get_sh_children(self):
        if self.child_files.has_key('BASH'):
            return self.child_files['BASH']
        return None
    def __str__(self):
        string = "Bash Script File Name: %s---->\n" % self.file
        if self.type_list is None:
            string += "\n---All Path in this File:\n"
            for path in self.all_path:
                string += '\t%s\n' % path
        string += "\n---All Children in this File:\n"
        if self.child_files:
            for (type, file_dic) in self.child_files.items():
                string += '\tType: %s\n' % type
                for (filename, fileinfo) in self.child_files[type].items():
                    string += '\t\tFile Name: %s\n' % filename
                    for items in fileinfo.keys():
                        string += '\t\t----%s: %s\n' % (items, fileinfo[items])
        else:
            if self.type_list:
                string += "Don't Have any %s Children in this File\n" % self.type_list
            else:
                string += "Don't Have any Children in this File\n"
        string += '\n' + '-'*100 + '\n'
        return string

def directory_handle(dir):
    """
    directory_handle: recursively find sub-dir and files
    """
    for root, dirs, files in os.walk(dir):
        sub_files = [ os.path.join(root, name) for name in files ]
        sub_files.sort()
        for file in sub_files:
            if file.endswith('.sh'):
                file_handle(file)

def file_handle(file, args=[]):
    """
    file_handle: analyze shell script
    """
    global relation_files
    global not_exist_script
    global type_list
    
    if not relation_files.has_key(file):
        try:
            onefile = RelationFiles(file, args, type_list)  #handle a shell script
            onefile.find_relation()                         #get relations
            relation_files[file] = onefile
            
            sh_children = onefile.get_sh_children()         #handle child scripts if this file has
            if sh_children:
                for (sh_name, sh_dic) in sh_children.items():
                    if sh_dic.has_key('args'):
                        file_handle(sh_name, args=sh_dic['args'][-1])####last one
                    else:
                        file_handle(sh_name)
        except IOError,e:
            #print 'Not Exist Bash Script File: %s\n' % file
            not_exist_script.append(file)

def get_usage():
    usage = """
        %prog [options] action [applist]:
        action: file relations
        """
    return usage

def directory_option(option, opt, value, parser):
    global args_sign
    args_sign = False
    
    global type_list
    if parser.values.type:
        type_list = parser.largs
    dir = os.path.abspath(value)
    if os.path.isdir(dir):
        directory_handle(dir)
    else:
        print '%s is not a Directory' % dir

def file_option(option, opt, value, parser):
    global args_sign
    args_sign = False
    
    global type_list
    if parser.values.type:
        type_list = parser.largs
    file_abspath = os.path.abspath(value)
    if os.path.isfile(file_abspath) and file_abspath.endswith('.sh'):
        file_handle(file_abspath)
    else:
        print '%s is not exists or is not a bash script' % file_abspath
        
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
                files.append(os.path.abspath(line))
        return files
    except IOError,e:
        print e, "\nconfigure file: %s not exist\n" % confile
        sys.exit(0)
def configure_option(option, opt, value, parser):
    try:
        global args_sign
        args_sign = False
        global type_list
        if parser.values.type:
            type_list = parser.largs
        
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
    global relation_files
    relation_files = {}
    global not_exist_script
    not_exist_script = []
    
    global args_sign
    args_sign = True
    global type_list
    type_list = None
    
    if argv is None:
        argv = sys.argv
    parser = OptionParser(usage=get_usage())
    parser.add_option("-d", "--dictory",
                        action="callback",
                        type="string",
                        help="check file's call relationship in dir",
                        metavar="string",
                        callback=directory_option)
    parser.add_option("-f", "--file",
                        action="callback",
                        type="string",
                        help="check one file's call relationship",
                        metavar="FILE",
                        callback=file_option)
    parser.add_option("-c", "--configure",
                        action="callback",
                        type="string",
                        help="read configuration from FILE",
                        metavar="FILE",
                        callback=configure_option)
    parser.add_option("-t", "--type",
                        action="store_true",
                        help="file/script type such as: BASH, C, PERL, PYTHON, TXT")
    (options, args) = parser.parse_args(argv[1:])
    if args_sign:
        parser.print_help()
    else:
        for e in not_exist_script:
            print 'Not Exist Bash Script File: %s' % e
        print '='*100
        for (file, file_relations) in relation_files.items():
            print file_relations
if __name__ == "__main__" :
    do_main()