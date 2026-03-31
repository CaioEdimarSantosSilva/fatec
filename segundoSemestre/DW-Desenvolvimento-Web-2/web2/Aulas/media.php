<?php
    $aluno = "Bolsonaro";
    $prova = 5;
    $trabalho = 8.5;

    $media = ($prova*0.6) + ($trabalho * 0.4);

    echo "<h1>Boletim</h1>";
    echo "<b>ALUNO: </b>" . $aluno ."<br>";
    echo "<b>PROVA: </b>" . $prova . "<br>";
    echo "<b>TRABALHO: </b>" . $trabalho . "<br>";
    echo "<b>MEDIA: </b>" . $media ."<br>";
    
    if($media >= 6){
        echo "<b>Status:</b> APROVADO <br>";
    }
    else{
        echo "<b>Status:</b> REPROVADO <br>" ;
    }

?>