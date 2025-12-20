using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using BrasilBurgerC.Data;
using BrasilBurgerC.Models;
using System.Text.Json;

namespace BrasilBurgerC.Controllers
{
    public class CommandeController : Controller
    {
        private readonly ApplicationDbContext _context;

        public CommandeController(ApplicationDbContext context)
        {
            _context = context;
        }

        private bool IsClient()
        {
            return HttpContext.Session.GetString("UserType") == "Client";
        }

        private int GetClientId()
        {
            var userId = HttpContext.Session.GetString("UserId");
            return int.TryParse(userId, out var id) ? id : 0;
        }

        // GET: Commande/Index
        public IActionResult Index()
        {
            if (!IsClient())
                return RedirectToAction("Login", "Auth");

            // Récupérer les commandes de ce client depuis la BD
            var clientId = GetClientId();
            var commandes = _context.Commandes
                .Where(c => c.ClientId == clientId)
                .Include(c => c.Items)
                .OrderByDescending(c => c.DateCommande)
                .ToList();
                
            return View(commandes);
        }

        // GET: Commande/Finaliser
        public IActionResult Finaliser()
        {
            if (!IsClient())
                return RedirectToAction("Login", "Auth");

            // Récupérer le panier de la session
            var panierJson = HttpContext.Session.GetString("Panier");
            var panier = string.IsNullOrEmpty(panierJson) 
                ? new List<PanierItem>() 
                : JsonSerializer.Deserialize<List<PanierItem>>(panierJson) ?? new List<PanierItem>();

            // Données hardcoded (mêmes que dans ProduitController et PanierController)
            var burgers = new List<(int id, string nom, int prix)>
            {
                (1, "Classic Burger", 2500),
                (2, "Cheese Burger", 3000),
                (3, "Bacon Burger", 3500),
                (4, "Double Burger", 4000)
            };

            var menus = new List<(int id, string nom, int prix)>
            {
                (1, "Menu Classique", 4500),
                (2, "Menu Complet", 5500),
                (3, "Menu Premium", 5800),
                (4, "Menu Deluxe", 6500)
            };

            var complements = new List<(int id, string nom, int prix)>
            {
                (1, "Frites Croustillantes", 1500),
                (2, "Boisson 50cl", 2000),
                (3, "Salade Fraîche", 1800),
                (4, "Sauce Extra", 500)
            };

            // Enrichir le panier avec les données hardcoded
            foreach (var item in panier)
            {
                if (item.BurgerId.HasValue)
                {
                    var burger = burgers.FirstOrDefault(b => b.id == item.BurgerId.Value);
                    if (burger != default)
                    {
                        item.Nom = burger.nom;
                        item.Prix = burger.prix;
                    }
                }
                else if (item.MenuId.HasValue)
                {
                    var menu = menus.FirstOrDefault(m => m.id == item.MenuId.Value);
                    if (menu != default)
                    {
                        item.Nom = menu.nom;
                        item.Prix = menu.prix;
                    }
                }
                else if (item.ComplementId.HasValue)
                {
                    var complement = complements.FirstOrDefault(c => c.id == item.ComplementId.Value);
                    if (complement != default)
                    {
                        item.Nom = complement.nom;
                        item.Prix = complement.prix;
                    }
                }
            }

            // Récupérer les zones de la base de données
            var zones = _context.Zones.Where(z => z.EstActive).ToList();

            ViewBag.Panier = panier;
            ViewBag.Total = panier.Sum(p => p.Prix * p.Quantite);
            ViewBag.Zones = zones;
            ViewBag.TypeCommande = HttpContext.Session.GetString("TypeCommande") ?? "SurPlace";
            return View();
        }

        // POST: Commande/Finaliser
        [HttpPost]
        [ValidateAntiForgeryToken]
        public IActionResult Finaliser(string typeCommande, string? adresseLivraison)
        {
            if (!IsClient())
                return RedirectToAction("Login", "Auth");

            HttpContext.Session.SetString("TypeCommande", typeCommande);
            if (!string.IsNullOrEmpty(adresseLivraison))
                HttpContext.Session.SetString("AdresseLivraison", adresseLivraison);

            return RedirectToAction("Paiement");
        }

        // GET: Commande/Paiement
        public IActionResult Paiement()
        {
            if (!IsClient())
                return RedirectToAction("Login", "Auth");

            // Récupérer le panier de la session
            var panierJson = HttpContext.Session.GetString("Panier");
            var panier = string.IsNullOrEmpty(panierJson) 
                ? new List<PanierItem>() 
                : JsonSerializer.Deserialize<List<PanierItem>>(panierJson) ?? new List<PanierItem>();

            // Données hardcoded (mêmes que dans ProduitController et PanierController)
            var burgers = new List<(int id, string nom, int prix)>
            {
                (1, "Classic Burger", 2500),
                (2, "Cheese Burger", 3000),
                (3, "Bacon Burger", 3500),
                (4, "Double Burger", 4000)
            };

            var menus = new List<(int id, string nom, int prix)>
            {
                (1, "Menu Classique", 4500),
                (2, "Menu Complet", 5500),
                (3, "Menu Premium", 5800),
                (4, "Menu Deluxe", 6500)
            };

            var complements = new List<(int id, string nom, int prix)>
            {
                (1, "Frites Croustillantes", 1500),
                (2, "Boisson 50cl", 2000),
                (3, "Salade Fraîche", 1800),
                (4, "Sauce Extra", 500)
            };

            // Enrichir le panier avec les données hardcoded
            foreach (var item in panier)
            {
                if (item.BurgerId.HasValue)
                {
                    var burger = burgers.FirstOrDefault(b => b.id == item.BurgerId.Value);
                    if (burger != default)
                    {
                        item.Nom = burger.nom;
                        item.Prix = burger.prix;
                    }
                }
                else if (item.MenuId.HasValue)
                {
                    var menu = menus.FirstOrDefault(m => m.id == item.MenuId.Value);
                    if (menu != default)
                    {
                        item.Nom = menu.nom;
                        item.Prix = menu.prix;
                    }
                }
                else if (item.ComplementId.HasValue)
                {
                    var complement = complements.FirstOrDefault(c => c.id == item.ComplementId.Value);
                    if (complement != default)
                    {
                        item.Nom = complement.nom;
                        item.Prix = complement.prix;
                    }
                }
            }

            ViewBag.Panier = panier;
            ViewBag.Total = panier.Sum(p => p.Prix * p.Quantite);
            return View();
        }

        // POST: Commande/Paiement
        [HttpPost]
        [ValidateAntiForgeryToken]
        public IActionResult Paiement(string modePaiement, string? numeroTransaction)
        {
            if (!IsClient())
                return RedirectToAction("Login", "Auth");

            HttpContext.Session.SetString("ModePaiement", modePaiement);
            if (!string.IsNullOrEmpty(numeroTransaction))
                HttpContext.Session.SetString("NumeroTransaction", numeroTransaction);

            return RedirectToAction("ConfirmerPaiement");
        }

        // GET: Commande/ConfirmerPaiement
        public IActionResult ConfirmerPaiement()
        {
            if (!IsClient())
                return RedirectToAction("Login", "Auth");

            var modePaiement = HttpContext.Session.GetString("ModePaiement");
            var typeCommande = HttpContext.Session.GetString("TypeCommande");

            if (string.IsNullOrEmpty(modePaiement))
                return RedirectToAction("Paiement");

            ViewBag.ModePaiement = modePaiement;
            ViewBag.TypeCommande = typeCommande;
            ViewBag.NumeroTransaction = HttpContext.Session.GetString("NumeroTransaction");

            return View();
        }

        // POST: Commande/ConfirmerPaiement
        [HttpPost]
        [ValidateAntiForgeryToken]
        public IActionResult ConfirmerPaiement(bool confirmer)
        {
            if (!IsClient())
                return RedirectToAction("Login", "Auth");

            if (!confirmer)
                return RedirectToAction("Paiement");

            try
            {
                // Récupérer le panier de la session
                var panierJson = HttpContext.Session.GetString("Panier");
                var panier = string.IsNullOrEmpty(panierJson) 
                    ? new List<PanierItem>() 
                    : JsonSerializer.Deserialize<List<PanierItem>>(panierJson) ?? new List<PanierItem>();

                // Créer la commande
                var typeCommande = HttpContext.Session.GetString("TypeCommande") ?? "SurPlace";
                Enum.TryParse<TypeCommande>(typeCommande, out var enumType);

                var commande = new Commande
                {
                    NumeroCommande = $"CMD#{DateTime.UtcNow.Ticks}",
                    DateCommande = DateTime.UtcNow,
                    ClientId = GetClientId(),
                    Statut = StatutCommande.EnCours,
                    TypeCommande = enumType,
                    Items = new List<CommandeItem>()
                };

                // Ajouter les items du panier
                foreach (var item in panier)
                {
                    var commandeItem = new CommandeItem
                    {
                        Quantite = item.Quantite,
                        PrixUnitaire = item.Prix,
                        BurgerId = item.BurgerId,
                        MenuId = item.MenuId,
                        ComplementId = item.ComplementId
                    };
                    commande.Items.Add(commandeItem);
                }

                // Sauvegarder en BD
                _context.Commandes.Add(commande);
                _context.SaveChanges();

                TempData["Success"] = $"Commande {commande.NumeroCommande} validée !";
                
                // Vider la session
                HttpContext.Session.Remove("Panier");
                HttpContext.Session.Remove("TypeCommande");
                HttpContext.Session.Remove("AdresseLivraison");
                HttpContext.Session.Remove("ModePaiement");
                HttpContext.Session.Remove("NumeroTransaction");

                return RedirectToAction("Index");
            }
            catch (Exception ex)
            {
                ViewBag.Error = $"Erreur lors de la sauvegarde: {ex.Message}";
                return RedirectToAction("Paiement");
            }
        }
    }
}
