from django.shortcuts import render, redirect
from app.models import Categoria, Contato
from app.forms import FormCategoria, FormContato

# Create your views here.
def index(request):
    return render(request, 'index.html')

def listarCategoria(request):
    _categorias = Categoria.objects.all().values()
    return render(request, 'categoria.html', {'categorias': _categorias})

def delCategoria(request, id_cat):
    _categoria = Categoria.objects.get(id=id_cat)
    _categoria.delete()
    return redirect('categoria')

def addCategoria(request):
    formulario = FormCategoria(request.POST or None)
    if request.POST:
        if formulario.is_valid():
            formulario.save()
            return redirect('categoria')

    return render(request, 'add-categoria.html', {'form':formulario})

def editCategoria(request,id_cat):
    _categoria = Categoria.objects.get(id=id_cat)
    formulario = FormCategoria(request.POST or None, instance=_categoria)
    if request.POST:
        if formulario.is_valid():
            formulario.save()
            return redirect('categoria')
    return render(request, 'edit-categoria.html', {'form':formulario})

def addContato(request):
    formulario = FormContato(request.POST or None)
    if request.POST:
        if formulario.is_valid():
            formulario.save()
            return redirect('contato')
    return render(request, 'add-contato.html', {'form':formulario})

def listarContato(request):
    _contatos = Contato.objects.all().values()
    return render(request, 'contato.html', {'contatos': _contatos})    


def delContato(request, id_cat):
    _contato = Contato.objects.get(id=id_cat)
    _contato.delete()
    return redirect('contato')