<?php

namespace App\Controller;

use App\Entity\Burger;
use App\Entity\Menu;
use App\Entity\Complement;
use App\Repository\BurgerRepository;
use App\Repository\MenuRepository;
use App\Repository\ComplementRepository;
use App\Repository\CategoryRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/menu')]
class MenuController extends AbstractController
{
    #[Route('', name: 'app_menu')]
    public function index(
        BurgerRepository $burgerRepository,
        MenuRepository $menuRepository,
        ComplementRepository $complementRepository,
        CategoryRepository $categoryRepository
    ): Response {
        return $this->render('menu/index.html.twig', [
            'burgers' => $burgerRepository->findAvailable(),
            'menus' => $menuRepository->findAvailable(),
            'complements' => $complementRepository->findAvailable(),
            'categories' => $categoryRepository->findAll(),
        ]);
    }

    #[Route('/burger/{id}', name: 'app_menu_burger')]
    public function showBurger(Burger $burger): Response
    {
        if ($burger->isArchived() || !$burger->isDisponible()) {
            throw $this->createNotFoundException('Ce burger n\'est pas disponible');
        }

        return $this->render('menu/burger.html.twig', [
            'burger' => $burger,
        ]);
    }

    #[Route('/formule/{id}', name: 'app_menu_formule')]
    public function showMenu(Menu $menu): Response
    {
        if ($menu->isArchived() || !$menu->isDisponible()) {
            throw $this->createNotFoundException('Ce menu n\'est pas disponible');
        }

        return $this->render('menu/formule.html.twig', [
            'menu' => $menu,
        ]);
    }

    #[Route('/api/products', name: 'app_menu_api_products', methods: ['GET'])]
    public function getProducts(
        BurgerRepository $burgerRepository,
        MenuRepository $menuRepository,
        ComplementRepository $complementRepository
    ): JsonResponse {
        $products = [];

        foreach ($burgerRepository->findAvailable() as $burger) {
            $products[] = [
                'id' => $burger->getId(),
                'type' => 'burger',
                'nom' => $burger->getNom(),
                'description' => $burger->getDescription(),
                'prix' => $burger->getPrix(),
                'image' => $burger->getImage(),
            ];
        }

        foreach ($menuRepository->findAvailable() as $menu) {
            $products[] = [
                'id' => $menu->getId(),
                'type' => 'menu',
                'nom' => $menu->getNom(),
                'description' => $menu->getDescription(),
                'prix' => $menu->getPrix(),
                'image' => $menu->getImage(),
            ];
        }

        foreach ($complementRepository->findAvailable() as $complement) {
            $products[] = [
                'id' => $complement->getId(),
                'type' => 'complement',
                'nom' => $complement->getNom(),
                'description' => $complement->getDescription(),
                'prix' => $complement->getPrix(),
                'image' => $complement->getImage(),
                'categorie' => $complement->getType(),
            ];
        }

        return $this->json($products);
    }
}
