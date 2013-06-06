import time, sys

#Import the XML-RPC client
from XmlRpc import xmlrpclib

print "Connecting to server"
server = xmlrpclib.Server("http://localhost:8000")
print

st = "T"*1000
print "Sending a string  of length %d to the server" % len(st)
server.receiveLargeString(st)
print "Check File size and restart tcpdump."
raw_input("Press Any Key to go on")


print "Sending lots of ints to the server"
NUM = 100
for ctr in range(NUM):
    server.receiveInt(1)
print "Done"

