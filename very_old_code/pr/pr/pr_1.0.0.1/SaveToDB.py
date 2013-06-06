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
                                            unix_socket='/var/run/mysqld/mysqld.sock')
            self.cursor = self.connect.cursor()
        except Exception, e:
            raise
    def some_prepare(self):
        """
        create database or tables
        """
        try:
            self.cursor.execute('drop database prdb;')  #first delete old db if it exists
        except Exception, e:
            pass
        try:
            self.cursor.execute('create database prdb;')#create db and files_table
            self.cursor.execute('use prdb;')
            self.cursor.execute('create table files_table(id tinyint unsigned not null, primary key(id), name varchar(100), type varchar(20), info varchar(200))')
            print "Create database prdb and table files_table correctly"
        except Exception, e:
            raise

    def save_relations(self, relation_files):
        """
        two tables: file information table and relation table
        """
        sql_string = ''
        try:
            fileid = 1
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
                sql_string = 'create table %s_children(id tinyint unsigned not null, info varchar(200), foreign key(id) references files_table(id), primary key(id))'
                self.cursor.execute(sql_string, (fid,))#creete this file's children table
                print 'create table %s_children' % fid
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
                                print 'insert into files_table: (%s, %s)' % (fileid, filename)
                            else:
                                self.cursor.execute(sql_string, (fileid, ftp_filename, type, tinfo,))
                                print 'insert into files_table: (%s, %s)' % (fileid, ftp_filename)
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
                        print 'insert into %s_children(%s)' % (fid, tid)
                self.cursor.execute('COMMIT')
        except Exception, e:
            raise
        
    def close_connect(self):
        try:
            self.cursor.close()
            self.connect.close()
        except Exception, e:
            raise    