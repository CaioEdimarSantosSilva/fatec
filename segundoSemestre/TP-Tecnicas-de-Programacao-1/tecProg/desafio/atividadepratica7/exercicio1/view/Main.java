package view;
/* Apenas para praticar, crie uma interface chamada "OperacaoMatematica". 
Crie também 4 métodos das operações básicas: 
soma, subtração, multiplicação e divisão implemente na classe Calculadora.
Implementar algum dos métodos  e veja o que acontece. */
import model.Calculadora2;

public class Main {

	public static void main(String[] args) {
		
		Calculadora2 Edison = new Calculadora2(20,7);
		
		System.out.println("A soma é: " + Edison.soma());
		System.out.println("A subtração é: " + Edison.subtracao());
		System.out.println("A multiplicação é: " + Edison.multiplicacao());
		System.out.println("A divisão é: " + Edison.divisao());
	}
}