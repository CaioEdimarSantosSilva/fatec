package atividadepratica1;

import java.util.Scanner;

public class Exercicio06 {

    public static void main(String[] args) {
        /*
         * Crie um programa que solicite ao usuário um número e calcule o fatorial desse
         * número.
         */

        int numero, resultado;
        Scanner scan = new Scanner(System.in);

        System.out.print("Digite um número: ");
        numero = scan.nextInt();
        resultado = numero;

        for(int contador = numero-1; contador >= 1; contador-- ){
            resultado *= contador;

        }
        System.out.println("O fatorial é " + resultado);
    }

}

