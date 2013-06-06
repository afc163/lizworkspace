#!/usr/bin/python
import CORBA
class mail:
	msg = 0
	def submit(self, msg):
		print "Message Recieved from:", msg.fr
		print "Message for:", msg.to
		for line in msg.body:
			print line
			self.msgs = self.msgs + 1;
		print "Message Served: ", self.msgs

CORBA.load_idl("msg.idl")
CORBA.load_idl("/usr/share/idl/name-service.idl")

orb = CORBA.ORB_init((), CORBA.ORB_ID)
poa = orb.resolve_initial_references("RootPOA")

servant = POA.MESSAGING.mail(mail())
poa.activate_object(servant)
ref = poa.servant_to_reference(servant)
open("./msg-server.ior","w").write(orb.object_to_string(ref))
print "Wrote IOR to file", orb.object_to_string(ref)
poa.the_POAManager.activate()
orb.run()
