package sn.brasilburger;

import java.sql.*;

public class QueryClients {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://ep-misty-sea-ad9qf4aa-pooler.c-2.us-east-1.aws.neon.tech:5432/neondb?sslmode=require&channel_binding=require";
        String user = "neondb_owner";
        String password = "npg_QgOj2PCvI7on";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Connexion réussie à Neon!\n");
            
            String query = "SELECT * FROM clients";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            System.out.println("==========================================");
            System.out.println("          LISTE DES CLIENTS");
            System.out.println("==========================================\n");
            
            boolean hasClients = false;
            while (rs.next()) {
                hasClients = true;
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nom: " + rs.getString("nom"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Téléphone: " + rs.getString("telephone"));
                System.out.println("Adresse: " + rs.getString("adresse"));
                System.out.println("-------------------------------------------");
            }
            
            if (!hasClients) {
                System.out.println("❌ Aucun client trouvé dans la base de données.");
            } else {
                System.out.println("✅ Clients chargés avec succès!");
            }
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
