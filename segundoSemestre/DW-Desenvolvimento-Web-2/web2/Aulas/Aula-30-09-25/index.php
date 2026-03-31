<?php
//Cookie
if (isset($_COOKIE['background'])) {
    $cor = $_COOKIE['background'];
}
//Sessão
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    //Inicia a sessão
    session_start();

    if (isset($_POST['nome']) && !empty($_POST['nome'])) {
        //Variavel de sessão
        $_SESSION['nome'] = $_POST['nome'];
        header('Location: usuario.php');
    }
    else{
        echo"<script>alert('Preencha todos os campos')</script>";
    }
}
?>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <title>Cookies e Sessões</title>
    <link rel="stylesheet" href="style.css">
</head>

<body style="background-color: <?php echo $cor; ?>">

    <main class="container">
        <h2>Sistema PHP</h2>

        <form method="POST" action="#">
            <input type="text" name="nome" placeholder="seu username">
            <input type="submit" value="Entrar" class="botao">
        </form>

        <a href="config.php" class="config-link">Configurações</a>

    </main>

</body>

</html>