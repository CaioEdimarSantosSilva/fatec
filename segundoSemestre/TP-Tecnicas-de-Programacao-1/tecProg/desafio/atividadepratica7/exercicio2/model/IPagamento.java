package model;

public interface IPagamento {
	public double calcularPagamento();
	public String emitirRecibo();
}