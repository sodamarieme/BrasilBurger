namespace BrasilBurgerC.Models
{
    public class Livreur
    {
        public int Id { get; set; }
        public string Nom { get; set; } = string.Empty;
        public string Prenom { get; set; } = string.Empty;
        public string Telephone { get; set; } = string.Empty;
        public bool EstDisponible { get; set; } = true;
        public DateTime DateCreation { get; set; } = DateTime.Now;

        // Zone assignée
        public int? ZoneId { get; set; }
        public Zone? Zone { get; set; }

        // Navigation properties
        public ICollection<Commande> Commandes { get; set; } = new List<Commande>();
    }
}

