package sn.brasilburger.util;

public class Colors {
    // Codes ANSI pour les couleurs
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    
    // Gras
    public static final String BOLD_RED = "\u001B[1;31m";
    public static final String BOLD_GREEN = "\u001B[1;32m";
    public static final String BOLD_YELLOW = "\u001B[1;33m";
    public static final String BOLD_BLUE = "\u001B[1;34m";
    public static final String BOLD_CYAN = "\u001B[1;36m";
    
    // Méthodes utiles
    public static String colorize(String text, String color) {
        return color + text + RESET;
    }
    
    public static String success(String text) {
        return BOLD_GREEN + "✅ " + text + RESET;
    }
    
    public static String error(String text) {
        return BOLD_RED + "❌ " + text + RESET;
    }
    
    public static String warning(String text) {
        return BOLD_YELLOW + "⚠️  " + text + RESET;
    }
    
    public static String info(String text) {
        return BOLD_CYAN + "ℹ️  " + text + RESET;
    }
    
    public static String title(String text) {
        return BOLD_CYAN + "╔════════════════════════════════════════════╗\n" +
               "║  " + String.format("%-38s", text) + "  ║\n" +
               "╚════════════════════════════════════════════╝" + RESET;
    }
}
