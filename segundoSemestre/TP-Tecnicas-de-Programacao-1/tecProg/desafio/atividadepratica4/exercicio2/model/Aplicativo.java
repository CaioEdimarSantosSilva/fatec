package atividadepratica4.exercicio2.model;

public class Aplicativo {
    public String nome;
    public boolean instalado = false;

    public void intalarAplicativo(){
        if(instalado){
            System.out.println("O aplicativo " + nome + " já foi instalado!");
        }
        else{
            System.out.println("O aplicativo " + nome + " instalado!");
            instalado = true;
        }
    }

    public void desistalarAplicativo(){
        if(instalado){
            System.out.println("O aplicativo " + nome + " desistalado!");
            instalado = false;
        }
        else{
            System.out.println("O aplicativo " + nome + " não foi instalado!");
            intalarAplicativo();
            desistalarAplicativo();
        }
    }

    public void execultarAplicativo(){
        if(instalado){
            System.out.println("O aplicativo " + nome + " executando!");
        }
        else{
            System.out.println("O aplicativo " + nome + " não esta instalado para execultar!");
            intalarAplicativo();
            execultarAplicativo();
        }
    }


}
