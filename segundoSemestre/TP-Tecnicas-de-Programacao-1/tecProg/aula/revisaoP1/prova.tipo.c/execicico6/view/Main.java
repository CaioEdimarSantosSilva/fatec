package view;

import model.Celular;

public class Main {
    public static void main(String[] args) {
        Celular sandero = new Celular();
        sandero.marca = "Samsung";
        sandero.modelo = "poket";
        sandero.preco = 499.99;

        System.out.println("Marca: " + sandero.marca + "\nModelo: " + sandero.modelo + "\nPreço: R$" + sandero.preco);

    }
}
