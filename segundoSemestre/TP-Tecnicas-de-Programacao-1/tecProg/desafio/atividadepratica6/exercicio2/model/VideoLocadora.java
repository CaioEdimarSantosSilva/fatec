package model;
/* Exercício 2 – Videolocadora com Controle de Popularidade
Crie uma classe Videolocadora com:

Um array de String chamado filmes;
Um array de int chamado vezesAlugado;
Um array de double chamado precosLocacao.
Implemente métodos para:

Listar os filmes com seus preços e quantas vezes já foram alugados.
Calcular o faturamento total esperado se todos os filmes fossem alugados uma vez.
Descobrir qual filme foi mais alugado.
Alugar um filme, registrando +1 no contador do vezesAlugado.
Devolver um filme (apenas informar que foi devolvido, sem remover do array).*/

public class VideoLocadora {
    private String[] filmes;
    private int[] vezesAlugado;
    private double[] precosLocacao;

    public VideoLocadora() {
        this.filmes = new String[0];
        this.vezesAlugado = new int[0];
        this.precosLocacao = new double[0];
    }

    /* get e set do filmes */
    public String[] getfilmes() {
        return this.filmes;
    }
    public void setfilmes(String[] nomes) {
        this.filmes = nomes;
    }

    /* get e set do vezesAlugado */
    public int[] getVezesAlugado() {
        return this.vezesAlugado;
    }

    public void setVezesAlugado(int[] vezesAlugado) {
        this.vezesAlugado = vezesAlugado;
    }

    /* get e set do precosLocacao */
    public double[] getPrecosLocacao() {
        return this.precosLocacao;
    }
    public void setPrecosLocacao(double[] precos) {
        this.precosLocacao = precos;
    }

    public void listarFilmes() {
        System.out.println("Todos os filmes:");
        for (int i = 0; i < filmes.length; i++) {
            System.out.println(" - " + filmes[i] + ", R$ " + precosLocacao[i] + ", " + vezesAlugado[i] + " em estoque.");
        }
    }

    public void valorTotal() {
        double valorTotal = 0;
        for (int i = 0; i < filmes.length; i++) {
            valorTotal += precosLocacao[i];
        }
        System.out.println("Valor total é R$ " + valorTotal);
    }

    public void maisCaroMaisBarato(String[] nomes, double[] precos) {
        double maiorPreco = precos[0];
        double menorPreco = precos[0];
        String maiorPrecoNome = nomes[0];
        String menorPrecoNome = nomes[0];
        for (int i = 1; i < precos.length; i++) {
            if (precos[i] > maiorPreco) {
                maiorPreco = precos[i];
                maiorPrecoNome = nomes[i];
            }
            if (precos[i] < menorPreco) {
                menorPreco = precos[i];
                menorPrecoNome = nomes[i];
            }
        }
        System.out.println("O produto com maior preço é: " + maiorPrecoNome + " com o valor: R$ " + maiorPreco);
        System.out.println("O produto com menor preço é: " + menorPrecoNome + " com o valor: R$ " + menorPreco);
    }

    public void alugar(String nome){
        for (int i = 0; i < filmes.length; i++) {
            if(nome == filmes[i]){
                vezesAlugado[i]++;
                System.out.println("O filme foi alugado, esta sendo alugado pela: " + vezesAlugado[i] + "° vez!");
            }
        }
    }
    public void devolver(String nome){
        System.out.println("O filme " + nome + " foi devolvido!");
    }



}
