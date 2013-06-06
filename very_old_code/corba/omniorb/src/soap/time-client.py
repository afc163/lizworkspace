import time, sys

#Import the ZSI client
from ZSI.client import Binding
from ZSI import TC

tracefile = None  #Set to sys.sysout to see debugging

print "Connecting to server"
start = time.time()
b = Binding(url='', ns='', host='localhost', port=8888,tracefile=tracefile)
end = time.time()
print "Time to connect to server, %f" % (end-start)
print


print "Sending a long string to the server"
st = "This is a test string" * 1000
start = time.time()
b.receiveLargeString(st)
end = time.time()
print "Time to send a string of %d chars, %f" % (len(st),end-start)
print

print "Recieving a long stirng from the server"
start = time.time()
res = b.sendLargeString()
end = time.time()
print "Time to receive a string of %d chars, %f" % (len(res),end-start)
print

print "Sending lots of ints to the server"
NUM = 5000
start = time.time()
for ctr in range(NUM):
    b.receiveInt(1)
end = time.time()
total = end-start
print "Time to send %d ints, %f (%f per call)" % (NUM,total,total/float(NUM))






