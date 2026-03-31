<?php

interface metodos{
    public function valorHE();
    public function saudacao();
}


//CLASSE ABSTRATA
abstract class Funcionario implements metodos{
    //ATRIBUTO
    protected $nome;
    protected $salariobase;

    protected static $totalFunc = 0;

    //CONSTRUTOR
    public function __construct($nm, $salb){
        $this->nome = $nm;
        $this->salariobase = $salb;
        self::$totalFunc++;
    }

    //METODOS
    public static function mostraTotalFunc(){
        return self::$totalFunc;
    }
    public function mostraFuncionario(){
        echo "Nome: " . $this->nome . "<br>Salário base: R$" . $this->salariobase;
    }
    public function exibirFuncao(){
        echo "<h1>FUNCIONARIO</h1>";
    }
    // public function setNome($nm){
    //     $this->nome = $nm;
    // }
    // public function getNome(){
    //     return $this->nome;
    // }

    //METODO ABSTRATO
    abstract public function calcSalario();

}

//HERANÇA
class Desenvolvedor extends Funcionario{
    private $horaExtra;
    public function __construct($nm, $salb, $he){
        parent::__construct($nm,$salb);
        $this->horaExtra = $he;
    }
    public function calcSalario(){
        return $this->salariobase + ($this->horaExtra * 50 );
    }
    public function exibirFuncao(){
        echo "<h1>DESENVOLVEDOR</h1>";
    }

    //FUNÇÕES DA INTERFACE
     public function valorHE(){
        return $this->horaExtra;
     }
     public function saudacao(){
        return "Seja bem-vindo!";
     }
}

//OBJETO
$d1 = new Desenvolvedor("Clebersons",1.99, 5);
$d2 = new Desenvolvedor("Clebersons",1.99, 5);
$d3 = new Desenvolvedor("Clebersons",1.99, 5);

$d1->exibirFuncao();
$d1->mostraFuncionario();
echo "<br>Salário final: R$" . $d1->calcSalario();
echo "<br>Valor da HE: R$" . $d1->valorHE();
echo "<br>Total de funcionários: " . Funcionario::mostraTotalFunc();


// $f1 = new Funcionario("Clebersons",1.99);
// $f1->setNome("Xamis");
// echo "Funcionario " . $f1->getNome();
// $f1->mostraFuncionario();
?>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Classe em PHP</title>

<style>
    body{
        background-color: rgba(66, 66, 66, 1);
        color: white;
        text-align: center;
        font-family: cursive;
        font-size: 30px;

    }
    h1{
        color: goldenrod;
    }
</style>
</head>
<body>
    
</body>
</html>