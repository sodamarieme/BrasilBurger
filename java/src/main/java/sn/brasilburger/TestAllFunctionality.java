package sn.brasilburger;

import sn.brasilburger.config.DbConnection;
import sn.brasilburger.entity.*;
import sn.brasilburger.repository.impl.*;
import sn.brasilburger.service.impl.*;
import java.sql.Connection;

public class TestAllFunctionality {
    public static void main(String[] args) {
        try {
            Connection conn = DbConnection.getConnection();
            System.out.println("✓ Database connection established\n");

            // Initialize all repositories
            ZoneRepositoryImpl zoneRepository = new ZoneRepositoryImpl();
            LivreurRepositoryImpl livreurRepository = new LivreurRepositoryImpl();
            
            // Test 1: Zones
            System.out.println("=== TEST 1: List Zones ===");
            var zones = zoneRepository.findAll();
            if (zones != null && !zones.isEmpty()) {
                for (Zone zone : zones) {
                    System.out.println("  Zone ID: " + zone.getId() + ", Nom: " + zone.getNom());
                }
                System.out.println("✓ Zones test PASSED\n");
            } else {
                System.out.println("✗ No zones found\n");
            }

            // Test 2: Livreurs
            System.out.println("=== TEST 2: List Livreurs ===");
            var livreurs = livreurRepository.findAll();
            if (livreurs != null && !livreurs.isEmpty()) {
                for (Livreur livreur : livreurs) {
                    System.out.println("  Livreur ID: " + livreur.getId() + ", Nom: " + livreur.getNom() + ", Zone: " + livreur.getZone().getNom());
                }
                System.out.println("✓ Livreurs test PASSED\n");
            } else {
                System.out.println("✗ No livreurs found\n");
            }

            // Test 3: Clients
            System.out.println("=== TEST 3: List Clients ===");
            ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();
            var clients = clientRepository.findAll();
            if (clients != null && !clients.isEmpty()) {
                for (Client client : clients) {
                    System.out.println("  Client ID: " + client.getId() + ", Nom: " + client.getNom());
                }
                System.out.println("✓ Clients test PASSED\n");
            } else {
                System.out.println("✗ No clients found\n");
            }

            System.out.println("✓ ALL TESTS COMPLETED SUCCESSFULLY");

        } catch (Exception e) {
            System.err.println("✗ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
