-- Script de création des tables pour Brasil Burger
-- PostgreSQL / Neon

-- 1. Table ZONES
CREATE TABLE zones (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    description TEXT
);
 
-- 2. Table CLIENTS
CREATE TABLE clients (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    telephone VARCHAR(20),
    adresse VARCHAR(255),
    zone_id INT,
    FOREIGN KEY (zone_id) REFERENCES zones(id)
);

-- 3. Table BURGERS
CREATE TABLE burgers (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    description TEXT,
    prix DECIMAL(10, 2) NOT NULL,
    image_url VARCHAR(500)
);

-- 4. Table COMPLEMENTS
CREATE TABLE complements (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    prix DECIMAL(10, 2) NOT NULL,
    image_url VARCHAR(500)
);

-- 5. Table MENUS
CREATE TABLE menus (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    description TEXT,
    prix DECIMAL(10, 2) NOT NULL
);

-- 6. Table MENU_ITEMS (junction table)
CREATE TABLE menu_items (
    id SERIAL PRIMARY KEY,
    menu_id INT NOT NULL,
    burger_id INT NOT NULL,
    complement_id INT,
    FOREIGN KEY (menu_id) REFERENCES menus(id),
    FOREIGN KEY (burger_id) REFERENCES burgers(id),
    FOREIGN KEY (complement_id) REFERENCES complements(id)
);

-- 7. Table PAIEMENTS
CREATE TABLE paiements (
    id SERIAL PRIMARY KEY,
    mode_paiement VARCHAR(50) NOT NULL,
    montant DECIMAL(10, 2) NOT NULL,
    date_paiement TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 8. Table COMMANDES
CREATE TABLE commandes (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL,
    date_commande TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    etat VARCHAR(50) NOT NULL,
    type_commande VARCHAR(50),
    paiement_id INT,
    total DECIMAL(10, 2),
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (paiement_id) REFERENCES paiements(id)
);

-- 9. Table COMMANDE_ITEMS (details des commandes)
CREATE TABLE commande_items (
    id SERIAL PRIMARY KEY,
    commande_id INT NOT NULL,
    burger_id INT,
    menu_id INT,
    complement_id INT,
    quantite INT DEFAULT 1,
    prix_unitaire DECIMAL(10, 2),
    FOREIGN KEY (commande_id) REFERENCES commandes(id),
    FOREIGN KEY (burger_id) REFERENCES burgers(id),
    FOREIGN KEY (menu_id) REFERENCES menus(id),
    FOREIGN KEY (complement_id) REFERENCES complements(id)
);

-- 10. Table GESTIONNAIRES
CREATE TABLE gestionnaires (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    mot_de_passe VARCHAR(255),
    telephone VARCHAR(20)
);

-- 11. Table LIVREURS
CREATE TABLE livreurs (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    telephone VARCHAR(20),
    zone_id INT,
    disponible BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (zone_id) REFERENCES zones(id)
);

-- Index pour les performances
CREATE INDEX idx_clients_zone ON clients(zone_id);
CREATE INDEX idx_commandes_client ON commandes(client_id);
CREATE INDEX idx_commande_items_commande ON commande_items(commande_id);
CREATE INDEX idx_livreurs_zone ON livreurs(zone_id);

-- Données de test
INSERT INTO zones (nom, description) VALUES
('Dakar Centre', 'Zone centre-ville de Dakar'),
('Dakar Banlieue', 'Zone banlieue de Dakar'),
('Thiès', 'Région de Thiès'),
('Saint-Louis', 'Région de Saint-Louis');

INSERT INTO clients (nom, email, telephone, adresse, zone_id) VALUES
('Jean Dupont', 'jean@example.com', '+221771234567', '123 Rue de la Paix', 1),
('Marie Samba', 'marie@example.com', '+221772345678', '456 Avenue du Centenaire', 1),
('Ahmed Mohamed', 'ahmed@example.com', '+221773456789', '789 Boulevard de la République', 2),
('Fatou Ndiaye', 'fatou@example.com', '+221774567890', '321 Rue des Pères', 3);

INSERT INTO burgers (nom, description, prix, image_url) VALUES
('Classic Burger', 'Burger classique avec fromage et salade', 3500.00, 'https://example.com/classic.jpg'),
('Spicy Burger', 'Burger piquant avec jalapeño et sauce spéciale', 4000.00, 'https://example.com/spicy.jpg'),
('Double Burger', 'Double steak avec fromage double', 5000.00, 'https://example.com/double.jpg'),
('Chicken Burger', 'Burger au poulet grillé', 3800.00, 'https://example.com/chicken.jpg');

INSERT INTO complements (nom, type, prix, image_url) VALUES
('Frites', 'GARNITURE', 1000.00, 'https://example.com/fries.jpg'),
('Salade', 'GARNITURE', 800.00, 'https://example.com/salad.jpg'),
('Boisson Gazeuse', 'BOISSON', 1200.00, 'https://example.com/soda.jpg'),
('Jus Frais', 'BOISSON', 1500.00, ''),
('Sauce BBQ', 'SAUCE', 500.00, '');

INSERT INTO menus (nom, description, prix) VALUES
('Menu Classique', 'Burger + Frites + Boisson', 5500.00),
('Menu Spécial', 'Burger Spicy + Frites + Jus', 6500.00),
('Menu Duo', 'Double Burger + Frites + Boisson', 8000.00);

INSERT INTO paiements (mode_paiement, montant) VALUES
('CARTE_CREDIT', 5500.00),
('VIREMENT_BANCAIRE', 6500.00),
('ESPECES', 8000.00);

INSERT INTO commandes (client_id, etat, type_commande, paiement_id, total) VALUES
(1, 'LIVREE', 'LIVRAISON', 1, 5500.00),
(2, 'PREPARATION', 'SUR_PLACE', 2, 6500.00),
(3, 'CONFIRMEE', 'RETRAIT', 3, 8000.00);

INSERT INTO commande_items (commande_id, burger_id, quantite, prix_unitaire) VALUES
(1, 1, 1, 3500.00),
(2, 2, 1, 4000.00),
(3, 3, 1, 5000.00);

INSERT INTO gestionnaires (nom, email, mot_de_passe, telephone) VALUES
('Admin Manager', 'admin@brasilburger.com', 'password123', '+221771111111'),
('Responsable Stock', 'stock@brasilburger.com', 'password123', '+221772222222');

INSERT INTO livreurs (nom, telephone, zone_id, disponible) VALUES
('Moussa Sall', '+221775555555', 1, TRUE),
('Ousmane Ba', '+221776666666', 2, TRUE),
('Ibrahima Diop', '+221777777777', 3, FALSE);
