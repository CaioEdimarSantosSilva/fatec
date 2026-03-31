package atividadepratica1;

import java.util.Scanner;

public class Exercicio05 {
    public static void main(String[] args) {
        /*
         * Crie um programa que solicite ao usuário a entrada de um número inteiro.
         * Verifique se o número é par ou ímpar e exiba uma mensagem correspondente.
         */

        int numero;
        Scanner scan = new Scanner(System.in);

        System.out.print("Digite um número: ");
        numero = scan.nextInt();

        if (numero == 0) {
            System.out.println("O número é zero");
        } else if (numero % 2 == 0) {
            System.out.println("O número é par");
        } else {
            System.out.println("O número é impar");
        }

    }
}
