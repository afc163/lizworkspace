
def application(environ, start_response):
    """Simplest possible application object"""
    status = '200 OK'
    response_headers = [('Content-type','text/plain')]
    start_response(status, response_headers)
    return ["Hello liz's  world!\n"]
    
class WSGIAppClass:
    """Produce the same output, but using a class
    """

    def __init__(self, environ, start_response):
        self.environ = environ
        self.start = start_response

    def __iter__(self):
        status = '200 OK'
        response_headers = [('Content-type','text/plain')]
        self.start(status, response_headers)
        yield "Hello world!\n"
