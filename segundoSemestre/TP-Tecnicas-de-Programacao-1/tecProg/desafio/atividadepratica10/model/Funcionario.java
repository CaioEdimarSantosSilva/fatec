package model;

class Funcionario {
    private String nome;
    private String cargo;
    private double salario;

    public Funcionario(String nome, String cargo, double salario) {
        this.nome = nome;
        this.cargo = cargo;
        this.salario = salario;
    }

    public String getNome() { 
        return nome; 
    }

    public String getCargo() { 
        return cargo; 
    }

    public double getSalario() { 
        return salario; 
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setCargo(String cargo){
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + " | Cargo: " + cargo + " | Salário: R$" + salario;
    }
}