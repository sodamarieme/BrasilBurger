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
            // Rediriger vers la page de connexion ou le catalogue selon l'état de connexion
            var userType = HttpContext.Session.GetString("UserType");
            if (userType == "Client")
                return RedirectToAction("Index", "Produit");
            else
                return RedirectToAction("Login", "Auth");
        }
    }
}
