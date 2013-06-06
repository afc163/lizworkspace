import time, sys

#Import the ZSI client
from ZSI.client import Binding
from ZSI import TC

tracefile = None  #Set to sys.sysout to see debugging

print "Connecting to server"
b = Binding(url='', ns='', host='localhost', port=8888,tracefile=tracefile)


print "Sending a long string to the server"
st = "T" * 1000
b.receiveLargeString(st)

print "Check File size and restart tcpdump."
raw_input("Press Any Key to go on")

print "Sending lots of ints to the server"
NUM = 100
for ctr in range(NUM):
    b.receiveInt(1)
print "Done"






