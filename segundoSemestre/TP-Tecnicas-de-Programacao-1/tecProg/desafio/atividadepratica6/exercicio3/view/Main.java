package view;

import model.Biblioteca;

public class Main {
    public static void main(String[] args) {

        String[] livros = {"Dom Casmurro", "1984", "O Cortiço"};
        int[] diasAtraso = {5, 0, 10};
        double[] multaPorDia = {2.2, 1.3, 1.5};

        Biblioteca bib = new Biblioteca(livros, diasAtraso, multaPorDia);

        bib.exibirLivros();
        bib.multaTotal();
        bib.mediaDiasAtraso();
        bib.emprestarLivro("1984");
        bib.devolverLivro("1984", 3, 2.0);
        bib.exibirLivros();
    }
}
