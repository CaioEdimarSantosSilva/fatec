<?php
require_once '../config.php';
verificarLogin();

$erro = '';
$sucesso = '';
$filme = null;

// Pegar ID do filme
$id_filme = isset($_GET['id']) ? (int)$_GET['id'] : 0;

if ($id_filme > 0) {
    // Buscar informações do filme
    $stmt = $pdo->prepare("SELECT * FROM filme WHERE id = ? AND disponivel = 'sim'");
    $stmt->execute([$id_filme]);
    $filme = $stmt->fetch();
    
    if (!$filme) {
        $erro = "Filme não encontrado ou indisponível!";
    }
} else {
    header('Location: ../index.php');
    exit();
}

// Processar aluguel
if ($_SERVER['REQUEST_METHOD'] === 'POST' && $filme) {
    $id_usuario = $_SESSION['usuario_id'];
    
    // Verificar se usuário já tem este filme alugado
    $stmt = $pdo->prepare("SELECT id FROM pedido WHERE id_usuario = ? AND id_filme = ? AND status = 'ativo'");
    $stmt->execute([$id_usuario, $id_filme]);
    
    if ($stmt->rowCount() > 0) {
        $erro = "Você já tem este filme alugado!";
    } else {
        // Inserir pedido
        $stmt = $pdo->prepare("INSERT INTO pedido (id_usuario, id_filme, status) VALUES (?, ?, 'ativo')");
        
        if ($stmt->execute([$id_usuario, $id_filme])) {
            $sucesso = "Filme alugado com sucesso! Redirecionando...";
            header("refresh:2;url=meus-pedidos.php");
        } else {
            $erro = "Erro ao processar aluguel. Tente novamente!";
        }
    }
}

// Buscar avaliações do filme
$stmt = $pdo->prepare("
    SELECT a.*, u.nome 
    FROM avaliacao a 
    JOIN usuario u ON a.id_usuario = u.id 
    WHERE a.id_filme = ? 
    ORDER BY a.data_avaliacao DESC 
    LIMIT 5
");
$stmt->execute([$id_filme]);
$avaliacoes = $stmt->fetchAll();
?>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><?php echo htmlspecialchars($filme['titulo'] ?? 'Filme'); ?> - EstanteFilmes</title>
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
            background: white;
            padding: 3em;
            border-radius: 20px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
        }
        
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2em;
            padding-bottom: 1em;
            border-bottom: 2px solid #DCEAFF;
        }
        
        .logo {
            color: #FFB521;
            font-size: 1.5em;
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
        
        .filme-detalhes {
            display: grid;
            grid-template-columns: 1fr 2fr;
            gap: 2em;
            margin-bottom: 3em;
        }
        
        .filme-capa {
            background: linear-gradient(135deg, #3685FF, #01337D);
            border-radius: 15px;
            height: 400px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 6em;
            color: #FFB521;
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
        
        .descricao {
            color: #555;
            line-height: 1.8;
            margin-bottom: 2em;
        }
        
        .preco {
            font-size: 2.5em;
            color: #FFB521;
            font-weight: bold;
            margin-bottom: 1em;
        }
        
        .btn-alugar {
            background: linear-gradient(135deg, #FFB521, #ffa500);
            color: #01337D;
            padding: 1.2em 2em;
            border: none;
            border-radius: 15px;
            font-size: 1.2em;
            font-weight: bold;
            cursor: pointer;
            width: 100%;
            transition: all 0.3s;
        }
        
        .btn-alugar:hover {
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
        }
        
        .sucesso {
            background: #00C851;
            color: white;
            padding: 1em;
            border-radius: 10px;
            margin-bottom: 1.5em;
        }
        
        .avaliacoes-section {
            margin-top: 3em;
            padding-top: 2em;
            border-top: 2px solid #DCEAFF;
        }
        
        .avaliacoes-section h2 {
            color: #01337D;
            margin-bottom: 1.5em;
        }
        
        .avaliacao-card {
            background: #DCEAFF;
            padding: 1.5em;
            border-radius: 15px;
            margin-bottom: 1em;
        }
        
        .avaliacao-header {
            display: flex;
            justify-content: space-between;
            margin-bottom: 0.5em;
        }
        
        .avaliacao-nome {
            font-weight: bold;
            color: #01337D;
        }
        
        .avaliacao-estrelas {
            color: #FFB521;
        }
        
        .avaliacao-comentario {
            color: #555;
            line-height: 1.6;
        }
        
        @media (max-width: 768px) {
            .filme-detalhes {
                grid-template-columns: 1fr;
            }
            
            .filme-capa {
                height: 300px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <a href="../index.php" class="logo">🎬 EstanteFilmes</a>
            <a href="meus-pedidos.php" class="voltar">Meus Pedidos</a>
        </div>
        
        <?php if ($erro): ?>
            <div class="erro"><?php echo $erro; ?></div>
        <?php endif; ?>
        
        <?php if ($sucesso): ?>
            <div class="sucesso"><?php echo $sucesso; ?></div>
        <?php endif; ?>
        
        <?php if ($filme): ?>
            <div class="filme-detalhes">
                <div class="filme-capa"><img src="../<?= htmlspecialchars($filme['capa']) ?>" alt="<?= htmlspecialchars($filme['titulo']) ?>" style="width:100%; height:100%; border-radius:15px; object-fit:cover;">
</div>
                <div class="filme-info">
                    <h1><?php echo htmlspecialchars($filme['titulo']); ?></h1>
                    <span class="genero"><?php echo htmlspecialchars($filme['genero']); ?></span>
                    <p class="descricao"><?php echo htmlspecialchars($filme['descricao']); ?></p>
                    <div class="preco">R$ <?php echo number_format($filme['preco'], 2, ',', '.'); ?></div>
                    
                    <form method="POST">
                        <button type="submit" class="btn-alugar">🎬 Alugar Agora</button>
                    </form>
                </div>
            </div>
            
            <?php if (count($avaliacoes) > 0): ?>
                <div class="avaliacoes-section">
                    <h2>Avaliações dos Clientes</h2>
                    <?php foreach ($avaliacoes as $av): ?>
                        <div class="avaliacao-card">
                            <div class="avaliacao-header">
                                <span class="avaliacao-nome"><?php echo htmlspecialchars($av['nome']); ?></span>
                                <span class="avaliacao-estrelas"><?php echo str_repeat('★', $av['estrelas']); ?></span>
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