package model;

public class Compra {
    private String prod;
    private double valor;

    public Compra(String prod, double valor) {
        super();
        this.prod = prod;
        this.valor = valor;
    }
    public String finalizarCompra(Cupom cup) {
        
        return "Compra Relizada, " + "Valor total: " + (this.valor - cup.getValor());
    }
    @Override
    public String toString() {
        return "Compra [prod=" + prod + ", valor=" + valor + "]";
    }
}
