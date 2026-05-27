from unittest.mock import Mock, patch

import requests
from django.core.cache import cache
from django.test import TestCase

from app.views import buscarProdutosGoogleBooks


class GoogleBooksApiTests(TestCase):
    def setUp(self):
        cache.clear()

    @patch('app.views.requests.get')
    def test_busca_google_books_formata_resultados(self, mock_get):
        response = Mock()
        response.raise_for_status.return_value = None
        response.json.return_value = {
            'items': [
                {
                    'id': 'abc123',
                    'volumeInfo': {
                        'title': 'Manga Teste',
                        'authors': ['Autor Teste'],
                        'imageLinks': {
                            'thumbnail': 'http://books.google.com/capa.jpg',
                        },
                        'infoLink': 'https://books.google.com/livro',
                    },
                    'saleInfo': {
                        'retailPrice': {'amount': 39.9},
                    },
                }
            ]
        }
        mock_get.return_value = response

        livros, erro = buscarProdutosGoogleBooks()

        self.assertIsNone(erro)
        self.assertEqual(livros[0]['titulo'], 'Manga Teste')
        self.assertEqual(livros[0]['autores'], 'Autor Teste')
        self.assertEqual(livros[0]['imagem'], 'https://books.google.com/capa.jpg')
        self.assertEqual(livros[0]['preco'], 39.9)
        mock_get.assert_called_once()
        self.assertEqual(mock_get.call_args.kwargs['params']['country'], 'BR')

    @patch('app.views.requests.get')
    def test_busca_google_books_trata_limite_da_api(self, mock_get):
        response = Mock()
        response.status_code = 429
        response.raise_for_status.side_effect = requests.HTTPError()
        mock_get.return_value = response

        livros, erro = buscarProdutosGoogleBooks()

        self.assertEqual(livros, [])
        self.assertIn('limite', erro)
