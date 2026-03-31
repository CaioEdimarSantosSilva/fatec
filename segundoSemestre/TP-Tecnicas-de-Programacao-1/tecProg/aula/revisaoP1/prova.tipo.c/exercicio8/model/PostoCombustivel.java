package model;

public class PostoCombustivel {
    private String[] tiposCombustivel;
    private double[] precosLitro;
    private int[] litrosDisponiveis;

    public PostoCombustivel(String[] tipos, double[] precos, int[] litros) {
        this.tiposCombustivel = tipos;
        this.precosLitro = precos;
        this.litrosDisponiveis = litros;
    }

    public void exibirCombustiveis() {
        for (int i = 0; i < tiposCombustivel.length; i++) {
            System.out.println("Tipo: " + tiposCombustivel[i]);
            System.out.println("Preço/Litro: R$ " + precosLitro[i]);
            System.out.println("Estoque: " + litrosDisponiveis[i] + " litros");
        }
    }

    public double calcularValorTotalEstoque() {
        double valorTotal = 0;
        for (int i = 0; i < tiposCombustivel.length; i++) {
            valorTotal += precosLitro[i] * litrosDisponiveis[i];
        }
        return valorTotal;
    }

    public void analisarEstoque() {
        int indiceMaior = 0;
        int indiceMenor = 0;

        for (int i = 1; i < litrosDisponiveis.length; i++) {
            if (litrosDisponiveis[i] > litrosDisponiveis[indiceMaior]) {
                indiceMaior = i;
            }
            if (litrosDisponiveis[i] < litrosDisponiveis[indiceMenor]) {
                indiceMenor = i;
            }
        }

        System.out.println("MAIOR ESTOQUE:");
        System.out.println("  " + tiposCombustivel[indiceMaior] + ": " + litrosDisponiveis[indiceMaior] + " litros");
        System.out.println("\nMENOR ESTOQUE:");
        System.out.println("  " + tiposCombustivel[indiceMenor] + ": " + litrosDisponiveis[indiceMenor] + " litros");
    }

    public boolean venderCombustivel(int indiceCombustivel, int litros) {
        if (indiceCombustivel < 0 || indiceCombustivel >= tiposCombustivel.length) {
            System.out.println("Erro: Combustível inválido!");
            return false;
        }

        if (litros > litrosDisponiveis[indiceCombustivel]) {
            System.out.println("\nErro: Estoque insuficiente!");
            System.out.println("Disponível: " + litrosDisponiveis[indiceCombustivel] + " litros");
            return false;
        }

        litrosDisponiveis[indiceCombustivel] -= litros;
        double valorVenda = litros * precosLitro[indiceCombustivel];

        System.out.println("Combustível: " + tiposCombustivel[indiceCombustivel]);
        System.out.println("Quantidade: " + litros + " litros");
        System.out.println("Valor Total: R$ " + valorVenda);
        System.out.println("Estoque Restante: " + litrosDisponiveis[indiceCombustivel] + " litros");

        return true;
    }

    public void reabastecerBomba(int indiceCombustivel, int litros) {
        if (indiceCombustivel < 0 || indiceCombustivel >= tiposCombustivel.length) {
            System.out.println("Erro: Combustível inválido!");
            return;
        }
        litrosDisponiveis[indiceCombustivel] += litros;
        System.out.println("Combustível: " + tiposCombustivel[indiceCombustivel]);
        System.out.println("Quantidade Adicionada: " + litros + " litros");
        System.out.println("Novo Estoque: " + litrosDisponiveis[indiceCombustivel] + " litros");
    }
}
