#! /usr/bin/python
#  -*- coding: utf-8 -*-
import MySQLdb

class MyDBTool(object):
    def __init__(self, host='localhost', db='prdb', user='root', passwd=''):
        self.host = host
        self.db = db
        self.user = user
        self.passwd = passwd
        try:
            self.connect = MySQLdb.connect(host=self.host, 
                                            db=self.db, 
                                            user=self.user, 
                                            passwd=self.passwd,
                                            )
            self.cursor = self.connect.cursor()
        except Exception, e:
            raise   ######如果prdb一开始就不存在的话，这边产生异常，connect未连接。待改进？
    def create_db(self):
        """
        create database and files_table
        """
        try:
            self.cursor.execute('create database prdb;')
            self.cursor.execute('use prdb;')
            self.cursor.execute('create table files_table(id tinyint unsigned not null, primary key(id), name varchar(100), type varchar(20), info varchar(200))')
            print "Create database prdb and table files_table correctly"
        except Exception, e:
            raise

    def clear_all(self):
        """
        clear all tables in prdb
        """
        self.some_prepare()
            
    def some_prepare(self):
        """
        create database or tables
        """
        try:
            self.cursor.execute('drop database prdb;')  #删掉原有的prdb，重新创建空的
        except Exception, e:
            pass
        self.create_db()
        
    def some_prepare_other(self):
        """
        add to database
        """
        try:
            self.cursor.execute('use prdb;')
            sql_string = 'select * from files_table;'  #如果files_table不存在（表示prdb为空的数据库），则抛出异常
            self.cursor.execute(sql_string)
        except Exception, e:
            self.some_prepare()   #创建新的prdb和files_table，统一起来就，虽然prdb存在而且是空的，但为了统一就调some_prepare把旧prdb删掉后，重新创建空的prdb和空的files_table
    
    def get_fileid(self):   #得到当前files_table中最大的id，该id唯一，应递加保存
        sql_string = 'select MAX(id) from files_table'
        self.cursor.execute(sql_string)
        fi, = self.cursor.fetchone()
        if not fi:
            fi = 0
        return fi
        
    def save_relations(self, relation_files, start_fileid=1):
        """
        two tables: file information table and relation table
        """
        sql_string = ''
        try:
            fileid = start_fileid
            for (file, (file_info, md5value)) in relation_files.items():
                self.cursor.execute('START TRANSACTION')
                sql_string = 'select * from files_table where name=%s'
                self.cursor.execute(sql_string, (file,))
                tmp = self.cursor.fetchone()
                if tmp :                #this file is already exists 
                    fid, fname, ftype, finfo = tmp
                else:                   #insert this file
                    sql_string = 'insert into files_table(id, name, type, info) values(%s, %s, %s, %s)'
                    self.cursor.execute(sql_string, (fileid, file, 'BASH', 'exist:True;md5:'+md5value+';',))
                    print 'insert into files_table: (%s, %s)' % (fileid, file)
                    fid = fileid
                    fileid += 1
                try:
                    sql_string = 'create table %s_children(id tinyint unsigned not null, info varchar(200), foreign key(id) references files_table(id), primary key(id))'
                    self.cursor.execute(sql_string, (fid,))#create this file's children table
                    print 'create table %s_children' % fid
                except:# 最好能知道表冲突的异常名，就替代这边。若这边产生已存表异常，则表示已经处理过相应脚本，就直接跳过
                    continue
                for (type, file_dic) in file_info.get_child_files().items():
                    for filename in file_dic.keys():
                        self.cursor.execute('select * from files_table where name=%s', (filename,))
                        tmp = self.cursor.fetchone()
                        if tmp :        #this file is already exists 
                            tid, tname, ttype, tinfo = tmp
                        else:
                            tinfo = ''
                            if file_dic[filename].has_key('exist'):
                                tinfo += 'exist:%s;' % file_dic[filename]['exist']
                            if file_dic[filename].has_key('md5'):
                                tinfo += 'md5:%s;' % file_dic[filename]['md5']
                            ftp_filename = ''
                            if file_dic[filename].has_key('info'):
                                tinfo += 'info:%s;' % file_dic[filename]['info']
                                host_index = filename.find('hostname: ')+10
                                file_index = filename.find('file: ')+6
                                ftp_hostname = filename[host_index:file_index-7]
                                ftp_filename = filename[file_index:]
                                tinfo += 'host:%s;' % ftp_hostname
                            #if tinfo == '':
                            #    tinfo = 'info...'
                            sql_string = 'insert into files_table(id, name, type, info) values(%s, %s, %s, %s)'
                            if ftp_filename == '':
                                self.cursor.execute(sql_string, (fileid, filename, type, tinfo,))
                                # print 'insert into files_table: (%s, %s)' % (fileid, filename)
                            else:
                                self.cursor.execute(sql_string, (fileid, ftp_filename, type, tinfo,))
                                # print 'insert into files_table: (%s, %s)' % (fileid, ftp_filename)
                            tid = fileid
                            fileid += 1
                        tinfo = ''
                        if file_dic[filename].has_key('linenumber'):
                            tinfo += 'linenumber:%s;' % file_dic[filename]['linenumber']
                        if file_dic[filename].has_key('args'):
                            tinfo += 'args:%s;' % file_dic[filename]['args']
                        sql_string = 'insert into %s_children(id, info) values(%s, %s)'
                        if tinfo == '':
                            tinfo = 'no other info'
                        self.cursor.execute(sql_string, (fid, tid, tinfo,))
                        # print 'insert into %s_children(%s)' % (fid, tid)
                self.cursor.execute('COMMIT')
        except Exception, e:
            raise
        
    def close_connect(self):
        try:
            self.cursor.close()
            self.connect.close()
        except Exception, e:
            raise    