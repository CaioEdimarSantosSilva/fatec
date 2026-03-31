<?php
require_once 'config.php';

$demos = [
    ['Administrador', 'admin@estante.com', 'admin123', 'admin'],
    ['Usuário Padrão', 'usuario@estante.com', 'user123', 'usuario'],
];

try {
    $pdo->beginTransaction();

    $stmt = $pdo->prepare("
        INSERT INTO usuario (nome, email, senha, nivel)
        VALUES (:nome, :email, :senha, :nivel)
        ON DUPLICATE KEY UPDATE 
            nome = VALUES(nome),
            senha = VALUES(senha),
            nivel = VALUES(nivel)
    ");

    foreach ($demos as $u) {
        [$nome, $email, $plain, $nivel] = $u;
        $hash = password_hash($plain, PASSWORD_DEFAULT);
        $stmt->execute([
            ':nome' => $nome,
            ':email' => $email,
            ':senha' => $hash,
            ':nivel' => $nivel
        ]);
        echo "Inserido/Atualizado: $email (hash length: " . strlen($hash) . ")<br>";
    }

    $pdo->commit();
    echo "<br><strong>Seed completo.</strong>";
} catch (Exception $e) {
    $pdo->rollBack();
    echo "Erro: " . $e->getMessage();
}
