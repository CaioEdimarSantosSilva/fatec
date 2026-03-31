package atividadepratica4.exercicio1.model;
public class Carro {
    public String cor;
    public String modelo;
    public int capacidadeTanque;

    private boolean ligado = false;

    public void ligar(){
        if(!ligado) {
            ligado = true;
            System.out.println("o carro ligado.");
        } else {
            System.out.println("O carro ja está ligado.");
        }
    }

    public void acelerar() {
        if(ligado){
            System.out.println("Carro andando");
        } else {
            System.out.println("Ligue o carro primeiro");
            this.ligar();
            this.acelerar();
        }
    }

    public void desligar() {
        if (ligado) {
            ligado = false;
            System.out.println("Carro desligado");
        }else {
            System.out.println("O carro já está desligado ligue ele primeiro");
        }
    }

    public void freiar() {
        if(ligado) {
            System.out.println("O carro está freiando");
        } else {
            System.out.println("Você está freiando parado.");
        }
    }

    public void buzinar(){
        if (ligado) {
            System.out.println("Biiiiiiiiiiiph");
        }else {
            System.out.println("...");
        }
    }
}
