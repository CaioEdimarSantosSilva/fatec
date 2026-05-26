from django.shortcuts import render, redirect, get_object_or_404
from django.contrib.auth import authenticate, login, logout
from django.contrib import messages
from django.contrib.auth.decorators import login_required
from django.contrib.admin.views.decorators import staff_member_required
from django.contrib.auth.models import Group
from django.contrib.auth.models import User
from django.core.cache import cache
from django.core.paginator import Paginator
from django.db import transaction
from django.db.models import Count, DecimalField, F, Q, Sum
from django.db.models.functions import TruncDate
from django.urls import reverse
from django.utils.http import url_has_allowed_host_and_scheme
from app.models import Categoria, Compra, Contato, Produto
from app.forms import FormCategoria, FormContato, ProdutoForm, FormUsuario, FormEditarUsuario
from firebase_config import db 
from google.cloud.firestore_v1.base_query import FieldFilter

import requests
import queue
import threading
import os

def executarComTimeout(funcao, timeout=5, padrao=None):
    resultado = queue.Queue(maxsize=1)

    def alvo():
        try:
            resultado.put(funcao())
        except Exception:
            resultado.put(padrao)

    thread = threading.Thread(target=alvo, daemon=True)
    thread.start()

    try:
        return resultado.get(timeout=timeout)
    except queue.Empty:
        return padrao

def listarAvaliacoesFirebase(compra_id=None, produto_id=None):
    def consulta():
        avaliacoes = []
        ref = db.collection('avaliacao')

        if compra_id is not None:
            ref = ref.where(filter=FieldFilter('compra_id', '==', compra_id))
        if produto_id is not None:
            ref = ref.where(filter=FieldFilter('produto_id', '==', produto_id))

        docs = ref.stream()

        for doc in docs:
            dados = doc.to_dict()
            dados['id'] = doc.id
            avaliacoes.append(dados)

        return avaliacoes

    return executarComTimeout(consulta, timeout=5, padrao=None)

def salvarAvaliacaoFirebase(dados):
    def salvar():
        db.collection('avaliacao').add(dados)
        return True

    return executarComTimeout(salvar, timeout=8, padrao=False)

def excluirAvaliacaoFirebase(id_avaliacao):
    def excluir():
        db.collection('avaliacao').document(id_avaliacao).delete()
        return True

    return executarComTimeout(excluir, timeout=8, padrao=False)

def buscarProdutosGoogleBooks():
    cache_key = 'google_books_sugestoes_loja'
    cached = cache.get(cache_key)
    if cached is not None:
        return cached

    livros_api = []
    erro_api = None
    params = {
        'q': 'livros hq mangá',
        'maxResults': 8,
        'printType': 'books',
        'langRestrict': 'pt',
    }
    api_key = os.environ.get('GOOGLE_BOOKS_API_KEY')

    if api_key:
        params['key'] = api_key

    try:
        response = requests.get(
            'https://www.googleapis.com/books/v1/volumes',
            params=params,
            timeout=5
        )
        response.raise_for_status()
    except requests.HTTPError:
        if response.status_code == 429:
            erro_api = 'A Google Books API atingiu o limite diário de consultas. Configure GOOGLE_BOOKS_API_KEY para usar uma chave própria.'
        else:
            erro_api = 'Não foi possível carregar os dados da Google Books API.'
        resultado = (livros_api, erro_api)
        cache.set(cache_key, resultado, 5 * 60)
        return resultado
    except requests.RequestException:
        erro_api = 'Não foi possível conectar à Google Books API.'
        resultado = (livros_api, erro_api)
        cache.set(cache_key, resultado, 5 * 60)
        return resultado

    for item in response.json().get('items', []):
        info = item.get('volumeInfo', {})
        sale = item.get('saleInfo', {})
        preco = sale.get('retailPrice', {}).get('amount')

        livros_api.append({
            'id': item.get('id'),
            'titulo': info.get('title', 'Título não informado'),
            'autores': ', '.join(info.get('authors', [])) or 'Autor não informado',
            'imagem': info.get('imageLinks', {}).get('thumbnail'),
            'link': info.get('infoLink'),
            'preco': preco,
        })

    resultado = (livros_api, erro_api)
    cache.set(cache_key, resultado, 60 * 60)
    return resultado

def index(request):
    avaliacoes = listarAvaliacoesFirebase() or []
    categorias_home = {}

    for nome_categoria in ('Livros', 'Mangás', 'HQs'):
        produtos = (
            Produto.objects
            .select_related('categoria')
            .filter(categoria__nome__iexact=nome_categoria)
            .annotate(vendas=Sum('compra__quantidade'))
            .order_by(F('vendas').desc(nulls_last=True), 'nome')[:4]
        )
        categorias_home[nome_categoria] = produtos

    return render(request, 'index.html', {
        'avaliacoes': avaliacoes[:3],
        'produtos_mais_vendidos': categorias_home,
    })

def quemSomos(request):
    usuarios = User.objects.all()[:3]
    return render(request, 'quem-somos.html', {'usuarios': usuarios})

def cadastrarUsuario(request):
    formulario = FormUsuario(request.POST or None)
    if request.method == 'POST':
        if formulario.is_valid():
            usuario = formulario.save()
            grupo_cliente, _ = Group.objects.get_or_create(name="Cliente")
            usuario.groups.add(grupo_cliente)
            messages.success(request, 'Conta criada com sucesso. Faça login para continuar.')
            return redirect('login')
    return render(request, 'cadastro.html', {'form': formulario})

def loginUsuario(request):
    if request.method == 'POST':
        username = request.POST.get('username')
        password = request.POST.get('password')
        usuario = authenticate(request, username=username, password=password)
        if usuario is not None:
            login(request, usuario)
            return redirect('index')
        else:
            return render(request, 'login.html', {'erro': 'Usuário ou senha inválidos.'})
    return render(request, 'login.html')

def logoutUsuario(request):
    logout(request)
    return redirect('login')

@login_required(login_url='login')
def editarUsuario(request):
    formulario = FormEditarUsuario(request.POST or None, instance=request.user)
    if request.method == 'POST':
        if formulario.is_valid():
            formulario.save()
            return redirect('index')
    return render(request, 'edit-usuario.html', {'form': formulario})

@login_required(login_url='login')
def perfil(request):
    compras = Compra.objects.filter(usuario=request.user).select_related('produto').order_by('-data_compra')
    return render(request, 'perfil.html', {'compras': compras})


#  Categoria 

@login_required
@staff_member_required
def listarCategoria(request):
    busca = request.GET.get('q', '').strip()
    categorias = Categoria.objects.annotate(total_produtos=Count('produto')).order_by('nome')

    if busca:
        categorias = categorias.filter(nome__icontains=busca)

    categorias_paginadas, querystring = paginar_queryset(request, categorias, 10)
    return render(request, 'categoria.html', {
        'categorias': categorias_paginadas,
        'busca': busca,
        'querystring': querystring,
    })

@login_required
@staff_member_required
def delCategoria(request, id_cat):
    _categoria = Categoria.objects.get(id=id_cat)
    _categoria.delete()
    return redirect('categoria')

@login_required
@staff_member_required
def addCategoria(request):
    formulario = FormCategoria(request.POST or None)
    if request.POST:
        if formulario.is_valid():
            formulario.save()
            return redirect('categoria')
    return render(request, 'add-categoria.html', {'form': formulario})

@login_required
@staff_member_required
def editCategoria(request, id_cat):
    _categoria = Categoria.objects.get(id=id_cat)
    formulario = FormCategoria(request.POST or None, instance=_categoria)
    if request.POST:
        if formulario.is_valid():
            formulario.save()
            return redirect('categoria')
    return render(request, 'edit-categoria.html', {'form': formulario})


#  Contato 

@login_required
@staff_member_required
def listarContato(request):
    busca = request.GET.get('q', '').strip()
    contatos = Contato.objects.all().order_by('-id')

    if busca:
        contatos = contatos.filter(
            Q(nome__icontains=busca) |
            Q(email__icontains=busca) |
            Q(assunto__icontains=busca) |
            Q(mensagem__icontains=busca)
        )

    contatos_paginados, querystring = paginar_queryset(request, contatos, 10)
    return render(request, 'contato.html', {
        'contatos': contatos_paginados,
        'busca': busca,
        'querystring': querystring,
    })

@login_required
@staff_member_required
def delContato(request, id_contato):
    _contato = Contato.objects.get(id=id_contato)
    _contato.delete()
    return redirect('contato')

def addContato(request):
    formulario = FormContato(request.POST or None)
    if request.POST:
        if formulario.is_valid():
            formulario.save()
            messages.success(request, 'Mensagem enviada com sucesso.')
            return redirect('index')
    return render(request, 'add-contato.html', {'form': formulario})


#  Produto 
def loja(request):
    categorias = Categoria.objects.filter(produto__isnull=False).distinct().order_by('nome')
    busca = request.GET.get('q', '').strip()
    categoria_id = request.GET.get('categoria', '').strip()
    ordem = request.GET.get('ordem', '').strip()

    _produtos = Produto.objects.select_related('categoria').annotate(vendas=Count('compra'))

    if busca:
        _produtos = _produtos.filter(
            Q(nome__icontains=busca) |
            Q(autor__icontains=busca) |
            Q(descricao__icontains=busca)
        )

    if categoria_id.isdigit():
        _produtos = _produtos.filter(categoria_id=categoria_id)

    ordenacoes = {
        'alfabetica': ('nome',),
        'ano_antigo': ('ano', 'nome'),
        'ano_novo': ('-ano', 'nome'),
        'mais_vendidos': ('-vendas', 'nome'),
    }
    _produtos = _produtos.order_by(*ordenacoes.get(ordem, ('id',)))

    query_params = request.GET.copy()
    query_params.pop('page', None)
    querystring = query_params.urlencode()

    paginator = Paginator(_produtos, 12)
    pagina = request.GET.get('page')
    produtos_paginados = paginator.get_page(pagina)
    produtos_api, erro_api = buscarProdutosGoogleBooks()

    return render(request, 'loja.html', {
        'produtos': produtos_paginados,
        'categorias': categorias,
        'busca': busca,
        'categoria_selecionada': categoria_id,
        'ordem_selecionada': ordem,
        'querystring': querystring,
        'produtos_api': produtos_api,
        'erro_api': erro_api
})

def detalheProduto(request, id_prod):
    produto = get_object_or_404(
        Produto.objects.select_related('categoria').annotate(vendas=Count('compra')),
        id=id_prod
    )
    avaliacoes = listarAvaliacoesFirebase(produto_id=produto.id) or []
    referer = request.META.get('HTTP_REFERER')
    voltar_url = referer if referer and url_has_allowed_host_and_scheme(
        referer,
        allowed_hosts={request.get_host()},
        require_https=request.is_secure()
    ) else reverse('loja')

    return render(request, 'detalhe-produto.html', {
        'avaliacoes': avaliacoes,
        'produto': produto,
        'voltar_url': voltar_url,
    })

@login_required(login_url='login')
def comprarProduto(request, id_prod):
    if request.method != 'POST':
        return redirect('loja')

    if request.user.is_staff or request.user.is_superuser:
        messages.error(request, 'Usuários administrativos não podem realizar compras. Entre com uma conta de cliente para comprar.')
        return redirect('perfil')

    with transaction.atomic():
        produto = get_object_or_404(Produto.objects.select_for_update(), id=id_prod)

        if produto.quantidade <= 0:
            messages.error(request, 'Produto sem estoque disponível.')
            return redirect('loja')

        produto.quantidade -= 1
        produto.save(update_fields=['quantidade'])

        Compra.objects.create(
            usuario=request.user,
            produto=produto,
            quantidade=1,
            valor_unitario=produto.preco,
            valor_total=produto.preco
        )

    messages.success(request, 'Compra realizada com sucesso.')
    return redirect('perfil')



@login_required
@staff_member_required
def listarProduto(request):
    busca = request.GET.get('q', '').strip()
    categoria_id = request.GET.get('categoria', '').strip()
    produtos = Produto.objects.select_related('categoria').annotate(vendas=Sum('compra__quantidade')).order_by('categoria__nome', 'nome')
    categorias = Categoria.objects.filter(produto__isnull=False).distinct().order_by('nome')

    if busca:
        produtos = produtos.filter(
            Q(nome__icontains=busca) |
            Q(autor__icontains=busca) |
            Q(descricao__icontains=busca)
        )

    if categoria_id.isdigit():
        produtos = produtos.filter(categoria_id=categoria_id)

    produtos_paginados, querystring = paginar_queryset(request, produtos, 12)
    return render(request, 'produto.html', {
        'produtos': produtos_paginados,
        'categorias': categorias,
        'busca': busca,
        'categoria_selecionada': categoria_id,
        'querystring': querystring,
    })

@login_required
@staff_member_required
def addProduto(request):
    if request.method == 'POST':
        form = ProdutoForm(request.POST, request.FILES)
        if form.is_valid():
            form.save()
            return redirect('produto')
    else:
        form = ProdutoForm()
    return render(request, 'add-produto.html', {'form': form})

@login_required
@staff_member_required
def editProduto(request, id_prod):
    _produto = get_object_or_404(Produto, id=id_prod)
    form = ProdutoForm(request.POST or None, request.FILES or None, instance=_produto)
    if request.method == 'POST':
        if form.is_valid():
            form.save()
            return redirect('produto')
    return render(request, 'edit-produto.html', {'form': form, 'produto': _produto})

@login_required
@staff_member_required
def delProduto(request, id_prod):
    _produto = get_object_or_404(Produto, id=id_prod)
    _produto.delete()
    return redirect('produto')


#  Dashboard 

def paginar_queryset(request, queryset, por_pagina=10):
    query_params = request.GET.copy()
    query_params.pop('page', None)
    paginator = Paginator(queryset, por_pagina)
    return paginator.get_page(request.GET.get('page')), query_params.urlencode()

@login_required
@staff_member_required
def dashboard(request):
    total_produtos = Produto.objects.count()
    total_categorias = Categoria.objects.count()
    total_contatos = Contato.objects.count()
    total_usuarios = User.objects.count()
    total_compras = Compra.objects.count()
    total_avaliacoes = len(listarAvaliacoesFirebase() or [])
    total_vendido = Compra.objects.aggregate(total=Sum('valor_total'))['total'] or 0
    valor_em_estoque = Produto.objects.aggregate(
        total=Sum(
            F('quantidade') * F('preco'),
            output_field=DecimalField(max_digits=12, decimal_places=2)
        )
    )['total'] or 0

    vendas_por_categoria = list(
        Compra.objects
        .values('produto__categoria__nome')
        .annotate(total=Sum('quantidade'), receita=Sum('valor_total'))
        .order_by('-total')
    )
    vendas_por_autor = list(
        Compra.objects
        .values('produto__autor')
        .annotate(total=Sum('quantidade'), receita=Sum('valor_total'))
        .order_by('-total')[:8]
    )
    produtos_mais_vendidos = list(
        Compra.objects
        .values('produto__nome')
        .annotate(total=Sum('quantidade'))
        .order_by('-total')[:8]
    )
    estoque_por_categoria = list(
        Produto.objects
        .values('categoria__nome')
        .annotate(total=Sum('quantidade'))
        .order_by('-total')
    )
    vendas_por_dia = list(
        Compra.objects
        .annotate(dia=TruncDate('data_compra'))
        .values('dia')
        .annotate(total=Sum('valor_total'))
        .order_by('dia')
    )[-7:]

    def dinheiro(valor):
        return float(valor or 0)

    graficos_dashboard = {
        'vendas_categoria': {
            'labels': [item['produto__categoria__nome'] or 'Sem categoria' for item in vendas_por_categoria],
            'dados': [item['total'] or 0 for item in vendas_por_categoria],
        },
        'vendas_autor': {
            'labels': [item['produto__autor'] or 'Autor não informado' for item in vendas_por_autor],
            'dados': [item['total'] or 0 for item in vendas_por_autor],
        },
        'financeiro': {
            'labels': ['Vendido', 'Em estoque'],
            'dados': [dinheiro(total_vendido), dinheiro(valor_em_estoque)],
        },
        'produtos': {
            'labels': [item['produto__nome'] for item in produtos_mais_vendidos],
            'dados': [item['total'] or 0 for item in produtos_mais_vendidos],
        },
        'estoque_categoria': {
            'labels': [item['categoria__nome'] or 'Sem categoria' for item in estoque_por_categoria],
            'dados': [item['total'] or 0 for item in estoque_por_categoria],
        },
        'vendas_dia': {
            'labels': [item['dia'] for item in vendas_por_dia],
            'dados': [dinheiro(item['total']) for item in vendas_por_dia],
        },
    }

    return render(request, 'dashboard.html', {
        'total_produtos': total_produtos,
        'total_categorias': total_categorias,
        'total_contatos': total_contatos,
        'total_usuarios': total_usuarios,
        'total_compras': total_compras,
        'total_avaliacoes': total_avaliacoes,
        'total_vendido': total_vendido,
        'valor_em_estoque': valor_em_estoque,
        'graficos_dashboard': graficos_dashboard,
    })

@login_required
@staff_member_required
def listarUsuarios(request):
    busca = request.GET.get('q', '').strip()
    perfil = request.GET.get('perfil', '').strip()
    usuarios = User.objects.all().order_by('-date_joined')

    if busca:
        usuarios = usuarios.filter(Q(username__icontains=busca) | Q(email__icontains=busca))

    if perfil == 'admin':
        usuarios = usuarios.filter(is_superuser=True)
    elif perfil == 'staff':
        usuarios = usuarios.filter(is_staff=True, is_superuser=False)
    elif perfil == 'cliente':
        usuarios = usuarios.filter(is_staff=False, is_superuser=False)

    usuarios_paginados, querystring = paginar_queryset(request, usuarios, 10)
    return render(request, 'usuarios.html', {
        'usuarios': usuarios_paginados,
        'busca': busca,
        'perfil_selecionado': perfil,
        'querystring': querystring,
    })

@login_required
@staff_member_required
def editUsuarioAdmin(request, id_user):
    _usuario = get_object_or_404(User, id=id_user)
    formulario = FormEditarUsuario(request.POST or None, instance=_usuario)
    if request.method == 'POST':
        if formulario.is_valid():
            formulario.save()
            return redirect('usuarios')
    return render(request, 'edit-usuario-admin.html', {'form': formulario, 'usuario': _usuario})

@login_required
@staff_member_required
def delUsuario(request, id_user):
    _usuario = get_object_or_404(User, id=id_user)
    _usuario.delete()
    return redirect('usuarios')

@login_required
@staff_member_required
def listarCompras(request):
    busca = request.GET.get('q', '').strip()
    compras = Compra.objects.select_related('usuario', 'produto').order_by('-data_compra')

    if busca:
        compras = compras.filter(
            Q(usuario__username__icontains=busca) |
            Q(produto__nome__icontains=busca)
        )

    compras_paginadas, querystring = paginar_queryset(request, compras, 10)
    return render(request, 'compras.html', {
        'compras': compras_paginadas,
        'busca': busca,
        'querystring': querystring,
    })

@login_required
@staff_member_required
def delCompra(request, id_compra):
    compra = get_object_or_404(Compra, id=id_compra)
    compra.delete()
    messages.success(request, 'Compra excluída com sucesso.')
    return redirect('compras')

@login_required
@staff_member_required
def listarAvaliacoesAdmin(request):
    avaliacoes = listarAvaliacoesFirebase()
    busca = request.GET.get('q', '').strip()

    if avaliacoes is None:
        messages.error(request, 'Não foi possível carregar as avaliações do Firebase.')
        avaliacoes = []

    if busca:
        termo = busca.lower()
        avaliacoes = [
            item for item in avaliacoes
            if termo in str(item.get('cliente', '')).lower()
            or termo in str(item.get('produto', '')).lower()
            or termo in str(item.get('comentario', '')).lower()
        ]

    avaliacoes_paginadas, querystring = paginar_queryset(request, avaliacoes, 10)
    return render(request, 'avaliacoes-admin.html', {
        'avaliacoes': avaliacoes_paginadas,
        'busca': busca,
        'querystring': querystring,
    })

@login_required
@staff_member_required
def delAvaliacaoAdmin(request, id_avaliacao):
    if excluirAvaliacaoFirebase(id_avaliacao):
        messages.success(request, 'Avaliação excluída com sucesso.')
    else:
        messages.error(request, 'Não foi possível excluir a avaliação no Firebase.')
    return redirect('avaliacoesadmin')


@login_required(login_url='login')
def avaliacao(request):
    compra_id = request.POST.get('compra_id') or request.GET.get('compra_id')
    compra = get_object_or_404(
        Compra.objects.select_related('produto'),
        id=compra_id,
        usuario=request.user
    )

    if request.method == 'POST':
        comentario = request.POST.get('comentario', '').strip()
        nota = request.POST.get('nota')

        if not comentario or not nota:
            messages.error(request, 'Preencha comentário e nota para salvar a avaliação.')
            return redirect(f'/avaliacao/?compra_id={compra.id}')

        try:
            nota = int(nota)
        except ValueError:
            messages.error(request, 'Informe uma nota válida.')
            return redirect(f'/avaliacao/?compra_id={compra.id}')

        if nota < 1 or nota > 5:
            messages.error(request, 'A nota deve estar entre 1 e 5.')
            return redirect(f'/avaliacao/?compra_id={compra.id}')

        salvo = salvarAvaliacaoFirebase({
            'usuario_id': request.user.id,
            'cliente': request.user.username,
            'compra_id': compra.id,
            'produto_id': compra.produto.id,
            'produto': compra.produto.nome,
            'comentario': comentario,
            'nota': nota
        })

        if salvo:
            messages.success(request, 'Avaliação salva com sucesso.')
        else:
            messages.error(request, 'Não foi possível salvar a avaliação no Firebase.')

        return redirect('perfil')

    avaliacoes = listarAvaliacoesFirebase(compra_id=compra.id) or []

    return render(request, 'avaliacao.html', {
        'avaliacoes': avaliacoes,
        'compra': compra
    })
