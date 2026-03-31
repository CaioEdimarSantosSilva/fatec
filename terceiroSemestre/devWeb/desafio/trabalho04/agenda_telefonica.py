# Desenvolver um programa em Python que simule uma agenda telefônica simples, utilizando exclusivamente um dicionário para armazenar os contatos. O programa deve permitir cadastrar, buscar, excluir e listar contatos.

# Cada contato deverá ser armazenado como um par chave-valor no dicionário, sendo:
# Chave: nome do contato
# Valor: número de telefone

# Funcionalidades obrigatórias:

# Cadastrar um contato
# Solicitar ao usuário o nome e o telefone.
# Caso o nome já exista, permitir a atualização do telefone.

# Buscar um contato
# Solicitar o nome a ser buscado.
# Exibir o telefone se o contato existir.
# Caso contrário, informar que o contato não foi encontrado.

# Excluir um contato
# Solicitar o nome do contato a ser excluído.
# Remover o contato do dicionário, se existir.
# Caso não exista, exibir mensagem apropriada.

# Listar todos os contatos
# Exibir o nome e o telefone de cada contato cadastrado.

# Sair
# Encerrar o programa.
import time

agenda_telefonica = {}

while True:
    print("\n" + "=" * 35)
    print("         AGENDA TELEFÔNICA")
    print("=" * 35)
    print(" [1] -> Cadastrar contato")
    print(" [2] -> Buscar contato")
    print(" [3] -> Excluir contato")
    print(" [4] -> Listar contatos")
    print(" [5] -> Sair")
    print("=" * 35)

    menu = input(" Escolha uma opção: ")
    
    match menu:
        case "1":
            nome = input("Nome do contato: ").strip()
            telefone = input("Telefone: ").strip()

            if nome in agenda_telefonica:
                print("Contato já existe! Telefone atualizado.")
            else:
                print("Contato cadastrado com sucesso!")

            agenda_telefonica[nome] = telefone
            time.sleep(1)

        case "2":
            nome = input("Nome do contato: ").strip()
            if nome in agenda_telefonica:
                print(f"Telefone de {nome}: {agenda_telefonica[nome]}")
            else:
                print("Contato não encontrado.")
            time.sleep(1)

        case "3":
            nome = input("Nome do contato: ").strip()
            if nome in agenda_telefonica:
                del agenda_telefonica[nome]
                print("Contato removido com sucesso!")
            else:
                print("Contato não encontrado.")
            time.sleep(1)

        case "4":
            if len(agenda_telefonica) == 0:
                print("Nenhum contato cadastrado.")
            else:
                print("\nLista de contatos:")
                print("-" * 30)
                for nome, telefone in agenda_telefonica.items():
                    telefone_partido = list(telefone)
                    telefone_partido.insert(0,'(')
                    telefone_partido.insert(3,')')
                    telefone_partido.insert(4,' ')
                    telefone_partido.insert(10,'-')
                    del telefone_partido[15:]
                    telefone_fomatado = "".join(telefone_partido)

                    print(f"{nome:} -> {telefone_fomatado}")            
            time.sleep(1)

        case "5":
            print("Saindo da agenda...")
            time.sleep(1)
            break

        case _:
            print("Opção inválida!")
