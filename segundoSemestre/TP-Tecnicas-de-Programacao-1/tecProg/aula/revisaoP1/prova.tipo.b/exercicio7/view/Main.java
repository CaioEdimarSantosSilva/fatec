package view;
import model.Funcionario;

public class Main {
    public static void main(String[] args) {
        Funcionario maria = new Funcionario();
        maria.setNome("Maria");
        maria.setSalario(7.500);
        maria.setCargo("Diretora");

        System.out.println("Nome: " + maria.getNome());
        System.out.println("Salario: " + maria.getSalario());
        System.out.println("Cargo " + maria.getCargo());
    }
}
