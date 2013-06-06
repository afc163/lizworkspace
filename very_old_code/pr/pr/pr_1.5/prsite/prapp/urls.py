from django.conf.urls.defaults import *

urlpatterns = patterns('prsite.prapp.views',
    (r'^$', 'show'),
    (r'^script/$', 'show'),
    (r'^program/$', 'show', {'type':1}),
    (r'^conf/$', 'show', {'type':2}),
    (r'^machine/$', 'show', {'type':3}),
    (r'^path/$', 'show', {'type':4}),
    (r'^search/$', 'start'),
    )
