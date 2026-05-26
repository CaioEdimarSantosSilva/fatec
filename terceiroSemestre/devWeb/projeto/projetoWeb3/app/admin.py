from django.contrib import admin
from app.models import Categoria, Compra, Produto

admin.site.register(Categoria)

@admin.register(Produto)
class ProdutoAdmin(admin.ModelAdmin):
    list_display = ("nome", "autor", "ano", "quantidade", "preco", "categoria", "imagem")
    search_fields = ("nome", "autor")
    list_filter = ("categoria", "ano", "preco")
    fields = ("nome", "autor", "descricao", "ano", "imagem", "quantidade", "preco", "categoria")

@admin.register(Compra)
class CompraAdmin(admin.ModelAdmin):
    list_display = ("usuario", "produto", "quantidade", "valor_total", "data_compra")
    search_fields = ("usuario__username", "produto__nome")
    list_filter = ("data_compra",)

# Register your models here.
