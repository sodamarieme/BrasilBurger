using Microsoft.AspNetCore.Mvc;
using BrasilBurgerC.Data;
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

        // /Produit/Index?type=Burger
        public IActionResult Index(string type)
        {
            var produits = _context.Produits
                .Where(p => !p.EstArchive);

            if (!string.IsNullOrEmpty(type))
            {
                produits = produits.Where(p => p.Type == type);
            }

            return View(produits.ToList());
        }
    }
}
