from django.conf.urls.defaults import *

urlpatterns = patterns('godinsite.godinapp.views',
    (r'^$', 'show'),
    (r'^start/$', 'start'),
    )
