# Solicite ao usuário dois números inteiros positivos (início e fim de um intervalo).
# Utilize uma função chamada eh_primo(numero) que retorne True se o número for primo e False caso contrário.
# Utilize um laço while para percorrer o intervalo informado.
# Conte quantos números primos existem no intervalo.
# Mostre:
# A quantidade de primos encontrados
# O maior primo do intervalo
# O menor primo do intervalo

# O cálculo do número primo deve ser feito manualmente (sem bibliotecas).

def eh_primo(numero):
    if numero <= 1:
        return False
    for indice in range(2, int(numero**0.5) + 1):
        if numero % indice == 0:
            return False
    return True

intervalo = []
inicio = int(input("\nDigite um número inteiro positivo para ser o início da lista: "))
fim = int(input("Digite um número inteiro positivo para ser o fim da lista: "))

for numero in range(inicio, fim + 1):
    intervalo.append(numero)

indice = 0
contador = 0
maior = None
menor = None

while indice < len(intervalo):
    if eh_primo(intervalo[indice]):
        if contador == 0:
            maior = intervalo[indice]
            menor = intervalo[indice]
        if maior < intervalo[indice]:
            maior = intervalo[indice]
        elif menor > intervalo[indice]:
            menor = intervalo[indice]
        contador += 1
    indice += 1

if contador == 0:
    print("Nenhum número primo encontrado.")
else:
    print(f"\nA quantidade de primos encontrados foi {contador}")
    print(f"O maior primo do intervalo é {maior}")
    print(f"O menor primo do intervalo é {menor}\n")