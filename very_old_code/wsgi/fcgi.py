from flup.server.fcgi import WSGIServer
import hello_world

WSGIServer(hello_world.application).run()

