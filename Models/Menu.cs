using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;

namespace BrasilBurgerC.Models
{
    public class Menu
    {
        public int Id { get; set; }

        public string Nom { get; set; } = string.Empty;

        public string Description { get; set; } = string.Empty;

        public bool EstArchive { get; set; } = false;

        // ===================== Composition du menu =====================

        public int BurgerId { get; set; }
        public Burger Burger { get; set; } = null!;

        public int? BoissonId { get; set; }
        public Complement? Boisson { get; set; }

        public int? FritesId { get; set; }
        public Complement? Frites { get; set; }

        // ===================== Prix calculé (NON stocké en base) =====================
        [NotMapped]
        public int Prix { get; set; }

        // ===================== Navigation =====================
        public ICollection<CommandeItem> CommandeItems { get; set; } = new List<CommandeItem>();
    }
}
