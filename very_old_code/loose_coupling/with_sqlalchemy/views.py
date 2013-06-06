# Create your views here.
from django.shortcuts import render_to_response
import sqlalchemy, sqlalchemy.orm
from models import Base, Language
from django.contrib.auth.decorators import login_required
from django.http import HttpResponseRedirect

engine = sqlalchemy.create_engine('sqlite:///loose.sqlite')
Session = sqlalchemy.orm.sessionmaker(bind=engine)
session = Session()
Base.metadata.create_all(engine)

def is_empty():
    return len(session.query(Language).all()) <= 0

def populate():
    new_langs = [Language('Python', 'py'), 
                 Language('Ruby', 'rb'), 
                 Language('Common Lisp', 'lisp'), 
                 Language('Objective-C', 'm'), 
                 ]
    session.add_all(new_langs)
    session.commit()

@login_required
def index(request):
    if is_empty():
        populate()
    langs = session.query(Language).all()
    return render_to_response('with_sqlalchemy/index.html',{'langs':langs, "user":request.user,})

def mlogin(request):
    from django.contrib.auth import authenticate, login
    from django.contrib.auth.models import User
    from forms import LoginForm
    
    if request.method == "POST": 
        form = LoginForm(request.POST)
        if form.is_valid():
            name_or_email = form.cleaned_data["name_or_email"]
            password = form.cleaned_data["password"]
            remember_me = form.cleaned_data["remember_me"]
            user = authenticate(username=name_or_email, password=password)
            if user is not None:
                if user.is_active:
                    login(request, user)
                    
                    return HttpResponseRedirect("/")
                else:
                    return render_to_response("form.html", {
                                              "aform": form,
                                              "err_message": "Disabled Account!",
                                              })
            else:
                return render_to_response("form.html", {
                                          "aform": form,
                                          "err_message": "Username or Password were incorrect!",
                                          })
        else:
            return render_to_response("form.html", {
                                  "aform": form,
                                  "err_message": "Information is not complete!",
                                  })
    else:
        return render_to_response("form.html", {
                                  "aform": LoginForm()
                                  })
