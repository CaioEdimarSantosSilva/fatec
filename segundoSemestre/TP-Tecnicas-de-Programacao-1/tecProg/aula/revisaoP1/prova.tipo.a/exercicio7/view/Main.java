package view;
import model.Aluno;

public class Main {
    public static void main(String[] args) {
        Aluno carlos = new Aluno();
        carlos.setNome("Carlos");
        carlos.setMatricula(134553);
        carlos.setNotaFinal(7.5);

        System.out.println("Nome: " + carlos.getNome());
        System.out.println("Matricula: " + carlos.getMatricula());
        System.out.println("Nota final " + carlos.getNotaFinal());
    }
}
