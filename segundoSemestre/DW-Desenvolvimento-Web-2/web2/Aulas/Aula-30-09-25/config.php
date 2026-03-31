<?php
if($_SERVER["REQUEST_METHOD"] === "POST"){
    $cor = $_POST['tema'];

    // cookie com duração de um mês
    setcookie("background",$cor,time() + 60 * 60 * 24 * 30);


    // redireciona  para o index;
    header('Location: index.php');
}
?>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Cookies e Sessões</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

    <main class="container">
        <h2>Configurações</h2>

        <form method="POST" action="">
            <label>Escolha um Tema:</label>
            <select name="tema">
                <option value="gainsboro">Escolha uma cor</option>
                <option value="white">Branco</option>
                <option value="lightgray">Cinza</option>
                <option value="black">Preto</option>
            </select>

            <input type="submit" value="Salvar"  class="botao">
        </form>

    </main>

</body>
</html>


