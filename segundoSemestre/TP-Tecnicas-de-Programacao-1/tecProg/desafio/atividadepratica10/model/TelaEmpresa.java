package model;

import javax.swing.*;
import java.awt.*;

public class TelaEmpresa extends JFrame {

    private Empresa empresa = new Empresa();

    private JTextField txtNomeCliente, txtEmailCliente;
    private JTextField txtNomeFunc, txtCargoFunc, txtSalarioFunc;
    private JTextArea areaExibicao;

    public TelaEmpresa() {
        setTitle("Sistema da Empresa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLayout(new BorderLayout());
        setResizable(false);

        // Painel principal (com BoxLayout)
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(25, 60, 25, 60));
        painelCentral.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelCentral.setBackground(new Color(245, 245, 245)); // tom leve de cinza

        JLabel titulo = new JLabel("Gerenciador de Empresa");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelCentral.add(titulo);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 20)));

        // Cliente
        painelCentral.add(label("Nome do Cliente:"));
        txtNomeCliente = campoTexto();
        painelCentral.add(txtNomeCliente);

        painelCentral.add(label("E-mail do Cliente:"));
        txtEmailCliente = campoTexto();
        painelCentral.add(txtEmailCliente);

        JButton btnAddCliente = botao("Adicionar Cliente");
        painelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        painelCentral.add(btnAddCliente);

        painelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        painelCentral.add(new JSeparator());
        painelCentral.add(Box.createRigidArea(new Dimension(0, 20)));

        // Funcionário
        painelCentral.add(label("Nome do Funcionário:"));
        txtNomeFunc = campoTexto();
        painelCentral.add(txtNomeFunc);

        painelCentral.add(label("Cargo:"));
        txtCargoFunc = campoTexto();
        painelCentral.add(txtCargoFunc);

        painelCentral.add(label("Salário:"));
        txtSalarioFunc = campoTexto();
        painelCentral.add(txtSalarioFunc);

        JButton btnAddFunc = botao("Adicionar Funcionário");
        painelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        painelCentral.add(btnAddFunc);

        painelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        painelCentral.add(new JSeparator());
        painelCentral.add(Box.createRigidArea(new Dimension(0, 20)));

        // Folha e média
        JButton btnFolha = botao("Calcular Folha Salarial");
        JButton btnMedia = botao("Calcular Média Salarial");
        painelCentral.add(btnFolha);
        painelCentral.add(Box.createRigidArea(new Dimension(0, 5)));
        painelCentral.add(btnMedia);

        painelCentral.add(Box.createRigidArea(new Dimension(0, 20)));

        // Área de exibição
        areaExibicao = new JTextArea(10, 30);
        areaExibicao.setEditable(false);
        areaExibicao.setLineWrap(true);
        areaExibicao.setWrapStyleWord(true);
        areaExibicao.setFont(new Font("Consolas", Font.PLAIN, 13));
        areaExibicao.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JScrollPane scrollArea = new JScrollPane(areaExibicao);
        scrollArea.setPreferredSize(new Dimension(400, 180));
        painelCentral.add(scrollArea);

        // Adiciona um JScrollPane para o painel inteiro
        JScrollPane scrollTela = new JScrollPane(painelCentral);
        add(scrollTela, BorderLayout.CENTER);

        // Centraliza a janela
        setLocationRelativeTo(null);

        // Ações
        btnAddCliente.addActionListener(e -> {
            String nome = txtNomeCliente.getText();
            String email = txtEmailCliente.getText();

            if (nome.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos do cliente!");
                return;
            }

            if (empresa.adicionarCliente(nome, email)) {
                areaExibicao.append("Cliente adicionado: " + nome + " (" + email + ")\n");
                txtNomeCliente.setText("");
                txtEmailCliente.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Limite de clientes atingido!");
            }
        });

        btnAddFunc.addActionListener(e -> {
            try {
                String nome = txtNomeFunc.getText();
                String cargo = txtCargoFunc.getText();
                double salario = Double.parseDouble(txtSalarioFunc.getText());

                if (empresa.adicionarFuncionario(nome, cargo, salario)) {
                    areaExibicao.append("Funcionário adicionado: " + nome + " (" + cargo + ") - R$" + salario + "\n");
                    txtNomeFunc.setText("");
                    txtCargoFunc.setText("");
                    txtSalarioFunc.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Limite de funcionários atingido!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Salário inválido!");
            }
        });

        btnFolha.addActionListener(e ->
                areaExibicao.append("Folha Salarial Total: R$" + empresa.calcularFolhaSalarial() + "\n")
        );

        btnMedia.addActionListener(e ->
                areaExibicao.append("Média Salarial: R$" + empresa.calcularMediaSalarial() + "\n")
        );
    }

    // ---------- Métodos auxiliares para estilo ----------

    private JLabel label(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        return lbl;
    }

    private JTextField campoTexto() {
        JTextField txt = new JTextField(15);
        txt.setMaximumSize(new Dimension(200, 28));
        txt.setAlignmentX(Component.CENTER_ALIGNMENT);
        return txt;
    }

    private JButton botao(String texto) {
        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(new Color(220, 230, 250));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 200)));
        return btn;
    }
}