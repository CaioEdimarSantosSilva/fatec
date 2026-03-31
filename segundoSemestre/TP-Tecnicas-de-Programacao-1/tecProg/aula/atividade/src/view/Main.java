package view;

import modelo.*;

public class Main {

	public static void main(String[] args) {
	
		Gerente ger = new Gerente("Ale",10);
       
		System.out.println(ger.abrirCaixa());
		System.out.println(ger.baterPonto());
	}
}
