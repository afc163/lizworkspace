#!/usr/bin/env python

import SocketServer, msgTypes

class OurRequestHandler(SocketServer.BaseRequestHandler):
    LARGE_STRING = "this is a large string" * 1000

    def handle(self):
        #Read in the first 1 byte to see the message type
        _type = self.request.recv(1)
        _type = int(_type)
        if _type == msgTypes.Commands.RECEIVE_STRING:
            #READ five bytes of hex for the size
            size = int(self.request.recv(5),16)
            data = self.request.recv(size)
            #Send the results
            self.request.send("%d" % msgTypes.Results.ACK)
        elif _type == msgTypes.Commands.SEND_STRING:
            #Send a large string
            self.request.send("%d" % msgTypes.Results.LARGE_STRING)
            self.request.send("%05x" % len(self.LARGE_STRING))
            self.request.send(self.LARGE_STRING)
        elif _type == msgTypes.Commands.RECEIVE_INT:
            #READ four bytes of hex for the value
            data = int(self.request.recv(4),16)
            #Send the results
            self.request.send("%d" % msgTypes.Results.ACK)
        else:
            raise "Unknown Message Type: %d" % _type

class OurServer(SocketServer.TCPServer):
    allow_reuse_address = 1

if __name__ == '__main__':
    address = ('localhost',8080)
    server = OurServer(address,OurRequestHandler)
    server.serve_forever()

