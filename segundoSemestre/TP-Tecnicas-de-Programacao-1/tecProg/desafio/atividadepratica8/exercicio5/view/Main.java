package view;

import model.Conversor;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== EXERCÍCIO 5: SOBRECARGA DE MÉTODOS (CONVERSOR) ===\n");
        
        Conversor conversor = new Conversor();
        
        // Testando primeira versão: Celsius para Fahrenheit
        double temperaturaF = conversor.converter(25.0);
        System.out.println("Resultado: " + String.format("%.2f", temperaturaF) + "°F\n");
        
        // Testando segunda versão: Quilômetros para Milhas
        double distanciaMilhas = conversor.converter(100.0, "milhas");
        System.out.println("Resultado: " + String.format("%.2f", distanciaMilhas) + " milhas\n");
        
        // Testando terceira versão: String para maiúsculas
        String textoConvertido = conversor.converter("olá mundo");
        System.out.println("Resultado: " + textoConvertido + "\n");
        
        System.out.println("--- Explicação ---");
        System.out.println("A sobrecarga de métodos permite implementar várias versões");
        System.out.println("do método converter() com funcionalidades distintas.");
        System.out.println("\nComo funciona:");
        System.out.println("- O compilador identifica qual versão executar baseando-se");
        System.out.println("  nos tipos e quantidade de parâmetros fornecidos");
        System.out.println("- Mesmo nome de método, assinaturas diferentes");
        System.out.println("- Facilita o uso: um único nome para operações relacionadas");
        System.out.println("\nNeste exemplo:");
        System.out.println("1. converter(double) - recebe apenas temperatura");
        System.out.println("2. converter(double, String) - recebe distância e unidade");
        System.out.println("3. converter(String) - recebe texto");
    }
}