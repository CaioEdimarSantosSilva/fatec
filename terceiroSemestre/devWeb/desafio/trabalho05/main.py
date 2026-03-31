# O sistema deve permitir:
# - Cadastro de livros
# - Cadastro de usuários
# - Realização de empréstimos
# - Devolução de livros
# - Listagem de livros disponíveis
# - Listagem de livros emprestados

# Interface
# O sistema pode funcionar via terminal, permitindo:
# 1 Cadastrar livro
# 2 Cadastrar usuário
# 3 Realizar empréstimo
# 4 Devolver livro
# 5 Listar livros disponíveis
# 6 Sair

import time
from livro import Livro, LivroDigital
from usuario import Usuario
from biblioteca import Biblioteca

def pausar(segundos=1.2):
    time.sleep(segundos)

def main():
    biblioteca_Alexandria = Biblioteca()

    while True:
        print("\n" + "=" * 35)
        print("            BIBLIOTECA")
        print("=" * 35)
        print(" [1] -> Cadastrar livro")
        print(" [2] -> Cadastrar usuário")
        print(" [3] -> Realizar empréstimo")
        print(" [4] -> Devolver livro")
        print(" [5] -> Listar livros disponíveis")
        print(" [6] -> Listar livros emprestados")
        print(" [7] -> Sair")
        print("=" * 35)

        menu = input(" Escolha uma opção: ")

        match menu:
            case "1":
                print("\n--- CADASTRAR LIVRO ---")
                print("[1] -> Livro físico")
                print("[2] -> Livro digital")
                tipo = input(" Tipo: ")

                titulo = input("Título: ")
                autor = input("Autor: ")
                ano = input("Ano: ")

                if tipo == "2":
                    tamanho = input("Tamanho do arquivo (MB): ")
                    livro = LivroDigital(titulo, autor, ano, tamanho)
                    print(f"\nLivro digital {titulo} adicionado com sucesso!")
                else:
                    livro = Livro(titulo, autor, ano)
                    print(f"\nLivro {titulo} adicionado com sucesso!")

                biblioteca_Alexandria.adicionar_livro(livro)
                pausar()

            case "2":
                print("\n--- CADASTRAR USUÁRIO ---")
                nome = input("Nome: ")
                matricula = input("Matrícula: ")
                usuario = Usuario(nome, matricula)
                biblioteca_Alexandria.cadastrar_usuario(usuario)
                print(f"\n Usuário {nome} cadastrado com sucesso!")
                pausar()

            case "3":
                print("\n--- REALIZAR EMPRÉSTIMO ---")
                usuario_nome = input("Nome do usuário: ")
                livro_titulo = input("Título do livro: ")

                usuario_encontrado = None
                for usuario in biblioteca_Alexandria.lista_usuarios:
                    if usuario.nome == usuario_nome:
                        usuario_encontrado = usuario
                        break

                if not usuario_encontrado:
                    print("\n Usuário não encontrado!")
                    pausar()
                    continue

                livro_encontrado = None
                for livro in biblioteca_Alexandria.lista_livros:
                    if livro.titulo == livro_titulo and livro.disponivel:
                        livro_encontrado = livro
                        break

                if not livro_encontrado:
                    print("\n Livro não encontrado ou indisponível!")
                else:
                    usuario_encontrado.pegar_emprestado(livro_encontrado)
                    print(f"\n '{livro_encontrado.titulo}' emprestado para {usuario_encontrado.nome}!")
                pausar()

            case "4":
                print("\n--- DEVOLVER LIVRO ---")
                usuario_nome = input("Nome do usuário: ")
                livro_titulo = input("Título do livro: ")

                usuario_encontrado = None
                for usuario in biblioteca_Alexandria.lista_usuarios:
                    if usuario.nome == usuario_nome:
                        usuario_encontrado = usuario
                        break

                if not usuario_encontrado:
                    print("\n Usuário não encontrado!")
                    pausar()
                    continue

                livro_encontrado = None
                for livro in biblioteca_Alexandria.lista_livros:
                    if livro.titulo == livro_titulo and livro.disponivel == False:
                        livro_encontrado = livro
                        break

                if not livro_encontrado:
                    print("\n Este livro não está emprestado ou não pertence à biblioteca!")
                else:
                    usuario_encontrado.devolver_livro(livro_encontrado)
                    print(f"\n '{livro_encontrado.titulo}' devolvido com sucesso!")
                pausar()

            case "5":
                biblioteca_Alexandria.listar_livros_disponiveis()
                pausar(1.5)

            case "6":
                print("\n" + "=" * 35)
                print(" [1] -> Todos os livros emprestados")
                print(" [2] -> Livros de um usuário específico")
                print("=" * 35)
                opcao_emprestados = input(" Escolha uma opção: ")
                match opcao_emprestados:
                    case "1":
                        biblioteca_Alexandria.listar_livros_emprestados()
                    case "2":
                        matricula = input("Matrícula do usuário: ")
                        biblioteca_Alexandria.livros_emprestados_usuario(matricula)
                    case _:
                        print("Opção inválida!")
                pausar(1.5)

            case "7":
                print("\nSaindo da biblioteca... Até logo! ")
                pausar()
                break

            case _:
                print("\n  Opção inválida! Tente novamente.")
                pausar(0.8)

if __name__ == "__main__":
    main()