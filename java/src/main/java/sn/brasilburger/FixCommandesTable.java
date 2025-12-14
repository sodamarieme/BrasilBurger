package sn.brasilburger;

import java.sql.*;

public class FixCommandesTable {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://ep-misty-sea-ad9qf4aa-pooler.c-2.us-east-1.aws.neon.tech:5432/neondb?sslmode=require&channel_binding=require";
        String user = "neondb_owner";
        String password = "npg_QgOj2PCvI7on";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Connexion réussie à Neon!\n");
            System.out.println("🔄 Correction de la table commandes...\n");
            
            Statement stmt = conn.createStatement();
            
            // Vérifier si la colonne id_client existe, sinon l'ajouter
            try {
                stmt.execute("ALTER TABLE commandes ADD COLUMN IF NOT EXISTS id_client INT");
                System.out.println("✅ Colonne 'id_client' ajoutée");
            } catch (Exception e) {
                System.out.println("⚠️  id_client: " + e.getMessage());
            }
            
            // Ajouter id_gestionnaire
            try {
                stmt.execute("ALTER TABLE commandes ADD COLUMN IF NOT EXISTS id_gestionnaire INT");
                System.out.println("✅ Colonne 'id_gestionnaire' ajoutée");
            } catch (Exception e) {
                System.out.println("⚠️  id_gestionnaire: " + e.getMessage());
            }
            
            // Ajouter type (pour type de commande)
            try {
                stmt.execute("ALTER TABLE commandes ADD COLUMN IF NOT EXISTS type VARCHAR(50)");
                System.out.println("✅ Colonne 'type' ajoutée");
            } catch (Exception e) {
                System.out.println("⚠️  type: " + e.getMessage());
            }
            
            // Ajouter les foreign keys si nécessaire
            try {
                stmt.execute("ALTER TABLE commandes ADD CONSTRAINT fk_commandes_id_client FOREIGN KEY (id_client) REFERENCES clients(id)");
                System.out.println("✅ Foreign key 'id_client' ajoutée");
            } catch (Exception e) {
                System.out.println("ℹ️  FK id_client existe déjà");
            }
            
            try {
                stmt.execute("ALTER TABLE commandes ADD CONSTRAINT fk_commandes_id_gestionnaire FOREIGN KEY (id_gestionnaire) REFERENCES gestionnaires(id)");
                System.out.println("✅ Foreign key 'id_gestionnaire' ajoutée");
            } catch (Exception e) {
                System.out.println("ℹ️  FK id_gestionnaire existe déjà");
            }
            
            stmt.close();
            
            Thread.sleep(500);
            
            // Afficher la structure
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet columns = metaData.getColumns(null, "public", "commandes", null);
            
            System.out.println("\n==========================================");
            System.out.println("   STRUCTURE DE LA TABLE COMMANDES");
            System.out.println("==========================================\n");
            
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String dataType = columns.getString("TYPE_NAME");
                System.out.println("✅ " + columnName + " (" + dataType + ")");
            }
            
            System.out.println("\n✅ TABLE COMMANDES PRÊTE!");
            
        } catch (Exception e) {
            System.out.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
