package com.novabank.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {

    private static DatabaseConnectionManager instance;

    private static final String URL = "jdbc:postgresql://localhost:5432/novaBank";
    private static final String USER = "postgres";
    private static final String PASSWORD = "655057621";

    // Constructor privado para evitar instanciación externa
    private DatabaseConnectionManager() {}

    // Singleton: única instancia global
    public static synchronized DatabaseConnectionManager getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionManager();
        }
        return instance;
    }

    // Devuelve una nueva conexión por cada llamada
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos", e);
        }
    }
}