package view;

import model.Produto;

public class Main {
    public static void main(String[] args) {
        Produto meuProduto = new Produto();

        meuProduto.setNome("Tenis");

        meuProduto.setPreco(399.89);
        
        meuProduto.setEstoque(10);
    }
}