package model;

public class SistemaDeSeguranca implements IAutenticavel {

    private String usuario = "Admin";
    private String senha = "1234";
    private boolean logado = false;

	public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean login(String usuario, String senha) {
        if (this.usuario.equals(usuario) && this.senha.equals(senha)) {
            System.out.println("Login realizado com sucesso!");
            logado = true;
            return logado;
        } else {
            System.out.println("Usuário ou senha incorretos. Tente novamente.\n");
            return logado;
        }
    }

    public void logout() {
        if (logado) {
            logado = false;
            System.out.println("Logout realizado com sucesso!");
        } else {
            System.out.println("Nenhum usuário autenticado.");
        }
    }

    public boolean isAutenticado() {
        return logado;
    }
}