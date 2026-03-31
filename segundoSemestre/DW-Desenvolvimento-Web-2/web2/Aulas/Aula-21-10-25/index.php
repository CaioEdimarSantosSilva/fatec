<?php
//Conexao com BD
require 'config.php';

//executa o select no banco de dados
$query = $pdo->query("SELECT * FROM avaliacao");

//trasforma os dados optidos no select em um array de arrays
$avaliacoes = $query->fetchAll();

?>


<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Avaliações</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <h1>Avaliações</h1>
    <a href="create.php">Nova Avaliação</a>
    <table>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Estrelas</th>
            <th>Comentários</th>
            <th>Ações</th>
        </tr>
        <?php foreach($avaliacoes as $a): ?>
            <tr>
                <td><?php echo $a['id']; ?></td>
                <td><?php echo $a['nome']; ?></td>
                <td><?php echo $a['estrelas']; ?></td>
                <td><?php echo $a['comentario']; ?></td>
                <td>
                    <a href="edit.php?id=<?php echo $a['id']; ?>">Editar</a>
                    |
                    <a href="delete.php?id=<?php echo $a['id']; ?>">Excluir</a>
                </td>
            </tr>
        <?php endforeach ?>
    </table> 
</body>
</html>