package atividadepratica1;

import java.util.Scanner;

public class Exercicio10 {
    public static void main(String[] args) {
        /*
         * Senha Secreta: Defina uma senha secreta (por exemplo: "Java123"). Use um loop
         * while para pedir ao usuário que insira a senha. Se a senha estiver incorreta,
         * continue pedindo a senha e informe ao usuário que a tentativa foi inválida.
         * Se ele acertar, saia do loop e imprima uma mensagem de sucesso.
         */
        String senha, senhaSecreta = "Java123";
        Scanner scan = new Scanner(System.in);
        do {
            System.out.print("Digite a senha secreta: ");
            senha = scan.nextLine();
            if (!senha.equals(senhaSecreta)) {
                System.out.println("Erro! Senha secreta incorreta, tente novamente");
            }
        } while (!senha.equals(senhaSecreta));
        System.out.print("Senha secreta correta");
    }
}
