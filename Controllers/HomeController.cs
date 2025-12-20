using Microsoft.AspNetCore.Mvc;
using BrasilBurgerC.Data;

namespace BrasilBurgerC.Controllers
{
    public class HomeController : Controller
    {
        private readonly ApplicationDbContext _context;

        public HomeController(ApplicationDbContext context)
        {
            _context = context;
        }


        public IActionResult Index()
        {
            // Rediriger toujours vers le catalogue (authentification requise seulement au paiement)
            return RedirectToAction("Index", "Produit");
        }
    }
}
