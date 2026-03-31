-- Geração de Modelo físico
-- Sql ANSI 2003 - brModelo.



CREATE TABLE CURSO (
sigla_curso VARCHAR(10) PRIMARY KEY,
nome_curso VARCHAR(50),
carga_horaria_curso INTEGER
)

CREATE TABLE UNIDADE_CURRICULAR (
sigla_unidade_curricular VARCHAR(10) PRIMARY KEY,
nome_unidade_curricular VARCHAR(50),
carga_horaria INTEGER
)

CREATE TABLE PROFESSOR (
codigo_professor INTEGER PRIMARY KEY,
nome_professor VARCHAR(50)
)

CREATE TABLE ALUNO (
registro_academico VARCHAR(10) PRIMARY KEY,
nome_aluno VARCHAR(50),
data_nascimento DATETIME,
logradouro VARCHAR(10),
complemento VARCHAR(10),
numero VARCHAR(10),
bairro VARCHAR(10),
sigla_curso VARCHAR(10),
FOREIGN KEY(sigla_curso) REFERENCES CURSO (sigla_curso)
)

CREATE TABLE MENSALIDADE (
data_emissao DATETIME,
data_vencimento DATETIME,
data_pagamento DATETIME,
valor NUMERIC(10),
registro_academico VARCHAR(10),
PRIMARY KEY(data_emissao,registro_academico),
FOREIGN KEY(registro_academico) REFERENCES ALUNO (registro_academico)
)

CREATE TABLE telefone (
telefone_PK INTEGER PRIMARY KEY,
telefone VARCHAR(15),
registro_academico_FK VARCHAR(),
FOREIGN KEY(registro_academico_FK) REFERENCES ALUNO (registro_academico)
)

CREATE TABLE PROFESSOR_UNIDADE_CURRICULAR (
codigo_professor INTEGER,
sigla_unidade_curricular VARCHAR(10),
FOREIGN KEY(codigo_professor) REFERENCES PROFESSOR (codigo_professor),
FOREIGN KEY(sigla_unidade_curricular) REFERENCES UNIDADE_CURRICULAR (sigla_unidade_curricular)
)

CREATE TABLE CURSO_UNIDADE_CURRICULAR (
sigla_unidade_curricular VARCHAR(10),
sigla_curso VARCHAR(10),
FOREIGN KEY(sigla_unidade_curricular) REFERENCES UNIDADE_CURRICULAR (sigla_unidade_curricular),
FOREIGN KEY(sigla_curso) REFERENCES CURSO (sigla_curso)
)

