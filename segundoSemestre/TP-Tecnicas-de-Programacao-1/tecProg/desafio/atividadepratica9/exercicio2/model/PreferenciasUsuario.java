package model;

import java.awt.*;
import javax.swing.*;

public class PreferenciasUsuario {
    private JFrame frame;
    private JComboBox<String> temaCombo;
    private JCheckBox notificacoesCheck;
    private JSlider volumeSlider;
    private JTextArea textArea;
    private Usuario usuario;

    public PreferenciasUsuario() {
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        frame.setVisible(true);
    }

    private void inicializarComponentes() {
        frame = new JFrame("Preferências do Usuário");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 400);
        frame.setLocationRelativeTo(null);


        String[] temas = {"Claro", "Escuro"};
        temaCombo = new JComboBox<>(temas);
        notificacoesCheck = new JCheckBox("Ativar Notificações");
        
        volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.setMajorTickSpacing(20);
        volumeSlider.setMinorTickSpacing(10);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);

        textArea = new JTextArea(5, 30);
        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        textArea.setText("Nenhuma preferência salva ainda.");

        usuario = new Usuario("Claro", false, 50);
    }

    private void configurarLayout() {
        frame.setLayout(new BorderLayout(10, 10));

        JPanel painelPreferencias = new JPanel();
        painelPreferencias.setLayout(new GridLayout(3, 2, 10, 10));
        painelPreferencias.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel temaLabel = new JLabel("Tema:");
        painelPreferencias.add(temaLabel);
        painelPreferencias.add(temaCombo);

        JLabel notificacoesLabel = new JLabel("Notificações:");
        painelPreferencias.add(notificacoesLabel);
        painelPreferencias.add(notificacoesCheck);

        JLabel volumeLabel = new JLabel("Volume:");
        painelPreferencias.add(volumeLabel);
        painelPreferencias.add(volumeSlider);

        frame.add(painelPreferencias, BorderLayout.NORTH);

        JButton salvarButton = new JButton("Salvar");
        JPanel painelBotao = new JPanel();
        painelBotao.add(salvarButton);
        frame.add(painelBotao, BorderLayout.CENTER);

        JPanel painelTextArea = new JPanel();
        painelTextArea.setBorder(BorderFactory.createTitledBorder("Preferências Salvas"));
        JScrollPane scrollPane = new JScrollPane(textArea);
        painelTextArea.add(scrollPane);
        frame.add(painelTextArea, BorderLayout.SOUTH);

        salvarButton.addActionListener(e -> salvarPreferencias());
    }

    private void configurarEventos() {
        temaCombo.addActionListener(e -> aplicarTema());
        
        aplicarTema();
    }

    private void salvarPreferencias() {
        usuario.setTema(getTema());
        usuario.setNotificacoes(isNotificacoesAtivas());
        usuario.setVolume(getVolume());

        exibirPreferencias();

        aplicarTema();

        JOptionPane.showMessageDialog(frame,
            "Preferências salvas com sucesso!",
            "Sucesso",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void exibirPreferencias() {
        String preferencias = "Tema: " + usuario.getTema() + "\n";
        preferencias += "Notificações: " + (usuario.getNotificacoes() ? "Ativadas" : "Desativadas") + "\n";
        preferencias += "Volume: " + usuario.getVolume() + "%\n";
        
        textArea.setText(preferencias);
    }

    private void aplicarTema() {
        String tema = getTema();
        
        if (tema.equals("Escuro")) {
            frame.getContentPane().setBackground(new Color(45, 45, 45));
            textArea.setBackground(new Color(60, 60, 60));
            textArea.setForeground(Color.WHITE);
            
            for (Component comp : frame.getContentPane().getComponents()) {
                if (comp instanceof JPanel) {
                    comp.setBackground(new Color(45, 45, 45));
                    aplicarTemaRecursivo((JPanel) comp, true);
                }
            }
        } else {
            frame.getContentPane().setBackground(Color.WHITE);
            textArea.setBackground(Color.WHITE);
            textArea.setForeground(Color.BLACK);
            for (Component comp : frame.getContentPane().getComponents()) {
                if (comp instanceof JPanel) {
                    comp.setBackground(Color.WHITE);
                    aplicarTemaRecursivo((JPanel) comp, false);
                }
            }
        }
        
        frame.repaint();
    }

    private void aplicarTemaRecursivo(JPanel panel, boolean escuro) {
        Color bg = escuro ? new Color(45, 45, 45) : Color.WHITE;
        Color fg = escuro ? Color.WHITE : Color.BLACK;
        
        panel.setBackground(bg);
        
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                aplicarTemaRecursivo((JPanel) comp, escuro);
            } else if (comp instanceof JLabel) {
                comp.setForeground(fg);
            }
        }
    }

    public String getTema() {
        return (String) temaCombo.getSelectedItem();
    }

    public boolean isNotificacoesAtivas() {
        return notificacoesCheck.isSelected();
    }

    public int getVolume() {
        return volumeSlider.getValue();
    }

    public Usuario getUsuario() {
        return usuario;
    }
}