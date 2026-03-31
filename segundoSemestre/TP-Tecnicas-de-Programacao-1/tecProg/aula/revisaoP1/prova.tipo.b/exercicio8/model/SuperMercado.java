package model;

public class SuperMercado {
    private String[] nomesProdutos;
    private double[] precos;
    private double[] descontos;

    public SuperMercado(String[] nomes, double[] precos, double[] descontos) {
        this.nomesProdutos = nomes;
        this.precos = precos;
        this.descontos = descontos;
    }
    public String[] listarProdutos() {
        String[] lista = new String[nomesProdutos.length];
        for (int i = 0; i < nomesProdutos.length; i++) {
            double precoOriginal = precos[i];
            double precoDesconto = precoOriginal * (1 - descontos[i]);
            lista[i] = nomesProdutos[i] + " | Preço: R$" + precoOriginal +
                       " | Com desconto: R$" + precoDesconto;
        }
        return lista;
    }
    public double calcularTotalComDesconto() {
        double total = 0;
        for (int i = 0; i < precos.length; i++) {
            total += precos[i] * (1 - descontos[i]);
        }
        return total;
    }
    public String produtoMaiorEconomia() {
        double maiorEconomia = 0;
        String produto = "";
        for (int i = 0; i < precos.length; i++) {
            double economia = precos[i] * descontos[i];
            if (economia > maiorEconomia) {
                maiorEconomia = economia;
                produto = nomesProdutos[i];
            }
        }
        return produto + " gera a maior economia: R$" + maiorEconomia;
    }

    public boolean comprarProduto(String nome) {
        int index = -1;
        for (int i = 0; i < nomesProdutos.length; i++) {
            if (nomesProdutos[i].equalsIgnoreCase(nome)) {
                index = i;
                break;
            }
        }
        if (index == -1) return false;

        String[] novosNomes = new String[nomesProdutos.length - 1];
        double[] novosPrecos = new double[precos.length - 1];
        double[] novosDescontos = new double[descontos.length - 1];

        int j = 0;
        for (int i = 0; i < nomesProdutos.length; i++) {
            if (i == index) continue;
            novosNomes[j] = nomesProdutos[i];
            novosPrecos[j] = precos[i];
            novosDescontos[j] = descontos[i];
            j++;
        }

        nomesProdutos = novosNomes;
        precos = novosPrecos;
        descontos = novosDescontos;
        return true;
    }

    public void reporProduto(String nome, double preco, double desconto) {
        String[] novosNomes = new String[nomesProdutos.length + 1];
        double[] novosPrecos = new double[precos.length + 1];
        double[] novosDescontos = new double[descontos.length + 1];

        for (int i = 0; i < nomesProdutos.length; i++) {
            novosNomes[i] = nomesProdutos[i];
            novosPrecos[i] = precos[i];
            novosDescontos[i] = descontos[i];
        }

        novosNomes[novosNomes.length - 1] = nome;
        novosPrecos[novosPrecos.length - 1] = preco;
        novosDescontos[novosDescontos.length - 1] = desconto;

        nomesProdutos = novosNomes;
        precos = novosPrecos;
        descontos = novosDescontos;
    }
}

