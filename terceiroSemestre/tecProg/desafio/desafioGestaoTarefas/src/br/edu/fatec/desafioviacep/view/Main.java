package br.edu.fatec.desafiotarefa.view;

import br.edu.fatec.desafiotarefa.controller.TarefaController;
import br.edu.fatec.desafiotarefa.model.Tarefa;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        TarefaController controller = new TarefaController();

        while (true) {

            System.out.println("\n===== MENU =====");
            System.out.println("1 - Criar tarefa");
            System.out.println("2 - Editar tarefa");
            System.out.println("3 - Excluir tarefa");
            System.out.println("4 - Listar todas");
            System.out.println("5 - Filtrar por categoria");
            System.out.println("6 - Filtrar por status");
            System.out.println("9 - Sair");
            System.out.print("Escolha: ");

            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Digite apenas números!");
                continue;
            }

            switch (opcao) {

                case 1 -> {
                    System.out.print("Titulo: ");
                    String titulo = scanner.nextLine();

                    System.out.print("Descricao: ");
                    String descricao = scanner.nextLine();

                    System.out.print("Status (PENDENTE/CONCLUIDA): ");
                    boolean concluida = scanner.nextLine().equalsIgnoreCase("concluida");

                    System.out.print("Categoria: ");
                    String categoria = scanner.nextLine();

                    Tarefa tarefa = new Tarefa(titulo, descricao, concluida, categoria);
                    controller.create(tarefa);
                }

                case 2 -> {
                    System.out.print("ID: ");
                    int id;
                    try {
                        id = Integer.parseInt(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println("ID inválido!");
                        continue;
                    }

                    System.out.print("Novo titulo: ");
                    String titulo = scanner.nextLine();

                    System.out.print("Nova descricao: ");
                    String descricao = scanner.nextLine();

                    System.out.print("Status (PENDENTE/CONCLUIDA): ");
                    boolean concluida = scanner.nextLine().equalsIgnoreCase("concluida");

                    System.out.print("Categoria: ");
                    String categoria = scanner.nextLine();

                    controller.update(id, titulo, descricao, concluida, categoria);
                }

                case 3 -> {
                    System.out.print("ID da tarefa: ");
                    int id;
                    try {
                        id = Integer.parseInt(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println("ID inválido!");
                        continue;
                    }

                    controller.delete(id);
                }

                case 4 -> controller.findAll();

                case 5 -> {
                    System.out.print("Categoria: ");
                    String categoria = scanner.nextLine();
                    controller.findByCategoria(categoria);
                }

                case 6 -> {
                    System.out.print("Status (PENDENTE/CONCLUIDA): ");
                    boolean status = scanner.nextLine().equalsIgnoreCase("concluida");
                    controller.findByStatus(status);
                }

                case 9 -> {
                    System.out.println("Encerrando...");
                    scanner.close();
                    return;
                }

                default -> System.out.println("Opção inválida!");
            }
        }
    }
}