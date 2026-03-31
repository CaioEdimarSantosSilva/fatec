package view;
import model.PagamentoCartao;
import model.PagamentoDinheiro;

/*Interface de Pagamentos
Crie uma interface chamada Pagamento com os seguintes métodos:
calcularPagamento(): Sem parâmetros, deve ser implementado para calcular o valor do pagamento.
emitirRecibo(): Retorna um recibo detalhado da transação.

Agora, implemente essa interface em duas classes: PagamentoCartao e PagamentoDinheiro.

A classe PagamentoCartao deve incluir uma taxa de 5% sobre o valor do pagamento.

A classe PagamentoDinheiro deve aplicar um desconto de 10% sobre o valor do pagamento.

No final, crie uma classe de teste que simule o pagamento de R$100,00 com as duas formas de pagamento,
calculando o valor final e emitindo um recibo.
*/

public class Main {

	public static void main(String args[]) {
		PagamentoDinheiro pagamentoDinheiro = new PagamentoDinheiro(100, 0.1);
	    System.out.println(pagamentoDinheiro.emitirRecibo());

	    System.out.println();

	    PagamentoCartao pagamentoCartao = new PagamentoCartao(100, 0.05);
	    System.out.println(pagamentoCartao.emitirRecibo());
	}
}