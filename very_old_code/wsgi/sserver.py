from wsgiref import simple_server
import hello_world
import middleware

allowed_users = ['guido','monty']

httpd = simple_server.WSGIServer(('',8000),simple_server.WSGIRequestHandler)
#httpd.set_app(hello_world.application)
httpd.set_app(middleware.AuthenticationMiddleware(hello_world.application,allowed_users))
httpd.serve_forever()

"""import hello_world

if __name__ == '__main__':
    from cgi_wsgi import run_with_cgi
    run_with_cgi(middleware.AuthenticationMiddleware(hello_world.application,allowed_users))"""
