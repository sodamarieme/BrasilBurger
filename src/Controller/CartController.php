<?php

namespace App\Controller;

use App\Entity\Order;
use App\Entity\OrderItem;
use App\Entity\Delivery;
use App\Repository\BurgerRepository;
use App\Repository\MenuRepository;
use App\Repository\ComplementRepository;
use App\Repository\ZoneRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Component\Security\Http\Attribute\IsGranted;

#[Route('/panier')]
class CartController extends AbstractController
{
    #[Route('', name: 'app_cart')]
    public function index(ZoneRepository $zoneRepository): Response
    {
        return $this->render('cart/index.html.twig', [
            'zones' => $zoneRepository->findActive(),
        ]);
    }

    #[Route('/valider', name: 'app_cart_checkout', methods: ['POST'])]
    #[IsGranted('ROLE_USER')]
    public function checkout(
        Request $request,
        EntityManagerInterface $em,
        BurgerRepository $burgerRepository,
        MenuRepository $menuRepository,
        ComplementRepository $complementRepository,
        ZoneRepository $zoneRepository
    ): Response {
        $data = json_decode($request->getContent(), true);
        
        if (empty($data['items'])) {
            return $this->json(['error' => 'Le panier est vide'], 400);
        }

        $user = $this->getUser();
        $order = new Order();
        $order->setClient($user);
        $order->setAdresseLivraison($data['adresse'] ?? $user->getAdresse() ?? '');
        $order->setNotes($data['notes'] ?? null);

        $totalProduits = 0;

        foreach ($data['items'] as $item) {
            $orderItem = new OrderItem();
            $orderItem->setType($item['type']);
            $orderItem->setProduitId($item['id']);
            $orderItem->setQuantite($item['quantity']);

            // Récupérer le produit selon son type
            $product = null;
            switch ($item['type']) {
                case 'burger':
                    $product = $burgerRepository->find($item['id']);
                    break;
                case 'menu':
                    $product = $menuRepository->find($item['id']);
                    break;
                case 'complement':
                    $product = $complementRepository->find($item['id']);
                    break;
            }

            if (!$product) {
                continue;
            }

            $orderItem->setProduitNom($product->getNom());
            $orderItem->setPrixUnitaire($product->getPrix());
            $orderItem->calculateSousTotal();
            
            $totalProduits += (float) $orderItem->getSousTotal();
            $order->addOrderItem($orderItem);
        }

        // Gestion de la livraison
        $fraisLivraison = 0;
        if (!empty($data['zoneId'])) {
            $zone = $zoneRepository->find($data['zoneId']);
            if ($zone) {
                $fraisLivraison = (float) $zone->getPrixLivraison();
                
                $delivery = new Delivery();
                $delivery->setZone($zone);
                $delivery->setPrixLivraison($zone->getPrixLivraison());
                $order->setDelivery($delivery);
                $em->persist($delivery);
            }
        }

        $order->setTotalProduits((string) $totalProduits);
        $order->setFraisLivraison((string) $fraisLivraison);
        $order->setTotal((string) ($totalProduits + $fraisLivraison));

        $em->persist($order);
        $em->flush();

        return $this->json([
            'success' => true,
            'message' => 'Commande créée avec succès',
            'orderId' => $order->getId(),
            'reference' => $order->getReference(),
        ]);
    }

    #[Route('/api/zone/{id}/prix', name: 'app_cart_zone_price', methods: ['GET'])]
    public function getZonePrice(int $id, ZoneRepository $zoneRepository): JsonResponse
    {
        $zone = $zoneRepository->find($id);
        
        if (!$zone) {
            return $this->json(['error' => 'Zone non trouvée'], 404);
        }

        return $this->json([
            'prix' => $zone->getPrixLivraison(),
            'nom' => $zone->getNom(),
        ]);
    }
}
