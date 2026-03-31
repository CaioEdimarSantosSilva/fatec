package view;

import model.Farmacia;
 
public class Main {
	public static void main(String[] args) {
		String[] nomesMedicamentos = {"Dipirona", "Parecetamol", "Tadala", "Xanax"};
		int[] diasParaVencimento = {10, 5, 25, 20};
		double[] precos = {20.00, 15.00, 37.75, 175.89};
		
		Farmacia drogaril = new Farmacia(nomesMedicamentos, diasParaVencimento, precos);
		drogaril.exibirMedicamentos();
		drogaril.mediaVencimento();
		drogaril.medicamentoVencimento();
		drogaril.desconto();
		drogaril.venda(1);
	}
 
}