package atividadepratica4.exercicio2.model;

public class Avaliacao {
    public String nome;
    public double data;
    public int nota;
    public String horarioFinalizacao;

    public void exibirNota(){
        System.out.println("A nota da avaliação do " + nome + " é: " +  nota + "!");
    }

    public void exibirHorarioFinal(){
        System.out.println("O horario de finalização da prova é as " + horarioFinalizacao);
    }


}
