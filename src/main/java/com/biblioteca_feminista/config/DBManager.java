package com.biblioteca_feminista.config;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {

    private static final Dotenv dotenv = Dotenv.load();

    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASS = dotenv.get("DB_PASS");
    private static Connection connection;

    public static Connection init() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexión exitosa");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return connection;
    }

    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Desconexión exitosa");
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
