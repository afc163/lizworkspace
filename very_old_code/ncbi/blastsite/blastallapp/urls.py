from django.conf.urls.defaults import *

urlpatterns = patterns('blastsite.blastallapp.views',
    (r'^$','show'),
    (r'query/','query'),
)
