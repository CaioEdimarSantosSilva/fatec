package view;
/* Interface Autenticavel

Crie uma interface chamada Autenticavel com os seguintes métodos:
login(String usuario, String senha): Verifica se o login e a senha estão corretos.
logout(): Faz o logout do usuário.

Implemente essa interface na classe SistemaDeSeguranca. Nessa classe:
Defina os valores de um login e senha corretos, como "admin" e "1234".
No método login(), implemente a lógica para verificar se o usuário e senha inseridos correspondem aos valores corretos.
Se o login for bem-sucedido, armazene um valor booleano que indica se o usuário está autenticado ou não.
Tarefa: Crie uma classe de teste que peça ao usuário para inserir o nome de usuário e senha. Se o login estiver correto, 
o sistema deverá exibir uma mensagem de boas-vindas. Caso contrário, deverá pedir para tentar novamente até acertar. 
O sistema também deve permitir fazer o logout. */

import java.util.Scanner;
import model.SistemaDeSeguranca;

public class Main {

	public static void main(String[] args) {

			Scanner scan = new Scanner(System.in);

			SistemaDeSeguranca sistema = new SistemaDeSeguranca();

	        boolean autenticado = false;
	        System.out.println("Sistema de Login");

	        while (!autenticado) {
	            System.out.print("Usuario: ");
	            String usuario = scan.nextLine();

	            System.out.print("Senha: ");
	            String senha = scan.nextLine();

	            autenticado = sistema.login(usuario, senha);
	        }

	        System.out.println("\nBem-vindo, " + sistema.getUsuario());

	        System.out.print("\nDeseja fazer sair?: ");
	        String opcao = scan.nextLine();

	        if (opcao.equalsIgnoreCase("sim")) {
	            sistema.logout();

	        scan.close();
	}
}