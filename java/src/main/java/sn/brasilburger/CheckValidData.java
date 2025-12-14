package sn.brasilburger;

import java.sql.*;

public class CheckValidData {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://ep-misty-sea-ad9qf4aa-pooler.c-2.us-east-1.aws.neon.tech:5432/neondb?sslmode=require&channel_binding=require";
        String user = "neondb_owner";
        String password = "npg_QgOj2PCvI7on";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Connexion réussie!\n");
            
            // Vérifier les clients valides
            System.out.println("=== CLIENTS DISPONIBLES ===");
            ResultSet rs = conn.createStatement().executeQuery("SELECT id, nom, prenom FROM clients ORDER BY id");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " | " + rs.getString("nom") + " " + rs.getString("prenom"));
            }
            
            // Vérifier les gestionnaires valides
            System.out.println("\n=== GESTIONNAIRES DISPONIBLES ===");
            rs = conn.createStatement().executeQuery("SELECT id, nom FROM gestionnaires ORDER BY id");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " | " + rs.getString("nom"));
            }
            
            // Vérifier les livreurs valides
            System.out.println("\n=== LIVREURS DISPONIBLES ===");
            rs = conn.createStatement().executeQuery("SELECT id, nom FROM livreurs ORDER BY id");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " | " + rs.getString("nom"));
            }
            
            // Vérifier les zones valides
            System.out.println("\n=== ZONES DISPONIBLES ===");
            rs = conn.createStatement().executeQuery("SELECT id, nom FROM zones ORDER BY id");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " | " + rs.getString("nom"));
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
