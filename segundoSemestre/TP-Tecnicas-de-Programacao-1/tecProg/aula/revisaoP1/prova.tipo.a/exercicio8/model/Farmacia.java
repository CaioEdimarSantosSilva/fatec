package model;
 
public class Farmacia {
 
	private String[] nomesMedicamentos;
	private int[] diasParaVencimento;
	private double[] precos;
	
	
	public Farmacia(String[] nomesMedicamentos, int[] diasParaVencimento, double[] precos) {
		this.nomesMedicamentos = nomesMedicamentos;
		this.diasParaVencimento = diasParaVencimento;
		this.precos = precos;
	}
	
	public void exibirMedicamentos() {
		for(int c = 0; c < nomesMedicamentos.length; c++) {
			System.out.println("Meidcamento: " + nomesMedicamentos[c] + " ||  Preço: " + precos[c] + " || Dias para vencimento: " + diasParaVencimento[c]);
		}
	}
	
	public void mediaVencimento() {
		int somador = 0;
		for(int c = 0; c < diasParaVencimento.length; c++) {
			somador += diasParaVencimento[c];
		}
		double media = somador / diasParaVencimento.length;
	}
	
	public void medicamentoVencimento() {
		for(int c = 0; c < diasParaVencimento.length; c++) {
			if(diasParaVencimento[c] <= 5) {
				System.out.println(nomesMedicamentos[c] + " este medicamento está proximo de vencer faltam " + diasParaVencimento[c] + " dias.");
			}
		}
	}
	
	
	public void desconto() {
		for(int c = 0; c < diasParaVencimento.length; c++) {
			if(diasParaVencimento[c] <= 5) {
				double desconto = precos[c] - (precos[c] * 0.5);
				System.out.println("Você ganhou um desconto de: R$ " + desconto + " reais.");
			}
		}
	}
	
	
	public void venda(int indice) {
		precos[indice] = 0;
		nomesMedicamentos[indice] = "";
		diasParaVencimento[indice] = 0;
	}
	
}
 
