namespace BrasilBurgerC.Models
{
    public class Burger
    {
        public int Id { get; set; }
        public required string Nom { get; set; }
        public int Prix { get; set; }
        public bool EstArchive { get; set; }
    }
}
