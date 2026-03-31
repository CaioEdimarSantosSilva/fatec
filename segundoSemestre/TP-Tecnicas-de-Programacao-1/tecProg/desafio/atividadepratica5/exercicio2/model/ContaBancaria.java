package model;

public class ContaBancaria {
    private double saldo;
    private String titular;


    public double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(double saldo) {
        System.out.println("Seu saldo é: " + saldo);
    }



    public double getDepositar() {
        return this.saldo;
    }

    public void setDepositar(double novoDeposito, String titular) {
        if(novoDeposito > 0) {
            this.saldo += novoDeposito;
            System.out.println("Depósito de: R$" + novoDeposito + " realizado com sucesso " + titular);
        } else {
            System.out.println("Depósito inválido" + titular);
        }
    }

    
    public double getSacar() {
        return this.saldo;
    }

    public void setSacar(double novoSaque, String titular) {
        if (novoSaque > 0 && novoSaque <= this.saldo) {
            this.saldo -= novoSaque;
            System.out.println("Saque de: R$ " + novoSaque + " realizado com sucesso " + titular);
        } else {
            System.out.println("Saque inválido.");
        }
    }
}