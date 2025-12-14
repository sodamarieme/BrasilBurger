package sn.brasilburger;

import java.sql.*;

public class FixDatabase {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://ep-misty-sea-ad9qf4aa-pooler.c-2.us-east-1.aws.neon.tech:5432/neondb?sslmode=require&channel_binding=require";
        String user = "neondb_owner";
        String password = "npg_QgOj2PCvI7on";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Connexion réussie à Neon!\n");
            System.out.println("🔄 Correction de la structure de la table clients...\n");
            
            Statement stmt = conn.createStatement();
            
            // Supprimer les tables dépendantes d'abord
            String[] dropStatements = {
                "DROP TABLE IF EXISTS commande_items CASCADE",
                "DROP TABLE IF EXISTS menu_items CASCADE",
                "DROP TABLE IF EXISTS commandes CASCADE",
                "DROP TABLE IF EXISTS clients CASCADE"
            };
            
            for (String sql : dropStatements) {
                try {
                    stmt.execute(sql);
                    System.out.println("✅ " + sql);
                } catch (SQLException e) {
                    // Ignorer les erreurs si les tables n'existent pas
                }
            }
            
            // Récréer la table clients avec les colonnes correctes
            stmt.execute("CREATE TABLE clients (" +
                    "id SERIAL PRIMARY KEY, " +
                    "nom VARCHAR(100) NOT NULL, " +
                    "prenom VARCHAR(100), " +
                    "email VARCHAR(100) UNIQUE, " +
                    "telephone VARCHAR(20), " +
                    "adresse VARCHAR(255), " +
                    "password VARCHAR(255), " +
                    "zone_id INT, " +
                    "FOREIGN KEY (zone_id) REFERENCES zones(id)" +
                    ")");
            System.out.println("✅ Table clients créée");
            
            // Récréer les autres tables
            stmt.execute("CREATE TABLE paiements (" +
                    "id SERIAL PRIMARY KEY, " +
                    "mode_paiement VARCHAR(50) NOT NULL, " +
                    "montant DECIMAL(10, 2) NOT NULL, " +
                    "date_paiement TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");
            System.out.println("✅ Table paiements créée");
            
            stmt.execute("CREATE TABLE commandes (" +
                    "id SERIAL PRIMARY KEY, " +
                    "client_id INT NOT NULL, " +
                    "date_commande TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "etat VARCHAR(50) NOT NULL, " +
                    "type_commande VARCHAR(50), " +
                    "paiement_id INT, " +
                    "total DECIMAL(10, 2), " +
                    "FOREIGN KEY (client_id) REFERENCES clients(id), " +
                    "FOREIGN KEY (paiement_id) REFERENCES paiements(id)" +
                    ")");
            System.out.println("✅ Table commandes créée");
            
            stmt.execute("CREATE TABLE menu_items (" +
                    "id SERIAL PRIMARY KEY, " +
                    "menu_id INT NOT NULL, " +
                    "burger_id INT NOT NULL, " +
                    "complement_id INT, " +
                    "FOREIGN KEY (menu_id) REFERENCES menus(id), " +
                    "FOREIGN KEY (burger_id) REFERENCES burgers(id), " +
                    "FOREIGN KEY (complement_id) REFERENCES complements(id)" +
                    ")");
            System.out.println("✅ Table menu_items créée");
            
            stmt.execute("CREATE TABLE commande_items (" +
                    "id SERIAL PRIMARY KEY, " +
                    "commande_id INT NOT NULL, " +
                    "burger_id INT, " +
                    "menu_id INT, " +
                    "complement_id INT, " +
                    "quantite INT DEFAULT 1, " +
                    "prix_unitaire DECIMAL(10, 2), " +
                    "FOREIGN KEY (commande_id) REFERENCES commandes(id), " +
                    "FOREIGN KEY (burger_id) REFERENCES burgers(id), " +
                    "FOREIGN KEY (menu_id) REFERENCES menus(id), " +
                    "FOREIGN KEY (complement_id) REFERENCES complements(id)" +
                    ")");
            System.out.println("✅ Table commande_items créée");
            
            // Insérer les clients corrigés
            stmt.execute("INSERT INTO clients (nom, prenom, email, telephone, adresse, password, zone_id) VALUES ('Jean', 'Dupont', 'jean@example.com', '+221771234567', '123 Rue de la Paix', 'pass123', 1)");
            stmt.execute("INSERT INTO clients (nom, prenom, email, telephone, adresse, password, zone_id) VALUES ('Marie', 'Samba', 'marie@example.com', '+221772345678', '456 Avenue du Centenaire', 'pass123', 1)");
            stmt.execute("INSERT INTO clients (nom, prenom, email, telephone, adresse, password, zone_id) VALUES ('Ahmed', 'Mohamed', 'ahmed@example.com', '+221773456789', '789 Boulevard de la République', 'pass123', 2)");
            stmt.execute("INSERT INTO clients (nom, prenom, email, telephone, adresse, password, zone_id) VALUES ('Fatou', 'Ndiaye', 'fatou@example.com', '+221774567890', '321 Rue des Pères', 'pass123', 3)");
            System.out.println("✅ Clients insérés");
            
            // Insérer les paiements
            stmt.execute("INSERT INTO paiements (mode_paiement, montant) VALUES ('CARTE_CREDIT', 5500.00)");
            stmt.execute("INSERT INTO paiements (mode_paiement, montant) VALUES ('VIREMENT_BANCAIRE', 6500.00)");
            stmt.execute("INSERT INTO paiements (mode_paiement, montant) VALUES ('ESPECES', 8000.00)");
            System.out.println("✅ Paiements insérés");
            
            // Insérer les commandes
            stmt.execute("INSERT INTO commandes (client_id, etat, type_commande, paiement_id, total) VALUES (1, 'LIVREE', 'LIVRAISON', 1, 5500.00)");
            stmt.execute("INSERT INTO commandes (client_id, etat, type_commande, paiement_id, total) VALUES (2, 'PREPARATION', 'SUR_PLACE', 2, 6500.00)");
            stmt.execute("INSERT INTO commandes (client_id, etat, type_commande, paiement_id, total) VALUES (3, 'CONFIRMEE', 'RETRAIT', 3, 8000.00)");
            System.out.println("✅ Commandes insérées");
            
            stmt.close();
            
            System.out.println("\n==========================================");
            System.out.println("✅ BASE DE DONNÉES CORRIGÉE!");
            System.out.println("==========================================\n");
            
        } catch (Exception e) {
            System.out.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
