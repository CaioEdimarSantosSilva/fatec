package view;

import model.Carro;

public class Main {
    public static void main(String[] args) {
    Carro sandero = new Carro();
		sandero.marca = "Renault";
		sandero.modelo = "Sandero";
		sandero.ano = 2017;
		
	System.out.println("Marca: " + sandero.marca + "\nModelo: " + sandero.modelo + "\nAno: " + sandero.ano);
    }
}
