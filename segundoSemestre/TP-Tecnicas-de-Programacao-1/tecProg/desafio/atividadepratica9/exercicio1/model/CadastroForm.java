package model;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class CadastroForm {
    private JFrame frame;
    private JTextField nomeField;
    private JSpinner idadeField;
    private JRadioButton masculino, feminino;
    private ButtonGroup grupoSexo;
    private JButton submitButton;
    private JLabel resumoLabel;

    public CadastroForm() {
        frame = new JFrame("Cadastro de Cliente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new GridLayout(6, 2));

        frame.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        frame.add(nomeField);

        frame.add(new JLabel("Idade:"));
        idadeField = new JSpinner(new SpinnerNumberModel(18, 0, 120, 1));
        frame.add(idadeField);

        frame.add(new JLabel("Sexo:"));
        masculino = new JRadioButton("Masculino");
        feminino = new JRadioButton("Feminino");
        grupoSexo = new ButtonGroup();
        grupoSexo.add(masculino);
        grupoSexo.add(feminino);

        JPanel sexoPanel = new JPanel();
        sexoPanel.add(masculino);
        sexoPanel.add(feminino);
        frame.add(sexoPanel);

        submitButton = new JButton("Cadastrar");
        frame.add(submitButton);

        resumoLabel = new JLabel(" ");
        frame.add(resumoLabel);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente();
            }
        });

        frame.setVisible(true);
    }

    private void cadastrarCliente() {
        String nome = nomeField.getText().trim();
        int idade = (Integer) idadeField.getValue();
        String sexo = masculino.isSelected() ? "Masculino" : feminino.isSelected() ? "Feminino" : "";

        if (nome.isEmpty() || sexo.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente2 cliente = new Cliente2(nome, idade, sexo);

        resumoLabel.setText(cliente.retorna());

        nomeField.setText("");
        idadeField.setValue(18);
        grupoSexo.clearSelection();
    }
}
