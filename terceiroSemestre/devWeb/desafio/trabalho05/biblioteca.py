# Classe Biblioteca, que deve conter:
# Atributos:
# - lista de livros
# - lista de usuários
# Métodos:
# - adicionar_livro()
# - cadastrar_usuario()
# - listar_livros_disponiveis()
# - listar_livros_emprestados()

class Biblioteca:
    def __init__(self):
        self.lista_livros = []
        self.lista_usuarios = []

    def __str__(self):
        resultado = '- Lista de livros:\n'
        for livro in self.lista_livros:
            resultado += f'{livro}\n'
        resultado += '- Lista de usuários:\n'
        for usuario in self.lista_usuarios:
            resultado += f'{usuario}\n'
        return resultado

    def adicionar_livro(self, livro):
        self.lista_livros.append(livro)

    def cadastrar_usuario(self, usuario):
        self.lista_usuarios.append(usuario)

    def listar_livros_disponiveis(self):
        disponiveis = [livro for livro in self.lista_livros if livro.disponivel]
        print("\n" + "=" * 45)
        print("          LIVROS DISPONÍVEIS")
        print("=" * 45)
        if not disponiveis:
            print("  Nenhum livro disponível no momento.")
        else:
            for i, livro in enumerate(disponiveis):
                print(f"  {i + 1}. {livro}")
                print("-" * 45)
        print("=" * 45)

    def listar_livros_emprestados(self):
        emprestados = [livro for livro in self.lista_livros if not livro.disponivel]
        print("\n" + "=" * 45)
        print("          LIVROS EMPRESTADOS")
        print("=" * 45)
        if not emprestados:
            print("  Nenhum livro emprestado no momento.")
        else:
            for i, livro in enumerate(emprestados):
                print(f"  {i + 1}. {livro}")
                print("-" * 45)
        print("=" * 45)

    def livros_emprestados_usuario(self, matricula):
        for usuario in self.lista_usuarios:
            if usuario.matricula == matricula:
                print("\n" + "=" * 45)
                print(f"    LIVROS DE {usuario.nome.upper()}")
                print("=" * 45)
                if not usuario.livros_emprestados:
                    print("  Este usuário não tem livros emprestados.")
                else:
                    for i, livro in enumerate(usuario.livros_emprestados):
                        print(f"  {i + 1}. {livro}")
                        print("-" * 45)
                print("=" * 45)
                return
        print("Usuário não encontrado!")