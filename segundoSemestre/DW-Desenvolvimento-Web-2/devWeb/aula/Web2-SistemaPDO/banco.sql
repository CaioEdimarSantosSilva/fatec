CREATE DATABASE IF NOT EXISTS estantefilmes;
USE estantefilmes;

CREATE TABLE IF NOT EXISTS filme (
  id INT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(100) NOT NULL,
  descricao TEXT NOT NULL,
  genero VARCHAR(50),
  preco DECIMAL(5,2) NOT NULL DEFAULT 9.90,
  capa VARCHAR(255),
  disponivel ENUM('sim','nao') DEFAULT 'sim',
  data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS usuario (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  senha VARCHAR(255) NOT NULL,
  url_foto VARCHAR(255) DEFAULT 'assets/images/default.png',
  nivel ENUM('admin','usuario') NOT NULL DEFAULT 'usuario',
  data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS pedido (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_usuario INT NOT NULL,
  id_filme INT NOT NULL,
  data_aluguel TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  data_devolucao DATE NULL,
  status ENUM('ativo','finalizado','cancelado') DEFAULT 'ativo',
  FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE,
  FOREIGN KEY (id_filme) REFERENCES filme(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS avaliacao (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_usuario INT,
  id_filme INT,
  estrelas INT CHECK(estrelas BETWEEN 1 AND 5),
  comentario TEXT,
  data_avaliacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE,
  FOREIGN KEY (id_filme) REFERENCES filme(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS contato (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  mensagem TEXT NOT NULL,
  data_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  lido ENUM('sim','nao') DEFAULT 'nao'
);

INSERT INTO filme (titulo, descricao, genero, preco, capa, disponivel) VALUES
('A Origem', 'Um thriller alucinante sobre invasão de sonhos e realidades paralelas.', 'Ficção Científica', 9.90, 'assets/images/filmes/origem.jpg', 'sim'),
('Parasita', 'Uma família pobre se infiltra na vida de uma família rica com consequências inesperadas.', 'Drama', 12.90, 'assets/images/filmes/parasita.jpg', 'sim'),
('Interestelar', 'Uma jornada épica através do espaço em busca de um novo lar para a humanidade.', 'Ficção Científica', 11.90, 'assets/images/filmes/interestelar.jpg', 'sim'),
('Coringa', 'A origem perturbadora do icônico vilão do Batman.', 'Drama', 10.90, 'assets/images/filmes/coringa.jpg', 'sim'),
('Matrix', 'Um hacker descobre a verdadeira natureza da realidade.', 'Ficção Científica', 8.90, 'assets/images/filmes/matrix.jpg', 'sim'),
('Pantera Negra', 'O rei de Wakanda luta para defender sua nação.', 'Ação', 11.90, 'assets/images/filmes/pantera.jpg', 'sim');

INSERT INTO contato (nome, email, mensagem, lido) VALUES
('João Silva', 'joao@email.com', 'Adorei o site, parabéns!', 'nao'),
('Maria Souza', 'maria@email.com', 'Não consigo acessar minha conta.', 'nao'),
('Carlos Lima', 'carlos@email.com', 'Sugiro adicionar mais filmes antigos.', 'sim');

INSERT INTO avaliacao (id_usuario, id_filme, estrelas, comentario) VALUES
(1, 1, 5, 'Um clássico absoluto!'),
(1, 2, 4, 'Excelente trama e efeitos.'),
(2, 3, 5, 'Incrível, uma das melhores atuações.'),

INSERT INTO pedido (id_usuario, id_filme, status) VALUES
(1, 1, 'finalizado'),
(2, 2, 'finalizado'),
(2, 3, 'finalizado');