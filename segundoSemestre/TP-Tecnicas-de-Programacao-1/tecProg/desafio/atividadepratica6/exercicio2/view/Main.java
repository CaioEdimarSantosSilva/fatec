package view;

import model.VideoLocadora;

public class Main {
    public static void main(String[] args) {
        VideoLocadora FilmesCity = new VideoLocadora();
        FilmesCity.setfilmes(new String[] { "Carros", "Shrek", "Como treinar seu dragão" });
        FilmesCity.setVezesAlugado(new int[] { 5, 10, 7 });
        FilmesCity.setPrecosLocacao(new double[] { 16.99, 24.50, 20.25 });

        FilmesCity.listarFilmes();
        System.out.println();
        FilmesCity.valorTotal();
        System.out.println();
        FilmesCity.alugar("Carros");
        System.out.println();
        FilmesCity.devolver("Shrek");
        System.out.println();
    }
}
