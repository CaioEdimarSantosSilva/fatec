package view;
import model.PostoCombustivel;

public class Main {
    public static void main(String[] args) {

        String[] tipos = { "Gasolina", "Etanol", "Diesel" };
        double[] precos = { 5.89, 4.29, 6.15 };
        int[] litros = { 5000, 3000, 4000 };

        PostoCombustivel posto = new PostoCombustivel(tipos, precos, litros);

        
        posto.exibirCombustiveis();

        
        double valorTotal = posto.calcularValorTotalEstoque();
        System.out.println("VALOR TOTAL EM ESTOQUE: R$ " + valorTotal);

       
        posto.analisarEstoque();

        posto.venderCombustivel(0, 50); 

        posto.reabastecerBomba(1, 2000); 

        posto.exibirCombustiveis();

        posto.analisarEstoque();

        valorTotal = posto.calcularValorTotalEstoque();
        System.out.println("VALOR TOTAL ATUALIZADO: R$ " + valorTotal);

    }
}
