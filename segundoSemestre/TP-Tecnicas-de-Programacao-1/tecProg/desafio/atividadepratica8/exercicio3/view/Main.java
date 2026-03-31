package view;

import model.Produto;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== EXERCÍCIO 3: SOBRECARGA DE CONSTRUTORES ===\n");
        
        // Usando o primeiro construtor
        Produto produto1 = new Produto("Notebook");
        produto1.exibirInformacoes();
        
        // Usando o segundo construtor
        Produto produto2 = new Produto("Mouse", 45.90);
        produto2.exibirInformacoes();
        
        // Usando o terceiro construtor
        Produto produto3 = new Produto("Teclado", 120.50, 15);
        produto3.exibirInformacoes();
        
        System.out.println("--- Explicação ---");
        System.out.println("A sobrecarga de construtores facilita a criação de objetos");
        System.out.println("com diferentes conjuntos de informações iniciais.");
        System.out.println("Isso proporciona flexibilidade na instanciação, permitindo");
        System.out.println("criar objetos com apenas os dados disponíveis no momento.");
    }
}