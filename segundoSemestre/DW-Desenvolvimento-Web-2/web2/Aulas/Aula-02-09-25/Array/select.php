<?php
$usuarios = [
    "0" => "Aluno",
    "1" => "Professor"
];

$cursos = ["ADS", "COMEX", "DSM", "GE", "PQ"];

$periodos = array("Matutino", "Vespertino", "Noturno");

    $fotos = array("https://uploads.jovemnerd.com.br/wp-content/uploads/2024/03/tai_lung_kung_fu_panda_4__ngu7vx.jpg", "https://www.epipoca.com.br/wp-content/uploads/2024/02/shifu-kung-fu-panda.jpg", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNf5H98R1dIIDmg49WytnVDnf_CFfm_uj-4g&s");
?>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Select</title>
</head>

<body>
    <form action="#" method="post">
        <label><strong>Usuário:</strong></label>
        <?php
        foreach ($usuarios as $user) {
            echo "<input type='radio' name='usuario' value ='$user'>$user";
        }
        ?>

        <br><br>
        <label></label><strong>Curso:</strong></label>
        <select name="sltcurso">
            <?php
            foreach ($cursos as $curso) {
                echo "<option value = '$curso'>$curso</option>";
            }
            ?>
        </select>
        
        <br><br>
        <label><strong>Período:</strong></label>
        <input type="submit">
        <hr>

        <h2>Galeria</h2>

        <?php
            foreach ($fotos as $f) {
                echo "<img src='$f' width='20%'>";
            }
        ?>
        <br><br>
        <?php
        foreach ($periodos as $p) {
            echo "<input type='checkbox' name='periodos[]' value ='$p'>$p";
        }
        ?>

    </form>
    <?php
    if ($_SERVER["REQUEST_METHOD"] == "POST") {

        $usuario = $_POST["usuario"];
        $curso = $_POST["sltcurso"];

        $periodos = $_POST["periodos"];

       

        echo "<br>$usuario matriculado em $curso";

        echo "<h3>Períodos: </h3>";
        echo "<ul>";
         foreach ($periodos as $p) {
            echo "<li>$p</li>";
        }
        echo "</ul>";
    }
    ?>
</body>

</html>