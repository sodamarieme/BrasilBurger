package sn.brasilburger;

import sn.brasilburger.config.DbConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckComplementsData {
    public static void main(String[] args) {
        try {
            Connection conn = DbConnection.getConnection();
            System.out.println("✓ Database connection established");

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM complements LIMIT 1");

            System.out.println("\n=== COMPLEMENTS TABLE COLUMNS ===");
            var columns = rs.getMetaData();
            for (int i = 1; i <= columns.getColumnCount(); i++) {
                System.out.println("Column " + i + ": " + columns.getColumnName(i) + " (" + columns.getColumnTypeName(i) + ")");
            }

            System.out.println("\n=== SAMPLE DATA ===");
            rs = st.executeQuery("SELECT * FROM complements");
            while (rs.next()) {
                System.out.print("ID: " + rs.getInt("id"));
                System.out.print(", Nom: " + rs.getString("nom"));
                System.out.print(", Type: " + rs.getObject("type"));
                System.out.println(", Prix: " + rs.getDouble("prix"));
            }

        } catch (Exception e) {
            System.err.println("✗ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
