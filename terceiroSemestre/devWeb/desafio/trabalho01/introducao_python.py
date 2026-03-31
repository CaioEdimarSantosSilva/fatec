# Um mochileiro está se preparando para uma viagem de avião no Brasil. Sua mochila de mão tem um limite de 23 kg.
# Ele poderá adicionar qualquer item que desejar, informando o nome do item e seu peso em kg, desde que a mochila não ultrapasse o limite de peso.

# O programa deve:
# Perguntar o nome do mochileiro.
# Permitir que o mochileiro digite o nome do item e o peso em kg.
# Antes de adicionar o item, verificar se ainda há espaço suficiente na mochila.
# Permitir que o mochileiro digite "fim" para encerrar a seleção a qualquer momento.
# No final, o programa deve mostrar:
# O nome do mochileiro
# O peso total dos itens adicionados
# O espaço restante na mochila
# A lista de itens adicionados

limite = 23
peso_total = 0
itens = []

nome = input("Digite o nome do mochileiro: ")

while True:
    item = input("\nDigite o nome do item ou 'fim' para encerrar: ")

    if item.lower() == "fim":
        break

    peso = float(input("Digite o peso do item (kg): "))

    if peso <= 0:
        print("Peso inválido. Digite um valor positivo.")
        continue

    if peso_total + peso <= limite:
        itens.append((item, peso))
        peso_total += peso
        print("Item adicionado com sucesso!")
    else:
        print("Peso excede o limite da mochila. Item não adicionado.")

espaco_restante = limite - peso_total

print("\nMochileiro:", nome)
print(f"Peso total: {peso_total:.2f} kg")
print(f"Espaço restante: {espaco_restante:.2f} kg")

if len(itens) == 0:
    print("A mochila está vazia.")
else:
    print("Itens adicionados:")
    for item, peso in itens:
        print(f"- {item}: {peso:.2f} kg")
