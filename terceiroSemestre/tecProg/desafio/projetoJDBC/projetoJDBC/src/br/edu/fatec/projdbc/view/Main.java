package br.edu.fatec.projdbc.view;

import br.edu.fatec.projdbc.db.DB;
import br.edu.fatec.projdbc.model.Aluno;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try(var conn = DB.connection()) {
            System.out.println("Conexão Realizada com sucesso");
            var query = "INSERT INTO tb_alunos(cpf, nome) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "412.222.333-44");
            stmt.setString(2, "Azien");
            stmt.execute();
            List<Aluno> alunos = new ArrayList<>();
            var consulta = "SELECT * FROM tb_alunos";
            PreparedStatement stmtConsulta = conn.prepareStatement(consulta);
            ResultSet rs = stmtConsulta.executeQuery();
            while (rs.next()){
                alunos.add(new Aluno(
                        rs.getInt("id"),
                        rs.getString("cpf"),
                        rs.getString("nome")));
            }
            alunos.forEach(System.out::println);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
