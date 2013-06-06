#!/usr/bin/env python

#An XML-RPC server

import SocketServer
from XmlRpc import xmlrpcserver


class OurRequestHandler(xmlrpcserver.RequestHandler):
    LARGE_STRING = "this is a large string" * 1000
    
    def call(self,method,params):
        if method == 'receiveLargeString':
            #print "Received Stirng of length %d" % len(params[0])
            return ""
        elif method == 'sendLargeString':
            return self.LARGE_STRING
        elif method == 'receiveInt':
            #print "Received Int %d" % params[0]
            return ""
        return params

class OurServer(SocketServer.TCPServer):
    allow_reuse_address = 1


if __name__ == '__main__':
    server = OurServer(('', 8000), OurRequestHandler)
    server.serve_forever()
