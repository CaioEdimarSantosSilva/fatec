package model;

public class Cupom {
    private String cod;
    private double valor;
    public Cupom(String cod, double valor) {
        super();
        this.cod = cod;
        this.valor = valor;
    }

    public String getCod() {
        return cod;
    }
    public void setCod(String cod) {
        this.cod = cod;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Cupom [cod=" + cod + ", valor=" + valor + "]";
    }
}
