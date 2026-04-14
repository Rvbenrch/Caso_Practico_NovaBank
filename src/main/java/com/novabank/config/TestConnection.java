package com.novabank.config;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {

        try (Connection conn = DatabaseConnectionManager.getInstance().getConnection()) {
            System.out.println("Conexión OK a la base de datos: " + conn.getCatalog());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}