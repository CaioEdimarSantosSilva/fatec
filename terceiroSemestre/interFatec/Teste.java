/******************************************************************************
 É COM HASNEXT PORRA 
*******************************************************************************/


import java.util.Scanner;

public class Teste {
        public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        StringBuilder saida = new StringBuilder();

        int quantida = entrada.nextInt();

        for (int i = 0; i < quantida; i++) {
            int n = entrada.nextInt();

            if (n % 2 == 0) {
                saida.append(n + " par\n");
            } else {
                saida.append(n + " impar\n");
            }
        }

        System.out.print(saida);
    }
}
