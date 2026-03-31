package model;

public class Empresa {
    private Cliente2[] clientes;
    private int qtdClientes;
    private Funcionario[] funcionarios;
    private int qtdFuncionarios;
    private double mediaSalarial;

    public Empresa() {
        this.clientes = new Cliente2[5];
        this.qtdClientes = 0;
        this.funcionarios = new Funcionario[10];
        this.qtdFuncionarios = 0;
    }

    //Parte 1 do exercicio
    public boolean adicionarCliente(String nome, String email) {
        if (qtdClientes >= clientes.length) {
            return false;
        }
        clientes[qtdClientes++] = new Cliente2(nome, email);
        return true;
    }

    public void exibirClientes() {
        if (qtdClientes == 0) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        for (int i = 0; i < qtdClientes; i++) {
            System.out.println((i+1) + ". " + clientes[i]);
        }
    }

    //Parte 2 do exercicio
    public boolean adicionarFuncionario(String nome, String cargo, double salario) {
        if (qtdFuncionarios >= funcionarios.length) return false;
        funcionarios[qtdFuncionarios++] = new Funcionario(nome, cargo, salario);
        return true;
    }

    public void exibirFuncionarios() {
        if (qtdFuncionarios == 0) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }
        for (int i = 0; i < qtdFuncionarios; i++) {
            System.out.println((i+1) + ". " + funcionarios[i]);
        }
    }

    public double calcularFolhaSalarial() {
        double total = 0;
        for (int i = 0; i < qtdFuncionarios; i++) {
            total += funcionarios[i].getSalario();
        }
        return total;
    }

    //Parte 3 do exercicio
    public double calcularMediaSalarial() {
        if (qtdFuncionarios == 0) return 0;

        Calculadora2 calc = new Calculadora2();

        double soma = 0;
        for (int i = 0; i < qtdFuncionarios; i++) {
            soma = calc.somar(soma, funcionarios[i].getSalario());
        }

        double mediaSalarial = soma * (1.0 / qtdFuncionarios);
        return mediaSalarial;
    }

    public void exibirMediaSalarial() {
        System.out.println("A média salarial dos funcionários é: R$" + mediaSalarial);
    }
}