<?php
session_start();

if (!isset($_SESSION['nome']) || !isset($_SESSION['email'])) {
    header("Location: login.php");
    exit();
}

if (isset($_GET['logout'])) {
    session_destroy();
    header("Location: login.php");
    exit();
}
?>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Área do Usuário</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <h2>Bem-vindo, <?php echo htmlspecialchars($_SESSION['nome']); ?>!</h2>

        <div class="welcome-text">
            E-mail: <?php echo htmlspecialchars($_SESSION['email']); ?>
        </div>

        <a href="?logout=true" style="text-decoration: none;">
            <button class="btn-danger">Logout</button>
        </a>
    </div>
</body>
</html>

 