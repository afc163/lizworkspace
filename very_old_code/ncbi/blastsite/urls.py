#coding:utf-8
from django.conf.urls.defaults import *
from django.conf import settings

urlpatterns = patterns('',
    #主应用站点
    (r'^main/', include('blastsite.mainapp.urls')),
    #clustalx工具
    (r'^clustalx/', include('blastsite.clustalxapp.urls')),
    #blastall使用工具
    (r'^blastall/', include('blastsite.blastallapp.urls')),
    (r'^site_media/(?P<path>.*)$', 'django.views.static.serve', {'document_root': settings.STATIC_PATH}),
    # Uncomment this for admin:
    # (r'^admin/', include('django.contrib.admin.urls')),
)
