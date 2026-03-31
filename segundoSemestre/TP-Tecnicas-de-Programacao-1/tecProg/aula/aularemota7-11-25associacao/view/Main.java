package view;

import java.util.ArrayList;
import java.util.List;

import model.*;

public class Main {
    public static void main(String[] args) {
        List<Object> qualquerCoisa = new ArrayList<>();
        qualquerCoisa.add(1);
        qualquerCoisa.add("Ale");
        qualquerCoisa.add('a');
        System.out.println(qualquerCoisa);

        List<String> nomes = new ArrayList<>();
        nomes.add("Ale");
        nomes.add("Aline");
        nomes.add("Ana");
        nomes.add(1,"Maria");
        System.out.println(nomes);

        for (String nome : nomes) {
            if(nome.length() <= 3){
                System.out.println("Nome: " + nome);
            }   
        }

        Aluno a1 = new Aluno("Ale", "2021001", 20);
        Aluno a2 = new Aluno("Aline", "2021002", 18);
        Aluno a3 = new Aluno("Ana", "2021003", 43);
        Aluno a4 = new Aluno("Maria", "2021004", 37);
        List<Aluno> alunos = new ArrayList<>();
        alunos.add(a1);
        alunos.add(a2);
        alunos.add(a3);
        alunos.add(a4);
        for (Aluno aluno : alunos) {
            if(aluno.getIdade() >= 21){
                System.out.println(aluno);
            } 
        }
       
    }

}
        // //Associação do tipo Composição
        // Endereco end = new Endereco("Rua A", "12345-678", 100);
        // Pessoa ale = new Pessoa( "Ale", 30, "123.456.789-00", end);
        // System.out.println(end);
        // System.out.println(ale);
        //
        // //Associação do tipo Agregação
        // Aluno al = new Aluno("Caio", "2021001", 20);
        // Diciplina tp = new Diciplina("TP1", "DSM", "2025/2", al);
        // System.out.println(tp);
        //
        // //Associação do tipo Dependência
        // Cupom cupom = new Cupom("FATEC777", 59.99);
        // Compra compra = new Compra("Notebook", 2000);
        // String resultado = compra.finalizarCompra(cupom);
        // System.out.println(resultado);
        // System.out.println(compra);