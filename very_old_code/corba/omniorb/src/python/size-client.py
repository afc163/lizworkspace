import socket,time

import msgTypes

print "Connecting to server"
s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
s.connect(('localhost',8080))

print "Sending a long string to the server (1000 chars)"
#Send the receive large string message
# byte 1: message type
# byte 2-6: string size in hex
# byte 7-n: string

st = "T" * 1000
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


print "Check File size and restart tcpdump."
raw_input("Press Any Key to go on")


print "Sending lots of ints to the server"
NUM = 100

#The receiveInt message looks like:
#byte 1: message type
#byte 2-4: 4byte int (in hex)

#Again, we need to connect for every message
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
print "Done"
