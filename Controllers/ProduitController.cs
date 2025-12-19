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

        public IActionResult Index()
        {
            var produits = _context.Produits.ToList();
            return View(produits);
        }
    }
}
