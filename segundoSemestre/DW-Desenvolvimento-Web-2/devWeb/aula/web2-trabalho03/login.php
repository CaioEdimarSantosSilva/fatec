<?php
session_start();

$emailCookie = isset($_COOKIE['email']) ? $_COOKIE['email'] : '';

if ($_SERVER["REQUEST_METHOD"] === "POST") {
    $email = $_POST["email"];
    $senha = $_POST["senha"];

    if (!empty($email) && !empty($senha)) {
        $arquivo = fopen("dados.txt", "r");
        $loginValido = false;
        $nomeUsuario = "";

        while (($linha = fgets($arquivo)) !== false) {
            $dados = explode(" || ", $linha);

            if (trim($dados[1]) === $email && trim($dados[2]) === $senha) {
                $loginValido = true;
                $nomeUsuario = trim($dados[0]);
                break;
            }
        }
        fclose($arquivo);

        if ($loginValido) {
            $_SESSION['nome'] = $nomeUsuario;
            $_SESSION['email'] = $email;

            header("Location: usuario.php");
            exit();
        } else {
            $erro = "Email ou senha incorretos!";
        }
    } else {
        $erro = "Preencha todos os campos!";
    }
}
?>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <h2>Login</h2>

        <?php if (isset($erro)) echo "<div class='error'>$erro</div>"; ?>

        <form method="post" action="">
            <label>E-mail:</label>
            <input type="email" name="email" value="<?php echo htmlspecialchars($emailCookie); ?>" required>

            <label>Senha:</label>
            <input type="password" name="senha" required>

            <button type="submit" class="btn-primary">Entrar</button>
        </form>
    </div>
</body>
</html>
