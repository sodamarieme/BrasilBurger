using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using BrasilBurgerC.Data;
using BrasilBurgerC.Models;
using System.Text.Json;

namespace BrasilBurgerC.Controllers
{
    public class PanierController : Controller
    {
        private readonly ApplicationDbContext _context;

        public PanierController(ApplicationDbContext context)
        {
            _context = context;
        }

        // GET: Panier/Index
        public IActionResult Index()
        {
            var panierJson = HttpContext.Session.GetString("Panier");
            var panier = string.IsNullOrEmpty(panierJson) 
                ? new List<PanierItem>() 
                : JsonSerializer.Deserialize<List<PanierItem>>(panierJson) ?? new List<PanierItem>();

            // Données hardcoded (mêmes que dans ProduitController)
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

            // Enrichir avec les données hardcoded
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

        // POST: Panier/Ajouter
        [HttpPost]
        public IActionResult Ajouter(string type, int? burgerId, int? menuId, int? complementId)
        {
            var panierJson = HttpContext.Session.GetString("Panier");
            var panier = string.IsNullOrEmpty(panierJson) 
                ? new List<PanierItem>() 
                : JsonSerializer.Deserialize<List<PanierItem>>(panierJson) ?? new List<PanierItem>();

            // Vérifier si l'item existe déjà
            PanierItem? existingItem = null;
            if (type == "Burger" && burgerId.HasValue)
            {
                existingItem = panier.FirstOrDefault(p => p.Type == "Burger" && p.BurgerId == burgerId.Value);
            }
            else if (type == "Menu" && menuId.HasValue)
            {
                existingItem = panier.FirstOrDefault(p => p.Type == "Menu" && p.MenuId == menuId.Value);
            }
            else if (type == "Complement" && complementId.HasValue)
            {
                existingItem = panier.FirstOrDefault(p => p.Type == "Complement" && p.ComplementId == complementId.Value);
            }

            if (existingItem != null)
            {
                existingItem.Quantite++;
            }
            else
            {
                var item = new PanierItem
                {
                    Type = type,
                    BurgerId = burgerId,
                    MenuId = menuId,
                    ComplementId = complementId,
                    Quantite = 1
                };

                // Récupérer le nom et prix depuis la base
                if (type == "Burger" && burgerId.HasValue)
                {
                    var burger = _context.Burgers.Find(burgerId.Value);
                    if (burger != null)
                    {
                        item.Nom = burger.Nom;
                        item.Prix = burger.Prix;
                    }
                }
                else if (type == "Menu" && menuId.HasValue)
                {
                    var menu = _context.Menus
                        .Include(m => m.Burger)
                        .Include(m => m.Boisson)
                        .Include(m => m.Frites)
                        .FirstOrDefault(m => m.Id == menuId.Value);
                    if (menu != null)
                    {
                        item.Nom = menu.Nom;
                        item.Prix = menu.Prix;
                    }
                }
                else if (type == "Complement" && complementId.HasValue)
                {
                    var complement = _context.Complements.Find(complementId.Value);
                    if (complement != null)
                    {
                        item.Nom = complement.Nom;
                        item.Prix = complement.Prix;
                    }
                }

                panier.Add(item);
            }

            HttpContext.Session.SetString("Panier", JsonSerializer.Serialize(panier));
            return Json(new { success = true, count = panier.Sum(p => p.Quantite) });
        }

        // POST: Panier/ModifierQuantite
        [HttpPost]
        public IActionResult ModifierQuantite(int index, int quantite)
        {
            var panierJson = HttpContext.Session.GetString("Panier");
            var panier = string.IsNullOrEmpty(panierJson) 
                ? new List<PanierItem>() 
                : JsonSerializer.Deserialize<List<PanierItem>>(panierJson) ?? new List<PanierItem>();

            if (index >= 0 && index < panier.Count)
            {
                if (quantite <= 0)
                {
                    panier.RemoveAt(index);
                }
                else
                {
                    panier[index].Quantite = quantite;
                }

                HttpContext.Session.SetString("Panier", JsonSerializer.Serialize(panier));
                return Json(new { success = true });
            }

            return Json(new { success = false });
        }

        // POST: Panier/Retirer
        [HttpPost]
        public IActionResult Retirer(int index)
        {
            var panierJson = HttpContext.Session.GetString("Panier");
            var panier = string.IsNullOrEmpty(panierJson) 
                ? new List<PanierItem>() 
                : JsonSerializer.Deserialize<List<PanierItem>>(panierJson) ?? new List<PanierItem>();

            if (index >= 0 && index < panier.Count)
            {
                panier.RemoveAt(index);
                HttpContext.Session.SetString("Panier", JsonSerializer.Serialize(panier));
                return Json(new { success = true });
            }

            return Json(new { success = false });
        }

        // POST: Panier/Vider
        [HttpPost]
        public IActionResult Vider()
        {
            HttpContext.Session.Remove("Panier");
            return RedirectToAction("Index");
        }
    }
}
