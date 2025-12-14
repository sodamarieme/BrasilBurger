package sn.brasilburger;

import java.sql.*;

public class AlterClientsTable {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://ep-misty-sea-ad9qf4aa-pooler.c-2.us-east-1.aws.neon.tech:5432/neondb?sslmode=require&channel_binding=require";
        String user = "neondb_owner";
        String password = "npg_QgOj2PCvI7on";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println(" Connexion réussie à Neon!\n");
            System.out.println("Modification de la table clients...\n");
            
            Statement stmt = conn.createStatement();
            
            // Ajouter la colonne prenom si elle n'existe pas
            try {
                stmt.execute("ALTER TABLE clients ADD COLUMN prenom VARCHAR(100)");
                System.out.println(" Colonne 'prenom' ajoutée");
            } catch (SQLException e) {
                System.out.println("ℹ  Colonne 'prenom' existe déjà");
            }
            
            // Ajouter la colonne password si elle n'existe pas
            try {
                stmt.execute("ALTER TABLE clients ADD COLUMN password VARCHAR(255)");
                System.out.println(" Colonne 'password' ajoutée");
            } catch (SQLException e) {
                System.out.println("  Colonne 'password' existe déjà");
            }
            
            stmt.close();
            
            // Vérifier la nouvelle structure
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet columns = metaData.getColumns(null, "public", "clients", null);
            
            System.out.println("\n==========================================");
            System.out.println("   NOUVELLE STRUCTURE DE LA TABLE CLIENTS");
            System.out.println("==========================================\n");
            
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String dataType = columns.getString("TYPE_NAME");
                System.out.println("Colonne: " + columnName + " | Type: " + dataType);
            }
            
            System.out.println("\nTABLE MODIFIÉE AVEC SUCCÈS!");
            
        } catch (Exception e) {
            System.out.println(" Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
