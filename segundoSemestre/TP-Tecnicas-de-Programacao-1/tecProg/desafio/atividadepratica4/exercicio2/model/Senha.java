package atividadepratica4.exercicio2.model;

public class Senha {
    public String senhaAtual;
    public String novaSenha;

    public void verficarSenha(){
        if(senhaAtual.equals(novaSenha)){
            System.out.println("A senha esta correta!");
        }
        else{
            System.out.println("A senha esta incorreta!");
        }
    }
}
