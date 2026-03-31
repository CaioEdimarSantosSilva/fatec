package view;

import model.Calculadora2;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== EXERCÍCIO 2: SOBRECARGA DE MÉTODOS ===\n");
        Calculadora2 calc = new Calculadora2();
        int resultado1 = calc.somar(5, 10);
        System.out.println("Resultado: " + resultado1 + "\n");
        int resultado2 = calc.somar(3, 7, 12);
        System.out.println("Resultado: " + resultado2 + "\n");
        double resultado3 = calc.somar(4.5, 3.2);
        System.out.println("Resultado: " + resultado3 + "\n");
        System.out.println("--- Explicação ---");
        System.out.println("Sobrecarga (Overload) permite criar várias versões do mesmo");
        System.out.println("método com diferentes assinaturas (número ou tipo de parâmetros).");
        System.out.println("O compilador escolhe qual versão executar com base nos argumentos.");
        System.out.println("\nDiferença para Sobrescrita (Override):");
        System.out.println("- Sobrecarga: mesmo método, assinaturas diferentes, mesma classe");
        System.out.println("- Sobrescrita: mesmo método, mesma assinatura, classes diferentes (herança)");
    }
}
