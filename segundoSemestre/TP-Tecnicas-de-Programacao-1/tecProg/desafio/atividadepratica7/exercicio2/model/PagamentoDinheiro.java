package model;

public class PagamentoDinheiro implements IPagamento {
	
	private double desconto;
	private double valor;
		
	public PagamentoDinheiro(double valor, double desconto) {
		this.desconto = desconto;
        this.valor = valor;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

    public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public double calcularPagamento() {	
		return this.valor - (this.valor * this.desconto);
	}
	
	public String emitirRecibo() {
		return "Valor: R$" + valor  + "\nValor com deconto: R$" + calcularPagamento();
	}
	

}