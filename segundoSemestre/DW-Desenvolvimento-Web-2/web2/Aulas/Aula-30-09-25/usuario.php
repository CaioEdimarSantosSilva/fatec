<?php
session_start();

if(isset($_SESSION['nome'])){
    $nome = $_SESSION['nome'];

}
else{
    header('Location: index.php');
}


?>

<!DOCTYPE html>
<html>
<head>
    <title>Área Restrita</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
  <div class="container">
        <h2>Olá, <?php echo $nome; ?> </h2>

        <img src="https://cdn.awsli.com.br/800x800/2618/2618163/produto/253864569/flork---bem-vindo-preto-88w0whifo1.png" alt="Imagem de boas-vindas">

        <p><a href="logout.php" class="botao" >Sair</a></p>
    </div>
</body>
</html>
