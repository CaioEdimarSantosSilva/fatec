<?php
session_start();
require 'config.php'; // conexão PDO

// Verifica se o usuário está logado
$usuarioLogado = null;
if (isset($_SESSION['usuario_id'])) {
    $stmt = $pdo->prepare("SELECT nome, url_foto FROM usuario WHERE id = ?");
    $stmt->execute([$_SESSION['usuario_id']]);
    $usuarioLogado = $stmt->fetch(PDO::FETCH_ASSOC);
}

// Busca os filmes disponíveis
$stmtFilmes = $pdo->query("SELECT id, titulo, descricao, genero, preco, capa FROM filme WHERE disponivel = 'sim'");
$filmes = $stmtFilmes->fetchAll(PDO::FETCH_ASSOC);

// Busca avaliações (com nome do usuário e filme)
$stmtAvaliacoes = $pdo->query("
    SELECT a.estrelas, a.comentario, u.nome AS usuario, f.titulo AS filme
    FROM avaliacao a
    JOIN usuario u ON a.id_usuario = u.id
    JOIN filme f ON a.id_filme = f.id
    ORDER BY a.data_avaliacao DESC
    LIMIT 6
");
$avaliacoes = $stmtAvaliacoes->fetchAll(PDO::FETCH_ASSOC);
?>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EstanteFilmes - Locadora Online</title>
    <link rel="stylesheet" href="assets/styles.css">
    <link rel="shortcut icon" href="assets/images/default.png" type="image/x-icon">
</head>

<body>

<header>
    <div class="conteinerLogo">
        <a href="index.php" class="logoTexto">EstanteFilmes</a>
    </div>

    <nav>
        <a href="#quem-somos">Quem Somos</a>
        <a href="#catalogo">Catálogo</a>
        <a href="#contato">Contato</a>

        <?php if ($usuarioLogado): ?>
            <a href="usuario/meus-pedidos.php">
                <img src="<?= htmlspecialchars($usuarioLogado['url_foto']) ?>" alt="Foto de <?= htmlspecialchars($usuarioLogado['nome']) ?>" class="foto-usuario">
            </a>
        <?php else: ?>
            <a href="login.php" class="btn-login">Login</a>
        <?php endif; ?>
    </nav>
</header>

<section class="banner">
    <h1>Alugue Filmes Online com Facilidade</h1>
    <p>Assista aos melhores filmes no conforto da sua casa. Catálogo completo, preços justos e qualidade garantida!</p>
    <a id="quem-somos" href="cadastro.php" class="btn-principal">Cadastre-se Agora</a>
</section>

<section class="quemSomos">
    <h2>Quem Somos</h2>
    <p>
        A EstanteFilmes é uma locadora online moderna que traz o melhor do cinema para você. Com um catálogo diversificado e atualizado constantemente, oferecemos filmes de todos os gêneros para alugar digitalmente. Qualidade HD, preços acessíveis e uma experiência incrível para os amantes da sétima arte.
    </p>
</section>

<section class="catalogoFilmes" id="catalogo">
    <h2>Catálogo de Filmes</h2>
    <div class="filmesGrid">
        <?php if (count($filmes) > 0): ?>
            <?php foreach ($filmes as $filme): ?>
                <div class="filmeCard">
                    <div class="filmeImg">
                        <?php if (!empty($filme['capa'])): ?>
                           <img src="<?= htmlspecialchars($filme['capa']) ?>" alt="<?= htmlspecialchars($filme['titulo']) ?>">
                        <?php else: ?>
                            🎬
                        <?php endif; ?>
                    </div>
                    <div class="filmeInfo">
                        <h3><?= htmlspecialchars($filme['titulo']) ?></h3>
                        <span class="genero"><?= htmlspecialchars($filme['genero']) ?></span>
                        <p><?= htmlspecialchars($filme['descricao']) ?></p>
                        <div class="preco">R$ <?= number_format($filme['preco'], 2, ',', '.') ?></div>
                        <a href="usuario/alugar.php?id=<?= $filme['id'] ?>" class="btn-alugar">Alugar Agora</a>
                    </div>
                </div>
            <?php endforeach; ?>
        <?php else: ?>
            <p>Nenhum filme disponível no momento.</p>
        <?php endif; ?>
    </div>
</section>

<section class="chamadaCadastro" id="cadastro">
    <h2>Pronto para começar a assistir?</h2>
    <p>Crie sua conta gratuitamente e tenha acesso a centenas de filmes incríveis. Assista quando e onde quiser!</p>
    <a href="cadastro.php" class="btn-principal">Criar Conta Grátis</a>
</section>

<section class="avaliacoes">
    <h2>O Que Dizem Nossos Clientes</h2>
    <div class="avaliacoesGrid">
        <?php if (count($avaliacoes) > 0): ?>
            <?php foreach ($avaliacoes as $av): ?>
                <div class="avaliacaoCard">
                    <div class="estrelas"><?= str_repeat('★', $av['estrelas']) . str_repeat('☆', 5 - $av['estrelas']) ?></div>
                    <p>"<?= htmlspecialchars($av['comentario']) ?>"</p>
                    <h4>— <?= htmlspecialchars($av['usuario']) ?> (<?= htmlspecialchars($av['filme']) ?>)</h4>
                </div>
            <?php endforeach; ?>
        <?php else: ?>
            <p>Seja o primeiro a avaliar nossos filmes!</p>
        <?php endif; ?>
    </div>
</section>

<footer id="contato">
    <div class="footerContent">
        <section class="contato">
            <h3>Contato</h3>
            <p>📍 Av. Paulista, 1000 - São Paulo/SP</p>
            <p>📞 (11) 3000-4000</p>
            <p>📧 contato@estantefilmes.com.br</p>
            <p>⏰ Atendimento: 24/7 Online</p>
        </section>

        <section class="faleConosco">
            <h3>Fale Conosco</h3>
            <form action="enviar_contato.php" method="POST">
                <input type="text" name="nome" placeholder="Seu nome" required>
                <input type="email" name="email" placeholder="Seu e-mail" required>
                <textarea name="mensagem" rows="4" placeholder="Sua mensagem" required></textarea>
                <button type="submit">Enviar Mensagem</button>
            </form>
        </section>
    </div>

    <div class="copyright">
        <p>&copy; 2025 EstanteFilmes - Locadora Online. Todos os direitos reservados.</p>
    </div>
</footer>

</body>
</html>
