package atividadepratica1;

import java.util.Scanner;

public class Exercicio08 {

    public static void main(String[] args) {
        /*
         * Soma dos Números Ímpares: Peça ao usuário que insira um número inteiro n.
         * Calcule e imprima a soma dos primeiros n números ímpares. Por exemplo, se o
         * usuário inserir 4, o programa deve calcular a soma de 1 + 3 + 5 + 7 = 16.
         */

        int numero, soma = 1, resultado = 0;
        Scanner scan = new Scanner(System.in);

        System.out.print("Digite um numero: ");
        numero = scan.nextInt();
        
        for(int contador = 1; contador <= numero; contador++ ){
            resultado += soma;
            if(contador != numero){
                System.out.print(soma + " + ");
            }
            else{
                System.out.print(soma);
            }
            soma+=2;
        }
          System.out.print(" = " + resultado);
    }
}
