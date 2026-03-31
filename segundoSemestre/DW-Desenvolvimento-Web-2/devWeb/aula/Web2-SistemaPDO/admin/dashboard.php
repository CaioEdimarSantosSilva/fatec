<?php
require '../config.php';

if (!isset($_SESSION['usuario_id']) || $_SESSION['usuario_nivel'] !== 'admin') {
    header("Location: ../login.php");
    exit;
}

if (isset($_GET['delete']) && isset($_GET['table'])) {
    $tabela = $_GET['table'];
    $id = intval($_GET['delete']);

    $permitidas = ['filme', 'avaliacao', 'contato', 'pedido', 'usuario'];
    if (in_array($tabela, $permitidas)) {
        $pdo->prepare("DELETE FROM $tabela WHERE id = ?")->execute([$id]);
        header("Location: dashboard.php");
        exit;
    }
}

if (isset($_GET['marcar_lido'])) {
    $id = intval($_GET['marcar_lido']);
    $pdo->prepare("UPDATE contato SET lido = 'sim' WHERE id = ?")->execute([$id]);
    header("Location: dashboard.php");
    exit;
}

$filmes = $pdo->query("SELECT * FROM filme ORDER BY id DESC")->fetchAll(PDO::FETCH_ASSOC);
$usuarios = $pdo->query("SELECT * FROM usuario ORDER BY id DESC")->fetchAll(PDO::FETCH_ASSOC);
$avaliacoes = $pdo->query("SELECT a.*, u.nome AS usuario, f.titulo AS filme 
                           FROM avaliacao a 
                           LEFT JOIN usuario u ON a.id_usuario = u.id 
                           LEFT JOIN filme f ON a.id_filme = f.id 
                           ORDER BY a.id DESC")->fetchAll(PDO::FETCH_ASSOC);
$contatos = $pdo->query("SELECT * FROM contato ORDER BY id DESC")->fetchAll(PDO::FETCH_ASSOC);
$pedidos = $pdo->query("SELECT p.*, u.nome AS usuario, f.titulo AS filme 
                        FROM pedido p 
                        LEFT JOIN usuario u ON p.id_usuario = u.id 
                        LEFT JOIN filme f ON p.id_filme = f.id 
                        ORDER BY p.id DESC")->fetchAll(PDO::FETCH_ASSOC);

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['salvar_filme'])) {
    $titulo = $_POST['titulo'];
    $descricao = $_POST['descricao'];
    $genero = $_POST['genero'];
    $preco = $_POST['preco'];
    $capa = $_POST['capa'];
    $disponivel = $_POST['disponivel'];

    if (!empty($_POST['id'])) {
        $stmt = $pdo->prepare("UPDATE filme SET titulo=?, descricao=?, genero=?, preco=?, capa=?, disponivel=? WHERE id=?");
        $stmt->execute([$titulo, $descricao, $genero, $preco, $capa, $disponivel, $_POST['id']]);
    } else {
        $stmt = $pdo->prepare("INSERT INTO filme (titulo, descricao, genero, preco, capa, disponivel) VALUES (?, ?, ?, ?, ?, ?)");
        $stmt->execute([$titulo, $descricao, $genero, $preco, $capa, $disponivel]);
    }

    header("Location: dashboard.php");
    exit;
}

$filme_editar = null;
if (isset($_GET['editar_filme'])) {
    $stmt = $pdo->prepare("SELECT * FROM filme WHERE id = ?");
    $stmt->execute([intval($_GET['editar_filme'])]);
    $filme_editar = $stmt->fetch(PDO::FETCH_ASSOC);
}
?>

<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <title>Painel Admin - EstanteFilmes</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #EDEDED;
            color: #01337D;
            line-height: 1.6;
            min-height: 100vh;
        }

        header {
            background: linear-gradient(135deg, #01337D 0%, #3685FF 100%);
            color: #EDEDED;
            padding: 1.5rem 2rem;
            box-shadow: 0 2px 10px rgba(1, 51, 125, 0.3);
            position: sticky;
            top: 0;
            z-index: 1000;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        header h1 {
            color: #EDEDED;
            font-size: 1.8rem;
            font-weight: 600;
        }

        .logout {
            background: #FFB521;
            color: #01337D;
            padding: 0.6rem 1.5rem;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s ease;
            border: 2px solid transparent;
        }

        .logout:hover {
            background: #EDEDED;
            border-color: #FFB521;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(255, 181, 33, 0.3);
        }

        section {
            max-width: 1400px;
            margin: 2rem auto;
            padding: 2rem;
            background: white;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(1, 51, 125, 0.1);
        }

        section h2 {
            color: #01337D;
            font-size: 1.6rem;
            margin-bottom: 1.5rem;
            padding-bottom: 0.5rem;
            border-bottom: 3px solid #FFB521;
            display: inline-block;
        }

        .toggle-form-btn {
            background: #3685FF;
            color: white;
            padding: 0.8rem 1.5rem;
            border: none;
            border-radius: 8px;
            font-size: 0.95rem;
            font-weight: 600;
            cursor: pointer;
            margin-bottom: 1rem;
            transition: all 0.3s ease;
            display: inline-block;
        }

        .toggle-form-btn:hover {
            background: #01337D;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(54, 133, 255, 0.3);
        }

        .form-container {
            max-height: 0;
            overflow: hidden;
            transition: max-height 0.4s ease-out, opacity 0.3s ease-out;
            opacity: 0;
        }

        .form-container.active {
            max-height: 1000px;
            opacity: 1;
            transition: max-height 0.5s ease-in, opacity 0.3s ease-in;
        }

        form {
            background: #DCEAFF;
            padding: 1.2rem;
            border-radius: 10px;
            margin-bottom: 2rem;
            border: 2px solid #3685FF;
            max-width: 900px;
        }

        form label {
            display: inline-block;
            color: #01337D;
            font-weight: 600;
            margin-top: 0.5rem;
            margin-bottom: 0.2rem;
            font-size: 0.9rem;
        }

        form input[type="text"],
        form input[type="number"],
        form input[type="email"],
        form textarea,
        form select {
            width: 100%;
            padding: 0.6rem;
            margin: 0.2rem 0 0.5rem 0;
            border: 2px solid #3685FF;
            border-radius: 6px;
            font-size: 0.95rem;
            background: white;
            color: #01337D;
            transition: all 0.3s ease;
        }

        form input:focus,
        form textarea:focus,
        form select:focus {
            outline: none;
            border-color: #FFB521;
            box-shadow: 0 0 0 3px rgba(255, 181, 33, 0.2);
        }

        form textarea {
            min-height: 80px;
            resize: vertical;
        }

        form button[type="submit"] {
            background: #FFB521;
            color: #01337D;
            padding: 0.7rem 1.8rem;
            border: none;
            border-radius: 8px;
            font-size: 0.95rem;
            font-weight: 600;
            cursor: pointer;
            margin-top: 0.5rem;
            transition: all 0.3s ease;
        }

        form button[type="submit"]:hover {
            background: #01337D;
            color: #EDEDED;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(1, 51, 125, 0.3);
        }

        table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0;
            margin-bottom: 2rem;
            background: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 2px 8px rgba(1, 51, 125, 0.1);
        }

        th {

            background-color: #01337D;
            border: 1px solid #EDEDED;
            color: #EDEDED;
            padding: 1rem;
            text-align: left;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 0.9rem;
            letter-spacing: 0.5px;
        }

        td {
            padding: 1rem;
            border-bottom: 1px solid #DCEAFF;
            color: #01337D;
        }

        tr:hover td {
            background: #DCEAFF;
            transition: background 0.2s ease;
        }

        tr:last-child td {
            border-bottom: none;
        }

        table a,
        table button {
            display: inline-block;
            text-decoration: none;
            color: white;
            padding: 0.4rem 1rem;
            border-radius: 6px;
            font-size: 0.9rem;
            margin-right: 0.5rem;
            margin-bottom: 0.3rem;
            transition: all 0.3s ease;
            border: none;
            cursor: pointer;
        }

        table a:first-of-type {
            background: #3685FF;
        }

        table a:first-of-type:hover {
            background: #01337D;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(54, 133, 255, 0.3);
        }

        table a:last-of-type {
            background: #FFB521;
            color: #01337D;
        }

        table a:last-of-type:hover {
            background: #01337D;
            color: #EDEDED;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(255, 181, 33, 0.3);
        }

        @media (max-width: 1200px) {
            section {
                margin: 1rem;
                padding: 1.5rem;
            }

            table {
                font-size: 0.9rem;
            }
        }

        @media (max-width: 768px) {
            header {
                flex-direction: column;
                text-align: center;
                gap: 1rem;
            }

            header h1 {
                font-size: 1.4rem;
            }

            section {
                padding: 1rem;
            }

            table {
                display: block;
                overflow-x: auto;
                white-space: nowrap;
            }

            form input,
            form textarea,
            form select {
                width: 100%;
            }
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(20px);
            }

            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        section {
            animation: fadeIn 0.5s ease-out;
        }

        input[type="hidden"] {
            display: none;
        }

        select {
            cursor: pointer;
            background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' viewBox='0 0 12 12'%3E%3Cpath fill='%2301337D' d='M6 9L1 4h10z'/%3E%3C/svg%3E");
            background-repeat: no-repeat;
            background-position: right 0.8rem center;
            padding-right: 2.5rem;
            appearance: none;
        }
    </style>
</head>

<body>

    <header>
        <h1>Painel Administrativo</h1>
        <a href="../logout.php" class="logout">Sair</a>
    </header>

    <section>
        <h2>Gerenciar Filmes</h2>

        <button id="toggleFormBtn" class="toggle-form-btn">
            ▼ Adicionar/Editar Filme
        </button>

        <div id="formContainer" class="form-container">
            <form method="POST">
                <input type="hidden" name="id" value="<?= $filme_editar['id'] ?? '' ?>">
                <label>Título:</label>
                <input type="text" name="titulo" required value="<?= htmlspecialchars($filme_editar['titulo'] ?? '') ?>">
                <br>
                <label>Descrição:</label>
                <textarea name="descricao" required><?= htmlspecialchars($filme_editar['descricao'] ?? '') ?></textarea>
                <br>
                <label>Gênero:</label>
                <input type="text" name="genero" value="<?= htmlspecialchars($filme_editar['genero'] ?? '') ?>">
                <br>
                <label>Preço:</label>
                <input type="number" step="0.01" name="preco" value="<?= htmlspecialchars($filme_editar['preco'] ?? '') ?>">
                <br>
                <label>URL da Capa:</label>
                <input type="text" name="capa" value="<?= htmlspecialchars($filme_editar['capa'] ?? '') ?>">
                <br>
                <label>Disponível:</label>
                <select name="disponivel">
                    <option value="sim" <?= isset($filme_editar) && $filme_editar['disponivel'] == 'sim' ? 'selected' : '' ?>>Sim</option>
                    <option value="nao" <?= isset($filme_editar) && $filme_editar['disponivel'] == 'nao' ? 'selected' : '' ?>>Não</option>
                </select>
                <button type="submit" name="salvar_filme">Salvar Filme</button>
            </form>
        </div>

        <table>
            <tr>
                <th>ID</th>
                <th>Título</th>
                <th>Gênero</th>
                <th>Preço</th>
                <th>Disponível</th>
                <th>Ações</th>
            </tr>
            <?php foreach ($filmes as $f): ?>
                <tr>
                    <td><?= $f['id'] ?></td>
                    <td><?= htmlspecialchars($f['titulo']) ?></td>
                    <td><?= htmlspecialchars($f['genero']) ?></td>
                    <td>R$ <?= number_format($f['preco'], 2, ',', '.') ?></td>
                    <td><?= $f['disponivel'] ?></td>
                    <td>
                        <a href="?editar_filme=<?= $f['id'] ?>">Editar</a>
                        <a href="?delete=<?= $f['id'] ?>&table=filme" onclick="return confirm('Apagar este filme?')">Excluir</a>
                    </td>
                </tr>
            <?php endforeach; ?>
        </table>
    </section>

    <section>
        <h2>Avaliações</h2>
        <table>
            <tr>
                <th>ID</th>
                <th>Usuário</th>
                <th>Filme</th>
                <th>Estrelas</th>
                <th>Comentário</th>
                <th>Ações</th>
            </tr>
            <?php foreach ($avaliacoes as $a): ?>
                <tr>
                    <td><?= $a['id'] ?></td>
                    <td><?= htmlspecialchars($a['usuario']) ?></td>
                    <td><?= htmlspecialchars($a['filme']) ?></td>
                    <td><?= $a['estrelas'] ?></td>
                    <td><?= htmlspecialchars($a['comentario']) ?></td>
                    <td><a href="?delete=<?= $a['id'] ?>&table=avaliacao" onclick="return confirm('Apagar esta avaliação?')">Excluir</a></td>
                </tr>
            <?php endforeach; ?>
        </table>
    </section>

    <section>
        <h2>Contatos</h2>
        <table>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Email</th>
                <th>Mensagem</th>
                <th>Lido</th>
                <th>Ações</th>
            </tr>
            <?php foreach ($contatos as $c): ?>
                <tr>
                    <td><?= $c['id'] ?></td>
                    <td><?= htmlspecialchars($c['nome']) ?></td>
                    <td><?= htmlspecialchars($c['email']) ?></td>
                    <td><?= htmlspecialchars($c['mensagem']) ?></td>
                    <td><?= $c['lido'] ?></td>
                    <td>
                        <?php if ($c['lido'] == 'nao'): ?>
                            <a href="?marcar_lido=<?= $c['id'] ?>">Marcar como lido</a>
                        <?php endif; ?>
                        <a href="?delete=<?= $c['id'] ?>&table=contato" onclick="return confirm('Apagar este contato?')">Excluir</a>
                    </td>
                </tr>
            <?php endforeach; ?>
        </table>
    </section>

    <section>
        <h2>Pedidos</h2>
        <table>
            <tr>
                <th>ID</th>
                <th>Usuário</th>
                <th>Filme</th>
                <th>Status</th>
                <th>Data Aluguel</th>
                <th>Ações</th>
            </tr>
            <?php foreach ($pedidos as $p): ?>
                <tr>
                    <td><?= $p['id'] ?></td>
                    <td><?= htmlspecialchars($p['usuario']) ?></td>
                    <td><?= htmlspecialchars($p['filme']) ?></td>
                    <td><?= $p['status'] ?></td>
                    <td><?= $p['data_aluguel'] ?></td>
                    <td><a href="?delete=<?= $p['id'] ?>&table=pedido" onclick="return confirm('Apagar este pedido?')">Excluir</a></td>
                </tr>
            <?php endforeach; ?>
        </table>
    </section>

    <section>
        <h2>Usuários</h2>
        <table>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Email</th>
                <th>Nível</th>
                <th>Ações</th>
            </tr>
            <?php foreach ($usuarios as $u): ?>
                <tr>
                    <td><?= $u['id'] ?></td>
                    <td><?= htmlspecialchars($u['nome']) ?></td>
                    <td><?= htmlspecialchars($u['email']) ?></td>
                    <td><?= $u['nivel'] ?></td>
                    <td><a href="?delete=<?= $u['id'] ?>&table=usuario" onclick="return confirm('Apagar este usuário?')">Excluir</a></td>
                </tr>
            <?php endforeach; ?>
        </table>
    </section>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const toggleBtn = document.getElementById('toggleFormBtn');
            const formContainer = document.getElementById('formContainer');
            <?php if ($filme_editar): ?>
                formContainer.classList.add('active');
                toggleBtn.innerHTML = '▲ Fechar Formulário';
            <?php endif; ?>

            toggleBtn.addEventListener('click', function() {
                formContainer.classList.toggle('active');

                if (formContainer.classList.contains('active')) {
                    toggleBtn.innerHTML = '▲ Fechar Formulário';
                } else {
                    toggleBtn.innerHTML = '▼ Adicionar/Editar Filme';
                }
            });
        });
    </script>

</body>

</html>