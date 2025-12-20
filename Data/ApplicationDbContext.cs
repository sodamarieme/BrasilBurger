using Microsoft.EntityFrameworkCore;
using BrasilBurgerC.Models;

namespace BrasilBurgerC.Data
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }

        // ===================== DbSets =====================
        public DbSet<Produit> Produits { get; set; }
        public DbSet<Burger> Burgers { get; set; }
        public DbSet<Menu> Menus { get; set; }
        public DbSet<Complement> Complements { get; set; }
        public DbSet<Client> Clients { get; set; }
        public DbSet<Commande> Commandes { get; set; }
        public DbSet<CommandeItem> CommandeItems { get; set; }
        public DbSet<Paiement> Paiements { get; set; }
        public DbSet<Zone> Zones { get; set; }
        public DbSet<Livreur> Livreurs { get; set; }

        // ===================== Relations =====================
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            modelBuilder.Entity<Client>()
                .HasIndex(c => c.Email)
                .IsUnique();

            modelBuilder.Entity<Client>()
                .HasIndex(c => c.Telephone)
                .IsUnique();

            modelBuilder.Entity<Commande>()
                .HasIndex(c => c.NumeroCommande)
                .IsUnique();

            modelBuilder.Entity<CommandeItem>()
                .HasOne(ci => ci.Commande)
                .WithMany(c => c.Items)
                .HasForeignKey(ci => ci.CommandeId);

            modelBuilder.Entity<Paiement>()
                .HasOne(p => p.Commande)
                .WithOne(c => c.Paiement)
                .HasForeignKey<Paiement>(p => p.CommandeId);
        }
    }
}
