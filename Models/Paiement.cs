namespace BrasilBurgerC.Models
{
    public enum ModePaiement
    {
        Wave,
        OrangeMoney
    }

    public class Paiement
    {
        public int Id { get; set; }
        public int CommandeId { get; set; }
        public Commande Commande { get; set; } = null!;
        public DateTime DatePaiement { get; set; } = DateTime.UtcNow;
        public int Montant { get; set; }
        public ModePaiement ModePaiement { get; set; }
        public string? NumeroTransaction { get; set; } // Pour Wave ou OM
        public bool EstValide { get; set; } = true;
    }
}

