#!/usr/bin/env python

import sys, time

# Import the CORBA module
from omniORB import CORBA

# Import the stubs from the server module
import Server

# Initialise the ORB
orb = CORBA.ORB_init(sys.argv, CORBA.ORB_ID)

print "Connecting to server"
start = time.time()
# Get the IOR from the file, we will consider this and converting the IOR
#to an object connect time
ior = open('server.ior','r').read()

# Convert the IOR to an object reference
obj = orb.string_to_object(ior)

# Narrow reference to a Server object
so  = obj._narrow(Server.ServerIF)
end = time.time()
print "Time to connect to server, %f" % (end-start)
print

if so is None:
    print "Object reference is not an Echo"
    sys.exit(1)

print "Sending a long string to the server"
st = "This is a test string" * 1000
start = time.time()
so.recieveLargeString(st)
end = time.time()
print "Time to send a string of %d chars, %f" % (len(st),end-start)
print

print "Recieving a long stirng from the server"
start = time.time()
res = so.sendLargeString()
end = time.time()
print "Time to receive a string of %d chars, %f" % (len(res),end-start)
print

print "Sending lots of ints to the server"
NUM = 5000
start = time.time()
for ctr in range(NUM):
    so.recieveInt(1)
end = time.time()
total = end-start
print "Time to send %d ints, %f (%f per call)" % (NUM,total,total/float(NUM))
