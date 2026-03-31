package view;

import model.Pagamento;
import model.PagamentoCartao;
import model.PagamentoBoleto;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== EXERCÍCIO 4: SOBRESCRITA DE MÉTODOS (PAGAMENTO) ===\n");
        Pagamento pagamento1 = new PagamentoCartao();
        Pagamento pagamento2 = new PagamentoBoleto();
        Pagamento pagamento3 = new Pagamento();
        System.out.println("Processando diferentes formas de pagamento:\n");
        pagamento1.processarPagamento();
        pagamento2.processarPagamento();
        pagamento3.processarPagamento();
        System.out.println("\n--- Explicação ---");
        System.out.println("O polimorfismo por sobrescrita facilita a implementação de");
        System.out.println("diferentes formas de pagamento dentro de um sistema sem alterar");
        System.out.println("o código da classe base.");
        System.out.println("\nVantagens:");
        System.out.println("1. Facilita manutenção: novas formas de pagamento podem ser");
        System.out.println("   adicionadas sem modificar código existente");
        System.out.println("2. Código mais limpo: não precisa de vários if/else");
        System.out.println("3. Extensibilidade: fácil adicionar PagamentoPix, etc.");
    }
}