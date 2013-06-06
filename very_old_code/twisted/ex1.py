# http://wiki.woodpecker.org.cn/moin/PyTwisted/tasteTwisted?highlight=(twisted)

from twisted.internet import protocol, reactor, defer, utils
from twisted.protocols import basic
from twisted.web import client
class FingerProtocol(basic.LineReceiver):
    def lineReceived(self, url):
        self.factory.getUrl(url
        ).addErrback(lambda _: "Internal error in server"
        ).addCallback(lambda m:
                      (self.transport.write(m+"\r\n"),
                       self.transport.loseConnection()))
class FingerFactory(protocol.ServerFactory):
    protocol = FingerProtocol
    def getUrl(self, url):
        return client.getPage(url)
reactor.listenTCP(2020, FingerFactory())
reactor.run()

