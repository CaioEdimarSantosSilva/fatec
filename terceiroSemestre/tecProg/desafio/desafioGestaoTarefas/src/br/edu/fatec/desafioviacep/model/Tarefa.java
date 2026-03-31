package br.edu.fatec.desafiotarefa.model;

public class Tarefa {
    private int id;
    private String titulo;
    private String descricao;
    private Boolean concluida;
    private String categoria;

    public Tarefa(String titulo, String descricao, Boolean concluida, String categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = concluida;
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", concluida=" + concluida +
                ", categoria='" + categoria + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boolean getConcluida() {
        return concluida;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setConcluida(Boolean concluida) {
        this.concluida = concluida;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
