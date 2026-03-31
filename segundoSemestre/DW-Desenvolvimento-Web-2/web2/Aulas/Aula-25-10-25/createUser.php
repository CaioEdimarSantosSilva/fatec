<?php

require 'config.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {

    if(isset($_FILES['foto']) && $_FILES['foto']['error'] === UPLOAD_ERR_OK) {

        $extensao = pathinfo($_FILES['foto']['name'], PATHINFO_EXTENSION); 
        $nomeArquivo = uniqid('user_', true) . '.' . $extensao;
        $caminhoFoto = 'images/users/' . $nomeArquivo;

        if(!move_uploaded_file($_FILES['foto']['tmp_name'], $caminhoFoto)) {
            echo "Erro ao salvar a foto.";
        }

    } else {
        echo "Erro no upload da foto.";
    }
    
    $senhaHash = password_hash($_POST['senha'], PASSWORD_DEFAULT);

    $query = $pdo->prepare("INSERT INTO usuario (nome, email, senha, foto) VALUES (?, ?, ?, ?)");

    $query->execute([$_POST['nome'], $_POST['email'], $senhaHash, $caminhoFoto]);

    header("Location: login.php");
    exit;
}


?>

<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
    <title>Novo Usuário</title>
</head>

<body>
    <h1>Novo Usuário</h1>
    <form action="#" method="post" enctype="multipart/form-data">
        <label>Nome:</label>
        <input type="text" name="nome" required>
        <br><br>

        <label>Email:</label>
        <input type="email" name="email" required>
        <br><br>

        <label>Senha:</label>
        <input type="passoword" name="senha" required>
        <br><br>

        <label>Foto:</label>
        <input type="file" name="foto" accept="image/*">
        <br><br>

        <input type="submit" value="Salvar">
    </form>
    <p>Já tem cadastro? <a href="login.php">Clique aqui</a></p>
</body>

</html>