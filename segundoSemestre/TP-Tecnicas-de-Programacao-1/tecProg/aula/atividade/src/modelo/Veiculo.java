package modelo;

public class Veiculo {
	
	private String marca;
	private String modelo;
	private double tanque;
	private double capacidade;
	private int ano;
	private boolean novo;
	

	public Veiculo(String marca, String modelo, double tanque, double capacidade, int ano, boolean novo) {
		super();
		this.marca = marca;
		this.modelo = modelo;
		this.tanque = tanque;
		this.capacidade = capacidade;
		this.ano = ano;
		this.novo = novo;
	}

	public double getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(double capacidade) {
		this.capacidade = capacidade;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public double getTanque() {
		return tanque;
	}

	public void setTanque(double tanque) {
		this.tanque = tanque;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public boolean isNovo() {
		return novo;
	}

	public void setNovo(boolean novo) {
		this.novo = novo;
	}

	public String ligar() {
		return "Ligar.. Ligado!";
	}
	
	public String abastecer(double litros) {
	return	litros<=this.tanque?"Abastecimento Ok":"Quantidade Maior!";
	}
	
	
}
