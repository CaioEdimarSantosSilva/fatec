from django import forms
from app.models import Categoria, Contato

class FormCategoria(forms.ModelForm):
    class Meta:
        model = Categoria
        fields = ['nome']
    

class FormContato(forms.ModelForm):
    class Meta:
        model = Contato
        fields = ['nome', 'email', 'assunto', 'mensagem']