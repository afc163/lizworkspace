#!/usr/bin/env python

import sys
from omniORB import CORBA
import Server__POA

class ServerImp(Server__POA.ServerIF):
    LARGE_STRING = "this is a large string" * 1000
    
    def recieveLargeString(self,data):
        #print "Recieved %d bytes" % len(data)
        pass

    def sendLargeString(self):
        #print "Sending %d bytes" % len(self.LARGE_STRING)
        return self.LARGE_STRING
    
    def recieveInt(self,data):
        #print "Recieved Int"
        pass


# Initialise the ORB
orb = CORBA.ORB_init(sys.argv, CORBA.ORB_ID)

# Find the root POA
poa = orb.resolve_initial_references("RootPOA")

# Create an instance of the server
s = ServerImp()

# Create an object reference, and implicitly activate the object
so = s._this()

# Send the IOR to a file
open('server.ior','w').write(orb.object_to_string(so))

# Activate the POA
poaManager = poa._get_the_POAManager()
poaManager.activate()

# Everything is running now, but if this thread drops out of the end
# of the file, the process will exit. orb.run() just blocks until the
# ORB is shut down
print "Server is running and awaiting queries"
orb.run()
