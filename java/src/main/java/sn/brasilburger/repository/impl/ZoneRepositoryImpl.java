package sn.brasilburger.repository.impl;

import sn.brasilburger.config.DbConnection;
import sn.brasilburger.entity.Zone;
import sn.brasilburger.repository.ZoneRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZoneRepositoryImpl implements ZoneRepository {

    private final Connection connection;

    public ZoneRepositoryImpl() {
        this.connection = DbConnection.getConnection();
    }

    @Override
    public void save(Zone zone) {
        String sql = "INSERT INTO zones (nom, description) VALUES (?, ?) RETURNING id";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, zone.getNom());
            ps.setString(2, "");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                zone.setId(rs.getInt("id"));
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur création zone");
            e.printStackTrace();
        }
    }

    @Override
    public List<Zone> findAll() {
        List<Zone> zones = new ArrayList<>();
        String sql = "SELECT id, nom FROM zones";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Zone z = new Zone();
                z.setId(rs.getInt("id"));
                z.setNom(rs.getString("nom"));
                z.setPrixLivraison(0);
                zones.add(z);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return zones;
    }

    @Override
    public Zone findById(int id) {
        String sql = "SELECT id, nom FROM zones WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Zone z = new Zone();
                z.setId(id);
                z.setNom(rs.getString("nom"));
                z.setPrixLivraison(0);
                return z;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
