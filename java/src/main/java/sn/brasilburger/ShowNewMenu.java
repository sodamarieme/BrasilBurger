package sn.brasilburger;

import sn.brasilburger.util.Colors;

public class ShowNewMenu {
    public static void main(String[] args) {
        System.out.println("\n\n");
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║                                               ║");
        System.out.println("║          🍔 BRASIL BURGER 🍟                 ║");
        System.out.println("║      SYSTÈME DE GESTION DES COMMANDES        ║");
        System.out.println("║                                               ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("  " + Colors.BOLD_CYAN + "┌─ MENU PRINCIPAL ──────────────────────────┐" + Colors.RESET);
        System.out.println("  │                                              │");
        System.out.println("  │  " + Colors.BOLD_GREEN + "1" + Colors.RESET + "  •  👥 Gestion des Clients              │");
        System.out.println("  │  " + Colors.BOLD_GREEN + "2" + Colors.RESET + "  •  📋 Gestion des Commandes            │");
        System.out.println("  │  " + Colors.BOLD_GREEN + "3" + Colors.RESET + "  •  🍔 Gestion des Burgers              │");
        System.out.println("  │  " + Colors.BOLD_GREEN + "4" + Colors.RESET + "  •  🥤 Gestion des Compléments          │");
        System.out.println("  │  " + Colors.BOLD_GREEN + "5" + Colors.RESET + "  •  📦 Gestion des Menus                │");
        System.out.println("  │  " + Colors.BOLD_GREEN + "6" + Colors.RESET + "  •  👔 Gestion des Gestionnaires        │");
        System.out.println("  │  " + Colors.BOLD_GREEN + "7" + Colors.RESET + "  •  🗺️  Gestion des Zones               │");
        System.out.println("  │  " + Colors.BOLD_GREEN + "8" + Colors.RESET + "  •  🚚 Gestion des Livreurs             │");
        System.out.println("  │                                              │");
        System.out.println("  │  " + Colors.BOLD_RED + "0" + Colors.RESET + "  •  ❌ Quitter l'application             │");
        System.out.println("  │                                              │");
        System.out.println("  " + Colors.BOLD_CYAN + "└──────────────────────────────────────────┘" + Colors.RESET);
        System.out.println();
        
        System.out.println("\n");
        System.out.println(Colors.BOLD_CYAN + "╔════════════════════════════════════════╗" + Colors.RESET);
        System.out.println(Colors.BOLD_CYAN + "║      📦 GESTION DES MENUS 📦          ║" + Colors.RESET);
        System.out.println(Colors.BOLD_CYAN + "╚════════════════════════════════════════╝" + Colors.RESET);
        System.out.println();
        System.out.println(Colors.BOLD_CYAN + "  ┌─────────────────────────────────────┐" + Colors.RESET);
        System.out.println(Colors.BOLD_CYAN + "  │" + Colors.RESET + "                                     " + Colors.BOLD_CYAN + "│" + Colors.RESET);
        System.out.println(Colors.BOLD_CYAN + "  │" + Colors.RESET + "  " + Colors.BOLD_GREEN + "1" + Colors.RESET + "  •  Créer un menu                 " + Colors.BOLD_CYAN + "│" + Colors.RESET);
        System.out.println(Colors.BOLD_CYAN + "  │" + Colors.RESET + "  " + Colors.BOLD_GREEN + "2" + Colors.RESET + "  •  Ajouter un item au menu      " + Colors.BOLD_CYAN + "│" + Colors.RESET);
        System.out.println(Colors.BOLD_CYAN + "  │" + Colors.RESET + "  " + Colors.BOLD_GREEN + "3" + Colors.RESET + "  •  Lister les menus             " + Colors.BOLD_CYAN + "│" + Colors.RESET);
        System.out.println(Colors.BOLD_CYAN + "  │" + Colors.RESET + "  " + Colors.BOLD_GREEN + "4" + Colors.RESET + "  •  Supprimer un menu            " + Colors.BOLD_CYAN + "│" + Colors.RESET);
        System.out.println(Colors.BOLD_CYAN + "  │" + Colors.RESET + "                                     " + Colors.BOLD_CYAN + "│" + Colors.RESET);
        System.out.println(Colors.BOLD_CYAN + "  │" + Colors.RESET + "  " + Colors.BOLD_RED + "0" + Colors.RESET + "  •  Retour au menu principal      " + Colors.BOLD_CYAN + "│" + Colors.RESET);
        System.out.println(Colors.BOLD_CYAN + "  │" + Colors.RESET + "                                     " + Colors.BOLD_CYAN + "│" + Colors.RESET);
        System.out.println(Colors.BOLD_CYAN + "  └─────────────────────────────────────┘" + Colors.RESET);
        System.out.println();
    }
}
