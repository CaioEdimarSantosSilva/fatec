package view;

import model.ContaBancaria;

public class Main {
    public static void main(String[] args) {

        ContaBancaria conta = new ContaBancaria();
        conta.setTitular("Maria Santos");
        conta.setNumeroConta(98765);
        conta.setSaldo(2500.75);

        System.out.println("Titular: " + conta.getTitular());
        System.out.println("Número da Conta: " + conta.getNumeroConta());
        System.out.println("Saldo: R$ " + conta.getSaldo());
    }
}
