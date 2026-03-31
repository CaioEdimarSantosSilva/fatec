package atividadepratica4.exercicio2.view;

import atividadepratica4.exercicio2.model.Aplicativo;
import atividadepratica4.exercicio2.model.Arquivo;
import atividadepratica4.exercicio2.model.Aula;
import atividadepratica4.exercicio2.model.Avaliacao;
import atividadepratica4.exercicio2.model.BancoDados;
import atividadepratica4.exercicio2.model.Cliente;
import atividadepratica4.exercicio2.model.ContaBancaria;
import atividadepratica4.exercicio2.model.Curso;
import atividadepratica4.exercicio2.model.Jogo;
import atividadepratica4.exercicio2.model.Mensagem;
import atividadepratica4.exercicio2.model.Pedido;
import atividadepratica4.exercicio2.model.Produto;
import atividadepratica4.exercicio2.model.Projeto;
import atividadepratica4.exercicio2.model.RedeSocial;
import atividadepratica4.exercicio2.model.Relatorio;
import atividadepratica4.exercicio2.model.Senha;

public class Main {
    public static void main(String[] args) {
        System.out.println();

        Aplicativo Paint = new Aplicativo();
        Paint.nome = "Paint";
        Paint.intalarAplicativo();

        System.out.println();

        Aplicativo Steam = new Aplicativo();
        Steam.nome = "Steam";
        Steam.execultarAplicativo();

        System.out.println();

        Arquivo Html = new Arquivo();
        Html.nome = "index";
        Html.estencao = ".html";
        Html.enviarArquivo();

        System.out.println();

        Arquivo Css = new Arquivo();
        Css.nome = "style";
        Css.estencao = ".css";
        Css.alterarArquivo();

        System.out.println();

        Aula Portugues = new Aula();
        Portugues.notas = new double[] { 2, 7, 10 };
        Portugues.mostrarNotas();

        System.out.println();

        Aula Matematica = new Aula();
        Matematica.notas = new double[] { 6, 8, 9 };
        Matematica.mostrarMedia();

        System.out.println();

        Avaliacao Historia = new Avaliacao();
        Historia.nome = "Homero";
        Historia.nota = 10;
        Historia.exibirNota();

        System.out.println();

        Avaliacao Ciencia = new Avaliacao();
        Ciencia.horarioFinalizacao = "15:30";
        Ciencia.exibirHorarioFinal();

        System.out.println();

        BancoDados MongoDB = new BancoDados();
        MongoDB.nome = "MongoDB";
        MongoDB.tipo = "não relacional";
        MongoDB.exibirTipoBancoDados();

        System.out.println();

        BancoDados MySQL = new BancoDados();
        MySQL.nome = "MySQL";
        MySQL.tipo = "relacional";
        MySQL.exibirTipoBancoDados();

        System.out.println();

        Cliente Mario = new Cliente();
        Mario.saldoCliente = 15.50;
        Mario.valorCompra = 7.30;
        Mario.pagarCompra();

        System.out.println();

        Cliente Xamis = new Cliente();
        Xamis.saldoCliente = 1.99;
        Xamis.valorCompra = 270.50;
        Xamis.pagarCompra();

        System.out.println();

        ContaBancaria Nubank = new ContaBancaria();
        Nubank.saldoConta = 4.20;
        Nubank.mostraSaldo();

        System.out.println();

        ContaBancaria Itau = new ContaBancaria();
        Itau.saldoConta = 151;
        Itau.mostraSaldo();

        System.out.println();

        Curso DSM = new Curso();
        DSM.professor = "Clebersons";
        DSM.aplicarProva();

        System.out.println();

        Curso ADS = new Curso();
        ADS.materia = "Matemática Discreta";
        ADS.exibirMateria();

        System.out.println();

        Jogo FogoGratis = new Jogo();
        FogoGratis.nome = "FogoGratis";
        FogoGratis.entrarJogo();

        System.out.println();

        Jogo Bangas = new Jogo();
        Bangas.nome = "Mobile legends bang bang";
        Bangas.sairJogo();

        System.out.println();

        Mensagem Email = new Mensagem();
        Email.couteudo = "Olá temos uma grande promoção! lique no link http://linksuspeito.com";
        Email.exibirMensagem();

        System.out.println();

        Mensagem Carta = new Mensagem();
        Carta.couteudo = "Oi vozinha, estou com saudades, como vai a vida?";
        Carta.exibirMensagem();

        System.out.println();

        Pedido Mercado = new Pedido();
        Mercado.item = "Arroz";
        Mercado.adicionarItem();

        System.out.println();

        Pedido Farmacia = new Pedido();
        Farmacia.item = "Tadala";
        Farmacia.adicionarItem();

        System.out.println();

        Produto Livro = new Produto();
        Livro.nome = "Story";
        Livro.valor = 29.99;
        Livro.motrarValor();

        System.out.println();

        Produto Coca = new Produto();
        Coca.nome = "Coca-cola";
        Coca.valor = 15;
        Coca.motrarValor();

        System.out.println();

        Projeto Semestral = new Projeto();
        Semestral.dataAtual = "07/08/2025";
        Semestral.prazoFinal = "29/11/2025";
        Semestral.iniciarProjeto();

        System.out.println();

        Projeto BancoDados = new Projeto();
        BancoDados.dataAtual = "02/03/2025";
        BancoDados.prazoFinal = "20/03/2025";
        BancoDados.iniciarProjeto();

        System.out.println();

        RedeSocial WhatsApp = new RedeSocial();
        WhatsApp.status = "Penso logo existo";
        WhatsApp.postarStatus();

        System.out.println();

        RedeSocial Facebook = new RedeSocial();
        Facebook.status = "Estudando...";
        Facebook.postarStatus();

        System.out.println();

        Relatorio Venda = new Relatorio();
        Venda.conteudo = "Skate completo ja montado vendido a R$500,00 ";
        Venda.exibirRelatorio();

        System.out.println();

        Relatorio Paciente = new Relatorio();
        Paciente.conteudo = "Paciente esta diagnosticado com demência";
        Paciente.exibirRelatorio();

        System.out.println();

        Senha Epic = new Senha();
        Epic.senhaAtual = "123";
        Epic.novaSenha  = "123";
        Epic.verficarSenha();


        System.out.println();

        Senha Netflix = new Senha();
        Netflix.senhaAtual = "456";
        Netflix.novaSenha = "123";
        Netflix.verficarSenha();

        System.out.println();

        
    }
}
