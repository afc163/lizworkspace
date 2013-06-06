import socket,time

import msgTypes

print "Connecting to server"
start = time.time()
s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
s.connect(('localhost',8080))
end = time.time()
print "Time to connect to server, %f" % (end-start)
print


print "Sending a long string to the server"
#Send the receive large string message
# byte 1: message type
# byte 2-6: string size in hex
# byte 7-n: string

st = "This is a test string" * 1000
start = time.time()
s.send("%d" % msgTypes.Commands.RECEIVE_STRING)
s.send("%05x" % len(st))
s.send(st)
#Receive the results
res = s.recv(1)
try:
    if int(res) != msgTypes.Results.ACK:
        raise "Error"
except:
    raise SystemExit("Invliad Result Code")
end = time.time()
print "Time to send a string of %d chars, %f" % (len(st),end-start)
print

#Note, because of the way we wrote the server, we need to reconnect!!!
s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
s.connect(('localhost',8080))

print "Recieving a long stirng from the server"
#Send the recieve large string message
# byte 1: message type


start = time.time()
s.send("%d" % msgTypes.Commands.SEND_STRING)

#The response will be the same as the message we sent previously
resCode = s.recv(1)
try:
    if int(resCode) != msgTypes.Results.LARGE_STRING:
        raise "Error"
    size = int(s.recv(5),16)
    res = s.recv(size)
except:
    raise SystemExit("Invliad Results")
end = time.time()
print "Time to receive a string of %d chars, %f" % (len(res),end-start)
print

print "Sending lots of ints to the server"
NUM = 5000

#The receiveInt message looks like:
#byte 1: message type
#byte 2-4: 4byte int (in hex)

#Again, we need to connect for every message
start = time.time()
for ctr in range(NUM):
    s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
    s.connect(('localhost',8080))
    s.send("%d" % msgTypes.Commands.RECEIVE_INT)
    s.send("%04x" % 1)
    res = s.recv(1)
    try:
        if int(res) != msgTypes.Results.ACK:
            raise "Error"
    except:
        raise SystemExit("Invliad Result Code")
end = time.time()
total = end-start
print "Time to send %d ints, %f (%f per call)" % (NUM,total,total/float(NUM))
