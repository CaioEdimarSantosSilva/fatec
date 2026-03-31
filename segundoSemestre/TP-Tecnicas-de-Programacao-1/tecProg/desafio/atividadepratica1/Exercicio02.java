package atividadepratica1;

import java.util.Scanner;

public class Exercicio02 {

    public static void main(String[] args) {
        /*
         * Peça ao usuário para inserir dois números inteiros. Compare os números e imprima
         * uma mensagem indicando se são iguais, diferentes, o primeiro é maior ou o
         * segundo é maior.
         */

        int numero1, numero2;
        Scanner scan = new Scanner(System.in);

        System.out.print("Digite um número: ");
        numero1 = scan.nextInt();

        System.out.print("Digite um número: ");
        numero2 = scan.nextInt();

        if (numero1 == numero2) {
            System.out.println("Os números são iguais");
        } else if (numero1 > numero2) {
            System.out.println("Os números são diferentes e o primeiro é maior");
        } else {
            System.out.println("Os números são diferentes e o segundo é maior");
        }

    }
}
