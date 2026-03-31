package model;

public class Pessoa {
    private String nome;
    private int idade;
    private String cpf;
    private Endereco end;
    public Pessoa( String nome, int idade, String cpf, Endereco end) {
        super();
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.end = end;

    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    @Override
    public String toString() {  
        return "Pessoa [nome=" + nome + ", idade=" + idade + ", cpf=" + cpf + ", end=" + end + "]";
    }
}
