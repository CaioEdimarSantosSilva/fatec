package atividadepratica4.exercicio2.model;

public class Jogo {
    public String nome;
    public boolean jogando = false;

    public void entrarJogo(){
        if(!jogando){
            System.out.println("Jogo " + nome + " foi iniciado!!!");
            jogando = true;
        }
        else{
            System.out.println("Jogo já esta em andamento!");
        }
    }
    public void sairJogo(){
        if(jogando){
            System.out.println("Jogo " + nome + " Encerrado!!!");
            jogando = false;
        }
        else{
            System.out.println("Inicie o jogo primeiro!");
        }
    }
}
