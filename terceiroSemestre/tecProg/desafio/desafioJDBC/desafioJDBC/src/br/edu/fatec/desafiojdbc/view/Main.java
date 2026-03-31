package br.edu.fatec.desafiojdbc.view;

import br.edu.fatec.desafiojdbc.db.DB;
import br.edu.fatec.desafiojdbc.model.Aluno;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try(var conn = DB.connection()) {
            System.out.println("Conexão Realizada com sucesso");

            /*fazer um menu para add ou ver alunos ou sair do app*/
            Scanner scanner = new Scanner(System.in);
            boolean validacao = true;

            while (validacao) {
                System.out.println("Seja bem vindo ao sistema de cadastro ou visualizacao de alunos");
                System.out.println("Digite 1 para adcionar um aluno, \n" +
                        "Digite 2 para ver todos alunos, \n" +
                        "Digite 3 para sair: \n");

                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch(opcao) {
                    case 1:
                        System.out.println("Digite o CPF: ");
                        String cpf = scanner.nextLine();

                        System.out.println("Digite o nome: ");
                        String nome = scanner.nextLine();

                        /*post*/
                        var query = "INSERT INTO tb_alunos(cpf, nome) VALUES (?, ?)";
                        PreparedStatement stmt = conn.prepareStatement(query);
                        stmt.setString(1, cpf);
                        stmt.setString(2, nome);
                        stmt.execute();
                        Thread.sleep(1500);
                        break;
                    case 2:
                        /*get*/
                        List<Aluno> alunos = new ArrayList<>();
                        var consulta = "SELECT * FROM tb_alunos";
                        PreparedStatement stmtConsulta = conn.prepareStatement(consulta);
                        ResultSet rs = stmtConsulta.executeQuery();
                        while (rs.next()){
                            alunos.add(new Aluno(
                                    rs.getInt("id"),
                                    rs.getString("cpf"),
                                    rs.getString("nome")));
                        }
                        alunos.forEach(System.out::println);
                        Thread.sleep(3000);
                        break;
                    case 3:
                        System.out.println("Obrigado por usar o sistema!");
                        validacao = false;
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                        Thread.sleep(1500);

                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

