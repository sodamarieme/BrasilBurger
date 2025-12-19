namespace BrasilBurgerC.Models
{
    public class Produit
    {
        public int Id { get; set; }

        public string Nom { get; set; } = string.Empty;

        public string Type { get; set; } = string.Empty;

        public int Prix { get; set; }

        public bool EstArchive { get; set; }
    }
}
