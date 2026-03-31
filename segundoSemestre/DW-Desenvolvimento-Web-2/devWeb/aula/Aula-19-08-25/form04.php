<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Form 04</title>
</head>

<body>
    <form action="#" method="post">
        <label>Valor 1:</label>
        <input type="text" name="txtvalor1">
        <br>

        <label>Valor 2:</label>
        <input type="text" name="txtvalor2">
        <br><br>

        <input type="submit" value="Somar" name="btnsoma">
        <input type="submit" value="Multiplicar" name="btnmult">
        <br><br><br>
    </form>
</body>

</html>

<?php
if ($_SERVER['REQUEST_METHOD'] == "POST") {
    $v1 = $_POST["txtvalor1"];
    $v2 = $_POST["txtvalor2"];

    if (isset($_POST['btnsoma'])) {
        echo "Soma: " . $v1 + $v2;
    }
    if (isset($_POST['btnmult'])) {
        echo "Multiplicação: " . $v1 * $v2;
    }
}
?>