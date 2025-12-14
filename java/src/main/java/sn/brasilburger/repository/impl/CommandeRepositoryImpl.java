package sn.brasilburger.repository.impl;

import sn.brasilburger.config.DbConnection;
import sn.brasilburger.entity.Commande;
import sn.brasilburger.entity.enums.EtatCommande;
import sn.brasilburger.entity.enums.TypeCommande;
import sn.brasilburger.repository.CommandeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeRepositoryImpl implements CommandeRepository {

    private final Connection connection;

    public CommandeRepositoryImpl() {
        this.connection = DbConnection.getConnection();
    }

    @Override
    public boolean save(Commande commande) {
        String sql = """
            INSERT INTO commandes
            (client_id, type_commande, etat, total)
            VALUES (?, ?, ?, ?) RETURNING id
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            // Vérifier que client existe
            if (commande.getClient() == null || commande.getClient().getId() <= 0) {
                System.out.println("❌ Client invalide ou non défini");
                return false;
            }

            ps.setInt(1, commande.getClient().getId());
            ps.setString(2, commande.getTypeCommande() != null ? commande.getTypeCommande().name() : "SUR_PLACE");
            ps.setString(3, commande.getEtatCommande() != null ? commande.getEtatCommande().name() : "EN_COURS");
            ps.setDouble(4, commande.getTotal() > 0 ? commande.getTotal() : 0.0);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                commande.setId(rs.getInt(1));
            }

            return true;

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'enregistrement de la commande");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Commande> findAll() {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT id, client_id, type_commande, etat, total, date_commande FROM commandes ORDER BY id";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Commande commande = new Commande();
                commande.setId(rs.getInt("id"));
                commande.setTotal(rs.getDouble("total"));

                String etatStr = rs.getString("etat");
                if (etatStr != null) {
                    try {
                        commande.setEtatCommande(EtatCommande.valueOf(etatStr));
                    } catch (IllegalArgumentException e) {
                        commande.setEtatCommande(EtatCommande.EN_COURS);
                    }
                } else {
                    commande.setEtatCommande(EtatCommande.EN_COURS);
                }
                
                String typeStr = rs.getString("type_commande");
                if (typeStr != null) {
                    try {
                        commande.setTypeCommande(TypeCommande.valueOf(typeStr));
                    } catch (IllegalArgumentException e) {
                        commande.setTypeCommande(TypeCommande.SUR_PLACE);
                    }
                } else {
                    commande.setTypeCommande(TypeCommande.SUR_PLACE);
                }

                Timestamp ts = rs.getTimestamp("date_commande");
                if (ts != null) {
                    commande.setDateCommande(ts.toLocalDateTime());
                }

                commandes.add(commande);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commandes;
    }

    @Override
    public void updateEtat(int idCommande, String nouvelEtat) {
        String sql = "UPDATE commandes SET etat = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nouvelEtat);
            ps.setInt(2, idCommande);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int idCommande) {
        String sql = "DELETE FROM commandes WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCommande);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTotal(int idCommande, double total) {
        String sql = "UPDATE commandes SET total = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, total);
            ps.setInt(2, idCommande);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
