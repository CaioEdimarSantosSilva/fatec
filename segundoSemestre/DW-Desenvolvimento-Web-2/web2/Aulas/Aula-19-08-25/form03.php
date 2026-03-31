<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Form 03</title>
</head>

<body>
    <form action="#" method="post">
        <label>Aluno:</label>
        <input type="text" name="txtaluno">
        <br>

        <label>Curso:</label>
        <input type="radio" value="ADS" name="rdcurso">ADS
        <input type="radio" value="DSM" name="rdcurso">DSM
        <br>

        <label>Período:</label>
        <select name="sltperiodo">
            <option value="Vespertino">Vespertino</option>
            <option value="Noturno">Noturno</option>
        </select>
        <br>

        <label>Ciclos Concluídos:</label>  
        <input type="checkbox" value="1" name="ciclos[]">1°
        <input type="checkbox" value="2" name="ciclos[]">2°
        <input type="checkbox" value="3" name="ciclos[]">3°
        <input type="checkbox" value="4" name="ciclos[]">4°
        <input type="checkbox" value="5" name="ciclos[]">5°
        <input type="checkbox" value="6" name="ciclos[]">6°
        <br>

        <input type="submit" value="Enviar">
        <br><br><br>
    </form>
</body>

</html>

<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $nome = $_POST['txtaluno'];
    $curso = $_POST['rdcurso'];
    $periodo = $_POST['sltperiodo'];
    $ciclos = $_POST['ciclos'];

    
    echo "<br>Aluno: " . $nome;
    echo "<br>Curso: " . $curso;
    echo "<br>Periodo: " . $periodo;
    echo "<br>Ciclos Concluidos: ";
    
    foreach($ciclos as $ciclo){
        echo $ciclo . " ";
    }
}

?>