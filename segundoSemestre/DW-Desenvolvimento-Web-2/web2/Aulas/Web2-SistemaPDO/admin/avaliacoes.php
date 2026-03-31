<?php
require '../config.php';
session_start();
if (!isset($_SESSION['user']) || $_SESSION['user']['nivel'] !== 'admin') exit;

if (isset($_GET['del'])) {
    $pdo->prepare("DELETE FROM avaliacao WHERE id=?")->execute([$_GET['del']]);
}

$avaliacoes = $pdo->query("SELECT * FROM avaliacao")->fetchAll(PDO::FETCH_ASSOC);
?>

<h2>Avaliações</h2>
<table border="1">
<tr><th>ID</th><th>Nome</th><th>Estrelas</th><th>Comentário</th><th>Ações</th></tr>
<?php foreach ($avaliacoes as $a): ?>
<tr>
    <td><?= $a['id'] ?></td>
    <td><?= htmlspecialchars($a['nome']) ?></td>
    <td><?= $a['estrelas'] ?></td>
    <td><?= htmlspecialchars($a['comentario']) ?></td>
    <td><a href="?del=<?= $a['id'] ?>">Excluir</a></td>
</tr>
<?php endforeach; ?>
</table>
