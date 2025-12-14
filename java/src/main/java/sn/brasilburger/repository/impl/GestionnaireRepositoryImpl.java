package sn.brasilburger.repository.impl;

import sn.brasilburger.config.DbConnection;
import sn.brasilburger.entity.Gestionnaire;
import sn.brasilburger.repository.GestionnaireRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireRepositoryImpl implements GestionnaireRepository {

    private final Connection connection;

    public GestionnaireRepositoryImpl() {
        this.connection = DbConnection.getConnection();
    }

    @Override
    public void save(Gestionnaire g) {
        String sql = """
            INSERT INTO gestionnaires (nom, email, telephone, password)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, g.getNom());
            ps.setString(2, g.getEmail());
            ps.setString(3, g.getTelephone());
            ps.setString(4, g.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Erreur création gestionnaire");
            e.printStackTrace();
        }
    }

    @Override
    public List<Gestionnaire> findAll() {
        List<Gestionnaire> list = new ArrayList<>();
        String sql = "SELECT * FROM gestionnaires";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Gestionnaire g = new Gestionnaire();
                g.setId(rs.getInt("id"));
                g.setNom(rs.getString("nom"));
                try {
                    g.setPrenom(rs.getString("prenom"));
                } catch (SQLException e) {
                    // prenom n'existe pas, on ignore
                }
                try {
                    g.setEmail(rs.getString("email"));
                } catch (SQLException e) {
                    // email n'existe pas, on ignore
                }
                try {
                    g.setTelephone(rs.getString("telephone"));
                } catch (SQLException e) {
                    // telephone n'existe pas, on ignore
                }
                list.add(g);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Gestionnaire findById(int id) {
        String sql = "SELECT * FROM gestionnaires WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Gestionnaire g = new Gestionnaire();
                g.setId(id);
                g.setNom(rs.getString("nom"));
                try {
                    g.setPrenom(rs.getString("prenom"));
                } catch (SQLException e) {
                    // prenom n'existe pas
                }
                try {
                    g.setEmail(rs.getString("email"));
                } catch (SQLException e) {
                    // email n'existe pas
                }
                try {
                    g.setTelephone(rs.getString("telephone"));
                } catch (SQLException e) {
                    // telephone n'existe pas
                }
                return g;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
