package model;

public class Produto {
    private String nome;
    private double preco;
    private int quantidadeEstoque;
    public Produto(String nome) {
        this.nome = nome;
        this.preco = 0.0;
        this.quantidadeEstoque = 0;
    }
    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEstoque = 0;
    }
    public Produto(String nome, double preco, int quantidadeEstoque) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }
    public void exibirInformacoes() {
        System.out.println("Produto: " + nome);
        System.out.println("Preço: R$ " + String.format("%.2f", preco));
        System.out.println("Quantidade em Estoque: " + quantidadeEstoque);
        System.out.println("------------------------");
    }
    public String getNome() {
        return nome;
    }
    public double getPreco() {
        return preco;
    }
    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
}