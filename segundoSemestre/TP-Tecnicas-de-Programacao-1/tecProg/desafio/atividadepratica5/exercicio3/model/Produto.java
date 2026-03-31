package model;

public class Produto {
    private String nome;
    private double preco;
    private int quantidadeEstoque;

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        System.out.println("O produto escolhido foi: " + nome);
        this.nome = nome;
    }

    public double getPreco(){
        return this.preco;
    }

    public void setPreco(double preco){
        if(preco < 0){
            System.out.println("Valor invalido!");
        }else{
            System.out.println("Seu produto escolhido custa: " + preco);
            this.preco = preco;
        }
    }

    public int getEstoque(){
        return this.quantidadeEstoque;
    }

    public void setEstoque(int quantidadeEstoque){
        if(quantidadeEstoque > 0){
            System.out.println("Quantidade desse produto no estoque é: " + quantidadeEstoque);
            this.quantidadeEstoque = quantidadeEstoque;
        }else{
            System.out.println("Não tem o produto no estoque");
        }
    }
}
