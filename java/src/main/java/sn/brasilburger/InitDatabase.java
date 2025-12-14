package sn.brasilburger;

import java.sql.*;

public class InitDatabase {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://ep-misty-sea-ad9qf4aa-pooler.c-2.us-east-1.aws.neon.tech:5432/neondb?sslmode=require&channel_binding=require";
        String user = "neondb_owner";
        String password = "npg_QgOj2PCvI7on";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Connexion réussie à Neon!\n");
            System.out.println("🔄 Création des tables et insertion des données...\n");
            
            Statement stmt = conn.createStatement();
            int count = 0;
            
            // Supprimer les tables existantes si elles existent
            String[] dropStatements = {
                "DROP TABLE IF EXISTS commande_items CASCADE",
                "DROP TABLE IF EXISTS menu_items CASCADE",
                "DROP TABLE IF EXISTS commandes CASCADE",
                "DROP TABLE IF EXISTS paiements CASCADE",
                "DROP TABLE IF EXISTS menus CASCADE",
                "DROP TABLE IF EXISTS complements CASCADE",
                "DROP TABLE IF EXISTS burgers CASCADE",
                "DROP TABLE IF EXISTS livreurs CASCADE",
                "DROP TABLE IF EXISTS clients CASCADE",
                "DROP TABLE IF EXISTS gestionnaires CASCADE",
                "DROP TABLE IF EXISTS zones CASCADE"
            };
            
            for (String sql : dropStatements) {
                try {
                    stmt.execute(sql);
                } catch (SQLException e) {
                    // Ignorer les erreurs si les tables n'existent pas
                }
            }
            
            // Créer les tables
            String[] createStatements = {
                "CREATE TABLE zones (id SERIAL PRIMARY KEY, nom VARCHAR(100) NOT NULL, description TEXT)",
                "CREATE TABLE clients (id SERIAL PRIMARY KEY, nom VARCHAR(100) NOT NULL, email VARCHAR(100) UNIQUE, telephone VARCHAR(20), adresse VARCHAR(255), zone_id INT, FOREIGN KEY (zone_id) REFERENCES zones(id))",
                "CREATE TABLE burgers (id SERIAL PRIMARY KEY, nom VARCHAR(100) NOT NULL, description TEXT, prix DECIMAL(10, 2) NOT NULL, image_url VARCHAR(500))",
                "CREATE TABLE complements (id SERIAL PRIMARY KEY, nom VARCHAR(100) NOT NULL, type VARCHAR(50) NOT NULL, prix DECIMAL(10, 2) NOT NULL, image_url VARCHAR(500))",
                "CREATE TABLE menus (id SERIAL PRIMARY KEY, nom VARCHAR(100) NOT NULL, description TEXT, prix DECIMAL(10, 2) NOT NULL)",
                "CREATE TABLE menu_items (id SERIAL PRIMARY KEY, menu_id INT NOT NULL, burger_id INT NOT NULL, complement_id INT, FOREIGN KEY (menu_id) REFERENCES menus(id), FOREIGN KEY (burger_id) REFERENCES burgers(id), FOREIGN KEY (complement_id) REFERENCES complements(id))",
                "CREATE TABLE paiements (id SERIAL PRIMARY KEY, mode_paiement VARCHAR(50) NOT NULL, montant DECIMAL(10, 2) NOT NULL, date_paiement TIMESTAMP DEFAULT CURRENT_TIMESTAMP)",
                "CREATE TABLE commandes (id SERIAL PRIMARY KEY, client_id INT NOT NULL, date_commande TIMESTAMP DEFAULT CURRENT_TIMESTAMP, etat VARCHAR(50) NOT NULL, type_commande VARCHAR(50), paiement_id INT, total DECIMAL(10, 2), FOREIGN KEY (client_id) REFERENCES clients(id), FOREIGN KEY (paiement_id) REFERENCES paiements(id))",
                "CREATE TABLE commande_items (id SERIAL PRIMARY KEY, commande_id INT NOT NULL, burger_id INT, menu_id INT, complement_id INT, quantite INT DEFAULT 1, prix_unitaire DECIMAL(10, 2), FOREIGN KEY (commande_id) REFERENCES commandes(id), FOREIGN KEY (burger_id) REFERENCES burgers(id), FOREIGN KEY (menu_id) REFERENCES menus(id), FOREIGN KEY (complement_id) REFERENCES complements(id))",
                "CREATE TABLE gestionnaires (id SERIAL PRIMARY KEY, nom VARCHAR(100) NOT NULL, email VARCHAR(100) UNIQUE, mot_de_passe VARCHAR(255), telephone VARCHAR(20))",
                "CREATE TABLE livreurs (id SERIAL PRIMARY KEY, nom VARCHAR(100) NOT NULL, telephone VARCHAR(20), zone_id INT, disponible BOOLEAN DEFAULT TRUE, FOREIGN KEY (zone_id) REFERENCES zones(id))"
            };
            
            for (String sql : createStatements) {
                try {
                    stmt.execute(sql);
                    count++;
                    System.out.println("✅ Table créée (" + count + ")");
                } catch (SQLException e) {
                    System.out.println("⚠️  Erreur: " + e.getMessage());
                }
            }
            
            // Insérer les données de test
            String[] insertStatements = {
                "INSERT INTO zones (nom, description) VALUES ('Dakar Centre', 'Zone centre-ville de Dakar')",
                "INSERT INTO zones (nom, description) VALUES ('Dakar Banlieue', 'Zone banlieue de Dakar')",
                "INSERT INTO zones (nom, description) VALUES ('Thiès', 'Région de Thiès')",
                "INSERT INTO zones (nom, description) VALUES ('Saint-Louis', 'Région de Saint-Louis')",
                "INSERT INTO clients (nom, email, telephone, adresse, zone_id) VALUES ('Jean Dupont', 'jean@example.com', '+221771234567', '123 Rue de la Paix', 1)",
                "INSERT INTO clients (nom, email, telephone, adresse, zone_id) VALUES ('Marie Samba', 'marie@example.com', '+221772345678', '456 Avenue du Centenaire', 1)",
                "INSERT INTO clients (nom, email, telephone, adresse, zone_id) VALUES ('Ahmed Mohamed', 'ahmed@example.com', '+221773456789', '789 Boulevard de la République', 2)",
                "INSERT INTO clients (nom, email, telephone, adresse, zone_id) VALUES ('Fatou Ndiaye', 'fatou@example.com', '+221774567890', '321 Rue des Pères', 3)",
                "INSERT INTO burgers (nom, description, prix) VALUES ('Classic Burger', 'Burger classique avec fromage et salade', 3500.00)",
                "INSERT INTO burgers (nom, description, prix) VALUES ('Spicy Burger', 'Burger piquant avec jalapeño et sauce spéciale', 4000.00)",
                "INSERT INTO burgers (nom, description, prix) VALUES ('Double Burger', 'Double steak avec fromage double', 5000.00)",
                "INSERT INTO burgers (nom, description, prix) VALUES ('Chicken Burger', 'Burger au poulet grillé', 3800.00)",
                "INSERT INTO complements (nom, type, prix) VALUES ('Frites', 'GARNITURE', 1000.00)",
                "INSERT INTO complements (nom, type, prix) VALUES ('Salade', 'GARNITURE', 800.00)",
                "INSERT INTO complements (nom, type, prix) VALUES ('Boisson Gazeuse', 'BOISSON', 1200.00)",
                "INSERT INTO complements (nom, type, prix) VALUES ('Jus Frais', 'BOISSON', 1500.00)",
                "INSERT INTO complements (nom, type, prix) VALUES ('Sauce BBQ', 'SAUCE', 500.00)",
                "INSERT INTO menus (nom, description, prix) VALUES ('Menu Classique', 'Burger + Frites + Boisson', 5500.00)",
                "INSERT INTO menus (nom, description, prix) VALUES ('Menu Spécial', 'Burger Spicy + Frites + Jus', 6500.00)",
                "INSERT INTO menus (nom, description, prix) VALUES ('Menu Duo', 'Double Burger + Frites + Boisson', 8000.00)",
                "INSERT INTO paiements (mode_paiement, montant) VALUES ('CARTE_CREDIT', 5500.00)",
                "INSERT INTO paiements (mode_paiement, montant) VALUES ('VIREMENT_BANCAIRE', 6500.00)",
                "INSERT INTO paiements (mode_paiement, montant) VALUES ('ESPECES', 8000.00)",
                "INSERT INTO commandes (client_id, etat, type_commande, paiement_id, total) VALUES (1, 'LIVREE', 'LIVRAISON', 1, 5500.00)",
                "INSERT INTO commandes (client_id, etat, type_commande, paiement_id, total) VALUES (2, 'PREPARATION', 'SUR_PLACE', 2, 6500.00)",
                "INSERT INTO commandes (client_id, etat, type_commande, paiement_id, total) VALUES (3, 'CONFIRMEE', 'RETRAIT', 3, 8000.00)",
                "INSERT INTO commande_items (commande_id, burger_id, quantite, prix_unitaire) VALUES (1, 1, 1, 3500.00)",
                "INSERT INTO commande_items (commande_id, burger_id, quantite, prix_unitaire) VALUES (2, 2, 1, 4000.00)",
                "INSERT INTO commande_items (commande_id, burger_id, quantite, prix_unitaire) VALUES (3, 3, 1, 5000.00)",
                "INSERT INTO gestionnaires (nom, email, mot_de_passe, telephone) VALUES ('Admin Manager', 'admin@brasilburger.com', 'password123', '+221771111111')",
                "INSERT INTO gestionnaires (nom, email, mot_de_passe, telephone) VALUES ('Responsable Stock', 'stock@brasilburger.com', 'password123', '+221772222222')",
                "INSERT INTO livreurs (nom, telephone, zone_id, disponible) VALUES ('Moussa Sall', '+221775555555', 1, TRUE)",
                "INSERT INTO livreurs (nom, telephone, zone_id, disponible) VALUES ('Ousmane Ba', '+221776666666', 2, TRUE)",
                "INSERT INTO livreurs (nom, telephone, zone_id, disponible) VALUES ('Ibrahima Diop', '+221777777777', 3, FALSE)"
            };
            
            for (String sql : insertStatements) {
                try {
                    stmt.execute(sql);
                    count++;
                } catch (SQLException e) {
                    System.out.println("⚠️  Erreur insertion: " + e.getMessage());
                }
            }
            
            stmt.close();
            
            System.out.println("\n==========================================");
            System.out.println("✅ INITIALISATION TERMINÉE!");
            System.out.println("==========================================\n");
            
            // Afficher les tables créées
            displayTables(conn);
            
            // Afficher les clients
            displayClients(conn);
            
        } catch (Exception e) {
            System.out.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void displayTables(Connection conn) {
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, "public", "%", new String[]{"TABLE"});
            
            System.out.println("📋 Tables créées:");
            while (tables.next()) {
                System.out.println("   - " + tables.getString("TABLE_NAME"));
            }
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la lecture des tables: " + e.getMessage());
        }
    }
    
    private static void displayClients(Connection conn) {
        try {
            String query = "SELECT * FROM clients";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            System.out.println("==========================================");
            System.out.println("          LISTE DES CLIENTS");
            System.out.println("==========================================\n");
            
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nom: " + rs.getString("nom"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Téléphone: " + rs.getString("telephone"));
                System.out.println("Adresse: " + rs.getString("adresse"));
                System.out.println("-------------------------------------------");
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }
}
