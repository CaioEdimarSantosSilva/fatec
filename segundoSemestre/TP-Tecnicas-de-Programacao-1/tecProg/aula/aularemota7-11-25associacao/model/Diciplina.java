package model;

public class Diciplina {
    private String nome;
    private String curso;
    private String anoSemestre;
    private Aluno aluno;
    public Diciplina(String nome, String curso, String anoSemestre, Aluno al) {
        super();
        this.nome = nome;
        this.curso = curso;
        this.anoSemestre = anoSemestre;
        this.aluno = al;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCurso() {
        return curso;
    }
    public void setCurso(String curso) {
        this.curso = curso;
    }
    public String getAnoSemestre() {
        return anoSemestre;
    }
    public void setAnoSemestre(String anoSemestre) {
        this.anoSemestre = anoSemestre;
    }
    @Override
    public String toString() {
        return "Diciplina [nome=" + nome + ", curso=" + curso + ", anoSemestre=" + anoSemestre + ", aluno=" + aluno + "]";
    }

}
    