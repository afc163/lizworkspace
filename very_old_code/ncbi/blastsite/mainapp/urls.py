from django.conf.urls.defaults import *

urlpatterns = patterns('blastsite.mainapp.views',
    (r'^$','list_all'),
    (r'^add/$','add'),
    (r'^query/$','query'),
    (r'^files/(?P<filename>.*)/$', 'download'),
)
