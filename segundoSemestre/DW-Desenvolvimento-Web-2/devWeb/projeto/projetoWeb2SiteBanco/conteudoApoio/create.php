<?php

require 'config.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {

    $query = $pdo->prepare("INSERT INTO avaliacao (nome, estrelas, comentario) VALUES (?, ?, ?)");

    $query->execute([$_POST['nome'], (int)$_POST['estrelas'], $_POST['comentario']]);

    header("Location: index.php");
    exit;
}


?>

<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <link rel="stylesheet" href="estilo.css">
    <title>Nova Avaliação</title>
</head>

<body>
    <h1>Nova Avaliação</h1>
    <form action="#" method="post">
        <label>Nome:</label>
        <input type="text" name="nome" required>
        <br><br>

        <label>Estrelas (1 a 5):</label>
        <input type="number" name="estrelas" min="1" max="5" required>
        <br><br>

        <label>Comentário:</label><br>
        <textarea name="comentario" rows="4" cols="50" required></textarea>
        <br><br>

        <input type="submit" value="Salvar">
    </form>
    <p><a href="index.php">Voltar</a></p>
</body>

</html>