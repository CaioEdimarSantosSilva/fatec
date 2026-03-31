# Classe Livro, que deve conter:
# Atributos:
# - título
# - autor
# - ano
# - disponível (booleano)
# Métodos:
# - emprestar()
# - devolver()
# - str()
# A lógica deve impedir que um livro já emprestado seja emprestado novamente.

# Implementação de Herança
# - Implemente uma subclasse de Livro, por exemplo: LivroDigital
# - Deve herdar de Livro e adicionar:
# - atributo tamanho_arquivo (em MB)
# - Sobrescreva o método __str__().

class Livro:
    def __init__(self, titulo, autor, ano, disponivel=True):
        self.titulo = titulo
        self.autor = autor
        self.ano = ano
        self.disponivel = disponivel

    def __str__(self):
        return (f'Titulo: {self.titulo}, '
                f'Autor: {self.autor}, '
                f'Ano: {self.ano}, '
                f'Disponivel: {"Sim" if self.disponivel else "Não"}')

    def emprestar(self):
        if self.disponivel:
            print(f"O livro {self.titulo} esta disponivel. Lembre de devolver!")
            self.disponivel = False
        else:
            print(f"O livro {self.titulo} esta indisponivel. Talvez amanhã!")

    def devolver(self):
        if self.disponivel == False:
            print(f"O livro {self.titulo} foi devolvido. Muito Obrigado!")
            self.disponivel = True
        else:
            print(f"O livro {self.titulo} já estava disponível!")


class LivroDigital(Livro):
    def __init__(self, titulo, autor, ano, tamanho_arquivo, disponivel=True):
        super().__init__(titulo, autor, ano, disponivel)
        self.tamanho_arquivo = tamanho_arquivo

    def __str__(self):
        return super().__str__() + f', Tamanho do Arquivo: {self.tamanho_arquivo} MB'