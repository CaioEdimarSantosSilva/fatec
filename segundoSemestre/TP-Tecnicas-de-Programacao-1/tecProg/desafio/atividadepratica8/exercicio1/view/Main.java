package view;

import model.Veiculo;
import model.Carro;
import model.Bicicleta;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== EXERCÍCIO 1: HIERARQUIA DE VEÍCULOS ===\n");
        Veiculo veiculo1 = new Carro();
        Veiculo veiculo2 = new Bicicleta();
        veiculo1.mover();
        veiculo2.mover();
        System.out.println("\n--- Explicação do Polimorfismo ---");
        System.out.println("A sobrescrita de métodos permite que objetos da mesma");
        System.out.println("hierarquia tenham comportamentos diferentes através do");
        System.out.println("método mover(). Cada subclasse implementa sua própria versão.");
    }
}
