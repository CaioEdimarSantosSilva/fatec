<?php
require '../config.php';
session_start();

if (!isset($_SESSION['user']) || $_SESSION['user']['nivel'] !== 'admin') {
    header("Location: ../login.php");
    exit;
}
?>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Painel Admin</title>
<link rel="stylesheet" href="../assets/styles.css">
</head>
<body>
    <header>
        <h1>Bem-vindo, <?= htmlspecialchars($_SESSION['user']['nome']) ?></h1>
        <a href="../logout.php">Sair</a>
    </header>

    <nav>
        <a href="usuarios.php">Usuários</a>
        <a href="servicos.php">Serviços/Produtos</a>
        <a href="avaliacoes.php">Avaliações</a>
        <a href="contatos.php">Contatos</a>
    </nav>
</body>
</html>
