package view;

import model.Livro;

public class Main {
    public static void main(String[] args) {

    Livro biblia = new Livro();
		biblia.titulo = "biblia";
		biblia.autor = "Deus";
		biblia.anoPublicacao = 90;
		
	System.out.println("Titulo: " + biblia.titulo + "\nAutor: " + biblia.autor + "\nAno Publicação: " + biblia.anoPublicacao); 
    }
}
