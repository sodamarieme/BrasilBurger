package sn.brasilburger.repository.impl;

import sn.brasilburger.config.DbConnection;
import sn.brasilburger.entity.Gestionnaire;
import sn.brasilburger.entity.Livreur;
import sn.brasilburger.entity.Zone;
import sn.brasilburger.repository.LivreurRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreurRepositoryImpl implements LivreurRepository {

    private final Connection connection;

    public LivreurRepositoryImpl() {
        this.connection = DbConnection.getConnection();
    }

    @Override
    public void save(Livreur livreur) {
        String sql = """
            INSERT INTO livreurs (nom, telephone, zone_id, disponible)
            VALUES (?, ?, ?, ?) RETURNING id
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, livreur.getNom());
            ps.setString(2, livreur.getTelephone());
            ps.setInt(3, livreur.getZone().getId());
            ps.setBoolean(4, true);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                livreur.setId(rs.getInt("id"));
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur création livreur");
            e.printStackTrace();
        }
    }

    @Override
    public List<Livreur> findAll() {
        List<Livreur> livreurs = new ArrayList<>();

        String sql = """
            SELECT l.id, l.nom, l.telephone, l.zone_id, l.disponible, z.nom AS zone_nom
            FROM livreurs l
            LEFT JOIN zones z ON l.zone_id = z.id
        """;

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Livreur l = new Livreur();
                l.setId(rs.getInt("id"));
                l.setNom(rs.getString("nom"));
                l.setTelephone(rs.getString("telephone"));

                Zone z = new Zone();
                z.setId(rs.getInt("zone_id"));
                z.setNom(rs.getString("zone_nom"));
                l.setZone(z);

                livreurs.add(l);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return livreurs;
    }
}
