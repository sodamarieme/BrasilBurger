namespace BrasilBurgerC.Models
{
    public class Complement
    {
        public int Id { get; set; }

        public string Nom { get; set; } = string.Empty;

        // "Boisson" ou "Frites"
        public string Type { get; set; } = string.Empty;

        public int Prix { get; set; }

        public bool EstArchive { get; set; } = false;

        // Navigation properties
        public ICollection<CommandeItem> CommandeItems { get; set; } = new List<CommandeItem>();
    }
}

