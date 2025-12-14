package sn.brasilburger;

import java.sql.*;

public class CheckCommandesTable {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://ep-misty-sea-ad9qf4aa-pooler.c-2.us-east-1.aws.neon.tech:5432/neondb?sslmode=require&channel_binding=require";
        String user = "neondb_owner";
        String password = "npg_QgOj2PCvI7on";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Connexion réussie!\n");
            
            // Afficher la structure de la table
            String sql = "SELECT column_name, data_type, is_nullable FROM information_schema.columns WHERE table_name='commandes' ORDER BY ordinal_position";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            System.out.println("=== STRUCTURE DE LA TABLE COMMANDES ===\n");
            while (rs.next()) {
                System.out.println("- " + rs.getString("column_name") + " (" + rs.getString("data_type") + ") nullable=" + rs.getString("is_nullable"));
            }
            
            // Afficher les données actuelles
            System.out.println("\n=== DONNÉES ACTUELLES ===\n");
            rs = stmt.executeQuery("SELECT id, id_client, id_gestionnaire, type_commande, etat_commande FROM commandes LIMIT 5");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + 
                                   " | client_id: " + (rs.wasNull() ? "NULL" : rs.getInt("id_client")) +
                                   " | gestionnaire_id: " + (rs.wasNull() ? "NULL" : rs.getInt("id_gestionnaire")) +
                                   " | type: " + rs.getString("type_commande") +
                                   " | état: " + rs.getString("etat_commande"));
            }
            
            stmt.close();
            
        } catch (Exception e) {
            System.out.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
