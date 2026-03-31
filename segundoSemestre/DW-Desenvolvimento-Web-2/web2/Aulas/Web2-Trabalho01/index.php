<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
    <title>Formulário de Pedido</title>
</head>
<body>
    <form action="#" method="post">
        <h1>PizzaTop</h1>

        <h2>Novo Pedido</h2>

        <label>Seu nome:</label>
        <br>
        <input class="nome" type="text" name="txtcliente">
        <br><br>

         <label>Escolha o Sabor:</label>
         <br>
        <select name="sltsabor">
            <option value="40">Mussarela - R$ 40,00</option>
            <option value="35">Calabresa - R$ 35,00</option>
            <option value="45">Portuguesa - R$ 45,00</option>
        </select>
        <br><br>

        <label>Borda Recheada:</label>
        <br>
        <input type="radio" value="5" name="rdborda">Sim (R$ 5,00)
        <br>
        <input type="radio" value="Não" name="rdborda">Não
        <br><br>

       

        <label>Bebida:</label>
        <br> 
        <input type="checkbox" value="12" name="bebida[]"> Suco 500ml - R$ 12,00
        <br>
        <input type="checkbox" value="8" name="bebida[]"> Regrigerante Lata - R$ 8,00
        <br>
        <input class="botao" type="submit" value="Finalizar Pedido">
        <br><br>
    </form>
</body>
</html>

<?php

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $nome = $_POST['txtcliente'];
    $sabor = $_POST['sltsabor'];
    $borda = $_POST['rdborda'];
    $bebidas = $_POST['bebida'];
    $valorTotal = 0;

    
    echo"<div class='resposta'><h1>Resumo do Pedido</h1>";
    echo "<br><strong>Cliente: </strong>" . $nome;
    

    if($sabor == "45"){
        echo "<br><strong>Sabor:</strong> Portuguesa R$ " . number_format($sabor, 2, ',', '.');
        $valorTotal += 45;
    }
    else if($sabor == "40"){
        echo "<br><strong>Sabor:</strong> Mussarela R$ " . number_format($sabor, 2, ',', '.');
        $valorTotal += 40;
    }
    else{
        echo "<br><strong>Sabor:</strong> Calabresa R$ " . number_format($sabor, 2, ',', '.');
        $valorTotal += 35;
    }


    if($borda == '5'){
        echo "<br><strong>Borda Recheada: </strong>  R$ " . number_format($borda, 2, ',', '.');
        $valorTotal += 5;
    }
    else{
        echo"<br><strong>Sem borda recheada.</strong>";
    }
    
    echo "<br><strong>Bebidas: </strong>";

    foreach($bebidas as $bebida){

        if($bebida == "12"){
            echo "Suco 500ml - R$ " . number_format($bebida, 2, ',', '.') . "<br>";
            $valorTotal += 12;
        }else{
            echo "Refrigerante Lata - R$ " . number_format($bebida, 2, ',', '.');
            $valorTotal += 8;
        }
    }

    echo "<br><strong>Valor Total:</strong> R$ " . number_format($valorTotal, 2, ',', '.') . "</div>";
}

?>