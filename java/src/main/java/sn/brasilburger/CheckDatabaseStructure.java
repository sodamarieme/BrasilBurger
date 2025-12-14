package sn.brasilburger;

import java.sql.*;

public class CheckDatabaseStructure {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://ep-misty-sea-ad9qf4aa-pooler.c-2.us-east-1.aws.neon.tech:5432/neondb?sslmode=require&channel_binding=require";
        String user = "neondb_owner";
        String password = "npg_QgOj2PCvI7on";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Connexion réussie à Neon!\n");
            
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet columns = metaData.getColumns(null, "public", "clients", null);
            
            System.out.println("==========================================");
            System.out.println("      STRUCTURE DE LA TABLE CLIENTS");
            System.out.println("==========================================\n");
            
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String dataType = columns.getString("TYPE_NAME");
                System.out.println("Colonne: " + columnName + " | Type: " + dataType);
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
