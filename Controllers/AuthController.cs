using Microsoft.AspNetCore.Mvc;
using BrasilBurgerC.Data;
using BrasilBurgerC.Models;
using System.Security.Cryptography;
using System.Text;

namespace BrasilBurgerC.Controllers
{
    public class AuthController : Controller
    {
        private readonly ApplicationDbContext _context;

        public AuthController(ApplicationDbContext context)
        {
            _context = context;
        }

        // GET: Auth/Login
        public IActionResult Login(string? returnUrl)
        {
            ViewBag.ReturnUrl = returnUrl;
            return View();
        }


        // POST: Auth/Login
        [HttpPost]
        [ValidateAntiForgeryToken]
        public IActionResult Login(string email, string motDePasse, string? returnUrl)
        {
            if (string.IsNullOrEmpty(email) || string.IsNullOrEmpty(motDePasse))
            {
                ViewBag.Error = "Email et mot de passe requis";
                return View();
            }

            // Vérifier si c'est un client
            var client = _context.Clients
                .FirstOrDefault(c => c.Email == email && c.MotDePasse == HashPassword(motDePasse));

            if (client != null)
            {
                HttpContext.Session.SetString("UserId", client.Id.ToString());
                HttpContext.Session.SetString("UserType", "Client");
                HttpContext.Session.SetString("UserName", $"{client.Prenom} {client.Nom}");
                
                // Rediriger vers la page demandée (ex: paiement) ou le catalogue
                if (!string.IsNullOrEmpty(returnUrl))
                    return Redirect(Uri.UnescapeDataString(returnUrl));
                return RedirectToAction("Index", "Produit");
            }

            ViewBag.Error = "Email ou mot de passe incorrect";
            return View();
        }

        // GET: Auth/Register
        public IActionResult Register()
        {
            return View();
        }

        // POST: Auth/Register
        [HttpPost]
        [ValidateAntiForgeryToken]
        public IActionResult Register(string nom, string prenom, string telephone, string email, string motDePasse)
        {
            if (string.IsNullOrEmpty(nom) || string.IsNullOrEmpty(prenom) || 
                string.IsNullOrEmpty(telephone) || string.IsNullOrEmpty(email) || 
                string.IsNullOrEmpty(motDePasse))
            {
                ViewBag.Error = "Tous les champs sont requis";
                return View();
            }

            // Vérifier si l'email existe déjà
            if (_context.Clients.Any(c => c.Email == email))
            {
                ViewBag.Error = "Cet email est déjà utilisé";
                return View();
            }

            // Vérifier si le téléphone existe déjà
            if (_context.Clients.Any(c => c.Telephone == telephone))
            {
                ViewBag.Error = "Ce numéro de téléphone est déjà utilisé";
                return View();
            }

            var client = new Client
            {
                Nom = nom,
                Prenom = prenom,
                Telephone = telephone,
                Email = email,
                MotDePasse = HashPassword(motDePasse),
                DateCreation = DateTime.UtcNow
            };

            _context.Clients.Add(client);
            _context.SaveChanges();

            // Connecter automatiquement
            HttpContext.Session.SetString("UserId", client.Id.ToString());
            HttpContext.Session.SetString("UserType", "Client");
            HttpContext.Session.SetString("UserName", $"{client.Prenom} {client.Nom}");

            return RedirectToAction("Index", "Produit");
        }

        // GET: Auth/Logout
        public IActionResult Logout()
        {
            HttpContext.Session.Clear();
            return RedirectToAction("Login");
        }

        private string HashPassword(string password)
        {
            // Hash simple pour l'exemple (en production, utiliser bcrypt ou similar)
            using (var sha256 = SHA256.Create())
            {
                var hashedBytes = sha256.ComputeHash(Encoding.UTF8.GetBytes(password));
                return Convert.ToBase64String(hashedBytes);
            }
        }
    }
}

