package model;

public class Biblioteca {
    private String[] livros;
    private int[] diasAtraso;
    private double[] multaPorDia;

    public Biblioteca(String[] livros, int[] diasAtraso, double[] multaPorDia) {
        this.livros = livros;
        this.diasAtraso = diasAtraso;
        this.multaPorDia = multaPorDia;
    }

    public void exibirLivros() {
        for (int c = 0; c < livros.length; c++) {
            System.out.println(
                    livros[c] + " || Dias de atraso: " + diasAtraso[c] + " || Multa: " + multaPorDia[c]);
        }
    }

    public void multaTotal() {
        double multaAcumulada = 0;

        for (int c = 0; c < livros.length; c++) {
            multaAcumulada += multaPorDia[c];
        }

        System.out.println("Multa total acumulada: " + multaAcumulada);
    }

    public void mediaDiasAtraso() {
        int totalDiasAtraso = 0;
        int livrosComAtraso = 0;

        for (int c = 0; c < livros.length; c++) {
            if (diasAtraso[c] > 0) {
                totalDiasAtraso += diasAtraso[c];
                livrosComAtraso++;
            }
        }

        if (livrosComAtraso > 0) {
            double media = (double) totalDiasAtraso / livrosComAtraso;
            System.out.println("Média de dias de atraso: " + media);
        } else {
            System.out.println("Nenhum livro com atraso!");
        }
    }

    // 4. Emprestar livro (remove temporariamente)
    public void emprestarLivro(String nomeLivro) {
        for (int c = 0; c < livros.length; c++) {
            if (livros[c] != null && livros[c].equals(nomeLivro)) {
                System.out.println("Livro '" + nomeLivro + "' emprestado!");
                livros[c] = null; // Remove temporariamente
                diasAtraso[c] = 0;
                multaPorDia[c] = 0;
                return;
            }
        }
        System.out.println("Livro não encontrado!");
    }

    public void devolverLivro(String nomeLivro, int diasDeAtraso, double multaDiaria) {
        for (int c = 0; c < livros.length; c++) {
            if (livros[c] == null) {
                livros[c] = nomeLivro;
                diasAtraso[c] = diasDeAtraso;
                multaPorDia[c] = multaDiaria;
                
                if (diasDeAtraso > 0) {
                    double multaTotal = diasDeAtraso * multaDiaria;
                    System.out.println("Livro '" + nomeLivro + "' devolvido com " + diasDeAtraso + 
                                     " dias de atraso. Multa: R$" + multaTotal);
                } else {
                    System.out.println("Livro '" + nomeLivro + "' devolvido sem atraso!");
                }
                return;
            }
        }
        System.out.println("Não há espaço disponível!");
    }
}