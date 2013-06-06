class AuthenticationMiddleware:
    def __init__(self, app, allowed_usernames):
        self.app = app
        self.allowed_usernames = allowed_usernames

    def __call__(self, environ, start_response):
        if environ.get('REMOTE_USER','anonymous') in self.allowed_usernames:
            return self.app(environ, start_response)
        start_response(
            '403 Forbidden', [('Content-type', 'text/html')])
        return ['You are forbidden to view this resource']
