package sn.brasilburger;

import java.sql.*;

public class CompleteFixAllTables {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://ep-misty-sea-ad9qf4aa-pooler.c-2.us-east-1.aws.neon.tech:5432/neondb?sslmode=require&channel_binding=require";
        String user = "neondb_owner";
        String password = "npg_QgOj2PCvI7on";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Connexion réussie à Neon!\n");
            System.out.println("🔄 CORRECTION COMPLÈTE DES TABLES...\n");
            
            Statement stmt = conn.createStatement();
            
            // ============== COMMANDES ==============
            System.out.println("📋 Correction de la table COMMANDES:");
            addColumnIfNotExists(stmt, "commandes", "id_client", "INT", "Colonne id_client");
            addColumnIfNotExists(stmt, "commandes", "id_gestionnaire", "INT", "Colonne id_gestionnaire");
            addColumnIfNotExists(stmt, "commandes", "id_livreur", "INT", "Colonne id_livreur");
            addColumnIfNotExists(stmt, "commandes", "id_zone", "INT", "Colonne id_zone");
            addColumnIfNotExists(stmt, "commandes", "type_commande", "VARCHAR(50)", "Colonne type_commande");
            addColumnIfNotExists(stmt, "commandes", "etat_commande", "VARCHAR(50)", "Colonne etat_commande");
            addColumnIfNotExists(stmt, "commandes", "total", "DECIMAL(10,2)", "Colonne total");
            
            // ============== BURGERS ==============
            System.out.println("\n📋 Correction de la table BURGERS:");
            addColumnIfNotExists(stmt, "burgers", "type", "VARCHAR(50)", "Colonne type");
            
            // ============== COMPLEMENTS ==============
            System.out.println("\n📋 Correction de la table COMPLEMENTS:");
            addColumnIfNotExists(stmt, "complements", "type_complement", "VARCHAR(50)", "Colonne type_complement");
            
            // ============== MENUS ==============
            System.out.println("\n📋 Correction de la table MENUS:");
            addColumnIfNotExists(stmt, "menus", "image_url", "VARCHAR(500)", "Colonne image_url");
            
            // ============== LIVREURS ==============
            System.out.println("\n📋 Correction de la table LIVREURS:");
            addColumnIfNotExists(stmt, "livreurs", "id_zone", "INT", "Colonne id_zone");
            
            stmt.close();
            
            Thread.sleep(1000);
            
            // Afficher les structures
            System.out.println("\n==========================================");
            System.out.println("    STRUCTURES DES TABLES");
            System.out.println("==========================================\n");
            
            String[] tables = {"commandes", "burgers", "complements", "menus", "livreurs"};
            
            for (String tableName : tables) {
                System.out.println("📋 " + tableName.toUpperCase());
                DatabaseMetaData metaData = conn.getMetaData();
                ResultSet columns = metaData.getColumns(null, "public", tableName, null);
                
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String dataType = columns.getString("TYPE_NAME");
                    System.out.println("   ✅ " + columnName + " (" + dataType + ")");
                }
                System.out.println();
            }
            
            System.out.println("✅ TOUTES LES CORRECTIONS APPLIQUÉES!");
            
        } catch (Exception e) {
            System.out.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void addColumnIfNotExists(Statement stmt, String table, String column, String type, String label) {
        try {
            stmt.execute("ALTER TABLE " + table + " ADD COLUMN IF NOT EXISTS " + column + " " + type);
            System.out.println("   ✅ " + label);
        } catch (SQLException e) {
            System.out.println("   ℹ️  " + label + " existe déjà");
        }
    }
}
