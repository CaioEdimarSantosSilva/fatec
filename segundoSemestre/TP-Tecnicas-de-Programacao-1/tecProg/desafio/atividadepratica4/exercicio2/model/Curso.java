package atividadepratica4.exercicio2.model;

public class Curso {
    public String professor;
    public String materia;

    public void aplicarProva(){
        System.out.println("A prova vai ser aplicada pelo professor " + professor);
    }

    public void exibirMateria(){
        System.out.println("Uma das matérias do curso é " + materia);
    }
}
