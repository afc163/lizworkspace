#
import os
import sys
import wsgiref.simple_server
import wsgiref.handlers
from types import TypeType, ClassType
import threading

import webob
import webob.exc
from routes import Mapper as _Mapper
from routes.middleware import RoutesMiddleware
from paste.exceptions.errormiddleware import ErrorMiddleware
import webhelpers as h
from mako.lookup import TemplateLookup
from google.appengine.api import users

c = threading.local()

def Mapper(*a, **kw):
   prefix = kw.pop('prefix', None)
   m = _Mapper(*a, **kw)
   if prefix:
       m.prefix = prefix
   return m

def make_app(mapper, controllers, template_dir=None, debug=False):
   app = Application(controllers, template_dir=template_dir)
   app = AuthenticateMiddleware(app)
   app = RoutesMiddleware(app, mapper)
   app = ErrorDocumentMiddleware(app)
   app = ErrorMiddleware(app, debug=debug)
   return app

def run(app, host='', port=8080):
   server = wsgiref.simple_server.make_server(host, port, app)
   server.serve_forever()

def run_cgi(app):
   wsgiref.handlers.CGIHandler().run(app)

class Application(object):
   def __init__(self, controllers, template_dir=None):
       self.controllers = controllers
       if template_dir:
           self.lookup = TemplateLookup(template_dir, input_encoding='utf8',
                                        output_encoding='utf8')

   def __call__(self, environ, start_response):
       controller = self.resolve(environ)
       environ['meilanweb.render'] = self.render
       s = controller(environ, start_response)
       return s

   def resolve(self, environ):
       """Get the controller app from the environ."""
       match = environ['wsgiorg.routing_args'][1]
       try:
           controller_name = match['controller']
       except KeyError:
           raise webob.exc.HTTPNotFound()
       controller_class_name = \
               class_name_from_controller_name(controller_name)
       controller = self.controllers[controller_class_name]
       if isinstance(controller, (TypeType, ClassType)):
           controller = controller()
       return controller

   def render(self, template, **kwargs):
       return self.lookup.get_template(template).render(**kwargs)


def class_name_from_controller_name(controller_name):
   # the code is stolen from Pylons
   """
   Excample::

       >>> class_name_from_controller_name('with-dashes')
       'WithDashes'
       >>> class_name_from_controller_name('with_underscores')
       'WithUnderscores'
       >>> class_name_from_controller_name('oneword')
       'Oneword'
   """
   words = controller_name.replace('-', '_').split('_')
   return ''.join(w.title() for w in words)


class Controller(object):
   def __call__(self, environ, start_response):
       c.__dict__.clear()
       self.environ = environ
       match = environ['wsgiorg.routing_args'][1]
       action_name = match['action'].replace('-', '_')
       action = getattr(self, action_name)
       kwargs = match.copy()
       del kwargs['controller']
       del kwargs['action']
       self.request = webob.Request(environ)
       self.response = webob.Response(request=self.request)
       retval = action(**kwargs)
       if retval:
           self.response.write(retval)
       return self.response(environ, start_response)

   def render(self, template, **kwargs):
       return self.environ['meilanweb.render'](template, c=c, h=h, **kwargs)


def default_template_dir(filepath):
   here = os.path.dirname(os.path.abspath(filepath))
   return os.path.join(here, 'templates')


class ErrorDocumentMiddleware(object):
   def __init__(self, app):
       self.app = app

   def __call__(self, environ, start_response):
       try:
           return self.app(environ, start_response)
       except webob.exc.WSGIHTTPException, e:
           e.environ = environ
           return e(environ, start_response)


class AuthenticateMiddleware(object):
   def __init__(self, app):
       self.app = app

   def __call__(self, environ, start_response):
       user = users.get_current_user()
       if user:
           environ['REMOTE_USER'] = user
       try:
           return self.app(environ, start_response)
       except webob.exc.HTTPUnauthorized, e:
           req = webob.Request(environ)
           url = users.create_login_url(req.url)
           raise webob.exc.HTTPTemporaryRedirect(location=url)

h.create_logout_url = users.create_logout_url

