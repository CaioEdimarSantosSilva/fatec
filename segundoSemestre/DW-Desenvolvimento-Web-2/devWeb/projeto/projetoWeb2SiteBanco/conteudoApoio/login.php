<?php

require 'config.php';

session_start();

if ($_SERVER['REQUEST_METHOD'] === 'POST') {

    $email = $_POST['email'];
    $senha = $_POST['senha'];

    $query = $pdo->prepare("SELECT * FROM usuario WHERE email = ?");
    $query->execute([$email]);
    $usuario = $query->fetch();

    if ($usuario && password_verify($senha, $usuario['senha'])) {
        $_SESSION['usuario_id'] = $usuario['id'];
        $_SESSION['usuario_nome'] = $usuario['nome'];
        $_SESSION['usuario_foto'] = $usuario['foto'];

        header("Location: dashboard.php");
        exit;
    } else {
        echo "<strong>Email e/ou senha incorretos.</strong>";
    }
}


?>


<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <link rel="stylesheet" href="estilo.css">
    <title>Login</title>
</head>

<body>
    <h1>Login</h1>
    <form action="#" method="post">

        <label>Email:</label>
        <input type="email" name="email" required>
        <br><br>

        <label>Senha:</label>
        <input type="passoword" name="senha" required>
        <br><br>

        <input type="submit" value="Entrar">
    </form>
    <p>Não tem cadastro? <a href="createUser.php">Clique aqui</a></p>
</body>

</html>