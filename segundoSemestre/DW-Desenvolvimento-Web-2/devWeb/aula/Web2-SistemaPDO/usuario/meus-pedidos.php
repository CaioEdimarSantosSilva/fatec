<?php
require_once '../config.php';
verificarLogin();

$id_usuario = $_SESSION['usuario_id'];

$stmt = $pdo->prepare("
    SELECT p.*, f.titulo, f.genero, f.preco, f.capa 
    FROM pedido p 
    JOIN filme f ON p.id_filme = f.id 
    WHERE p.id_usuario = ? 
    ORDER BY p.data_aluguel DESC
");
$stmt->execute([$id_usuario]);
$pedidos = $stmt->fetchAll();


$stmtUser = $pdo->prepare("SELECT nome, url_foto FROM usuario WHERE id = ?");
$stmtUser->execute([$id_usuario]);
$usuario = $stmtUser->fetch(PDO::FETCH_ASSOC);


if (isset($_GET['finalizar'])) {
    $id_pedido = (int)$_GET['finalizar'];
    $stmt = $pdo->prepare("UPDATE pedido SET status = 'finalizado', data_devolucao = NOW() WHERE id = ? AND id_usuario = ?");
    $stmt->execute([$id_pedido, $id_usuario]);
    header('Location: meus-pedidos.php');
    exit();
}


if (isset($_POST['alterar_foto']) && isset($_FILES['nova_foto'])) {
    if ($_FILES['nova_foto']['error'] === 0) {
        $diretorio = '../assets/images/usuarios/';
        if (!is_dir($diretorio)) mkdir($diretorio, 0755, true);

        $extensao = pathinfo($_FILES['nova_foto']['name'], PATHINFO_EXTENSION);
        $novoNome = uniqid('user_') . '.' . $extensao;
        $caminhoDestino = $diretorio . $novoNome;

        if (move_uploaded_file($_FILES['nova_foto']['tmp_name'], $caminhoDestino)) {
            $stmtUpdate = $pdo->prepare("UPDATE usuario SET url_foto = ? WHERE id = ?");
            $stmtUpdate->execute(['assets/images/usuarios/' . $novoNome, $id_usuario]);
            $usuario['url_foto'] = 'assets/images/usuarios/' . $novoNome;
        } else {
            $erroFoto = "Erro ao enviar a foto. Tente novamente.";
        }
    } else {
        $erroFoto = "Selecione uma imagem válida!";
    }
}

?>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Meus Pedidos - EstanteFilmes</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(180deg, #DCEAFF 0%, #b8d4ff 100%);
            min-height: 100vh;
            padding: 2em;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
        }

        .header {
            background: white;
            padding: 2em;
            border-radius: 20px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
            margin-bottom: 2em;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            color: #FFB521;
            font-size: 1.8em;
            font-weight: bold;
            text-decoration: none;
        }

        .user-info {
            display: flex;
            gap: 1em;
            align-items: center;
        }

        .user-name {
            color: #01337D;
            font-weight: bold;
        }

        .btn-logout {
            background: #ff4444;
            color: white;
            padding: 0.7em 1.5em;
            border-radius: 10px;
            text-decoration: none;
            transition: all 0.3s;
        }

        .btn-logout:hover {
            background: #cc0000;
            transform: translateY(-2px);
        }

        .btn-home {
            background: #3685FF;
            color: white;
            padding: 0.7em 1.5em;
            border-radius: 10px;
            text-decoration: none;
            transition: all 0.3s;
        }

        .btn-home:hover {
            background: #01337D;
        }

        h1 {
            color: #01337D;
            margin-bottom: 1.5em;
            text-align: center;
            font-size: 2.5em;
        }

        .pedidos-grid {
            display: grid;
            gap: 1.5em;
        }

        .pedido-card {
            background: white;
            padding: 2em;
            border-radius: 20px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
            display: grid;
            grid-template-columns: auto 1fr auto;
            gap: 2em;
            align-items: center;
            transition: all 0.3s;
        }

        .pedido-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
        }

        .pedido-icone {
            font-size: 4em;
            background: linear-gradient(135deg, #3685FF, #01337D);
            width: 100px;
            height: 100px;
            border-radius: 15px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .pedido-info h3 {
            color: #01337D;
            margin-bottom: 0.5em;
            font-size: 1.5em;
        }

        .pedido-genero {
            display: inline-block;
            background: #DCEAFF;
            color: #01337D;
            padding: 0.3em 0.8em;
            border-radius: 10px;
            font-size: 0.9em;
            font-weight: bold;
            margin-bottom: 0.8em;
        }

        .pedido-detalhes {
            color: #666;
            line-height: 1.8;
        }

        .pedido-status {
            text-align: center;
        }

        .status-badge {
            display: inline-block;
            padding: 0.7em 1.5em;
            border-radius: 20px;
            font-weight: bold;
            margin-bottom: 1em;
        }

        .status-ativo {
            background: #00C851;
            color: white;
        }

        .status-finalizado {
            background: #666;
            color: white;
        }

        .btn-finalizar {
            background: linear-gradient(135deg, #FFB521, #ffa500);
            color: #01337D;
            padding: 0.8em 1.5em;
            border-radius: 10px;
            text-decoration: none;
            font-weight: bold;
            transition: all 0.3s;
            display: inline-block;
        }

        .btn-finalizar:hover {
            background: linear-gradient(135deg, #3685FF, #0066ff);
            color: white;
            transform: scale(1.05);
        }

        .btn-avaliar {
            background: #3685FF;
            color: white;
            padding: 0.8em 1.5em;
            border-radius: 10px;
            text-decoration: none;
            font-weight: bold;
            transition: all 0.3s;
            display: inline-block;
        }

        .btn-avaliar:hover {
            background: #01337D;
        }

        .vazio {
            background: white;
            padding: 3em;
            border-radius: 20px;
            text-align: center;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
        }

        .vazio p {
            color: #666;
            font-size: 1.2em;
            margin-bottom: 1.5em;
        }

        .btn-catalogo {
            background: linear-gradient(135deg, #FFB521, #ffa500);
            color: #01337D;
            padding: 1em 2em;
            border-radius: 15px;
            text-decoration: none;
            font-weight: bold;
            font-size: 1.1em;
            display: inline-block;
            transition: all 0.3s;
        }

        .btn-catalogo:hover {
            background: linear-gradient(135deg, #3685FF, #0066ff);
            color: white;
            transform: scale(1.05);
        }

        @media (max-width: 768px) {
            .header {
                flex-direction: column;
                gap: 1em;
            }

            .pedido-card {
                grid-template-columns: 1fr;
                text-align: center;
            }

            .pedido-icone {
                margin: 0 auto;
            }
        }

        .perfil-section {
            background: white;
            padding: 2em;
            border-radius: 20px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
            margin-bottom: 2em;
            text-align: center;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .perfil-section img {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
            margin-bottom: 1em;
        }

        .perfil-container {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 2em;
        }

        .perfil-section input[type="file"] {
            margin-top: 1em;
        }

        .btn-alterar {
            margin-top: 1em;
            background: #3685FF;
            color: white;
            padding: 0.7em 1.5em;
            border-radius: 10px;
            border: none;
            cursor: pointer;
            font-weight: bold;
            transition: all 0.3s;
        }

        .btn-alterar:hover {
            background: #01337D;
        }

        .erro {
            color: red;
            margin-top: 1em;
        }
    </style>
</head>

<body>
    <div class="container">
        <div class="header">
            <a href="../index.php" class="logo">🎬 EstanteFilmes</a>
            <div class="user-info">
                <span class="user-name">👤 <?php echo htmlspecialchars($_SESSION['usuario_nome']); ?></span>
                <a href="../index.php" class="btn-home">Home</a>
                <a href="../logout.php" class="btn-logout">Sair</a>
            </div>
        </div>

        <!-- Seção de Perfil -->
        <section class="perfil-section">
            <div class="perfil-container">
                <div class="perfil-foto">
                    <img src="../<?= htmlspecialchars($usuario['url_foto'] ?: 'assets/images/default-user.png') ?>" alt="Foto de <?= htmlspecialchars($usuario['nome']) ?>">
                </div>
                <div class="perfil-info">
                    <form method="POST" enctype="multipart/form-data">
                        <label for="nova_foto">Alterar Foto</label>
                        <input type="file" id="nova_foto" name="nova_foto" accept="image/*" required>
                        <br>
                        <button type="submit" name="alterar_foto" class="btn-alterar">Salvar Nova Foto</button>
                    </form>
                    <?php if (isset($erroFoto)) : ?>
                        <div class="erro"><?= $erroFoto ?></div>
                    <?php endif; ?>
                </div>
            </div>
        </section>


        <h1>Meus Aluguéis</h1>

        <?php if (count($pedidos) > 0): ?>
            <div class="pedidos-grid">
                <?php foreach ($pedidos as $pedido): ?>
                    <div class="pedido-card">
                        <div class="pedido-icone">🎬</div>

                        <div class="pedido-info">
                            <h3><?php echo htmlspecialchars($pedido['titulo']); ?></h3>
                            <span class="pedido-genero"><?php echo htmlspecialchars($pedido['genero']); ?></span>
                            <div class="pedido-detalhes">
                                <p><strong>Valor:</strong> R$ <?php echo number_format($pedido['preco'], 2, ',', '.'); ?></p>
                                <p><strong>Alugado em:</strong> <?php echo date('d/m/Y H:i', strtotime($pedido['data_aluguel'])); ?></p>
                                <?php if ($pedido['data_devolucao']): ?>
                                    <p><strong>Devolvido em:</strong> <?php echo date('d/m/Y', strtotime($pedido['data_devolucao'])); ?></p>
                                <?php endif; ?>
                            </div>
                        </div>

                        <div class="pedido-status">
                            <span class="status-badge status-<?php echo $pedido['status']; ?>">
                                <?php echo ucfirst($pedido['status']); ?>
                            </span>
                            <?php if ($pedido['status'] === 'ativo'): ?>
                                <br>
                                <a href="?finalizar=<?php echo $pedido['id']; ?>" class="btn-finalizar" onclick="return confirm('Finalizar este aluguel?')">
                                    ✓ Finalizar
                                </a>
                            <?php elseif ($pedido['status'] === 'finalizado'): ?>
                                <br>
                                <a href="avaliar.php?id=<?php echo $pedido['id_filme']; ?>" class="btn-avaliar">
                                    ★ Avaliar
                                </a>
                            <?php endif; ?>
                        </div>
                    </div>
                <?php endforeach; ?>
            </div>
        <?php else: ?>
            <div class="vazio">
                <p>🎬 Você ainda não alugou nenhum filme.</p>
                <a href="../index.php#catalogo" class="btn-catalogo">Ver Catálogo</a>
            </div>
        <?php endif; ?>
    </div>
</body>

</html>