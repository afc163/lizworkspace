#coding=utf-8
from django import forms

class LoginForm(forms.Form):
    name_or_email = forms.CharField(max_length=100)
    password = forms.CharField(widget=forms.widgets.PasswordInput)
    remember_me = forms.BooleanField(required=False)

class SignUpForm(forms.Form):
    name_or_email = forms.CharField(max_length=100)
    password = forms.CharField(widget=forms.widgets.PasswordInput)
    password_confirm = forms.CharField(widget=forms.widgets.PasswordInput)

