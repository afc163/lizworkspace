#!/usr/bin/env python

import sys, time, random

# Import the CORBA module
from omniORB import CORBA

# Import the stubs from the server module
import Server

# Initialise the ORB
orb = CORBA.ORB_init(sys.argv, CORBA.ORB_ID)

print "Connecting to server"
# Get the IOR from the file, we will consider this and converting the IOR
#to an object connect time
ior = open('server.ior','r').read()

# Convert the IOR to an object reference
obj = orb.string_to_object(ior)

# Narrow reference to a Server object
so  = obj._narrow(Server.ServerIF)

if so is None:
    print "Object reference is not an Echo"
    sys.exit(1)


#Create a random string
st = "T"*1000
print "Sending a string  of length %d to the server" % len(st)
#so.recieveLargeString(st)
print "Check File size and restart tcpdump."
raw_input("Press Any Key to go on")


print "Sending lots of ints to the server"
NUM = 100
for ctr in range(NUM):
    so.recieveInt(1)
print "Done"

