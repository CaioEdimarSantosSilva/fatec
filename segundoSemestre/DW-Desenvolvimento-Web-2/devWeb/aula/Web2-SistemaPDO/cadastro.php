<?php
require_once 'config.php';

$erro = '';
$sucesso = '';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $nome = limparDados($_POST['nome']);
    $email = limparDados($_POST['email']);
    $senha = $_POST['senha'];
    $confirmar_senha = $_POST['confirmar_senha'];
    $imagem_perfil = null;

    if (isset($_FILES['imagem_perfil']) && $_FILES['imagem_perfil']['error'] === 0) {
        $diretorio = 'assets/images/usuarios/';

        if (!is_dir($diretorio)) {
            mkdir($diretorio, 0755, true);
        }

        $nomeArquivo = uniqid() . '-' . basename($_FILES['imagem_perfil']['name']);
        $caminhoDestino = $diretorio . $nomeArquivo;

        if (move_uploaded_file($_FILES['imagem_perfil']['tmp_name'], $caminhoDestino)) {
            $imagem_perfil = $caminhoDestino;
        } else {
            $erro = "Falha ao enviar a imagem!";
        }
    } else {
        $imagem_perfil = 'assets/images/default.png';
    }

    if (empty($nome) || empty($email) || empty($senha)) {
        $erro = "Todos os campos são obrigatórios!";
    } elseif (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $erro = "E-mail inválido!";
    } elseif (strlen($senha) < 6) {
        $erro = "A senha deve ter no mínimo 6 caracteres!";
    } elseif ($senha !== $confirmar_senha) {
        $erro = "As senhas não coincidem!";
    } else {
        $stmt = $pdo->prepare("SELECT id FROM usuario WHERE email = ?");
        $stmt->execute([$email]);

        if ($stmt->rowCount() > 0) {
            $erro = "Este e-mail já está cadastrado!";
        } else {
            $senha_hash = password_hash($senha, PASSWORD_DEFAULT);
            $stmt = $pdo->prepare("INSERT INTO usuario (nome, email, senha, nivel, url_foto) VALUES (?, ?, ?, 'usuario', ?)");

            if ($stmt->execute([$nome, $email, $senha_hash, $imagem_perfil])) {
                $sucesso = "Cadastro realizado com sucesso! Redirecionando...";
                header("refresh:2;url=login.php");
            } else {
                $erro = "Erro ao cadastrar. Tente novamente!";
            }
        }
    }
}
?>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro - EstanteFilmes</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #01337D 0%, #3685FF 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 2em;
        }

        .container {
            background: white;
            padding: 3em;
            border-radius: 20px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.3);
            max-width: 450px;
            width: 100%;
            animation: slideUp 0.5s ease-out;
        }

        @keyframes slideUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }

            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        h1 {
            color: #01337D;
            text-align: center;
            margin-bottom: 0.5em;
            font-size: 2em;
        }

        .logo {
            text-align: center;
            color: #FFB521;
            font-size: 1.2em;
            margin-bottom: 2em;
            font-weight: bold;
        }

        .form-group {
            margin-bottom: 1.5em;
        }

        label {
            display: block;
            color: #01337D;
            font-weight: bold;
            margin-bottom: 0.5em;
        }

        input {
            width: 100%;
            padding: 1em;
            border: 2px solid #DCEAFF;
            border-radius: 10px;
            font-size: 1em;
            transition: all 0.3s;
        }

        input:focus {
            outline: none;
            border-color: #FFB521;
            box-shadow: 0 0 10px rgba(255, 181, 33, 0.2);
        }

        .btn {
            width: 100%;
            padding: 1.2em;
            background: linear-gradient(135deg, #FFB521, #ffa500);
            color: #01337D;
            border: none;
            border-radius: 10px;
            font-size: 1.1em;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.3s;
        }

        .btn:hover {
            background: linear-gradient(135deg, #3685FF, #0066ff);
            color: white;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(54, 133, 255, 0.4);
        }

        .erro {
            background: #ff4444;
            color: white;
            padding: 1em;
            border-radius: 10px;
            margin-bottom: 1em;
            text-align: center;
        }

        .sucesso {
            background: #00C851;
            color: white;
            padding: 1em;
            border-radius: 10px;
            margin-bottom: 1em;
            text-align: center;
        }

        .links {
            text-align: center;
            margin-top: 1.5em;
        }

        .links a {
            color: #3685FF;
            text-decoration: none;
            transition: color 0.3s;
        }

        .links a:hover {
            color: #FFB521;
        }
    </style>
</head>

<body>
    <div class="container">
        <div class="logo">🎬 EstanteFilmes</div>
        <h1>Criar Conta</h1>

        <?php if ($erro): ?>
            <div class="erro"><?php echo $erro; ?></div>
        <?php endif; ?>

        <?php if ($sucesso): ?>
            <div class="sucesso"><?php echo $sucesso; ?></div>
        <?php endif; ?>

        <form method="POST" enctype="multipart/form-data">
            <div class="form-group">
                <label for="nome">Nome Completo</label>
                <input type="text" id="nome" name="nome" required>
            </div>

            <div class="form-group">
                <label for="email">E-mail</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="senha">Senha</label>
                <input type="password" id="senha" name="senha" required>
            </div>

            <div class="form-group">
                <label for="confirmar_senha">Confirmar Senha</label>
                <input type="password" id="confirmar_senha" name="confirmar_senha" required>
            </div>
            <div class="form-group">
                <label for="imagem_perfil">Foto de Perfil</label>
                <input type="file" id="imagem_perfil" name="imagem_perfil" required>
            </div>
            <button type="submit" class="btn">Cadastrar</button>
        </form>

        <div class="links">
            <p>Já tem uma conta? <a href="login.php">Faça login</a></p>
            <p><a href="index.php">← Voltar para home</a></p>
        </div>
    </div>
</body>

</html>