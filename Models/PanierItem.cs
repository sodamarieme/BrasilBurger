namespace BrasilBurgerC.Models
{
    public class PanierItem
    {
        public string Type { get; set; } = string.Empty; // "Burger", "Menu", "Complement"
        public int? BurgerId { get; set; }
        public int? MenuId { get; set; }
        public int? ComplementId { get; set; }
        public string Nom { get; set; } = string.Empty;
        public int Prix { get; set; }
        public int Quantite { get; set; } = 1;
    }
}

