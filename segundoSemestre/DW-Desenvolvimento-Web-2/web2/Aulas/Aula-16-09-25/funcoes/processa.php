<?php

require("funcoes.php");

if ($_SERVER["REQUEST_METHOD"] === "POST") {

    if (!empty($_POST["txtnome"]) && !empty($_POST["txtn1"]) && !empty($_POST["txtn2"])) {

        $nm = $_POST["txtnome"];
        $n1 = $_POST["txtn1"];
        $nm = $_POST["txtn2"];

        saudacao($nm);

        echo "<br>Sua média é ".media($n1,$n2);

    } else {
        erro();
    }

}

?>