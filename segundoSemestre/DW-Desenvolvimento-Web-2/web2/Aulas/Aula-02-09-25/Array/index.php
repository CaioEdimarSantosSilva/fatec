<?php

//ARRAY PADRÃO
$nomes = ["Bolsonaro", "Lula", "Neymar"];
$nomes[] = "Datena"; //ADD UM NOVO ELEMENTO
$nomes[2] = "Felipe Neto"; //ALTERA UM ELEMENTO
sort($nomes); //DO MENOR PARA O MAIOR

echo "Segundo nome: " . $nomes[1];

//CONCATENAÇÃO FUNCIONA SEM OS "."
for ($c = 0; $c < count($nomes); $c++) {
    echo "<br>Nomes " . $c . ": " . $nomes[$c];
}

$idades = array(53, 62, 32, 10);
rsort($idades); //DO MAIOR PARA O MENOR

echo "<hr><br>Última idade: " . $idades[3];
echo "<br>Total de idades: " . count($idades);

foreach ($idades as $i) {
    echo "<br>$i";
}

//ARRAY ASSOSIATIVO
$aluno = [
    "Matricula" => 123,
    "Nome" => "Pablo Marçal",
    "Curso" => "Motivacional"
];

echo "<hr><br>Nome: " . $aluno["Nome"];
$aluno["periodo"] = "Vespertino";

unset($aluno['Matricula']); //EXCLUI O ELEMENTO PASSADO

sort($aluno); //ORDEM CRESCENTE (A-Z)

//EXIBE APENAS OS VALORES
foreach ($aluno as $a) {
    echo "<br>$a";
}

$unidades = [
    0 => "SV",
    1 => "PG"
];

//EXIBE A CHAVE E OS VALORES
foreach ($unidades as $chave => $valor) {
    echo "<br>$chave: $valor";
}
