<?php

//Criar ou abre o arquivo
$arquivo = fopen("dados.txt","a");

//Escrever no arquivo
// fwrite($arquivo,"primeira linha.\n");

//Fechar o arquivo
fclose($arquivo);



//Abre o arquivo
$arquivo = fopen("dados.txt","r");
//Lê o arquivo
// $conteudo = fread($arquivo, filesize("dados.txt"));
// echo nl2br($conteudo);

while (($linha = fgets($arquivo)) != false) {
    echo "<br>" . $linha;
    $array = explode(",", $linha);
    print_r($array);
}

fclose($arquivo);

?>