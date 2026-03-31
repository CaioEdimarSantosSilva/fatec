package view;
import model.SuperMercado;

public class Main {
    public static void main(String[] args) {
        String[] nomes = {"Arroz", "Feijão", "Macarrão"};
        double[] precos = {20.0, 10.0, 8.0};
        double[] descontos = {0.10, 0.20, 0.05};

        SuperMercado extra = new SuperMercado(nomes, precos, descontos);

        System.out.println("=== Lista de Produtos ===");
        for (String p : extra.listarProdutos()) {
            System.out.println(p);
        }

        System.out.println("\nTotal da compra com descontos: R$" +
                           extra.calcularTotalComDesconto());

        System.out.println("\n" + extra.produtoMaiorEconomia());

        if (extra.comprarProduto("Feijão")) {
            System.out.println("\nFeijão comprado!");
        }
        for (String p : extra.listarProdutos()) {
            System.out.println(p);
        }

        extra.reporProduto("Feijão", 10.0, 0.20);
        System.out.println("\nFeijão reposto!");
        for (String p : extra.listarProdutos()) {
            System.out.println(p);
        }
    }
}
