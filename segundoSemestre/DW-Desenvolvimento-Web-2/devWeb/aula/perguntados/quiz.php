<?php include 'data.php';
?>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="responsive.css">
    <title>Mitoloquiz</title>
</head>
<body>
    <form action="#" method="post">
        <h2>Mitoloquiz - Quiz de Mitologias</h2>
        <br><br>
        <label class="escolha_tema">Escolher uma Mitologia:</label>

        <select name="sltquiz" required>
            <option value="" disabled selected>Selecionar</option>
            <?php
            $temas = array_keys($quiz);
            sort($temas);

            foreach ($temas as $tema) {
                echo "<option value='$tema'>$tema</option>";
            }
            ?>
        </select>
        <br><br>
        <input class="botao" type="submit" value="Jogar">
    </form>
</body>
</html>
<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $tema = $_POST['sltquiz'] ?? $_POST['tema_quiz'] ?? '';
    $perguntas = $quiz[$tema];
    $pontuacao = 0;
    if (!isset($_POST['resposta_0'])) {
        echo "<form class='pergunta_form' action='#' method='post'> <div class='cabecario_perguntas'><h3>Mitologia $tema</h3> <h3>Pontuação:0/6</h3></div> <div class='grid_pergunta'>";
        foreach ($perguntas as $index => $pergunta) {
            echo "<div class='item_pergunta'><p>{$pergunta['pergunta']}</p>";
            foreach ($pergunta['opcoes'] as $opcao) {
                echo "<label><input type='radio' name='resposta_$index' value='$opcao' required> $opcao</label><br>";
            }
            echo "</div>";
        }
        echo "</div><br><input type='hidden' name='tema_quiz' value='$tema'><input class='botao' type='submit' value='Finalizar'>";
        echo "</form>";
    } else {
        foreach ($perguntas as $index => $pergunta) {
            $resposta_usuario = $_POST["resposta_$index"] ?? '';
            if ($resposta_usuario === $pergunta['resposta_correta']) {
                $pontuacao++;
            }
        }
        echo "<form class='pergunta_form'> <div class='cabecario_perguntas'><h3 class='tema_perguntas'>Mitologia $tema</h3> <h3>Pontuação:$pontuacao/" . count($perguntas) . "</h3></div> <div class='grid_pergunta'>";

        foreach ($perguntas as $index => $pergunta) {
            $resposta_usuario = $_POST["resposta_$index"] ?? '';
            echo "<div class='item_pergunta'><p><strong>{$pergunta['pergunta']}</strong></p>";
            foreach ($pergunta['opcoes'] as $opcao) {
                $classe = '';
                $checked = '';

                if ($resposta_usuario === $opcao) {
                    $checked = 'checked';
                }

                if ($opcao === $pergunta['resposta_correta']) {
                    $classe = 'resposta-correta';
                }

                echo "<label class='$classe'><input type='radio' name='resposta_$index' value='$opcao' $checked disabled> $opcao</label><br>";
            }
            echo "</div>";
        }
        echo "</div><br><input class='botao' type='button' value='Jogar Novamente' onclick='window.location.href = window.location.pathname;'>";
        echo "</form>";
    }
}
?>
