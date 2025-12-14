package sn.brasilburger;

import java.sql.*;

public class FixEnumValues {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://ep-misty-sea-ad9qf4aa-pooler.c-2.us-east-1.aws.neon.tech:5432/neondb?sslmode=require&channel_binding=require";
        String user = "neondb_owner";
        String password = "npg_QgOj2PCvI7on";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Connexion réussie à Neon!\n");
            System.out.println("🔄 Correction des valeurs d'enum...\n");
            
            Statement stmt = conn.createStatement();
            
            // Corriger les valeurs CONFIRMEE vers EN_COURS
            stmt.execute("UPDATE commandes SET etat_commande = 'EN_COURS' WHERE etat_commande = 'CONFIRMEE'");
            
            // Corriger les valeurs LIVRAISON vers SUR_PLACE
            stmt.execute("UPDATE commandes SET type_commande = 'SUR_PLACE' WHERE type_commande = 'LIVRAISON'");
            stmt.execute("UPDATE commandes SET type_commande = 'SUR_PLACE' WHERE type_commande = 'RETRAIT'");
            
            System.out.println("✅ Valeurs corrigées");
            
            // Afficher les commandes
            ResultSet rs = stmt.executeQuery("SELECT id, etat_commande, type_commande, total FROM commandes");
            
            System.out.println("\n==========================================");
            System.out.println("      COMMANDES APRÈS CORRECTION");
            System.out.println("==========================================\n");
            
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + 
                                   " | État: " + rs.getString("etat_commande") + 
                                   " | Type: " + rs.getString("type_commande") + 
                                   " | Total: " + rs.getDouble("total"));
            }
            
            System.out.println("\n✅ CORRECTION COMPLÉTÉE!");
            
            stmt.close();
            
        } catch (Exception e) {
            System.out.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
