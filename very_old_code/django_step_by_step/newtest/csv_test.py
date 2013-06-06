#coding=utf-8
from django.http import HttpResponse
from django.template import loader, Context

address = [
	('盛艳', 'lizzie'),
	('王姝', 'sonia')
	]

def output(request, filename):
	response = HttpResponse(mimetype='text/csv/')
	response['Content-Disposition'] = 'attachment; filename=%s.csv' % filename

	t = loader.get_template('csv.html')
	c = Context({
			'data':address,
			})
	response.write(t.render(c))
	return response
