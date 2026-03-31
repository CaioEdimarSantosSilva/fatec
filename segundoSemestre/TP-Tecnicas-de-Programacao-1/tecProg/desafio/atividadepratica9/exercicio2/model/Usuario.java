package model;

public class Usuario {
    private String tema;
    private boolean notificacoes;
    private int volume;

    public Usuario(String tema, boolean notificacoes, int volume) {
        this.tema = tema;
        this.notificacoes = notificacoes;
        this.volume = volume;
    }

    public String getTema() {
        return tema;
    }

    public boolean getNotificacoes() {
        return notificacoes;
    }

    public int getVolume() {
        return volume;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public void setNotificacoes(boolean notificacoes) {
        this.notificacoes = notificacoes;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String retorna() {
        return "Tema: " + tema + ", Notificações: " + notificacoes + ", Volume: " + volume;
    }

}
