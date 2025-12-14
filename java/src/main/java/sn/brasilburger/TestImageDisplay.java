package sn.brasilburger;

import sn.brasilburger.config.DbConnection;
import sn.brasilburger.repository.impl.MenuRepositoryImpl;
import sn.brasilburger.repository.impl.BurgerRepositoryImpl;
import sn.brasilburger.repository.impl.ComplementRepositoryImpl;
import sn.brasilburger.entity.Menu;
import sn.brasilburger.entity.Burger;
import sn.brasilburger.entity.Complement;
import java.util.List;

public class TestImageDisplay {
    public static void main(String[] args) {
        System.out.println("\n========================================");
        System.out.println("   AFFICHAGE DES IMAGES CLOUDINARY");
        System.out.println("========================================\n");
        
        // Test Menus
        System.out.println("--- MENUS ---");
        MenuRepositoryImpl menuRepo = new MenuRepositoryImpl();
        List<Menu> menus = menuRepo.findAll();
        for (Menu menu : menus) {
            System.out.println("ID: " + menu.getId() + " | Nom: " + menu.getNom());
            System.out.println("  Image: " + menu.getImageUrl());
            System.out.println();
        }
        
        // Test Burgers
        System.out.println("\n--- BURGERS ---");
        BurgerRepositoryImpl burgerRepo = new BurgerRepositoryImpl();
        List<Burger> burgers = burgerRepo.findAll();
        for (Burger burger : burgers) {
            System.out.println("ID: " + burger.getId() + " | Nom: " + burger.getNom());
            System.out.println("  Image: " + burger.getImageUrl());
            System.out.println();
        }
        
        // Test Complements
        System.out.println("\n--- COMPLEMENTS ---");
        ComplementRepositoryImpl compRepo = new ComplementRepositoryImpl();
        List<Complement> complements = compRepo.findAll();
        for (Complement comp : complements) {
            System.out.println("ID: " + comp.getId() + " | Nom: " + comp.getNom());
            System.out.println("  Image: " + comp.getImageUrl());
            System.out.println();
        }
    }
}
