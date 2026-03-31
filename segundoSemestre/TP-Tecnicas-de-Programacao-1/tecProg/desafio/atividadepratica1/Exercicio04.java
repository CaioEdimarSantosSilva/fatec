package atividadepratica1;

import java.util.Scanner;

public class Exercicio04 {

    public static void main(String[] args) {
        /*
         * Crie um programa que solicite ao usuário um número e exiba a tabuada desse
         * número de 1 a 10.
         */

        int numero, resultado;
        Scanner scan = new Scanner(System.in);

        System.out.print("Digite um número: ");
        numero = scan.nextInt();

        for (int contador = 1; contador <= 10; contador++) {
            resultado = numero * contador;
            System.out.println(numero + " x " + contador + " = " + resultado);
        }
    }
}