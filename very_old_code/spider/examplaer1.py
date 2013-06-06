#!/usr/bin/env python
#coding=utf-8
One-to-one relationships

from django.db import models

class Place(models.Model):
    name = models.CharField(max_length=50)
    address = models.CharField(max_length=80)

    def __unicode__(self):
        return u"%s the place" % self.name

class Restaurant(models.Model):
    place = models.OneToOneField(Place)
    serves_hot_dogs = models.BooleanField()
    serves_pizza = models.BooleanField()

    def __unicode__(self):
        return u"%s the restaurant" % self.place.name

class Waiter(models.Model):
    restaurant = models.ForeignKey(Restaurant)
    name = models.CharField(max_length=50)

    def __unicode__(self):
        return u"%s the waiter at %s" % (self.name, self.restaurant)

class ManualPrimaryKey(models.Model):
    primary_key = models.CharField(max_length=10, primary_key=True)
    name = models.CharField(max_length = 50)

class RelatedModel(models.Model):
    link = models.OneToOneField(ManualPrimaryKey)
    name = models.CharField(max_length = 50)

>>> from mysite.models import Place, Restaurant, Waiter, ManualPrimaryKey, RelatedModel

# Create a couple of Places.
>>> p1 = Place(name='Demon Dogs', address='944 W. Fullerton')
>>> p1.save()
>>> p2 = Place(name='Ace Hardware', address='1013 N. Ashland')
>>> p2.save()

# Create a Restaurant. Pass the ID of the "parent" object as this object's ID.
>>> r = Restaurant(place=p1, serves_hot_dogs=True, serves_pizza=False)
>>> r.save()

# A Restaurant can access its place.
>>> r.place
<Place: Demon Dogs the place>

# A Place can access its restaurant, if available.
>>> p1.restaurant
<Restaurant: Demon Dogs the restaurant>

# p2 doesn't have an associated restaurant.
>>> p2.restaurant
Traceback (most recent call last):
    ...
DoesNotExist: Restaurant matching query does not exist.

# Set the place using assignment notation. Because place is the primary key on Restaurant,
# the save will create a new restaurant
>>> r.place = p2
>>> r.save()
>>> p2.restaurant
<Restaurant: Ace Hardware the restaurant>
>>> r.place
<Place: Ace Hardware the place>

# Set the place back again, using assignment in the reverse direction
# Need to reget restaurant object first, because the reverse set
# can't update the existing restaurant instance
>>> p1.restaurant = r
>>> r.save()
>>> p1.restaurant
<Restaurant: Demon Dogs the restaurant>

>>> r = Restaurant.objects.get(pk=1)
>>> r.place
<Place: Demon Dogs the place>

# Restaurant.objects.all() just returns the Restaurants, not the Places.
# Note that there are two restaurants - Ace Hardware the Restaurant was created
# in the call to r.place = p2. This means there are multiple restaurants referencing
# a single place...
>>> Restaurant.objects.all()
[<Restaurant: Demon Dogs the restaurant>, <Restaurant: Ace Hardware the restaurant>]

# Place.objects.all() returns all Places, regardless of whether they have
# Restaurants.
>>> Place.objects.order_by('name')
[<Place: Ace Hardware the place>, <Place: Demon Dogs the place>]

>>> Restaurant.objects.get(place__id__exact=1)
<Restaurant: Demon Dogs the restaurant>
>>> Restaurant.objects.get(pk=1)
<Restaurant: Demon Dogs the restaurant>
>>> Restaurant.objects.get(place__exact=1)
<Restaurant: Demon Dogs the restaurant>
>>> Restaurant.objects.get(place__exact=p1)
<Restaurant: Demon Dogs the restaurant>
>>> Restaurant.objects.get(place=1)
<Restaurant: Demon Dogs the restaurant>
>>> Restaurant.objects.get(place=p1)
<Restaurant: Demon Dogs the restaurant>
>>> Restaurant.objects.get(place__pk=1)
<Restaurant: Demon Dogs the restaurant>
>>> Restaurant.objects.get(place__name__startswith="Demon")
<Restaurant: Demon Dogs the restaurant>

>>> Place.objects.get(id__exact=1)
<Place: Demon Dogs the place>
>>> Place.objects.get(pk=1)
<Place: Demon Dogs the place>
>>> Place.objects.get(restaurant__place__exact=1)
<Place: Demon Dogs the place>
>>> Place.objects.get(restaurant__place__exact=p1)
<Place: Demon Dogs the place>
>>> Place.objects.get(restaurant__pk=1)
<Place: Demon Dogs the place>
>>> Place.objects.get(restaurant=1)
<Place: Demon Dogs the place>
>>> Place.objects.get(restaurant=r)
<Place: Demon Dogs the place>
>>> Place.objects.get(restaurant__exact=1)
<Place: Demon Dogs the place>
>>> Place.objects.get(restaurant__exact=r)
<Place: Demon Dogs the place>

# Add a Waiter to the Restaurant.
>>> w = r.waiter_set.create(name='Joe')
>>> w.save()
>>> w
<Waiter: Joe the waiter at Demon Dogs the restaurant>

# Query the waiters
>>> Waiter.objects.filter(restaurant__place__pk=1)
[<Waiter: Joe the waiter at Demon Dogs the restaurant>]
>>> Waiter.objects.filter(restaurant__place__exact=1)
[<Waiter: Joe the waiter at Demon Dogs the restaurant>]
>>> Waiter.objects.filter(restaurant__place__exact=p1)
[<Waiter: Joe the waiter at Demon Dogs the restaurant>]
>>> Waiter.objects.filter(restaurant__pk=1)
[<Waiter: Joe the waiter at Demon Dogs the restaurant>]
>>> Waiter.objects.filter(id__exact=1)
[<Waiter: Joe the waiter at Demon Dogs the restaurant>]
>>> Waiter.objects.filter(pk=1)
[<Waiter: Joe the waiter at Demon Dogs the restaurant>]
>>> Waiter.objects.filter(restaurant=1)
[<Waiter: Joe the waiter at Demon Dogs the restaurant>]
>>> Waiter.objects.filter(restaurant=r)
[<Waiter: Joe the waiter at Demon Dogs the restaurant>]

# Delete the restaurant; the waiter should also be removed
>>> r = Restaurant.objects.get(pk=1)
>>> r.delete()

# One-to-one fields still work if you create your own primary key
>>> o1 = ManualPrimaryKey(primary_key="abc123", name="primary")
>>> o1.save()
>>> o2 = RelatedModel(link=o1, name="secondary")
>>> o2.save()

/////////////////////////////////ManyToOne relationship
from django.db import models

class Reporter(models.Model):
    first_name = models.CharField(max_length=30)
    last_name = models.CharField(max_length=30)
    email = models.EmailField()

    def __unicode__(self):
        return u"%s %s" % (self.first_name, self.last_name)

class Article(models.Model):
    headline = models.CharField(max_length=100)
    pub_date = models.DateField()
    reporter = models.ForeignKey(Reporter)

    def __unicode__(self):
        return self.headline

    class Meta:
        ordering = ('headline',)



>>> from mysite.models import Reporter, Article

# Create a few Reporters.
>>> r = Reporter(first_name='John', last_name='Smith', email='john@example.com')
>>> r.save()

>>> r2 = Reporter(first_name='Paul', last_name='Jones', email='paul@example.com')
>>> r2.save()

# Create an Article.
>>> from datetime import datetime
>>> a = Article(id=None, headline="This is a test", pub_date=datetime(2005, 7, 27), reporter=r)
>>> a.save()

>>> a.reporter.id
1

>>> a.reporter
<Reporter: John Smith>

# Article objects have access to their related Reporter objects.
>>> r = a.reporter
>>> r.first_name, r.last_name
(u'John', u'Smith')

# Create an Article via the Reporter object.
>>> new_article = r.article_set.create(headline="John's second story", pub_date=datetime(2005, 7, 29))
>>> new_article
<Article: John's second story>
>>> new_article.reporter.id
1

# Create a new article, and add it to the article set.
>>> new_article2 = Article(headline="Paul's story", pub_date=datetime(2006, 1, 17))
>>> r.article_set.add(new_article2)
>>> new_article2.reporter.id
1
>>> r.article_set.all()
[<Article: John's second story>, <Article: Paul's story>, <Article: This is a test>]

# Add the same article to a different article set - check that it moves.
>>> r2.article_set.add(new_article2)
>>> new_article2.reporter.id
2
>>> r.article_set.all()
[<Article: John's second story>, <Article: This is a test>]
>>> r2.article_set.all()
[<Article: Paul's story>]

# Assign the article to the reporter directly using the descriptor
>>> new_article2.reporter = r
>>> new_article2.save()
>>> new_article2.reporter
<Reporter: John Smith>
>>> new_article2.reporter.id
1
>>> r.article_set.all()
[<Article: John's second story>, <Article: Paul's story>, <Article: This is a test>]
>>> r2.article_set.all()
[]

# Set the article back again using set descriptor.
>>> r2.article_set = [new_article, new_article2]
>>> r.article_set.all()
[<Article: This is a test>]
>>> r2.article_set.all()
[<Article: John's second story>, <Article: Paul's story>]

# Funny case - assignment notation can only go so far; because the
# ForeignKey cannot be null, existing members of the set must remain
>>> r.article_set = [new_article]
>>> r.article_set.all()
[<Article: John's second story>, <Article: This is a test>]
>>> r2.article_set.all()
[<Article: Paul's story>]

# Reporter cannot be null - there should not be a clear or remove method
>>> hasattr(r2.article_set, 'remove')
False
>>> hasattr(r2.article_set, 'clear')
False

# Reporter objects have access to their related Article objects.
>>> r.article_set.all()
[<Article: John's second story>, <Article: This is a test>]

>>> r.article_set.filter(headline__startswith='This')
[<Article: This is a test>]

>>> r.article_set.count()
2

>>> r2.article_set.count()
1

# Get articles by id
>>> Article.objects.filter(id__exact=1)
[<Article: This is a test>]
>>> Article.objects.filter(pk=1)
[<Article: This is a test>]

# Query on an article property
>>> Article.objects.filter(headline__startswith='This')
[<Article: This is a test>]

# The API automatically follows relationships as far as you need.
# Use double underscores to separate relationships.
# This works as many levels deep as you want. There's no limit.
# Find all Articles for any Reporter whose first name is "John".
>>> Article.objects.filter(reporter__first_name__exact='John')
[<Article: John's second story>, <Article: This is a test>]

# Check that implied __exact also works
>>> Article.objects.filter(reporter__first_name='John')
[<Article: John's second story>, <Article: This is a test>]

# Query twice over the related field.
>>> Article.objects.filter(reporter__first_name__exact='John', reporter__last_name__exact='Smith')
[<Article: John's second story>, <Article: This is a test>]

# The underlying query only makes one join when a related table is referenced twice.
>>> query = Article.objects.filter(reporter__first_name__exact='John', reporter__last_name__exact='Smith')
>>> null, sql, null = query._get_sql_clause()
>>> sql.count('INNER JOIN')
1

# The automatically joined table has a predictable name.
>>> Article.objects.filter(reporter__first_name__exact='John').extra(where=["many_to_one_article__reporter.last_name='Smith'"])
[<Article: John's second story>, <Article: This is a test>]

# And should work fine with the unicode that comes out of
# newforms.Form.cleaned_data
>>> Article.objects.filter(reporter__first_name__exact='John').extra(where=["many_to_one_article__reporter.last_name='%s'" % u'Smith'])
[<Article: John's second story>, <Article: This is a test>]

# Find all Articles for the Reporter whose ID is 1.
# Use direct ID check, pk check, and object comparison
>>> Article.objects.filter(reporter__id__exact=1)
[<Article: John's second story>, <Article: This is a test>]
>>> Article.objects.filter(reporter__pk=1)
[<Article: John's second story>, <Article: This is a test>]
>>> Article.objects.filter(reporter=1)
[<Article: John's second story>, <Article: This is a test>]
>>> Article.objects.filter(reporter=r)
[<Article: John's second story>, <Article: This is a test>]

>>> Article.objects.filter(reporter__in=[1,2]).distinct()
[<Article: John's second story>, <Article: Paul's story>, <Article: This is a test>]
>>> Article.objects.filter(reporter__in=[r,r2]).distinct()
[<Article: John's second story>, <Article: Paul's story>, <Article: This is a test>]

# You need two underscores between "reporter" and "id" -- not one.
>>> Article.objects.filter(reporter_id__exact=1)
Traceback (most recent call last):
    ...
TypeError: Cannot resolve keyword 'reporter_id' into field. Choices are: id, headline, pub_date, reporter

# You need to specify a comparison clause
>>> Article.objects.filter(reporter_id=1)
Traceback (most recent call last):
    ...
TypeError: Cannot resolve keyword 'reporter_id' into field. Choices are: id, headline, pub_date, reporter

# You can also instantiate an Article by passing
# the Reporter's ID instead of a Reporter object.
>>> a3 = Article(id=None, headline="This is a test", pub_date=datetime(2005, 7, 27), reporter_id=r.id)
>>> a3.save()
>>> a3.reporter.id
1
>>> a3.reporter
<Reporter: John Smith>

# Similarly, the reporter ID can be a string.
>>> a4 = Article(id=None, headline="This is a test", pub_date=datetime(2005, 7, 27), reporter_id="1")
>>> a4.save()
>>> a4.reporter
<Reporter: John Smith>

# Reporters can be queried
>>> Reporter.objects.filter(id__exact=1)
[<Reporter: John Smith>]
>>> Reporter.objects.filter(pk=1)
[<Reporter: John Smith>]
>>> Reporter.objects.filter(first_name__startswith='John')
[<Reporter: John Smith>]

# Reporters can query in opposite direction of ForeignKey definition
>>> Reporter.objects.filter(article__id__exact=1)
[<Reporter: John Smith>]
>>> Reporter.objects.filter(article__pk=1)
[<Reporter: John Smith>]
>>> Reporter.objects.filter(article=1)
[<Reporter: John Smith>]
>>> Reporter.objects.filter(article=a)
[<Reporter: John Smith>]

>>> Reporter.objects.filter(article__in=[1,4]).distinct()
[<Reporter: John Smith>]
>>> Reporter.objects.filter(article__in=[1,a3]).distinct()
[<Reporter: John Smith>]
>>> Reporter.objects.filter(article__in=[a,a3]).distinct()
[<Reporter: John Smith>]

>>> Reporter.objects.filter(article__headline__startswith='This')
[<Reporter: John Smith>, <Reporter: John Smith>, <Reporter: John Smith>]
>>> Reporter.objects.filter(article__headline__startswith='This').distinct()
[<Reporter: John Smith>]

# Counting in the opposite direction works in conjunction with distinct()
>>> Reporter.objects.filter(article__headline__startswith='This').count()
3
>>> Reporter.objects.filter(article__headline__startswith='This').distinct().count()
1

# Queries can go round in circles.
>>> Reporter.objects.filter(article__reporter__first_name__startswith='John')
[<Reporter: John Smith>, <Reporter: John Smith>, <Reporter: John Smith>, <Reporter: John Smith>]
>>> Reporter.objects.filter(article__reporter__first_name__startswith='John').distinct()
[<Reporter: John Smith>]
>>> Reporter.objects.filter(article__reporter__exact=r).distinct()
[<Reporter: John Smith>]

# Check that implied __exact also works
>>> Reporter.objects.filter(article__reporter=r).distinct()
[<Reporter: John Smith>]

# If you delete a reporter, his articles will be deleted.
>>> Article.objects.all()
[<Article: John's second story>, <Article: Paul's story>, <Article: This is a test>, <Article: This is a test>, <Article: This is a test>]
>>> Reporter.objects.order_by('first_name')
[<Reporter: John Smith>, <Reporter: Paul Jones>]
>>> r2.delete()
>>> Article.objects.all()
[<Article: John's second story>, <Article: This is a test>, <Article: This is a test>, <Article: This is a test>]
>>> Reporter.objects.order_by('first_name')
[<Reporter: John Smith>]

# Deletes using a join in the query
>>> Reporter.objects.filter(article__headline__startswith='This').delete()
>>> Reporter.objects.all()
[]
>>> Article.objects.all()
[]

/////////////////////////////////ManyToMany relationship


from django.db import models

class Publication(models.Model):
    title = models.CharField(max_length=30)

    def __unicode__(self):
        return self.title

    class Meta:
        ordering = ('title',)

class Article(models.Model):
    headline = models.CharField(max_length=100)
    publications = models.ManyToManyField(Publication)

    def __unicode__(self):
        return self.headline

    class Meta:
        ordering = ('headline',)



>>> from mysite.models import Publication, Article

# Create a couple of Publications.
>>> p1 = Publication(id=None, title='The Python Journal')
>>> p1.save()
>>> p2 = Publication(id=None, title='Science News')
>>> p2.save()
>>> p3 = Publication(id=None, title='Science Weekly')
>>> p3.save()

# Create an Article.
>>> a1 = Article(id=None, headline='Django lets you build Web apps easily')
>>> a1.save()

# Associate the Article with a Publication.
>>> a1.publications.add(p1)

# Create another Article, and set it to appear in both Publications.
>>> a2 = Article(id=None, headline='NASA uses Python')
>>> a2.save()
>>> a2.publications.add(p1, p2)
>>> a2.publications.add(p3)

# Adding a second time is OK
>>> a2.publications.add(p3)

# Add a Publication directly via publications.add by using keyword arguments.
>>> new_publication = a2.publications.create(title='Highlights for Children')

# Article objects have access to their related Publication objects.
>>> a1.publications.all()
[<Publication: The Python Journal>]
>>> a2.publications.all()
[<Publication: Highlights for Children>, <Publication: Science News>, <Publication: Science Weekly>, <Publication: The Python Journal>]

# Publication objects have access to their related Article objects.
>>> p2.article_set.all()
[<Article: NASA uses Python>]
>>> p1.article_set.all()
[<Article: Django lets you build Web apps easily>, <Article: NASA uses Python>]
>>> Publication.objects.get(id=4).article_set.all()
[<Article: NASA uses Python>]

# We can perform kwarg queries across m2m relationships
>>> Article.objects.filter(publications__id__exact=1)
[<Article: Django lets you build Web apps easily>, <Article: NASA uses Python>]
>>> Article.objects.filter(publications__pk=1)
[<Article: Django lets you build Web apps easily>, <Article: NASA uses Python>]
>>> Article.objects.filter(publications=1)
[<Article: Django lets you build Web apps easily>, <Article: NASA uses Python>]
>>> Article.objects.filter(publications=p1)
[<Article: Django lets you build Web apps easily>, <Article: NASA uses Python>]

>>> Article.objects.filter(publications__title__startswith="Science")
[<Article: NASA uses Python>, <Article: NASA uses Python>]

>>> Article.objects.filter(publications__title__startswith="Science").distinct()
[<Article: NASA uses Python>]

# The count() function respects distinct() as well.
>>> Article.objects.filter(publications__title__startswith="Science").count()
2

>>> Article.objects.filter(publications__title__startswith="Science").distinct().count()
1

>>> Article.objects.filter(publications__in=[1,2]).distinct()
[<Article: Django lets you build Web apps easily>, <Article: NASA uses Python>]
>>> Article.objects.filter(publications__in=[1,p2]).distinct()
[<Article: Django lets you build Web apps easily>, <Article: NASA uses Python>]
>>> Article.objects.filter(publications__in=[p1,p2]).distinct()
[<Article: Django lets you build Web apps easily>, <Article: NASA uses Python>]

# Reverse m2m queries are supported (i.e., starting at the table that doesn't
# have a ManyToManyField).
>>> Publication.objects.filter(id__exact=1)
[<Publication: The Python Journal>]
>>> Publication.objects.filter(pk=1)
[<Publication: The Python Journal>]

>>> Publication.objects.filter(article__headline__startswith="NASA")
[<Publication: Highlights for Children>, <Publication: Science News>, <Publication: Science Weekly>, <Publication: The Python Journal>]

>>> Publication.objects.filter(article__id__exact=1)
[<Publication: The Python Journal>]
>>> Publication.objects.filter(article__pk=1)
[<Publication: The Python Journal>]
>>> Publication.objects.filter(article=1)
[<Publication: The Python Journal>]
>>> Publication.objects.filter(article=a1)
[<Publication: The Python Journal>]

>>> Publication.objects.filter(article__in=[1,2]).distinct()
[<Publication: Highlights for Children>, <Publication: Science News>, <Publication: Science Weekly>, <Publication: The Python Journal>]
>>> Publication.objects.filter(article__in=[1,a2]).distinct()
[<Publication: Highlights for Children>, <Publication: Science News>, <Publication: Science Weekly>, <Publication: The Python Journal>]
>>> Publication.objects.filter(article__in=[a1,a2]).distinct()
[<Publication: Highlights for Children>, <Publication: Science News>, <Publication: Science Weekly>, <Publication: The Python Journal>]

# If we delete a Publication, its Articles won't be able to access it.
>>> p1.delete()
>>> Publication.objects.all()
[<Publication: Highlights for Children>, <Publication: Science News>, <Publication: Science Weekly>]
>>> a1 = Article.objects.get(pk=1)
>>> a1.publications.all()
[]

# If we delete an Article, its Publications won't be able to access it.
>>> a2.delete()
>>> Article.objects.all()
[<Article: Django lets you build Web apps easily>]
>>> p2.article_set.all()
[]

# Adding via the 'other' end of an m2m
>>> a4 = Article(headline='NASA finds intelligent life on Earth')
>>> a4.save()
>>> p2.article_set.add(a4)
>>> p2.article_set.all()
[<Article: NASA finds intelligent life on Earth>]
>>> a4.publications.all()
[<Publication: Science News>]

# Adding via the other end using keywords
>>> new_article = p2.article_set.create(headline='Oxygen-free diet works wonders')
>>> p2.article_set.all()
[<Article: NASA finds intelligent life on Earth>, <Article: Oxygen-free diet works wonders>]
>>> a5 = p2.article_set.all()[1]
>>> a5.publications.all()
[<Publication: Science News>]

# Removing publication from an article:
>>> a4.publications.remove(p2)
>>> p2.article_set.all()
[<Article: Oxygen-free diet works wonders>]
>>> a4.publications.all()
[]

# And from the other end
>>> p2.article_set.remove(a5)
>>> p2.article_set.all()
[]
>>> a5.publications.all()
[]

# Relation sets can be assigned. Assignment clears any existing set members
>>> p2.article_set = [a4, a5]
>>> p2.article_set.all()
[<Article: NASA finds intelligent life on Earth>, <Article: Oxygen-free diet works wonders>]
>>> a4.publications.all()
[<Publication: Science News>]
>>> a4.publications = [p3]
>>> p2.article_set.all()
[<Article: Oxygen-free diet works wonders>]
>>> a4.publications.all()
[<Publication: Science Weekly>]

# Relation sets can be cleared:
>>> p2.article_set.clear()
>>> p2.article_set.all()
[]
>>> a4.publications.all()
[<Publication: Science Weekly>]

# And you can clear from the other end
>>> p2.article_set.add(a4, a5)
>>> p2.article_set.all()
[<Article: NASA finds intelligent life on Earth>, <Article: Oxygen-free diet works wonders>]
>>> a4.publications.all()
[<Publication: Science News>, <Publication: Science Weekly>]
>>> a4.publications.clear()
>>> a4.publications.all()
[]
>>> p2.article_set.all()
[<Article: Oxygen-free diet works wonders>]

# Relation sets can also be set using primary key values
>>> p2.article_set = [a4.id, a5.id]
>>> p2.article_set.all()
[<Article: NASA finds intelligent life on Earth>, <Article: Oxygen-free diet works wonders>]
>>> a4.publications.all()
[<Publication: Science News>]
>>> a4.publications = [p3.id]
>>> p2.article_set.all()
[<Article: Oxygen-free diet works wonders>]
>>> a4.publications.all()
[<Publication: Science Weekly>]

# Recreate the article and Publication we have deleted.
>>> p1 = Publication(id=None, title='The Python Journal')
>>> p1.save()
>>> a2 = Article(id=None, headline='NASA uses Python')
>>> a2.save()
>>> a2.publications.add(p1, p2, p3)

# Bulk delete some Publications - references to deleted publications should go
>>> Publication.objects.filter(title__startswith='Science').delete()
>>> Publication.objects.all()
[<Publication: Highlights for Children>, <Publication: The Python Journal>]
>>> Article.objects.all()
[<Article: Django lets you build Web apps easily>, <Article: NASA finds intelligent life on Earth>, <Article: NASA uses Python>, <Article: Oxygen-free diet works wonders>]
>>> a2.publications.all()
[<Publication: The Python Journal>]

# Bulk delete some articles - references to deleted objects should go
>>> q = Article.objects.filter(headline__startswith='Django')
>>> print q
[<Article: Django lets you build Web apps easily>]
>>> q.delete()

# After the delete, the QuerySet cache needs to be cleared, and the referenced objects should be gone
>>> print q
[]
>>> p1.article_set.all()
[<Article: NASA uses Python>]

# An alternate to calling clear() is to assign the empty set
>>> p1.article_set = []
>>> p1.article_set.all()
[]

>>> a2.publications = [p1, new_publication]
>>> a2.publications.all()
[<Publication: Highlights for Children>, <Publication: The Python Journal>]
>>> a2.publications = []
>>> a2.publications.all()
[]
