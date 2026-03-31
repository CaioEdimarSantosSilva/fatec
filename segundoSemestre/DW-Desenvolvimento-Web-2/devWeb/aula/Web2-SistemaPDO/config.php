<?php

define('DB_HOST', 'localhost');
define('DB_NAME', 'estantefilmes');
define('DB_USER', 'root');
define('DB_PASS', '');

try {
    $pdo = new PDO(
        "mysql:host=" . DB_HOST . ";dbname=" . DB_NAME . ";charset=utf8mb4",
        DB_USER,
        DB_PASS,
        [
            PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
            PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
            PDO::ATTR_EMULATE_PREPARES => false
        ]
    );
} catch (PDOException $e) {
    die("Erro na conexão: " . $e->getMessage());
}

if (session_status() === PHP_SESSION_NONE) {
    session_start();
}

function verificarLogin()
{
    if (!isset($_SESSION['usuario_id'])) {
        header('Location: ../login.php');
        exit();
    }
}

function verificarAdmin()
{
    if (!isset($_SESSION['usuario_id']) || $_SESSION['usuario_nivel'] !== 'admin') {
        header('Location: ../index.php');
        exit();
    }
}

function limparDados($dados)
{
    return htmlspecialchars(strip_tags(trim($dados)));
}
