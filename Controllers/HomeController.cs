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

        public IActionResult Logout()
        {
            // Redirige vers le catalogue UML
            return RedirectToAction("Index", "Home");
        }
    }
}


