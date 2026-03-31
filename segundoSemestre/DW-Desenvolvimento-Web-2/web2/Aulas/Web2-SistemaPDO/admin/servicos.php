<?php
require '../config.php';
session_start();
if (!isset($_SESSION['user']) || $_SESSION['user']['nivel'] !== 'admin') exit;

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $pdo->prepare("INSERT INTO filme (titulo, descricao, icone) VALUES (?, ?, ?)")
        ->execute([$_POST['titulo'], $_POST['descricao'], $_POST['icone']]);
}

$filmes = $pdo->query("SELECT * FROM filme")->fetchAll(PDO::FETCH_ASSOC);
?>

<h2>Filmes/Serviços</h2>
<form method="POST">
    <input name="titulo" placeholder="Título">
    <textarea name="descricao" placeholder="Descrição"></textarea>
    <input name="icone" placeholder="Ícone (ex: 🎬)">
    <button type="submit">Cadastrar</button>
</form>

<table border="1">
<tr><th>ID</th><th>Título</th><th>Descrição</th><th>Ícone</th></tr>
<?php foreach ($filmes as $f): ?>
<tr>
    <td><?= $f['id'] ?></td>
    <td><?= htmlspecialchars($f['titulo']) ?></td>
    <td><?= htmlspecialchars($f['descricao']) ?></td>
    <td><?= htmlspecialchars($f['icone']) ?></td>
</tr>
<?php endforeach; ?>
</table>
