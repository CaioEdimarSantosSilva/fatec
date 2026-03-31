lista = [6, 5, 0, 2]

print(f"Lista: {lista}")
print(f"Tamanho da lista: {len(lista)}")
print(f"Primeiro valor: {lista[0]}")
print(f"Ultimo valor: {lista[-1]}")  # DA PRA PEGAR AS ULTIMAS POSIÇOES USANDO O NEGATIVO

lista[-2] = 3
print(lista)

lista.insert(-2, 4)
print(lista)

lista.append(1)
print(lista)

lista2 = ['a', 'b', 'c']
lista.extend(lista2)
print(lista)

lista.remove('c')
print(lista)

lista.pop(-1)
lista.pop(-1)
print(lista)

lista.sort()
print(lista)

del lista[-1]
print(lista)

# del lista
# lista.clear()
# print(lista)

for item in lista:
    print(item)

listas = [['a','b'],[10,20]]
print(listas[0][1])


