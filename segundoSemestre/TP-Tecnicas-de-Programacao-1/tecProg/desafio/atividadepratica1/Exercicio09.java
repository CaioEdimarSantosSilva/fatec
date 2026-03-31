package atividadepratica1;

import java.util.Scanner;

public class Exercicio09 {
    public static void main(String[] args) {
        /*
         * Lista de Nomes: Peça ao usuário para inserir 5 nomes (um de cada vez). Após
         * inserir todos os nomes, peça outro nome e use um loop for para percorrer a
         * lista e verificar se o 6º(último) nome digitado está presente no array dos 5
         * nomes informados inicialmente.
         */
        String nomeUltimo;
        String[] nomes = new String[5];
        Scanner scan = new Scanner(System.in);
        boolean nomeEncontrado = false;

        for (int contador = 0; contador < nomes.length; contador++) {
            System.out.print("Digite o " + (contador + 1) + "° nome: ");
            nomes[contador] = scan.nextLine();
        }

        System.out.print("Digite o 6° nome: ");
        nomeUltimo = scan.nextLine();

        for (int contador = 0; contador < nomes.length; contador++) {
            if (nomeUltimo.equals(nomes[contador])) {
                nomeEncontrado = true;
            }
        }
        
        if (nomeEncontrado) {
            System.out.println("O nome esta na lista");
        } else {
            System.out.println("O nome não esta na lista");
        }
    }
}
