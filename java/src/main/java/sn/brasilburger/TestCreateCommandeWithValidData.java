package sn.brasilburger;

import sn.brasilburger.entity.Client;
import sn.brasilburger.entity.Commande;
import sn.brasilburger.entity.Gestionnaire;
import sn.brasilburger.entity.enums.TypeCommande;
import sn.brasilburger.repository.impl.ClientRepositoryImpl;
import sn.brasilburger.repository.impl.CommandeRepositoryImpl;
import sn.brasilburger.repository.impl.GestionnaireRepositoryImpl;
import sn.brasilburger.service.impl.ClientServiceImpl;
import sn.brasilburger.service.impl.CommandeServiceImpl;
import sn.brasilburger.service.impl.GestionnaireServiceImpl;

public class TestCreateCommandeWithValidData {
    public static void main(String[] args) {
        try {
            System.out.println("🔄 Test de création de commande avec données valides...\n");
            
            // Créer les services
            ClientRepositoryImpl clientRepo = new ClientRepositoryImpl();
            CommandeRepositoryImpl commandeRepo = new CommandeRepositoryImpl();
            GestionnaireRepositoryImpl gestionnaireRepo = new GestionnaireRepositoryImpl();
            
            ClientServiceImpl clientService = new ClientServiceImpl(clientRepo);
            GestionnaireServiceImpl gestionnaireService = new GestionnaireServiceImpl(gestionnaireRepo);
            CommandeServiceImpl commandeService = new CommandeServiceImpl(commandeRepo, null, null, null, null);
            
            // Afficher les données valides
            System.out.println("=== CLIENTS DISPONIBLES ===");
            var clients = clientService.listerClients();
            for (Client c : clients) {
                System.out.println(c.getId() + " - " + c.getNom() + " " + (c.getPrenom() != null ? c.getPrenom() : ""));
            }
            
            System.out.println("\n=== GESTIONNAIRES DISPONIBLES ===");
            var gestionnaires = gestionnaireService.listerGestionnaires();
            for (Gestionnaire g : gestionnaires) {
                System.out.println(g.getId() + " - " + g.getNom());
            }
            
            // Créer une commande avec des données valides
            System.out.println("\n🔄 Création de commande avec ID client=1, gestionnaire=1...\n");
            
            Commande c = new Commande();
            Client client = new Client();
            client.setId(1);
            c.setClient(client);
            
            Gestionnaire g = new Gestionnaire();
            g.setId(1);  // ID valide (pas 100!)
            c.setGestionnaire(g);
            
            c.setTypeCommande(TypeCommande.SUR_PLACE);
            c.setTotal(7500.0);
            
            boolean result = commandeService.creerCommande(c);
            
            if (result) {
                System.out.println("✅ Commande créée avec succès!");
                System.out.println("   ID: " + c.getId());
                System.out.println("   Client: " + c.getClient().getId());
                System.out.println("   Gestionnaire: " + c.getGestionnaire().getId());
                System.out.println("   Type: " + c.getTypeCommande());
                System.out.println("   Total: " + c.getTotal());
            } else {
                System.out.println("❌ Erreur lors de la création");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
