namespace BrasilBurgerC.Models
{
    public class Produit
    {
        public int Id { get; set; }
        public string Nom { get; set; } = "";
        public int Prix { get; set; }
        public string Type { get; set; } = ""; // burger | menu | complement
        public bool EstArchive { get; set; }
    }
}
