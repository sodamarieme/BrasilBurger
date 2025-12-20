namespace BrasilBurgerC.Models
{
    public class CommandeItem
    {
        public int Id { get; set; }
        public int CommandeId { get; set; }
        public Commande Commande { get; set; } = null!;
        
        public int Quantite { get; set; } = 1;
        public int PrixUnitaire { get; set; }
        public int PrixTotal => Quantite * PrixUnitaire;

        // Peut être un Burger, Menu, ou Complement
        public int? BurgerId { get; set; }
        public Burger? Burger { get; set; }
        
        public int? MenuId { get; set; }
        public Menu? Menu { get; set; }
        
        public int? ComplementId { get; set; }
        public Complement? Complement { get; set; }

        // Pour identifier le type d'item - non mappé à la base de données
        [System.ComponentModel.DataAnnotations.Schema.NotMapped]
        public string TypeItem
        {
            get
            {
                if (BurgerId.HasValue) return "Burger";
                if (MenuId.HasValue) return "Menu";
                if (ComplementId.HasValue) return "Complement";
                return "Inconnu";
            }
        }

        [System.ComponentModel.DataAnnotations.Schema.NotMapped]
        public string NomItem
        {
            get
            {
                if (BurgerId.HasValue) return Burger?.Nom ?? "Burger";
                if (MenuId.HasValue) return Menu?.Nom ?? "Menu";
                if (ComplementId.HasValue) return Complement?.Nom ?? "Complement";
                return "Inconnu";
            }
        }
    }
}

