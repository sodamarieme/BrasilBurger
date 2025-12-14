package sn.brasilburger;

import java.sql.*;

public class CheckMenuItemsTable {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://ep-misty-sea-ad9qf4aa-pooler.c-2.us-east-1.aws.neon.tech:5432/neondb?sslmode=require&channel_binding=require";
        String user = "neondb_owner";
        String password = "npg_QgOj2PCvI7on";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Connexion réussie!\n");
            
            // Afficher la structure
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet columns = metaData.getColumns(null, "public", "menu_items", null);
            
            System.out.println("=== STRUCTURE DE menu_items ===\n");
            while (columns.next()) {
                System.out.println("- " + columns.getString("COLUMN_NAME") + " (" + columns.getString("TYPE_NAME") + ")");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
