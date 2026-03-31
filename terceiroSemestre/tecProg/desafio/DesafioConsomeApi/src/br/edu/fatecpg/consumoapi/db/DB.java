package br.edu.fatecpg.consumoapi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    private static final String URL  = "jdbc:postgresql://localhost:5432/consumoapi";
    private static final String USER = "postgres";
    private static final String PASS = "Xamis_1221";
    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}