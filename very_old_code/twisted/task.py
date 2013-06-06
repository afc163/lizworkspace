#!/usr/bin/python
#  -*- coding: utf-8 -*-
import sys, os

libpath = "/svn/trunk/edn"
if libpath not in sys.path:
    sys.path.insert(0, libpath)
    
#import os
#os.environ['DJANGO_SETTINGS_MODULE'] = 'settings'
from django.conf import settings
settings.configure(DATABASE_ENGINE="mysql",
                  DATABASE_HOST="localhost",
                  DATABASE_NAME="dn",
                  DATABASE_USER="shengyan",
                  DATABASE_PASSWORD="asdzxc321456")

from db.models import *

for dn in DomainName.objects.all():
    print dn.name


