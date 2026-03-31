package br.edu.fatec.desafiotarefa.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    public static Connection connection() throws SQLException {
        try {
            var jdbcUrl = "jdbc:postgresql://localhost:5432/gestao_tarefas";
            var user = "postgres";
            var password = "Xamis_1221";

            return DriverManager.getConnection(jdbcUrl, user, password);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}

