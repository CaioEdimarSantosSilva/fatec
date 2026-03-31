package atividadepratica4.exercicio2.model;

public class Aula {
    public String materia;
    public double[] notas;
    public double somaNotas = 0;
    public double media;

    public void mostrarNotas() {
        for(int c = 0; c < notas.length; c++){
            System.out.print("Sua " + (c+1) + "º nota é " + notas[c]);
        }
        System.out.println(" ");
    }

    public void mostrarMedia() {
        for (double nota : notas) {
            somaNotas += nota;
        }
        media = somaNotas / notas.length;
        System.out.printf("Sua média é %.2f!\n", media, "!");
    }

}
