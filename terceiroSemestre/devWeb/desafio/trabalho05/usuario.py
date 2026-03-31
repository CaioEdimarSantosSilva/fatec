# Classe Usuario, que deve conter:
# Atributos:
# - nome
# - matricula
# - lista de livros emprestados
# Métodos:
# - pegar_emprestado(livro)
# - devolver_livro(livro)
# - str()
 
class Usuario:
    def __init__(self, nome, matricula):
        self.nome = nome
        self.matricula = matricula
        self.livros_emprestados = []
 
    def __str__(self):
        return f'- Nome: {self.nome}\n- Matricula: {self.matricula}'
 
    def pegar_emprestado(self, livro):
        livro.emprestar()
        self.livros_emprestados.append(livro)
 
    def devolver_livro(self, livro):
        livro.devolver()
        self.livros_emprestados.remove(livro)
 
    def listar_emprestados(self):
        if not self.livros_emprestados:
            print("Nenhum livro emprestado.")
        else:
            for livro in self.livros_emprestados:
                print(livro)
 
        