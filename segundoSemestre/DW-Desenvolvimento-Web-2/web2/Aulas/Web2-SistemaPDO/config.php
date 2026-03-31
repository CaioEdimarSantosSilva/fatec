<?php
// config.php - Configuração de conexão com banco de dados

// Configurações do banco
define('DB_HOST', 'localhost');
define('DB_NAME', 'estantefilmes');
define('DB_USER', 'root');
define('DB_PASS', '');

// Criar conexão
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
} catch(PDOException $e) {
    die("Erro na conexão: " . $e->getMessage());
}

// Iniciar sessão
if (session_status() === PHP_SESSION_NONE) {
    session_start();
}

// Função para verificar se usuário está logado
function verificarLogin() {
    if (!isset($_SESSION['usuario_id'])) {
        header('Location: ../login.php');
        exit();
    }
}

// Função para verificar se é admin
function verificarAdmin() {
    if (!isset($_SESSION['usuario_id']) || $_SESSION['usuario_nivel'] !== 'admin') {
        header('Location: ../index.php');
        exit();
    }
}

// Função para limpar dados
function limparDados($dados) {
    return htmlspecialchars(strip_tags(trim($dados)));
}
?>