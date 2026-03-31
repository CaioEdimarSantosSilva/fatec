package br.edu.fatec.desafiotarefa.controller;

import br.edu.fatec.desafiotarefa.db.DB;
import br.edu.fatec.desafiotarefa.model.Tarefa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TarefaController {

    private static final String TABELA = "tarefas";

    public void create(Tarefa tarefa) {

        String sql = "INSERT INTO " + TABELA + " (titulo, descricao, concluida, categoria) VALUES (?, ?, ?, ?)";

        try (Connection conn = DB.connection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setBoolean(3, tarefa.getConcluida());
            stmt.setString(4, tarefa.getCategoria());

            stmt.executeUpdate();
            System.out.println("Tarefa criada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao criar tarefa: " + e.getMessage());
        }
    }

    public void update(int id, String titulo, String descricao, Boolean concluida, String categoria) {

        String sql = "UPDATE " + TABELA + " SET titulo = ?, descricao = ?, concluida = ?, categoria = ? WHERE id = ?";

        try (Connection conn = DB.connection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, titulo);
            stmt.setString(2, descricao);
            stmt.setBoolean(3, concluida);
            stmt.setString(4, categoria);
            stmt.setInt(5, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0)
                System.out.println("Tarefa atualizada!");
            else
                System.out.println("ID não encontrado.");

        } catch (Exception e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
    }

    public void delete(int id) {

        String sql = "DELETE FROM " + TABELA + " WHERE id = ?";

        try (Connection conn = DB.connection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0)
                System.out.println("Tarefa excluída!");
            else
                System.out.println("ID não encontrado.");

        } catch (Exception e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        }
    }

    public void findAll() {

        String sql = "SELECT * FROM " + TABELA;

        try (Connection conn = DB.connection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("titulo") + " | " +
                                rs.getString("descricao") + " | " +
                                (rs.getBoolean("concluida") ? "CONCLUIDA" : "PENDENTE") + " | " +
                                rs.getString("categoria")
                );
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }
    }

    public void findByCategoria(String categoria) {

        String sql = "SELECT * FROM " + TABELA + " WHERE categoria ILIKE ?";

        try (Connection conn = DB.connection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + categoria + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                        rs.getString("titulo") + " | " +
                        rs.getString("descricao") + " | " +
                        rs.getBoolean("concluida") + " | " +
                        rs.getString("categoria"));
            }

        } catch (Exception e) {
            System.out.println("Erro no filtro: " + e.getMessage());
        }
    }

    public void findByStatus(Boolean concluida) {

        String sql = "SELECT * FROM " + TABELA + " WHERE concluida = ?";

        try (Connection conn = DB.connection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, concluida);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                        rs.getString("titulo") + " | " +
                        rs.getString("descricao") + " | " +
                        rs.getBoolean("concluida") + " | " +
                        rs.getString("categoria"));
            }

        } catch (Exception e) {
            System.out.println("Erro no filtro: " + e.getMessage());
        }
    }
}