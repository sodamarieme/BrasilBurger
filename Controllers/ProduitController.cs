using Microsoft.AspNetCore.Mvc;
using BrasilBurgerC.Data;
using BrasilBurgerC.Models;
using System.Linq;

namespace BrasilBurgerC.Controllers
{
    public class ProduitController : Controller
    {
        private readonly ApplicationDbContext _context;

        public ProduitController(ApplicationDbContext context)
        {
            _context = context;
        }

        public IActionResult Index(string? type)
        {
            // Données hardcodées pour les burgers avec vraies images Unsplash
            var burgers = new List<Burger>
            {
                new Burger { Id = 1, Nom = "Classic Burger", Prix = 2500, EstArchive = false },
                new Burger { Id = 2, Nom = "Cheese Burger", Prix = 3000, EstArchive = false },
                new Burger { Id = 3, Nom = "Bacon Burger", Prix = 3500, EstArchive = false },
                new Burger { Id = 4, Nom = "Double Burger", Prix = 4000, EstArchive = false }
            };

            var complements = new List<Complement>
            {
                new Complement { Id = 1, Nom = "Frites Croustillantes", Prix = 1500, EstArchive = false },
                new Complement { Id = 2, Nom = "Boisson 50cl", Prix = 2000, EstArchive = false },
                new Complement { Id = 3, Nom = "Salade Fraîche", Prix = 1800, EstArchive = false },
                new Complement { Id = 4, Nom = "Sauce Extra", Prix = 500, EstArchive = false }
            };

            var menus = new List<Menu>
            {
                new Menu { Id = 1, Nom = "Menu Classique", Description = "Burger + Frites", Prix = 4500, EstArchive = false },
                new Menu { Id = 2, Nom = "Menu Complet", Description = "Burger + Frites + Boisson", Prix = 5500, EstArchive = false },
                new Menu { Id = 3, Nom = "Menu Premium", Description = "Burger Premium + Boisson", Prix = 5800, EstArchive = false },
                new Menu { Id = 4, Nom = "Menu Deluxe", Description = "Double Burger + Frites + Boisson", Prix = 6500, EstArchive = false }
            };

            ViewBag.Burgers = burgers;
            ViewBag.Menus = menus;
            ViewBag.Complements = complements;
            ViewBag.Type = type ?? "all";

            return View();
        }
    }
}
