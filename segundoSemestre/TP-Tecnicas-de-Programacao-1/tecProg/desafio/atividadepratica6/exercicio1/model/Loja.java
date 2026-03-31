package model;

public class Loja {
    private String[] nomesProdutos;
    private int[] quantidadeEstoque;
    private double[] precosProdutos;

    public Loja(String[] nomes, int[] quantidades, double[] precos){
        this.nomesProdutos = nomes;
        this.precosProdutos = precos;
        this.quantidadeEstoque = quantidades;
    }
    
    public void exibirProdutos(){
        for(int c = 0; c < nomesProdutos.length; c++){
            System.out.println(nomesProdutos[c] + " || Quantidade: " + quantidadeEstoque[c] + " || Preços: " + precosProdutos[c]);
        }
    }

    public void calcularCaroBarato(){
        double precoCaro = precosProdutos[0];
        double precoBarato = precosProdutos[0];
        String nomeCaro = nomesProdutos[0];
        String nomeBarato = nomesProdutos[0];

        for(int c = 0; c < precosProdutos.length; c++){
            if(precosProdutos[c] > precoCaro){
                precoCaro = precosProdutos[c];
                nomeCaro = nomesProdutos[c];
            }
            if(precosProdutos[c] < precoBarato){
                precoBarato = precosProdutos[c];
                nomeBarato = nomesProdutos[c];
            }
        }
        System.out.println("Mais caro: " + nomeCaro + " || Preço: " + precoCaro);
        System.out.println("Mais barato: " + nomeBarato + " || Preço: " + precoBarato);
    }

    public void calcularValorTotal(){
        double valorTotal = 0;

        for(int c = 0; c < precosProdutos.length; c++){
            valorTotal += precosProdutos[c] * quantidadeEstoque[c];
        }

        System.out.println("Valor total dos produtos no estoque: " + valorTotal);
    }

    public void compraProduto(String nome, int quantidade){
        for(int c = 0; c < quantidadeEstoque.length; c++){
            if(nomesProdutos[c].equals(nome)){
                if(quantidadeEstoque[c] >= quantidade){
                    quantidadeEstoque[c] -= quantidade;
                    System.out.println("Compra realizada");
                }
            }
        }
    }

    public void reporProduto(String nome, int quantidade) {
        for (int i = 0; i < nomesProdutos.length; i++) {
            if (nomesProdutos[i].equals(nome)) {
                quantidadeEstoque[i] += quantidade;
                System.out.println("Reposição realizada!");
            }
        }
    }

}