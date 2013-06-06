#!/usr/bin/env python

#A ZSI server to handle our requests

#Import the ZSI machinery
from ZSI import dispatch

LARGE_STRING = "this is a large string" * 1000

def receiveLargeString(data):
    #print "Recieved String of size %d" % len(data)
    pass

def sendLargeString():
    return LARGE_STRING


def receiveInt(data):
    #print "Recived Int %d" % data
    #print type(data)
    pass

print "Starting server..."
dispatch.AsServer(port=8888)

