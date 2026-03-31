package model;

public class Calculadora implements OperacaoMatematica {
	
	private int n1;
	private int n2;
	
	public Calculadora(int n1, int n2) {
		this.n1 = n1;
		this.n2 = n2;
	}

	public int getN1() {
		return n1;
	}
	public void setN1(int n1) {
		this.n1 = n1;
	}
	public int getN2() {
		return n2;
	}
	public void setN2(int n2) {
		this.n2 = n2;
	}
	
    
	public int soma() {
		return n1 + n2;
	}
	
	public int subtracao() {
		return n1 - n2;
	}
	
	public int multiplicacao() {
		return n1 * n2;
	}
	
	public int divisao() {
    try {
        return n1 / n2;
    } catch (ArithmeticException e) {
        System.out.println("Erro: divisão por zero!");
        return 0;
    }
}
	
	
}