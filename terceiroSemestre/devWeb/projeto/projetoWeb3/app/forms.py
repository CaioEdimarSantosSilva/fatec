from django import forms
from django.contrib.auth.forms import UserCreationForm
from django.contrib.auth.models import User

from app.models import Categoria, Contato, Produto


class FormUsuario(UserCreationForm):
    class Meta:
        model = User
        fields = ['username', 'email', 'password1', 'password2']
        labels = {
            'username': 'Nome',
            'email': 'E-mail',
        }


class FormEditarUsuario(forms.ModelForm):
    class Meta:
        model = User
        fields = ['username', 'email']
        labels = {
            'username': 'Nome',
            'email': 'E-mail',
        }


class FormCategoria(forms.ModelForm):
    class Meta:
        model = Categoria
        fields = ['nome']


class FormContato(forms.ModelForm):
    class Meta:
        model = Contato
        fields = ['nome', 'email', 'assunto', 'mensagem']


class ProdutoForm(forms.ModelForm):
    class Meta:
        model = Produto
        fields = ['nome', 'autor', 'descricao', 'ano', 'imagem', 'quantidade', 'preco', 'categoria']
        labels = {
            'nome': 'Título',
            'autor': 'Autor',
            'descricao': 'Descrição',
            'ano': 'Ano de publicação',
            'imagem': 'Imagem',
            'quantidade': 'Estoque',
            'preco': 'Preço',
            'categoria': 'Categoria',
        }
