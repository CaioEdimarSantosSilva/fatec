package view;

import model.Loja;

public class Main {
    public static void main(String[] args) {
        Loja americanas = new Loja();
        americanas.setNomesProdutos(new String[] { "lapis", "borracha", "bolsa" });
        americanas.setQuantidadeEstoque(new int[] { 5, 10, 7 });
        americanas.setPrecosProdutos(new double[] { 11.99, 4.50, 150.25 });

        americanas.exibirProdutos(americanas.getNomesProdutos(), americanas.getQuantidadeEstoque(),
                americanas.getPrecosProdutos());
        System.out.println();
        americanas.maisCaroMaisBarato(americanas.getNomesProdutos(), americanas.getPrecosProdutos());
        System.out.println();
        americanas.valorTotal(americanas.getNomesProdutos(), americanas.getQuantidadeEstoque(),
                americanas.getPrecosProdutos());
        System.out.println();
        americanas.comprar("lapis", 2);
        System.out.println();
        americanas.repor("lapis", 7);
        System.out.println();
    }
}
