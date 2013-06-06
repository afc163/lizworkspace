from django.conf.urls.defaults import *
from django.conf import settings

urlpatterns = patterns('',
    # Example:
    # (r'^prsite/', include('prsite.foo.urls')),
    (r'godin/', include('godinsite.godinapp.urls')),
    # Uncomment this for admin:
    (r'^admin/', include('django.contrib.admin.urls')),
    (r'^site_media/(?P<path>.*)$', 'django.views.static.serve', {'document_root': settings.STATIC_PATH}),
)
