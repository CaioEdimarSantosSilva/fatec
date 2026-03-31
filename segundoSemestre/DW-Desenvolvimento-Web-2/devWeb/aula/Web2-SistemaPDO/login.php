<?php
require_once 'config.php';

if (session_status() === PHP_SESSION_NONE) {
    session_start();
}

$erro = '';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $email = limparDados($_POST['email']);
    $senha = $_POST['senha'];

    if (empty($email) || empty($senha)) {
        $erro = "Preencha todos os campos!";
    } else {
        $stmt = $pdo->prepare("SELECT * FROM usuario WHERE email = ?");
        $stmt->execute([$email]);
        $usuario = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($usuario && password_verify($senha, $usuario['senha'])) {
            $_SESSION['usuario_id'] = $usuario['id'];
            $_SESSION['usuario_nome'] = $usuario['nome'];
            $_SESSION['usuario_email'] = $usuario['email'];
            $_SESSION['usuario_nivel'] = $usuario['nivel'];

            if ($usuario['nivel'] === 'admin') {
                header('Location: admin/dashboard.php');
            } else {
                header('Location: usuario/meus-pedidos.php');
            }
            exit();
        } else {
            $erro = "E-mail ou senha incorretos!";
        }
    }
}
?>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - EstanteFilmes</title>
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
            position: relative;
            overflow: hidden;
        }

        body::before {
            content: '★ ★ ★ ★ ★ ★ ★';
            position: absolute;
            top: 10%;
            left: 0;
            right: 0;
            text-align: center;
            font-size: 2em;
            color: rgba(255, 181, 33, 0.2);
            letter-spacing: 3em;
            animation: float 6s ease-in-out infinite;
        }

        @keyframes float {

            0%,
            100% {
                transform: translateY(0);
            }

            50% {
                transform: translateY(-20px);
            }
        }

        .container {
            background: white;
            padding: 3em;
            border-radius: 20px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.3);
            max-width: 450px;
            width: 100%;
            animation: slideUp 0.5s ease-out;
            position: relative;
            z-index: 1;
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
            font-size: 1.5em;
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
            transform: scale(1.02);
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
            transform: translateY(-3px);
            box-shadow: 0 5px 15px rgba(54, 133, 255, 0.4);
        }

        .erro {
            background: #ff4444;
            color: white;
            padding: 1em;
            border-radius: 10px;
            margin-bottom: 1em;
            text-align: center;
            animation: shake 0.5s;
        }

        @keyframes shake {

            0%,
            100% {
                transform: translateX(0);
            }

            25% {
                transform: translateX(-10px);
            }

            75% {
                transform: translateX(10px);
            }
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

        .demo-info {
            background: #DCEAFF;
            padding: 1em;
            border-radius: 10px;
            margin-bottom: 1.5em;
            font-size: 0.9em;
            color: #01337D;
        }

        .demo-info strong {
            color: #FFB521;
        }
    </style>
</head>

<body>
    <div class="container">
        <div class="logo">🎬 EstanteFilmes</div>
        <h1>Login</h1>

        <div class="demo-info">
            <strong>Demo:</strong><br>
            Admin: admin@estante.com / admin123<br>
            Usuário: usuario@estante.com / user123
        </div>

        <?php if ($erro): ?>
            <div class="erro"><?= htmlspecialchars($erro) ?></div>
        <?php endif; ?>

        <form method="POST">
            <div class="form-group">
                <label for="email">E-mail</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="senha">Senha</label>
                <input type="password" id="senha" name="senha" required>
            </div>
            <button type="submit" class="btn">Entrar</button>
        </form>

        <div class="links">
            <p>Não tem uma conta? <a href="cadastro.php">Cadastre-se</a></p>
            <p><a href="index.php">← Voltar para home</a></p>
        </div>
    </div>
</body>

</html>