namespace BrasilBurgerC.Models
{
    public class Zone
    {
        public int Id { get; set; }
        public string Nom { get; set; } = string.Empty;
        public int PrixLivraison { get; set; }
        public bool EstActive { get; set; } = true;

        // Navigation properties
        public ICollection<Commande> Commandes { get; set; } = new List<Commande>();
        public ICollection<Livreur> Livreurs { get; set; } = new List<Livreur>();
    }
}

