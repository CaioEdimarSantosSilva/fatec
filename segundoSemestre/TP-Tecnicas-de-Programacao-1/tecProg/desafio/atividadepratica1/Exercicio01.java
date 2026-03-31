package atividadepratica1;

import java.util.Scanner;

public class Exercicio01 {

    public static void main(String[] args) {
        /*
         * Crie um programa que solicite ao usuário digitar um número. Se o número for
         * positivo, exiba
         * "Número positivo", caso contrário, exiba "Número negativo".
         */

        int numero;
        Scanner scan = new Scanner(System.in);

        System.out.print("Digite um número: ");
        numero = scan.nextInt();

        if (numero > 0) {
            System.out.println("Número positivo");
        } else if (numero == 0) {
            System.out.println("Número igual a zero");
        } else {
            System.out.println("Número negativo");
        }

    }
}