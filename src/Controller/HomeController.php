<?php

namespace App\Controller;

use App\Repository\BurgerRepository;
use App\Repository\MenuRepository;
use App\Repository\ComplementRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

class HomeController extends AbstractController
{
    #[Route('/', name: 'app_home')]
    public function index(
        BurgerRepository $burgerRepository,
        MenuRepository $menuRepository,
        ComplementRepository $complementRepository
    ): Response {
        $burgers = $burgerRepository->findAvailable();
        $menus = $menuRepository->findAvailable();
        $complements = $complementRepository->findAvailable();
        
        // Prendre les 6 premiers pour la page d'accueil
        $featuredBurgers = array_slice($burgers, 0, 6);
        $featuredMenus = array_slice($menus, 0, 3);
        $featuredComplements = array_slice($complements, 0, 4);

        return $this->render('home/index.html.twig', [
            'burgers' => $featuredBurgers,
            'menus' => $featuredMenus,
            'complements' => $featuredComplements,
        ]);
    }
}
