using Microsoft.EntityFrameworkCore;
using BrasilBurgerC.Models;

namespace BrasilBurgerC.Data
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options) { }

        public DbSet<Produit> Produits { get; set; }
    }
}
