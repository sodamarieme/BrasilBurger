# Brasil Burger - Features Documentation

## 1. ✅ Authentication System (Client Login/Register)
- Session management avec timeout 1 heure
- Password hashing sécurisé
- Client registration et connexion
- **Files**: AuthController.cs, Auth views (Login/Register)

## 2. ✅ Product Catalog with Filtering
- 4 Burgers (2500-4000 FCFA)
- 4 Menus (4500-6500 FCFA)
- 4 Compléments (500-2000 FCFA)
- Filtrage par type (Tous, Par Burger, Par Menu, Compléments)
- Images Unsplash dynamiques
- **Files**: ProduitController.cs, Views/Produit/Index.cshtml

## 3. ✅ Shopping Cart Management
- Ajouter/Retirer produits du panier
- Modifier quantité
- Calcul total en temps réel
- Persistance en session (JSON)
- Affichage prix corrects
- **Files**: PanierController.cs, Views/Panier/Index.cshtml, js/panier.js

## 4. ✅ Order Finalization Workflow
- Choix du type de livraison (A emporter / Livraison)
- Sélection zone de livraison
- Saisie informations client (Nom, Téléphone, Adresse)
- Vérification panier avant finalisation
- **Files**: CommandeController.cs, Views/Commande/finaliser.cshtml

---
