<?php
require '../config.php';
session_start();
if (!isset($_SESSION['user']) || $_SESSION['user']['nivel'] !== 'admin') exit;

if (isset($_GET['del'])) {
    $pdo->prepare("DELETE FROM contato WHERE id=?")->execute([$_GET['del']]);
}

$contatos = $pdo->query("SELECT * FROM contato ORDER BY data_envio DESC")->fetchAll(PDO::FETCH_ASSOC);
?>

<h2>Mensagens Recebidas</h2>
<table border="1">
<tr><th>Nome</th><th>Email</th><th>Mensagem</th><th>Data</th><th>Ações</th></tr>
<?php foreach ($contatos as $c): ?>
<tr>
    <td><?= htmlspecialchars($c['nome']) ?></td>
    <td><?= htmlspecialchars($c['email']) ?></td>
    <td><?= htmlspecialchars($c['mensagem']) ?></td>
    <td><?= $c['data_envio'] ?></td>
    <td><a href="?del=<?= $c['id'] ?>">Excluir</a></td>
</tr>
<?php endforeach; ?>
</table>
