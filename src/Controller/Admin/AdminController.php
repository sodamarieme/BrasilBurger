<?php

namespace App\Controller\Admin;

use App\Entity\Burger;
use App\Entity\Menu;
use App\Entity\Complement;
use App\Entity\Category;
use App\Entity\Order;
use App\Entity\Delivery;
use App\Entity\Zone;
use App\Entity\Livreur;
use App\Repository\BurgerRepository;
use App\Repository\MenuRepository;
use App\Repository\ComplementRepository;
use App\Repository\CategoryRepository;
use App\Repository\OrderRepository;
use App\Repository\DeliveryRepository;
use App\Repository\ZoneRepository;
use App\Repository\LivreurRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Component\Security\Http\Attribute\IsGranted;

#[Route('/admin')]
#[IsGranted('ROLE_ADMIN')]
class AdminController extends AbstractController
{
    #[Route('', name: 'app_admin_dashboard')]
    public function dashboard(
        OrderRepository $orderRepository,
        BurgerRepository $burgerRepository,
        MenuRepository $menuRepository
    ): Response {
        $stats = $orderRepository->getStatistics();
        
        return $this->render('admin/dashboard.html.twig', [
            'stats' => $stats,
            'recentOrders' => $orderRepository->findBy([], ['createdAt' => 'DESC'], 10),
            'burgerCount' => count($burgerRepository->findAll()),
            'menuCount' => count($menuRepository->findAll()),
        ]);
    }

    // ========== CATALOGUE - BURGERS ==========
    
    #[Route('/burgers', name: 'app_admin_burgers')]
    public function burgers(BurgerRepository $burgerRepository): Response
    {
        return $this->render('admin/burgers/index.html.twig', [
            'burgers' => $burgerRepository->findBy(['archived' => false], ['createdAt' => 'DESC']),
        ]);
    }

    #[Route('/burgers/nouveau', name: 'app_admin_burger_new', methods: ['GET', 'POST'])]
    public function newBurger(Request $request, EntityManagerInterface $em, CategoryRepository $categoryRepository): Response
    {
        if ($request->isMethod('POST')) {
            $burger = new Burger();
            $burger->setNom($request->request->get('nom'));
            $burger->setDescription($request->request->get('description'));
            $burger->setPrix($request->request->get('prix'));
            $burger->setImage($request->request->get('image'));
            $burger->setDisponible($request->request->has('disponible'));
            
            if ($categoryId = $request->request->get('category')) {
                $burger->setCategory($categoryRepository->find($categoryId));
            }

            $em->persist($burger);
            $em->flush();

            $this->addFlash('success', 'Burger créé avec succès');
            return $this->redirectToRoute('app_admin_burgers');
        }

        return $this->render('admin/burgers/form.html.twig', [
            'burger' => null,
            'categories' => $categoryRepository->findAll(),
        ]);
    }

    #[Route('/burgers/{id}/modifier', name: 'app_admin_burger_edit', methods: ['GET', 'POST'])]
    public function editBurger(Burger $burger, Request $request, EntityManagerInterface $em, CategoryRepository $categoryRepository): Response
    {
        if ($request->isMethod('POST')) {
            $burger->setNom($request->request->get('nom'));
            $burger->setDescription($request->request->get('description'));
            $burger->setPrix($request->request->get('prix'));
            $burger->setImage($request->request->get('image'));
            $burger->setDisponible($request->request->has('disponible'));
            $burger->setUpdatedAt(new \DateTimeImmutable());
            
            if ($categoryId = $request->request->get('category')) {
                $burger->setCategory($categoryRepository->find($categoryId));
            } else {
                $burger->setCategory(null);
            }

            $em->flush();

            $this->addFlash('success', 'Burger modifié avec succès');
            return $this->redirectToRoute('app_admin_burgers');
        }

        return $this->render('admin/burgers/form.html.twig', [
            'burger' => $burger,
            'categories' => $categoryRepository->findAll(),
        ]);
    }

    #[Route('/burgers/{id}/archiver', name: 'app_admin_burger_archive', methods: ['POST'])]
    public function archiveBurger(Burger $burger, EntityManagerInterface $em): Response
    {
        $burger->setArchived(true);
        $burger->setUpdatedAt(new \DateTimeImmutable());
        $em->flush();

        $this->addFlash('success', 'Burger archivé avec succès');
        return $this->redirectToRoute('app_admin_burgers');
    }

    // ========== CATALOGUE - MENUS ==========
    
    #[Route('/menus', name: 'app_admin_menus')]
    public function menus(MenuRepository $menuRepository): Response
    {
        return $this->render('admin/menus/index.html.twig', [
            'menus' => $menuRepository->findBy(['archived' => false], ['createdAt' => 'DESC']),
        ]);
    }

    #[Route('/menus/nouveau', name: 'app_admin_menu_new', methods: ['GET', 'POST'])]
    public function newMenu(
        Request $request,
        EntityManagerInterface $em,
        BurgerRepository $burgerRepository,
        ComplementRepository $complementRepository
    ): Response {
        if ($request->isMethod('POST')) {
            $menu = new Menu();
            $menu->setNom($request->request->get('nom'));
            $menu->setDescription($request->request->get('description'));
            $menu->setPrix($request->request->get('prix'));
            $menu->setImage($request->request->get('image'));
            $menu->setDisponible($request->request->has('disponible'));
            
            // Ajouter les burgers sélectionnés
            foreach ($request->request->all('burgers') as $burgerId) {
                if ($burger = $burgerRepository->find($burgerId)) {
                    $menu->addBurger($burger);
                }
            }
            
            // Ajouter les compléments sélectionnés
            foreach ($request->request->all('complements') as $complementId) {
                if ($complement = $complementRepository->find($complementId)) {
                    $menu->addComplement($complement);
                }
            }

            $em->persist($menu);
            $em->flush();

            $this->addFlash('success', 'Menu créé avec succès');
            return $this->redirectToRoute('app_admin_menus');
        }

        return $this->render('admin/menus/form.html.twig', [
            'menu' => null,
            'burgers' => $burgerRepository->findAvailable(),
            'complements' => $complementRepository->findAvailable(),
        ]);
    }

    #[Route('/menus/{id}/modifier', name: 'app_admin_menu_edit', methods: ['GET', 'POST'])]
    public function editMenu(
        Menu $menu,
        Request $request,
        EntityManagerInterface $em,
        BurgerRepository $burgerRepository,
        ComplementRepository $complementRepository
    ): Response {
        if ($request->isMethod('POST')) {
            $menu->setNom($request->request->get('nom'));
            $menu->setDescription($request->request->get('description'));
            $menu->setPrix($request->request->get('prix'));
            $menu->setImage($request->request->get('image'));
            $menu->setDisponible($request->request->has('disponible'));
            $menu->setUpdatedAt(new \DateTimeImmutable());
            
            // Réinitialiser les burgers et compléments
            foreach ($menu->getBurgers() as $burger) {
                $menu->removeBurger($burger);
            }
            foreach ($menu->getComplements() as $complement) {
                $menu->removeComplement($complement);
            }
            
            // Ajouter les nouveaux
            foreach ($request->request->all('burgers') as $burgerId) {
                if ($burger = $burgerRepository->find($burgerId)) {
                    $menu->addBurger($burger);
                }
            }
            foreach ($request->request->all('complements') as $complementId) {
                if ($complement = $complementRepository->find($complementId)) {
                    $menu->addComplement($complement);
                }
            }

            $em->flush();

            $this->addFlash('success', 'Menu modifié avec succès');
            return $this->redirectToRoute('app_admin_menus');
        }

        return $this->render('admin/menus/form.html.twig', [
            'menu' => $menu,
            'burgers' => $burgerRepository->findAvailable(),
            'complements' => $complementRepository->findAvailable(),
        ]);
    }

    #[Route('/menus/{id}/archiver', name: 'app_admin_menu_archive', methods: ['POST'])]
    public function archiveMenu(Menu $menu, EntityManagerInterface $em): Response
    {
        $menu->setArchived(true);
        $menu->setUpdatedAt(new \DateTimeImmutable());
        $em->flush();

        $this->addFlash('success', 'Menu archivé avec succès');
        return $this->redirectToRoute('app_admin_menus');
    }

    // ========== CATALOGUE - COMPLEMENTS ==========
    
    #[Route('/complements', name: 'app_admin_complements')]
    public function complements(ComplementRepository $complementRepository): Response
    {
        return $this->render('admin/complements/index.html.twig', [
            'complements' => $complementRepository->findBy(['archived' => false], ['type' => 'ASC', 'nom' => 'ASC']),
        ]);
    }

    #[Route('/complements/nouveau', name: 'app_admin_complement_new', methods: ['GET', 'POST'])]
    public function newComplement(Request $request, EntityManagerInterface $em): Response
    {
        if ($request->isMethod('POST')) {
            $complement = new Complement();
            $complement->setNom($request->request->get('nom'));
            $complement->setDescription($request->request->get('description'));
            $complement->setPrix($request->request->get('prix'));
            $complement->setImage($request->request->get('image'));
            $complement->setType($request->request->get('type'));
            $complement->setDisponible($request->request->has('disponible'));

            $em->persist($complement);
            $em->flush();

            $this->addFlash('success', 'Complément créé avec succès');
            return $this->redirectToRoute('app_admin_complements');
        }

        return $this->render('admin/complements/form.html.twig', [
            'complement' => null,
        ]);
    }

    #[Route('/complements/{id}/modifier', name: 'app_admin_complement_edit', methods: ['GET', 'POST'])]
    public function editComplement(Complement $complement, Request $request, EntityManagerInterface $em): Response
    {
        if ($request->isMethod('POST')) {
            $complement->setNom($request->request->get('nom'));
            $complement->setDescription($request->request->get('description'));
            $complement->setPrix($request->request->get('prix'));
            $complement->setImage($request->request->get('image'));
            $complement->setType($request->request->get('type'));
            $complement->setDisponible($request->request->has('disponible'));

            $em->flush();

            $this->addFlash('success', 'Complément modifié avec succès');
            return $this->redirectToRoute('app_admin_complements');
        }

        return $this->render('admin/complements/form.html.twig', [
            'complement' => $complement,
        ]);
    }

    #[Route('/complements/{id}/archiver', name: 'app_admin_complement_archive', methods: ['POST'])]
    public function archiveComplement(Complement $complement, EntityManagerInterface $em): Response
    {
        $complement->setArchived(true);
        $em->flush();

        $this->addFlash('success', 'Complément archivé avec succès');
        return $this->redirectToRoute('app_admin_complements');
    }

    // ========== CATEGORIES ==========
    
    #[Route('/categories', name: 'app_admin_categories')]
    public function categories(CategoryRepository $categoryRepository): Response
    {
        return $this->render('admin/categories/index.html.twig', [
            'categories' => $categoryRepository->findAll(),
        ]);
    }

    #[Route('/categories/nouveau', name: 'app_admin_category_new', methods: ['GET', 'POST'])]
    public function newCategory(Request $request, EntityManagerInterface $em): Response
    {
        if ($request->isMethod('POST')) {
            $category = new Category();
            $category->setNom($request->request->get('nom'));
            $category->setDescription($request->request->get('description'));
            $category->setImage($request->request->get('image'));

            $em->persist($category);
            $em->flush();

            $this->addFlash('success', 'Catégorie créée avec succès');
            return $this->redirectToRoute('app_admin_categories');
        }

        return $this->render('admin/categories/form.html.twig', [
            'category' => null,
        ]);
    }

    #[Route('/categories/{id}/modifier', name: 'app_admin_category_edit', methods: ['GET', 'POST'])]
    public function editCategory(Category $category, Request $request, EntityManagerInterface $em): Response
    {
        if ($request->isMethod('POST')) {
            $category->setNom($request->request->get('nom'));
            $category->setDescription($request->request->get('description'));
            $category->setImage($request->request->get('image'));

            $em->flush();

            $this->addFlash('success', 'Catégorie modifiée avec succès');
            return $this->redirectToRoute('app_admin_categories');
        }

        return $this->render('admin/categories/form.html.twig', [
            'category' => $category,
        ]);
    }

    // ========== COMMANDES ==========
    
    #[Route('/commandes', name: 'app_admin_orders')]
    public function orders(Request $request, OrderRepository $orderRepository): Response
    {
        $statut = $request->query->get('statut');
        $date = $request->query->get('date');
        
        $orders = $orderRepository->findBy([], ['createdAt' => 'DESC']);
        
        // Filtrer par statut si spécifié
        if ($statut) {
            $orders = array_filter($orders, fn($o) => $o->getStatut() === $statut);
        }
        
        return $this->render('admin/orders/index.html.twig', [
            'orders' => $orders,
            'currentStatut' => $statut,
        ]);
    }

    #[Route('/commandes/{id}', name: 'app_admin_order_show')]
    public function showOrder(Order $order): Response
    {
        return $this->render('admin/orders/show.html.twig', [
            'order' => $order,
        ]);
    }

    #[Route('/commandes/{id}/statut', name: 'app_admin_order_status', methods: ['POST'])]
    public function updateOrderStatus(Order $order, Request $request, EntityManagerInterface $em): Response
    {
        $newStatut = $request->request->get('statut');
        
        $validStatuts = [
            Order::STATUS_PENDING,
            Order::STATUS_CONFIRMED,
            Order::STATUS_PREPARING,
            Order::STATUS_READY,
            Order::STATUS_DELIVERING,
            Order::STATUS_DELIVERED,
            Order::STATUS_CANCELLED,
        ];
        
        if (in_array($newStatut, $validStatuts)) {
            $order->setStatut($newStatut);
            $order->setUpdatedAt(new \DateTimeImmutable());
            $em->flush();
            
            $this->addFlash('success', 'Statut de la commande mis à jour');
        }
        
        return $this->redirectToRoute('app_admin_order_show', ['id' => $order->getId()]);
    }

    #[Route('/commandes/{id}/annuler', name: 'app_admin_order_cancel', methods: ['POST'])]
    public function cancelOrder(Order $order, EntityManagerInterface $em): Response
    {
        $order->setStatut(Order::STATUS_CANCELLED);
        $order->setUpdatedAt(new \DateTimeImmutable());
        $em->flush();
        
        $this->addFlash('success', 'Commande annulée');
        return $this->redirectToRoute('app_admin_orders');
    }

    // ========== LIVRAISONS ==========
    
    #[Route('/livraisons', name: 'app_admin_deliveries')]
    public function deliveries(DeliveryRepository $deliveryRepository, ZoneRepository $zoneRepository): Response
    {
        return $this->render('admin/deliveries/index.html.twig', [
            'deliveries' => $deliveryRepository->findBy([], ['createdAt' => 'DESC']),
            'zones' => $zoneRepository->findActive(),
        ]);
    }

    #[Route('/livraisons/{id}/assigner', name: 'app_admin_delivery_assign', methods: ['POST'])]
    public function assignDelivery(
        Delivery $delivery,
        Request $request,
        EntityManagerInterface $em,
        LivreurRepository $livreurRepository
    ): Response {
        $livreurId = $request->request->get('livreur_id');
        
        if ($livreur = $livreurRepository->find($livreurId)) {
            $delivery->setLivreur($livreur);
            $delivery->setStatut(Delivery::STATUS_ASSIGNED);
            $delivery->setAssignedAt(new \DateTimeImmutable());
            
            // Mettre à jour le statut de la commande
            $delivery->getCommande()->setStatut(Order::STATUS_DELIVERING);
            $delivery->getCommande()->setUpdatedAt(new \DateTimeImmutable());
            
            $em->flush();
            
            $this->addFlash('success', 'Livreur assigné à la livraison');
        }
        
        return $this->redirectToRoute('app_admin_deliveries');
    }

    // ========== ZONES ==========
    
    #[Route('/zones', name: 'app_admin_zones')]
    public function zones(ZoneRepository $zoneRepository): Response
    {
        return $this->render('admin/zones/index.html.twig', [
            'zones' => $zoneRepository->findAll(),
        ]);
    }

    #[Route('/zones/nouveau', name: 'app_admin_zone_new', methods: ['GET', 'POST'])]
    public function newZone(Request $request, EntityManagerInterface $em): Response
    {
        if ($request->isMethod('POST')) {
            $zone = new Zone();
            $zone->setNom($request->request->get('nom'));
            $zone->setDescription($request->request->get('description'));
            $zone->setPrixLivraison($request->request->get('prix_livraison'));
            $zone->setActive($request->request->has('active'));

            $em->persist($zone);
            $em->flush();

            $this->addFlash('success', 'Zone créée avec succès');
            return $this->redirectToRoute('app_admin_zones');
        }

        return $this->render('admin/zones/form.html.twig', [
            'zone' => null,
        ]);
    }

    #[Route('/zones/{id}/modifier', name: 'app_admin_zone_edit', methods: ['GET', 'POST'])]
    public function editZone(Zone $zone, Request $request, EntityManagerInterface $em): Response
    {
        if ($request->isMethod('POST')) {
            $zone->setNom($request->request->get('nom'));
            $zone->setDescription($request->request->get('description'));
            $zone->setPrixLivraison($request->request->get('prix_livraison'));
            $zone->setActive($request->request->has('active'));

            $em->flush();

            $this->addFlash('success', 'Zone modifiée avec succès');
            return $this->redirectToRoute('app_admin_zones');
        }

        return $this->render('admin/zones/form.html.twig', [
            'zone' => $zone,
        ]);
    }

    // ========== LIVREURS ==========
    
    #[Route('/livreurs', name: 'app_admin_livreurs')]
    public function livreurs(LivreurRepository $livreurRepository): Response
    {
        return $this->render('admin/livreurs/index.html.twig', [
            'livreurs' => $livreurRepository->findAll(),
        ]);
    }

    #[Route('/livreurs/nouveau', name: 'app_admin_livreur_new', methods: ['GET', 'POST'])]
    public function newLivreur(Request $request, EntityManagerInterface $em, ZoneRepository $zoneRepository): Response
    {
        if ($request->isMethod('POST')) {
            $livreur = new Livreur();
            $livreur->setNom($request->request->get('nom'));
            $livreur->setPrenom($request->request->get('prenom'));
            $livreur->setTelephone($request->request->get('telephone'));
            $livreur->setEmail($request->request->get('email'));
            $livreur->setDisponible($request->request->has('disponible'));
            $livreur->setActive($request->request->has('active'));
            
            foreach ($request->request->all('zones') as $zoneId) {
                if ($zone = $zoneRepository->find($zoneId)) {
                    $livreur->addZone($zone);
                }
            }

            $em->persist($livreur);
            $em->flush();

            $this->addFlash('success', 'Livreur créé avec succès');
            return $this->redirectToRoute('app_admin_livreurs');
        }

        return $this->render('admin/livreurs/form.html.twig', [
            'livreur' => null,
            'zones' => $zoneRepository->findAll(),
        ]);
    }

    #[Route('/livreurs/{id}/modifier', name: 'app_admin_livreur_edit', methods: ['GET', 'POST'])]
    public function editLivreur(Livreur $livreur, Request $request, EntityManagerInterface $em, ZoneRepository $zoneRepository): Response
    {
        if ($request->isMethod('POST')) {
            $livreur->setNom($request->request->get('nom'));
            $livreur->setPrenom($request->request->get('prenom'));
            $livreur->setTelephone($request->request->get('telephone'));
            $livreur->setEmail($request->request->get('email'));
            $livreur->setDisponible($request->request->has('disponible'));
            $livreur->setActive($request->request->has('active'));
            
            // Réinitialiser les zones
            foreach ($livreur->getZones() as $zone) {
                $livreur->removeZone($zone);
            }
            
            foreach ($request->request->all('zones') as $zoneId) {
                if ($zone = $zoneRepository->find($zoneId)) {
                    $livreur->addZone($zone);
                }
            }

            $em->flush();

            $this->addFlash('success', 'Livreur modifié avec succès');
            return $this->redirectToRoute('app_admin_livreurs');
        }

        return $this->render('admin/livreurs/form.html.twig', [
            'livreur' => $livreur,
            'zones' => $zoneRepository->findAll(),
        ]);
    }
}
