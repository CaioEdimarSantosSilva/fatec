package atividadepratica1;

import java.util.Scanner;

public class Exercicio03 {

    public static void main(String[] args) {
        /*
         * Crie um menu que oferece duas opções ao usuário:
         * "1. Calcular área do quadrado" e "2. Calcular área do círculo".
         * Solicite a escolha do usuário e realize o cálculo da área com base na opção
         * selecionada.
         */

        double lado, raio, area = 0, pi = 3.14159;
        int menu;

        Scanner scan = new Scanner(System.in);
        do {
            System.out.print("Escolha entre: 1. Calcular área do quadrado ou 2. Calcular área do círculo: ");
            menu = scan.nextInt();

            switch (menu) {
                case 1:
                    System.out.print("Digite o comprimento de um dos lados do quadrado: ");
                    lado = scan.nextDouble();

                    area = lado * lado;
                    System.out.println("O valor da área do quadrado é " + area);
                    break;

                case 2:
                    System.out.print("Digite o raio do circulo: ");
                    raio = scan.nextDouble();

                    area = (raio * raio) * pi;
                    System.out.println("O valor da área do circulo é " + area);
                    break;

                default:
                    System.out.println("ERRO! você não digitou uma das opções validas que é 1 ou 2");
                    break;
            }
        } while (menu != 1 && menu != 2);

    }
}
