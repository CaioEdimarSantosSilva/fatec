/******************************************************************************
-Entrada
A primeira linha da entrada contém a quantidade Q (2 ≤ Q ≤ 1000) de pares de bolinhas sorteadas. Em
seguida, há 2Q linhas, cada uma com um número inteiro entre 1 e 30. Estas linhas devem ser lidas aos pares:
o primeiro valor é o número sorteado por Dorothy, e o segundo é o de Dagmar. Como elas sorteiam bolinhas
físicas e não as devolvem ao globo, os valores sorteados por ambas nunca serão iguais.

-Saída
Para cada par de valores o programa deve imprimir na saída quem decide e se a Nonna vai.
Se o valor da Dorothy for maior que o da Dagmar e a soma dos valores for maior que 40 o programa
deve escrever "DOROTHY DECIDE E A NONNA VAI". Caso a soma não ultrapasse 40 escreva apenas
"DOROTHYDECIDE".
Se o valor da Dorothy for menor que o da Dagmar e a soma dos valores for maior que 40 o programa
deve escrever "DAGMAR DECIDE E A NONNA VAI". Caso a soma não ultrapasse 40 escreva apenas
"DAGMARDECIDE".
As aspas não devem ser incluídas na saída. A saída é toda em letras maiúsculas. Ao final da última linha
não se equeçam da quebra de linha.

-Exemplo de Entrada 1
4
23
12
26
20
12
23
20
26

-Exemplo de Saída 1
DOROTHY DECIDE
DOROTHY DECIDE E A NONNA VAI
DAGMAR DECIDE
DAGMAR DECIDE E A NONNA VAI

*******************************************************************************/

import java.util.Scanner;

public class ProblemaI {
        public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        int quantidadePares = entrada.nextInt();
        StringBuilder saida = new StringBuilder();

        for (int i = 0; i < quantidadePares; i++) {
            int dorothy = entrada.nextInt();
            int dagmar = entrada.nextInt();
            int soma = dorothy + dagmar;

            if (dorothy > dagmar) {
                if (soma > 40) {
                    saida.append("DOROTHY DECIDE E A NONNA VAI\n");
                } else {
                    saida.append("DOROTHY DECIDE\n");
                }
            } else {
                if (soma > 40) {
                    saida.append("DAGMAR DECIDE E A NONNA VAI\n");
                } else {
                    saida.append("DAGMAR DECIDE\n");
                }
            }
        }

        System.out.print(saida);
    }
}
