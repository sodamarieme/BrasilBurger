package sn.brasilburger;

import sn.brasilburger.config.DbConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckMenusTable {
    public static void main(String[] args) {
        try {
            Connection conn = DbConnection.getConnection();
            System.out.println("✓ Database connection established");

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM menus LIMIT 1");

            System.out.println("\n=== MENUS TABLE COLUMNS ===");
            var columns = rs.getMetaData();
            for (int i = 1; i <= columns.getColumnCount(); i++) {
                System.out.println("Column " + i + ": " + columns.getColumnName(i) + " (" + columns.getColumnTypeName(i) + ")");
            }

            System.out.println("\n=== SAMPLE DATA ===");
            rs = st.executeQuery("SELECT * FROM menus");
            while (rs.next()) {
                System.out.print("ID: " + rs.getInt("id"));
                System.out.print(", Nom: " + rs.getString("nom"));
                try {
                    System.out.print(", Image: " + rs.getString("image"));
                } catch (Exception e) {
                    System.out.print(", Image: [NOT FOUND]");
                }
                try {
                    System.out.println(", Actif: " + rs.getBoolean("actif"));
                } catch (Exception e) {
                    System.out.println(", Actif: [NOT FOUND]");
                }
            }

        } catch (Exception e) {
            System.err.println("✗ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
