package sn.brasilburger;

import sn.brasilburger.entity.Client;
import sn.brasilburger.entity.Commande;
import sn.brasilburger.entity.Gestionnaire;
import sn.brasilburger.entity.enums.TypeCommande;
import sn.brasilburger.repository.impl.CommandeRepositoryImpl;

public class TestCreateCommande {
    public static void main(String[] args) {
        try {
            System.out.println("🔄 Test de création de commande...\n");
            
            CommandeRepositoryImpl repo = new CommandeRepositoryImpl();
            
            // Créer une commande avec les IDs valides
            Commande c = new Commande();
            Client client = new Client();
            client.setId(1); // ID client valide
            c.setClient(client);
            
            Gestionnaire g = new Gestionnaire();
            g.setId(1); // ID gestionnaire valide
            c.setGestionnaire(g);
            
            c.setTypeCommande(TypeCommande.SUR_PLACE);
            c.setTotal(5000.0);
            
            // Créer la commande
            boolean result = repo.save(c);
            
            if (result) {
                System.out.println("✅ Commande créée avec succès!");
                System.out.println("   ID généré: " + c.getId());
                System.out.println("   Client: " + c.getClient().getId());
                System.out.println("   Gestionnaire: " + c.getGestionnaire().getId());
                System.out.println("   Type: " + c.getTypeCommande());
                System.out.println("   État: " + c.getEtatCommande());
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
