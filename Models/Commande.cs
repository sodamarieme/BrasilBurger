namespace BrasilBurgerC.Models
{
    public enum TypeCommande
    {
        SurPlace,
        Emporter,
        Livraison
    }

    public enum StatutCommande
    {
        EnCours,
        Validee,
        Terminee,
        Annulee
    }

    public class Commande
    {
        public int Id { get; set; }
        public string NumeroCommande { get; set; } = string.Empty; // Format: Commande #timestamp
        public DateTime DateCommande { get; set; } = DateTime.UtcNow;
        public TypeCommande TypeCommande { get; set; }
        public StatutCommande Statut { get; set; } = StatutCommande.EnCours;

        // Informations client
        public int ClientId { get; set; }
        public Client Client { get; set; } = null!;
        public string NomComplet { get; set; } = string.Empty;
        public string Telephone { get; set; } = string.Empty;

        // Informations de livraison
        public string? AdresseLivraison { get; set; }
        public int? ZoneId { get; set; }
        public Zone? Zone { get; set; }
        public int? LivreurId { get; set; }
        public Livreur? Livreur { get; set; }

        // Items de la commande
        public ICollection<CommandeItem> Items { get; set; } = new List<CommandeItem>();

        // Paiement
        public Paiement? Paiement { get; set; }

        // Total calculé - non mappé à la base de données
        [System.ComponentModel.DataAnnotations.Schema.NotMapped]
        public int Total
        {
            get
            {
                return Items.Sum(item => item.PrixTotal);
            }
        }
    }
}

