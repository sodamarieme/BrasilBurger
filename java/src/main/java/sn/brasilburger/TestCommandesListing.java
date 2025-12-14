package sn.brasilburger;

import sn.brasilburger.config.DbConnection;
import sn.brasilburger.entity.Commande;
import sn.brasilburger.repository.impl.CommandeRepositoryImpl;

public class TestCommandesListing {
    public static void main(String[] args) {
        try {
            System.out.println("🔄 Test du listing des commandes...\n");
            
            CommandeRepositoryImpl repo = new CommandeRepositoryImpl();
            var commandes = repo.findAll();
            
            System.out.println("✅ Commandes chargées avec succès!");
            System.out.println("📊 Nombre de commandes: " + commandes.size() + "\n");
            
            for (Commande c : commandes) {
                System.out.println("ID: " + c.getId() + 
                                   " | État: " + c.getEtatCommande() + 
                                   " | Type: " + c.getTypeCommande() + 
                                   " | Total: " + c.getTotal());
            }
            
            System.out.println("\n✅ TEST RÉUSSI!");
            
        } catch (Exception e) {
            System.out.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        }     
    }
}
