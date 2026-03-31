-- Banco de dados
CREATE DATABASE IF NOT EXISTS sistema;
USE sistema;

-- Tabela de avaliações
CREATE TABLE IF NOT EXISTS avaliacao (
  id int(11) NOT NULL,
  nome varchar(100) NOT NULL,
  estrelas int(11) NOT NULL,
  comentario text NOT NULL
);

-- Tabela de usuários
CREATE TABLE IF NOT EXISTS usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    foto VARCHAR(255)
);

ALTER TABLE usuario
ADD COLUMN IF NOT EXISTS nivel ENUM('admin','usuario') NOT NULL DEFAULT 'usuario';

-- Tabela de serviços
CREATE TABLE IF NOT EXISTS servico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT NOT NULL,
    foto VARCHAR(255)
);

-- Tabela de contatos
CREATE TABLE IF NOT EXISTS contato (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    mensagem TEXT NOT NULL,
    data_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);