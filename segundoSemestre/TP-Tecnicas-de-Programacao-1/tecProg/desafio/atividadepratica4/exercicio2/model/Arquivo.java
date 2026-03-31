package atividadepratica4.exercicio2.model;

public class Arquivo {
    public String nome;
    public String estencao;

    public void enviarArquivo(){
        System.out.println("O arquivo "+ nome + estencao + " foi enviado!");
    }

    public void alterarArquivo(){
        System.out.println("O arquivo "+ nome + estencao + " foi alterado!");
    }
    
}
