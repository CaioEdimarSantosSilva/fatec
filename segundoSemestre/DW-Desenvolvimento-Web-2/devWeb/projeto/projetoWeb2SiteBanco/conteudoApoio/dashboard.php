<?php

require 'config.php';

session_start();

if (!isset($_SESSION['usuario_id'])) {
    header("Location: login.php");
    exit;
}

    $query = $pdo->prepare("SELECT nome, email, foto FROM usuario WHERE id = ?");
    $query->execute([$_SESSION['usuario_id']]);
    $usuario = $query->fetch();


?>


<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <link rel="stylesheet" href="estilo.css">
    <title>Dashboard</title>
</head>
<body>
    
    <h1>Dashboard</h1>

    <?php  if(!empty($usuario['foto'])){?>

    <img src="<?php echo $usuario['foto'];?>" width="350px">

    <?php 
    }
    else{
        ?>

        <p>Usuário sem foto.</p>

        <?php
    } ?>


    <p>Bem-vindo(a), <?php echo $usuario['nome'];?></p>
    <p>Email: <?php echo $usuario['email'];?></p>
    <a href="logout.php">Sair</a>

</body>
</html>