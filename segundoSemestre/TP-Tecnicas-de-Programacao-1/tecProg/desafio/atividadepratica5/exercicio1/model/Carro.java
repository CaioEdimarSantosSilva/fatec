package model;

public class Carro {
    private String marca;
	private String modelo;
	private int ano;
	private double tanque;
	private double capacidadeTanque;
	
	public Carro(String marca, String modelo, int ano){
		if(ano<2015) {
			System.out.println("Não pode criar!");
		}else {
			this.ano = ano;
		}
		this.marca = marca;
		this.modelo = modelo;
	}
	/*CAPACIDADE TANQUE*/
	public double getCapacidadeTanque(){
		return this.capacidadeTanque;
	}

	public void setCapacidadeTanque(double novaTanque) {
		this.capacidadeTanque = novaTanque;
	}

	/*TANQUE*/
	public double getTanque(){
		return this.tanque;
	}

	public void setTanque(double novaTanque) {
		if(novaTanque <= this.capacidadeTanque){
			this.tanque = novaTanque;
		}
	}
	
	public void encherTanque(double capacidade, double litros, double quantidadeAtual, double valorLitro) {
		if(quantidadeAtual == capacidade){
			System.out.println("Tanque já esta cheio!");
		}
		else if(litros > capacidade){
			System.out.println("Tanque não tem essa capacidade de litros");
		}
		else{
			double quantidadeNova = litros + quantidadeAtual;
			if(quantidadeNova <= capacidade){
				this.tanque += litros;
				double valorTotal = valorLitro * litros;
				System.out.println("O valor para encher o tanque é R$" + valorTotal);
			}
			else{
				System.out.println("A quantidade de litros excede a capacidade do tanque");
			}
		}

	}

	/*MARCA*/
	public String getMarca() {
		return this.marca;
	}
	public void setMarca(String novaMarca) {
		this.marca = novaMarca;
	}
	
	/*MODELO*/
	public String getModelo() {
		return this.modelo;
	}
	public void setModelo(String novaModelo) {
		this.modelo = novaModelo;
	}
	
	/*ANO*/
	public int getAno() {
		return this.ano;
	}
	public void setAno(int novaAno) {
		if(ano<2015) {
			System.out.println("O ano não pode alterado!");
		}
		else {
			this.ano = ano;
		}
		
	}
	public void ligar() {
		System.out.println("Carro Ligado!");
	}
	
	public void acelelar() {
		System.out.println("Acelerando...");
	}
}
