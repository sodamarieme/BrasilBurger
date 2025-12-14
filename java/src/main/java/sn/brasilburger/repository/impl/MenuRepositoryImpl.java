package sn.brasilburger.repository.impl;

import sn.brasilburger.config.DbConnection;
import sn.brasilburger.entity.Menu;
import sn.brasilburger.entity.MenuItem;
import sn.brasilburger.entity.enums.TypeItem;
import sn.brasilburger.repository.MenuRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuRepositoryImpl implements MenuRepository {

    private final Connection connection;

    public MenuRepositoryImpl() {
        this.connection = DbConnection.getConnection();
    }

    @Override
    public int save(Menu menu) {
        String sql = "INSERT INTO menus (nom, description, prix, image_url) VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, menu.getNom());
            ps.setString(2, menu.getImage()); // Using image field for description
            ps.setDouble(3, 0); // Default price, will be calculated from items
            ps.setString(4, menu.getImage()); // image_url

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur insertion menu");
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void addItemToMenu(int menuId, MenuItem item) {
        String sql = "";
        
        // Déterminer le type d'item et construire la bonne requête
        if (item.getTypeItem().name().equals("BURGER")) {
            sql = "INSERT INTO menu_items (menu_id, burger_id) VALUES (?, ?)";
        } else if (item.getTypeItem().name().equals("COMPLEMENT")) {
            sql = "INSERT INTO menu_items (menu_id, complement_id) VALUES (?, ?)";
        } else {
            System.out.println("❌ Type d'item non supporté: " + item.getTypeItem());
            return;
        }
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, menuId);
            ps.setInt(2, item.getIdItem());
            ps.executeUpdate();
            System.out.println("✅ Item ajouté au menu avec succès");
        } catch (SQLException e) {
            System.out.println("❌ Erreur ajout item menu");
            e.printStackTrace();
        }
    }

    @Override
    public List<Menu> findAll() {
        List<Menu> menus = new ArrayList<>();
        String sql = "SELECT id, nom, description, prix, image_url FROM menus";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Menu menu = new Menu();
                menu.setId(rs.getInt("id"));
                menu.setNom(rs.getString("nom"));
                menu.setImageUrl(rs.getString("image_url"));
                menu.setActif(true); // Default to true since no actif column in database
                menus.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menus;
    }

    @Override
    public Menu findById(int id) {
        String sql = "SELECT id, nom, description, prix, image_url FROM menus WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Menu menu = new Menu();
                menu.setId(rs.getInt("id"));
                menu.setNom(rs.getString("nom"));
                menu.setImageUrl(rs.getString("image_url"));
                menu.setActif(true); // Default to true since no actif column in database
                return menu;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM menus WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
public double calculerPrixMenu(int idMenu) {
    String sql = """
        SELECT SUM(COALESCE(b.prix, 0) + COALESCE(c.prix, 0)) as total
        FROM menu_items mi
        LEFT JOIN burgers b ON mi.burger_id = b.id
        LEFT JOIN complements c ON mi.complement_id = c.id
        WHERE mi.menu_id = ?
    """;

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, idMenu);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getDouble(1);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;
}

}
