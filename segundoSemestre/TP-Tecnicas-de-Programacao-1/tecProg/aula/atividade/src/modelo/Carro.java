package modelo;

public class Carro extends Veiculo {
    private boolean ac;
    private double portaMala;

    public Carro(String marca, String modelo, double tanque, int ano, boolean novo,
                 boolean ac, double portaMala, double capacidade) {
        super(marca, modelo, tanque, capacidade, ano, novo); 
        this.ac = ac;
        this.portaMala = portaMala;
    }

    public boolean isAc() {
        return ac;
    }

    public void setAc(boolean ac) {
        this.ac = ac;
    }

    public double getPortaMala() {
        return portaMala;
    }

    public void setPortaMala(double portaMala) {
        this.portaMala = portaMala;
    }
    
    public String estacionar() {
        return "Estacionado!";
    }
}

	
	

