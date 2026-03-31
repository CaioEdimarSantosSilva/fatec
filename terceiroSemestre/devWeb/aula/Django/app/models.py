from django.db import models

# Create your models here.
class Categoria(models.Model):
    nome = models.CharField(max_length=100)

class Contato(models.Model):
    nome = models.CharField(max_length=255)
    email = models.EmailField(max_length=255)
    assunto = models.CharField(max_length=255)
    mensagem = models.CharField(max_length=255)
    