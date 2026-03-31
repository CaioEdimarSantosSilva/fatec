<?php
require '../config.php';
session_start();

if (!isset($_SESSION['user']) || $_SESSION['user']['nivel'] !== 'admin') {
    header("Location: ../login.php");
    exit;
}

// Inserir novo usuário
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $nome = $_POST['nome'];
    $email = $_POST['email'];
    $senha = password_hash($_POST['senha'], PASSWORD_DEFAULT);
    $nivel = $_POST['nivel'];

    $pdo->prepare("INSERT INTO usuario (nome, email, senha, nivel) VALUES (?, ?, ?, ?)")
        ->execute([$nome, $email, $senha, $nivel]);
}

$usuarios = $pdo->query("SELECT * FROM usuario")->fetchAll(PDO::FETCH_ASSOC);
?>

<h2>Usuários</h2>
<form method="POST">
    <input name="nome" placeholder="Nome">
    <input name="email" placeholder="E-mail">
    <input name="senha" placeholder="Senha">
    <select name="nivel">
        <option value="usuario">Usuário</option>
        <option value="admin">Admin</option>
    </select>
    <button type="submit">Adicionar</button>
</form>

<table border="1">
<tr><th>ID</th><th>Nome</th><th>Email</th><th>Nível</th></tr>
<?php foreach ($usuarios as $u): ?>
<tr>
    <td><?= $u['id'] ?></td>
    <td><?= htmlspecialchars($u['nome']) ?></td>
    <td><?= htmlspecialchars($u['email']) ?></td>
    <td><?= $u['nivel'] ?></td>
</tr>
<?php endforeach; ?>
</table>
