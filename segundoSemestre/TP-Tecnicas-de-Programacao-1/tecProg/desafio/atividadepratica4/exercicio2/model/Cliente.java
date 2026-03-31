package atividadepratica4.exercicio2.model;

public class Cliente {
    public double valorCompra;
    public double saldoCliente;

    public void pagarCompra(){
        if(valorCompra <= saldoCliente){
            System.out.println("Compra paga!");
        }
        else{
            System.out.println("Saldo insuficiente para pagar!");
        }
    }


}
