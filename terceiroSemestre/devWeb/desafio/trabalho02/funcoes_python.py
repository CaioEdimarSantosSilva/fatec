# Desenvolver um programa que gere uma lista com cinco números aleatórios. 
# Em seguida, o sistema deve solicitar ao usuário que informe cinco números e 
# verificar quantos deles coincidem com os números gerados, exibindo ao final a pontuação obtida.
 
# O programa deve conter duas funções:
# Uma função responsável por gerar 5 números aleatórios.
# Uma função responsável por verificar a quantidade de acertos do usuário.
# GERAR NÚMEROS ALEÁTORIOS ENTRE 1 E 10

import random

def numeros_aleatorios():
    numeros_pc = []
    i = 1
    while i <= 5:
        numero = random.randint(1, 10)

        if numero not in numeros_pc:
            numeros_pc.append(numero)
            i += 1
    return numeros_pc

def contar_acertos(pc, usuario):
    pontos = 0
    for n in usuario:
        if n in pc:
            pontos += 1
    return pontos

numeros_usuario = []
x = 1

while x <= 5:
    num = int(input("Digite um número entre 1 e 10: "))
    if num < 1 or num > 10:
        print("Número inválido. Digite entre 1 e 10.")
        continue
    if num not in numeros_usuario:
        numeros_usuario.append(num)
        x += 1
    else:
        print("Número repetido, tente outro.")

numeros_pc = numeros_aleatorios()
print(f"\nNúmeros sorteados: {numeros_pc}")
pontuacao = contar_acertos(numeros_pc, numeros_usuario)
print(f"Você fez {pontuacao} ponto(s)\n")