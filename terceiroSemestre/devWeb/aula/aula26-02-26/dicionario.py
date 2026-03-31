aluno = {
    'id': '001',
    'nome': 'Caio'
}

aluno['curso'] = 'DSM'
del aluno['curso']
# del aluno

aluno.update({'nome': 'Caio Martins'})
aluno.update({'curso': 'DSM'})

print(aluno)
print(aluno['nome'])
print(aluno.values())
print(aluno.keys())
print(aluno.items())
for chave, valor in aluno.items():
    print(f"{chave} - {valor}")

alunos = {
    'aluno1': {
        'id': '001',
        'nome': 'Caio'
    },
    'aluno2': {
        'id': '002',
        'nome': 'Carlos'
    },
    'aluno3': {
        'id': '003',
        'nome': 'Geovanny'
    },
    'aluno4': {
        'id': '004',
        'nome': 'João'
    }
}
print(alunos['aluno1']['nome'])