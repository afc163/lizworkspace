# coding=utf-8
from django.conf.urls.defaults import *
from django.conf import settings

urlpatterns = patterns('',
    # Example:
    # (r'^newtest/', include('newtest.foo.urls')),
	(r'^$', 'newtest.helloworld.index'),
	(r'^add/$', 'newtest.add.index'),
	(r'^list/$', 'newtest.list.index'),
	(r'^csv/(?P<filename>\w+)/$', 'newtest.csv_test.output'),

	(r'^login/$', 'newtest.login.login'),
	(r'^logout/$', 'newtest.login.logout'),

	(r'^wiki/$', 'newtest.wiki.views.index'),
	(r'^wiki/(?P<pagename>\w+)/$', 'newtest.wiki.views.index'),
	(r'^wiki/(?P<pagename>\w+)/edit/$', 'newtest.wiki.views.edit'),
	(r'^wiki/(?P<pagename>\w+)/save/$', 'newtest.wiki.views.save'),

	(r'^address/', include('newtest.address.urls')),
	# site_media是用来存放CSS和图片的地方。django.views.static.serve需要一个document_root的参数
	(r'^site_media/(?P<path>.*)$', 'django.views.static.serve', {'document_root': settings.STATIC_PATH}),

	(r'^calendar/$', 'django.views.generic.simple.direct_to_template', {'template':'my_calendar/calendar.html'}),

	(r'^ajax/$', 'django.views.generic.simple.direct_to_template', {'template':'ajax/ajax.html'}),
	(r'^ajax/input/$', 'newtest.ajax.views.input'),
	(r'^ajax/json/$', 'newtest.ajax.views.json'),
    # Uncomment this for admin:
   (r'^admin/', include('django.contrib.admin.urls')),
)
