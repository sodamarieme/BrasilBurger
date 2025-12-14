package sn.brasilburger;

import java.sql.*;

public class FixClientsTable {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://ep-misty-sea-ad9qf4aa-pooler.c-2.us-east-1.aws.neon.tech:5432/neondb?sslmode=require&channel_binding=require";
        String user = "neondb_owner";
        String password = "npg_QgOj2PCvI7on";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Connexion réussie à Neon!\n");
            System.out.println("🔄 Correction de la table clients...\n");
            
            Statement stmt = conn.createStatement();
            
            // Ajouter prenom
            String addPrenomSQL = "ALTER TABLE clients ADD COLUMN IF NOT EXISTS prenom VARCHAR(100)";
            try {
                stmt.execute(addPrenomSQL);
                System.out.println("✅ Colonne 'prenom' traitée");
            } catch (Exception e) {
                System.out.println("⚠️  prenom: " + e.getMessage());
            }
            
            // Ajouter password
            String addPasswordSQL = "ALTER TABLE clients ADD COLUMN IF NOT EXISTS password VARCHAR(255)";
            try {
                stmt.execute(addPasswordSQL);
                System.out.println("✅ Colonne 'password' traitée");
            } catch (Exception e) {
                System.out.println("⚠️  password: " + e.getMessage());
            }
            
            stmt.close();
            
            // Vérifier la structure
            Thread.sleep(1000);
            
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet columns = metaData.getColumns(null, "public", "clients", null);
            
            System.out.println("\n==========================================");
            System.out.println("   STRUCTURE ACTUELLE DE CLIENTS");
            System.out.println("==========================================\n");
            
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String dataType = columns.getString("TYPE_NAME");
                System.out.println("✅ " + columnName + " (" + dataType + ")");
            }
            
            System.out.println("\n✅ TABLE PRÊTE!");
            
        } catch (Exception e) {
            System.out.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
