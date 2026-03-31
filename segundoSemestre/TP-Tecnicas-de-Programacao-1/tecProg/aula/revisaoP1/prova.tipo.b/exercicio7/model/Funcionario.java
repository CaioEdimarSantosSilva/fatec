package model;

public class Funcionario {
    private String nome;
	private double salario;
	private String cargo;
	
	public String getNome(){
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
		System.out.println("Olá sou " + nome);
	}
	
	public double getSalario() {
		return this.salario;
	}
	
	public void setSalario(double salario) {
		this.salario = salario;
		System.out.println("Salário alterado para " + salario);
	}
	
	public String getCargo() {
		return this.cargo;
	}
	
	public void setCargo(String cargo) {
		this.cargo = cargo;
		System.out.println("Meu cargo é " + cargo);
	}
}
