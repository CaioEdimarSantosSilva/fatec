package br.edu.fatecpg.desfarioviacep.view;

import br.edu.fatecpg.desfarioviacep.model.Endereco;
import br.edu.fatecpg.desfarioviacep.service.ConsomeApi;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        List<Endereco> enderecos = new ArrayList<>();
        List<Endereco> historico = new ArrayList<>();

        Scanner scan = new Scanner(System.in);
        boolean validacao = true;

        System.out.println("Seja bem vindo ao app Busca Endereços");

        while(validacao) {
            System.out.println("Digite 1 para buscar um endereço, \n" +
                    "Digite 2 para excluir um endereço, \n" +
                    "Digite 3 para ver todos endereços, \n" +
                    "Digite 4 para ver o historico de todos endereços, \n" +
                    "Digite 9 para sair:");

            int opcao = scan.nextInt();
            scan.nextLine();

            switch (opcao){
                case 1:
                    System.out.println("Digite um CEP válido: ");
                    String cepDigitado = scan.nextLine();

                    boolean jaExiste = false;

                    String cepNormalizado = cepDigitado.replace("-", "");

                    for (Endereco e : enderecos) {
                        String cepLista = e.getCep().replace("-", "");

                        if (cepLista.equals(cepNormalizado)) {
                            System.out.println("Já existe esse CEP cadastrado:");
                            System.out.println(e + "\n");
                            jaExiste = true;
                            break;
                        }
                    }

                    if (!jaExiste) {
                        try {
                            String retorno = ConsomeApi.buscaCep(cepDigitado);
                            Endereco end = gson.fromJson(retorno, Endereco.class);
                            enderecos.add(end);
                            historico.add(end);
                            System.out.println(end + "\n");
                        } catch (IOException | InterruptedException e) {
                            System.out.println("Erro ao buscar CEP.\n");
                        }
                    }
                    break;

                case 2:
                    System.out.println("Digite o CEP que deseja remover:");
                    String cepRemover = scan.nextLine();

                    boolean removido = false;

                    for (int i = 0; i < enderecos.size(); i++) {
                        String cepRemoverNormalizado = cepRemover.replace("-", "");
                        String cepLista = enderecos.get(i).getCep().replace("-", "");

                        if (cepLista.equals(cepRemoverNormalizado)) {
                            enderecos.remove(i);
                            System.out.println("CEP removido com sucesso!\n");
                            removido = true;
                            break;
                        }
                    }

                    if (!removido) {
                        System.out.println("CEP não encontrado.\n");
                    }
                    break;

                case 3:
                    if (enderecos.isEmpty()) {
                        System.out.println("Nenhum CEP cadastrado. \n");
                    } else {
                        for (Endereco e : enderecos) {
                            System.out.println(e);
                        }
                    }
                    break;

                case 4:
                    if (historico.isEmpty()) {
                        System.out.println("Nenhum CEP foi buscado ainda.\n");
                    } else {
                        System.out.println("===== HISTÓRICO DE BUSCAS =====");
                        for (Endereco e : historico) {
                            System.out.println(e);
                        }
                    }
                    break;

                case 9:
                    System.out.println("Obrigado por usar o sistema!");
                    validacao = false;
                    break;
            }
        }

       /*
        CEPS VALIDOS
        01001000
        11749440
        11749498
        */
    }
}