package sn.brasilburger.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

    private static final String URL = "jdbc:postgresql://ep-misty-sea-ad9qf4aa-pooler.c-2.us-east-1.aws.neon.tech:5432/neondb?sslmode=require&channel_binding=require";
    private static final String USER = "neondb_owner";
    private static final String PASSWORD = "npg_QgOj2PCvI7on";     

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("🌍 Connexion NEON réussie !");
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur connexion NEON");
            e.printStackTrace();
        }
        return connection;
    }
}
