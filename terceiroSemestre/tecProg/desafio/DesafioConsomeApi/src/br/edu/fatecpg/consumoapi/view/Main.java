package br.edu.fatecpg.consumoapi.view;

import br.edu.fatecpg.consumoapi.dao.EmpresaDAO;
import br.edu.fatecpg.consumoapi.model.Empresa;
import br.edu.fatecpg.consumoapi.service.BrasilApi;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Gson gson = new Gson();
        Scanner scan = new Scanner(System.in);
        EmpresaDAO dao = new EmpresaDAO();
        boolean rodando = true;

        while (rodando) {
            System.out.println("\n===== Cadastro de Empresas =====");
            System.out.println("1 - Cadastrar empresa (via CNPJ)");
            System.out.println("2 - Excluir empresa");
            System.out.println("3 - Listar empresas");
            System.out.println("4 - Sair");
            System.out.print("Escolha: ");

            int opcao;
            try {
                opcao = Integer.parseInt(scan.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Digite um número.");
                continue;
            }

            switch (opcao) {

                // -------------------------------------------------------- CADASTRAR
                case 1 -> {
                    System.out.print("Informe o CNPJ: ");
                    String cnpjDigitado = scan.nextLine();

                    // Limpeza: mantém só letras maiúsculas e dígitos
                    String cnpjNormalizado = cnpjDigitado
                            .toUpperCase()
                            .replaceAll("[^A-Z0-9]", "");

                    if (cnpjNormalizado.length() != 14) {
                        System.out.println("CNPJ inválido. Informe os 14 dígitos.");
                        break;
                    }

                    try {
                        // 1. Consulta a API
                        System.out.println("Consultando BrasilAPI...");
                        String json = BrasilApi.buscaEmpresa(cnpjNormalizado);

                        // 2. Converte JSON → objeto
                        Empresa empresa = gson.fromJson(json, Empresa.class);

                        // 3. Normaliza o CNPJ retornado pela API
                        empresa.setCnpj(empresa.getCnpj().replaceAll("[^A-Z0-9]", ""));

                        // 4. Persiste via DAO
                        dao.inserir(empresa);

                        System.out.println("Empresa cadastrada com sucesso!");
                        System.out.println("  Razão Social : " + empresa.getRazao_social());
                        System.out.println("  Sócios       : " + empresa.getQsa().size());

                    } catch (IllegalArgumentException e) {
                        // CNPJ não encontrado na BrasilAPI (HTTP 404)
                        System.out.println("Erro: " + e.getMessage());

                    } catch (IOException | InterruptedException e) {
                        System.out.println("Erro de rede ao consultar a API: " + e.getMessage());

                    } catch (SQLException e) {
                        // Trata casos específicos do PostgreSQL
                        if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                            System.out.println("Empresa já cadastrada (CNPJ duplicado).");
                        } else {
                            System.out.println("Erro de banco de dados: " + e.getMessage());
                        }
                    }
                }

                // -------------------------------------------------------- EXCLUIR
                case 2 -> {
                    System.out.print("Informe o CNPJ da empresa a excluir: ");
                    String cnpjExcluir = scan.nextLine()
                            .toUpperCase()
                            .replaceAll("[^A-Z0-9]", "");

                    try {
                        boolean removida = dao.excluir(cnpjExcluir);
                        if (removida) {
                            System.out.println("Empresa removida com sucesso.");
                        } else {
                            System.out.println("Nenhuma empresa encontrada com esse CNPJ.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Erro ao excluir: " + e.getMessage());
                    }
                }

                // -------------------------------------------------------- LISTAR
                case 3 -> {
                    try {
                        List<Empresa> empresas = dao.listarTodas();

                        if (empresas.isEmpty()) {
                            System.out.println("Nenhuma empresa cadastrada.");
                            break;
                        }

                        String linha = "=".repeat(60);
                        String sublinha = "-".repeat(60);

                        System.out.println("\n" + linha);
                        System.out.printf("  EMPRESAS CADASTRADAS (%d)%n", empresas.size());
                        System.out.println(linha);

                        int num = 1;
                        for (Empresa e : empresas) {
                            System.out.printf("%n  [%d] %s%n", num++, e.getRazao_social());
                            System.out.println(sublinha);
                            System.out.printf("  CNPJ          : %s%n", e.getCnpj());
                            System.out.printf("  Nome Fantasia : %s%n",
                                    e.getNome_fantasia() == null || e.getNome_fantasia().isBlank()
                                            ? "(não informado)" : e.getNome_fantasia());
                            System.out.printf("  Endereço      : %s%n",
                                    e.getLogradouro() == null || e.getLogradouro().isBlank()
                                            ? "(não informado)" : e.getLogradouro());

                            System.out.println();
                            if (e.getQsa().isEmpty()) {
                                System.out.println("  Sócios: (nenhum registrado)");
                            } else {
                                System.out.printf("  Sócios (%d):%n", e.getQsa().size());
                                for (var s : e.getQsa()) {
                                    System.out.printf("    • %-45s  Doc: %-14s  [%s]%n",
                                            s.getNome_socio(),
                                            s.getCnpj_cpf_do_socio().isBlank()
                                                    ? "—" : s.getCnpj_cpf_do_socio(),
                                            s.getQualificacao_socio());
                                }
                            }
                            System.out.println(sublinha);
                        }

                    } catch (SQLException e) {
                        System.out.println("Erro ao listar empresas: " + e.getMessage());
                    }
                }

                // -------------------------------------------------------- SAIR
                case 4 -> {
                    System.out.println("Encerrando... Até logo!");
                    rodando = false;
                }

                default -> System.out.println("Opção inválida.");
            }
        }

        scan.close();
    }
}