#! /usr/bin/python
#  -*- coding: utf-8 -*-

import MySQLdb

class MysqlExcuter(object):
    def __init__(self, host='localhost', db='prdb', user='root', passwd='lizzie1985'):
        self.host = host
        self.db = db
        self.user = user
        self.passwd = passwd
        try:
            self.connect = MySQLdb.connect(host=self.host, 
                                            db=self.db, 
                                            user=self.user, 
                                            passwd=self.passwd,
                                            unix_socket='/tmp/mysql.sock')
            self.cursor = self.connect.cursor()
        except Exception, e:
            raise
    
    def do_excute(self, statment, paramter):
        try:
            self.cursor.execute(statment, paramter)
            self.result = self.cursor.fetchall()
        except Exception, e:
            raise
    
    def close_connect(self):
        try:
            self.cursor.close()
            self.connect.close()
        except Exception, e:
            raise

if __name__ == "__main__" :
    try:
        excute = MysqlExcuter()
        excute.do_excute("select * from test")
        for r in excute.result:
            print r
    except Exception, e:
        print e
    finally:
        excute.close_connect()