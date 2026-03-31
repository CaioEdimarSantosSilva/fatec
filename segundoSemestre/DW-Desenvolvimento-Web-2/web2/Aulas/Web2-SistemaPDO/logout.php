<?php
// logout.php - Encerrar sessão do usuário
session_start();
session_destroy();
header('Location: index.php');
exit();
?>