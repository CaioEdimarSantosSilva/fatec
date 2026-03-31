package view;
import model.Empresa;
import model.TelaEmpresa;

public class Main {
    public static void main(String[] args) {

        //Parte 1 do exercicio - Associação
        Empresa empresa = new Empresa();
        empresa.adicionarCliente("Carlos", "carlos@exemplo.com");
        empresa.adicionarCliente("Caio", "caio@exemplo.com");
        empresa.adicionarCliente("Geovanny", "geovanny@exemplo.com");
        empresa.adicionarCliente("Eduardo", "eduardo@exemplo.com");
        empresa.adicionarCliente("Weverton", "weverton@exemplo.com");
        System.out.println("Clientes cadastrados:");
        empresa.exibirClientes();

        
        //Parte 2 do exercicio - Composição
        empresa.adicionarFuncionario("João", "Gerente", 5000);
        empresa.adicionarFuncionario("Maria", "Atendente", 2500);
        empresa.adicionarFuncionario("Pedro", "Técnico", 3000);
        System.out.println("\nFuncionários cadastrados:");
        empresa.exibirFuncionarios();
        System.out.println("\nTotal da folha salarial: R$" + empresa.calcularFolhaSalarial());


        //Parte 3 do exercicio - Dependência
        System.out.println("\nMédia salarial: R$" + empresa.calcularMediaSalarial());
        empresa.exibirMediaSalarial();


        //Interface gráfica
        new TelaEmpresa().setVisible(true);
    }
}
