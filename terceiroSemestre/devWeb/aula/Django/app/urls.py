from django.urls import path
from . import views

urlpatterns = [
    path('', views.index, name="index"),
    path('categoria', views.listarCategoria, name="categoria"),
    path('del-categoria/<int:id_cat>', views.delCategoria, name="delcategoria"),
    path('add-categoria', views.addCategoria, name="addcategoria"),
    path('edit-categoria/<int:id_cat>', views.editCategoria, name="editcategoria"),
    path('add-contato', views.addContato, name="addcontato"),
    path('contato', views.listarContato, name="contato"),
    path('del-contato/<int:id_cat>', views.delContato, name="delcontato"),
]
