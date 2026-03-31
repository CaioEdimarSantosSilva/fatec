package modelo;

public class Gerente extends Colaborador implements IFuncionario {
	private boolean caixa = false;
	
	public Gerente(String nome, int codigo) {
		super(nome, codigo);
	}

	public String baterPonto() {
		return "Registro Realizado";
	}
	
	public boolean isCaixa() {
		return caixa;
	}

	public String fecharCaixa() {
		if(this.isCaixa()) {
			this.caixa = false;
			return "Caixa Fechado";
		} else {
			return "O Caixa Já está fechado!";
		}
	}
	
	public String abrirCaixa() {
		if(!this.isCaixa()) {
			this.caixa = true;
			return "Caixa Aberto";
		} else {
			return "O Caixa Já está aberto";
		}
	}
	
	
}
