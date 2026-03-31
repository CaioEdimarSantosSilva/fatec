<?php
require_once '../config.php';
verificarLogin();

$id_usuario = $_SESSION['usuario_id'];
$erro = '';
$sucesso = '';
$filme = null;

$id_filme = isset($_GET['id']) ? (int)$_GET['id'] : 0;

if ($id_filme > 0) {
    $stmt = $pdo->prepare("
        SELECT COUNT(*) as total 
        FROM pedido 
        WHERE id_usuario = ? AND id_filme = ? AND status = 'finalizado'
    ");
    $stmt->execute([$id_usuario, $id_filme]);
    $alugou = $stmt->fetch()['total'] > 0;

    if (!$alugou) {
        $erro = "Você precisa alugar e finalizar este filme antes de avaliá-lo!";
    } else {
        $stmt = $pdo->prepare("SELECT * FROM filme WHERE id = ?");
        $stmt->execute([$id_filme]);
        $filme = $stmt->fetch();

        if (!$filme) {
            $erro = "Filme não encontrado!";
        } else {
            $stmt = $pdo->prepare("SELECT * FROM avaliacao WHERE id_usuario = ? AND id_filme = ?");
            $stmt->execute([$id_usuario, $id_filme]);
            $avaliacao_existente = $stmt->fetch();
        }
    }
} else {
    header('Location: meus-pedidos.php');
    exit();
}

if ($_SERVER['REQUEST_METHOD'] === 'POST' && $filme && $alugou) {
    $estrelas = isset($_POST['estrelas']) ? (int)$_POST['estrelas'] : 0;
    $comentario = limparDados($_POST['comentario']);

    if ($estrelas < 1 || $estrelas > 5) {
        $erro = "Selecione uma avaliação de 1 a 5 estrelas!";
    } elseif (empty($comentario)) {
        $erro = "Por favor, escreva um comentário!";
    } elseif (strlen($comentario) < 10) {
        $erro = "O comentário deve ter no mínimo 10 caracteres!";
    } else {
        if ($avaliacao_existente) {
            $stmt = $pdo->prepare("
                UPDATE avaliacao 
                SET estrelas = ?, comentario = ?, data_avaliacao = NOW() 
                WHERE id_usuario = ? AND id_filme = ?
            ");
            $resultado = $stmt->execute([$estrelas, $comentario, $id_usuario, $id_filme]);
        } else {
            $stmt = $pdo->prepare("
                INSERT INTO avaliacao (id_usuario, id_filme, estrelas, comentario) 
                VALUES (?, ?, ?, ?)
            ");
            $resultado = $stmt->execute([$id_usuario, $id_filme, $estrelas, $comentario]);
        }

        if ($resultado) {
            $sucesso = "Avaliação enviada com sucesso! Redirecionando...";
            header("refresh:2;url=meus-pedidos.php");
        } else {
            $erro = "Erro ao enviar avaliação. Tente novamente!";
        }
    }
}

if ($filme) {
    $stmt = $pdo->prepare("
        SELECT a.*, u.nome, u.url_foto 
        FROM avaliacao a 
        JOIN usuario u ON a.id_usuario = u.id 
        WHERE a.id_filme = ? 
        ORDER BY a.data_avaliacao DESC
    ");
    $stmt->execute([$id_filme]);
    $avaliacoes = $stmt->fetchAll();

    if (count($avaliacoes) > 0) {
        $soma_estrelas = array_sum(array_column($avaliacoes, 'estrelas'));
        $media_estrelas = round($soma_estrelas / count($avaliacoes), 1);
    } else {
        $media_estrelas = 0;
    }
}
?>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Avaliar <?php echo htmlspecialchars($filme['titulo'] ?? 'Filme'); ?> - EstanteFilmes</title>
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
            max-width: 900px;
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

        .voltar {
            background: #3685FF;
            color: white;
            padding: 0.7em 1.5em;
            border-radius: 10px;
            text-decoration: none;
            transition: all 0.3s;
        }

        .voltar:hover {
            background: #01337D;
            transform: translateY(-2px);
        }

        .filme-header {
            background: white;
            padding: 2em;
            border-radius: 20px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
            margin-bottom: 2em;
            display: grid;
            grid-template-columns: auto 1fr;
            gap: 2em;
            align-items: center;
        }

        .filme-icone {
            font-size: 5em;
            background: linear-gradient(135deg, #3685FF, #01337D);
            width: 120px;
            height: 120px;
            border-radius: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .filme-info h1 {
            color: #01337D;
            margin-bottom: 0.5em;
        }

        .genero {
            display: inline-block;
            background: #DCEAFF;
            color: #01337D;
            padding: 0.5em 1em;
            border-radius: 15px;
            font-weight: bold;
            margin-bottom: 1em;
        }

        .media-avaliacoes {
            display: flex;
            align-items: center;
            gap: 1em;
            margin-top: 1em;
        }

        .estrelas-media {
            font-size: 2em;
            color: #FFB521;
        }

        .numero-media {
            font-size: 2em;
            color: #01337D;
            font-weight: bold;
        }

        .total-avaliacoes {
            color: #666;
        }

        .form-avaliacao {
            background: white;
            padding: 2.5em;
            border-radius: 20px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
            margin-bottom: 2em;
        }

        .form-avaliacao h2 {
            color: #FFB521;
            margin-bottom: 1.5em;
            text-align: center;
        }

        .rating-container {
            text-align: center;
            margin-bottom: 2em;
        }

        .rating-container label {
            display: block;
            color: #01337D;
            font-weight: bold;
            margin-bottom: 1em;
            font-size: 1.2em;
        }

        .estrelas-input {
            display: flex;
            justify-content: center;
            gap: 0.5em;
            flex-direction: row-reverse;
        }

        .estrelas-input input[type="radio"] {
            display: none;
        }

        .estrelas-input label {
            font-size: 3em;
            color: #ddd;
            cursor: pointer;
            transition: all 0.3s;
        }

        .estrelas-input label:hover,
        .estrelas-input label:hover~label,
        .estrelas-input input[type="radio"]:checked~label {
            color: #FFB521;
            transform: scale(1.2);
        }

        .form-group {
            margin-bottom: 1.5em;
        }

        .form-group label {
            display: block;
            color: #01337D;
            font-weight: bold;
            margin-bottom: 0.5em;
        }

        .form-group textarea {
            width: 100%;
            padding: 1em;
            border: 2px solid #DCEAFF;
            border-radius: 10px;
            font-size: 1em;
            font-family: Arial, sans-serif;
            min-height: 150px;
            resize: vertical;
            transition: all 0.3s;
        }

        .form-group textarea:focus {
            outline: none;
            border-color: #FFB521;
            box-shadow: 0 0 10px rgba(255, 181, 33, 0.2);
            transform: scale(1.01);
        }

        .btn-enviar {
            background: linear-gradient(135deg, #FFB521, #ffa500);
            color: #01337D;
            padding: 1.2em 3em;
            border: none;
            border-radius: 15px;
            font-size: 1.2em;
            font-weight: bold;
            cursor: pointer;
            width: 100%;
            transition: all 0.3s;
        }

        .btn-enviar:hover {
            background: linear-gradient(135deg, #3685FF, #0066ff);
            color: white;
            transform: translateY(-3px);
            box-shadow: 0 5px 15px rgba(54, 133, 255, 0.4);
        }

        .erro {
            background: #ff4444;
            color: white;
            padding: 1em;
            border-radius: 10px;
            margin-bottom: 1.5em;
            text-align: center;
        }

        .sucesso {
            background: #00C851;
            color: white;
            padding: 1em;
            border-radius: 10px;
            margin-bottom: 1.5em;
            text-align: center;
        }

        .avaliacoes-section {
            background: white;
            padding: 2.5em;
            border-radius: 20px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
        }

        .avaliacoes-section h2 {
            color: #01337D;
            margin-bottom: 1.5em;
            text-align: center;
        }

        .avaliacao-card {
            background: #DCEAFF;
            padding: 1.5em;
            border-radius: 15px;
            margin-bottom: 1.5em;
            transition: all 0.3s;
        }

        .avaliacao-card:hover {
            transform: translateX(10px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        .avaliacao-header {
            display: flex;
            align-items: center;
            gap: 1em;
            margin-bottom: 1em;
        }

        .avaliacao-foto {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            object-fit: cover;
            border: 3px solid #FFB521;
            background: linear-gradient(135deg, #3685FF, #01337D);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.5em;
            color: #FFB521;
        }

        .avaliacao-foto img {
            width: 100%;
            height: 100%;
            border-radius: 50%;
            object-fit: cover;
        }

        .avaliacao-usuario {
            flex: 1;
        }

        .avaliacao-nome {
            font-weight: bold;
            color: #01337D;
        }

        .avaliacao-data {
            font-size: 0.9em;
            color: #666;
        }

        .avaliacao-estrelas {
            color: #FFB521;
            font-size: 1.2em;
        }

        .avaliacao-comentario {
            color: #555;
            line-height: 1.8;
            margin-top: 0.5em;
        }

        .minha-avaliacao {
            border: 3px solid #FFB521;
            background: linear-gradient(135deg, rgba(255, 181, 33, 0.1), rgba(54, 133, 255, 0.1));
        }

        @media (max-width: 768px) {
            .header {
                flex-direction: column;
                gap: 1em;
            }

            .filme-header {
                grid-template-columns: 1fr;
                text-align: center;
            }

            .filme-icone {
                margin: 0 auto;
            }

            .estrelas-input label {
                font-size: 2em;
            }
        }
    </style>
</head>

<body>
    <div class="container">
        <div class="header">
            <a href="../index.php" class="logo">🎬 EstanteFilmes</a>
            <a href="meus-pedidos.php" class="voltar">← Voltar</a>
        </div>

        <?php if ($erro && !$filme): ?>
            <div class="erro"><?php echo $erro; ?></div>
            <div style="text-align: center; margin-top: 2em;">
                <a href="meus-pedidos.php" class="voltar" style="display: inline-block;">← Voltar para Meus Pedidos</a>
            </div>
        <?php endif; ?>

        <?php if ($filme): ?>
            <div class="filme-header">
                <div class="filme-icone">🎬</div>
                <div class="filme-info">
                    <h1><?php echo htmlspecialchars($filme['titulo']); ?></h1>
                    <span class="genero"><?php echo htmlspecialchars($filme['genero']); ?></span>

                    <?php if (count($avaliacoes) > 0): ?>
                        <div class="media-avaliacoes">
                            <span class="estrelas-media"><?php echo str_repeat('★', round($media_estrelas)); ?></span>
                            <span class="numero-media"><?php echo $media_estrelas; ?></span>
                            <span class="total-avaliacoes">(<?php echo count($avaliacoes); ?> avaliações)</span>
                        </div>
                    <?php endif; ?>
                </div>
            </div>

            <div class="form-avaliacao">
                <h2><?php echo $avaliacao_existente ? '✏️ Editar Minha Avaliação' : '⭐ Avaliar Filme'; ?></h2>

                <?php if ($erro && $alugou): ?>
                    <div class="erro"><?php echo $erro; ?></div>
                <?php endif; ?>

                <?php if ($sucesso): ?>
                    <div class="sucesso"><?php echo $sucesso; ?></div>
                <?php endif; ?>

                <form method="POST">
                    <div class="rating-container">
                        <label>Quantas estrelas você dá?</label>
                        <div class="estrelas-input">
                            <input type="radio" name="estrelas" value="5" id="star5" <?php echo ($avaliacao_existente && $avaliacao_existente['estrelas'] == 5) ? 'checked' : ''; ?>>
                            <label for="star5">★</label>

                            <input type="radio" name="estrelas" value="4" id="star4" <?php echo ($avaliacao_existente && $avaliacao_existente['estrelas'] == 4) ? 'checked' : ''; ?>>
                            <label for="star4">★</label>

                            <input type="radio" name="estrelas" value="3" id="star3" <?php echo ($avaliacao_existente && $avaliacao_existente['estrelas'] == 3) ? 'checked' : ''; ?>>
                            <label for="star3">★</label>

                            <input type="radio" name="estrelas" value="2" id="star2" <?php echo ($avaliacao_existente && $avaliacao_existente['estrelas'] == 2) ? 'checked' : ''; ?>>
                            <label for="star2">★</label>

                            <input type="radio" name="estrelas" value="1" id="star1" <?php echo ($avaliacao_existente && $avaliacao_existente['estrelas'] == 1) ? 'checked' : ''; ?>>
                            <label for="star1">★</label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="comentario">Seu comentário</label>
                        <textarea
                            name="comentario"
                            id="comentario"
                            placeholder="Conte o que você achou do filme..."
                            required><?php echo $avaliacao_existente ? htmlspecialchars($avaliacao_existente['comentario']) : ''; ?></textarea>
                    </div>

                    <button type="submit" class="btn-enviar">
                        <?php echo $avaliacao_existente ? '✏️ Atualizar Avaliação' : '⭐ Enviar Avaliação'; ?>
                    </button>
                </form>
            </div>

            <?php if (count($avaliacoes) > 0): ?>
                <div class="avaliacoes-section">
                    <h2>💬 Todas as Avaliações (<?php echo count($avaliacoes); ?>)</h2>

                    <?php foreach ($avaliacoes as $av): ?>
                        <div class="avaliacao-card <?php echo ($av['id_usuario'] == $id_usuario) ? 'minha-avaliacao' : ''; ?>">
                            <div class="avaliacao-header">
                                <div class="avaliacao-foto">
                                    <?php if ($av['url_foto'] && $av['url_foto'] !== 'assets/images/default.png'): ?>
                                        <img src="../<?php echo htmlspecialchars($av['url_foto']); ?>" alt="<?php echo htmlspecialchars($av['nome']); ?>" onerror="this.parentElement.innerHTML='👤';">
                                    <?php else: ?>
                                        👤
                                    <?php endif; ?>
                                </div>
                                <div class="avaliacao-usuario">
                                    <div class="avaliacao-nome">
                                        <?php echo htmlspecialchars($av['nome']); ?>
                                        <?php if ($av['id_usuario'] == $id_usuario): ?>
                                            <span style="color: #FFB521;"> (Você)</span>
                                        <?php endif; ?>
                                    </div>
                                    <div class="avaliacao-data">
                                        <?php echo date('d/m/Y H:i', strtotime($av['data_avaliacao'])); ?>
                                    </div>
                                </div>
                                <div class="avaliacao-estrelas">
                                    <?php echo str_repeat('★', $av['estrelas']); ?>
                                </div>
                            </div>
                            <p class="avaliacao-comentario"><?php echo htmlspecialchars($av['comentario']); ?></p>
                        </div>
                    <?php endforeach; ?>
                </div>
            <?php endif; ?>
        <?php endif; ?>
    </div>
</body>

</html>