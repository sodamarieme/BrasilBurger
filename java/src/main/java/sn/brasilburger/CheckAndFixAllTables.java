package sn.brasilburger;

import java.sql.*;

public class CheckAndFixAllTables {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://ep-misty-sea-ad9qf4aa-pooler.c-2.us-east-1.aws.neon.tech:5432/neondb?sslmode=require&channel_binding=require";
        String user = "neondb_owner";
        String password = "npg_QgOj2PCvI7on";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println(" Connexion réussie à Neon!\n");
            
            Statement stmt = conn.createStatement();
            
            // Corriger la table commandes
            System.out.println(" Correction de la table commandes...");
            try {
                stmt.execute("ALTER TABLE commandes ADD COLUMN IF NOT EXISTS etat_commande VARCHAR(50)");
                System.out.println(" Colonne 'etat_commande' ajoutée");
            } catch (Exception e) {
                System.out.println(" " + e.getMessage());
            }
            
            // Corriger la table burgers
            System.out.println("\n Vérification de la table burgers...");
            try {
                stmt.execute("ALTER TABLE burgers ADD COLUMN IF NOT EXISTS type VARCHAR(50)");
                System.out.println(" Colonne 'type' ajoutée");
            } catch (Exception e) {
                System.out.println("  " + e.getMessage());
            }
            
            // Corriger la table complements
            System.out.println("\n Vérification de la table complements...");
            try {
                stmt.execute("ALTER TABLE complements ADD COLUMN IF NOT EXISTS type_complement VARCHAR(50)");
                System.out.println(" Colonne 'type_complement' ajoutée");
            } catch (Exception e) {
                System.out.println("  " + e.getMessage());
            }
            
            // Corriger la table menus
            System.out.println("\nVérification de la table menus...");
            try {
                stmt.execute("ALTER TABLE menus ADD COLUMN IF NOT EXISTS image_url VARCHAR(500)");
                System.out.println(" Colonne 'image_url' ajoutée");
            } catch (Exception e) {
                System.out.println("  " + e.getMessage());
            }
            
            stmt.close();
            
            System.out.println("\n==========================================");
            System.out.println("    VÉRIFICATION DES TABLES");
            System.out.println("==========================================\n");
            
            // Afficher la structure de chaque table importante
            String[] tables = {"clients", "commandes", "burgers", "complements", "menus"};
            
            for (String tableName : tables) {
                System.out.println(" Table: " + tableName);
                DatabaseMetaData metaData = conn.getMetaData();
                ResultSet columns = metaData.getColumns(null, "public", tableName, null);
                
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String dataType = columns.getString("TYPE_NAME");
                    System.out.println("    " + columnName + " (" + dataType + ")");
                }
                System.out.println();
            }
            
            System.out.println(" TOUTES LES TABLES SONT PRÊTES!");
            
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
