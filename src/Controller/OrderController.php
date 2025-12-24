<?php

namespace App\Controller;

use App\Entity\Order;
use App\Repository\OrderRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Component\Security\Http\Attribute\IsGranted;

#[Route('/commandes')]
#[IsGranted('ROLE_USER')]
class OrderController extends AbstractController
{
    #[Route('', name: 'app_orders')]
    public function index(OrderRepository $orderRepository): Response
    {
        $orders = $orderRepository->findByClient($this->getUser()->getId());

        return $this->render('order/index.html.twig', [
            'orders' => $orders,
        ]);
    }

    #[Route('/{id}', name: 'app_order_show')]
    public function show(Order $order): Response
    {
        // Vérifier que l'utilisateur est le propriétaire de la commande
        if ($order->getClient() !== $this->getUser()) {
            throw $this->createAccessDeniedException('Vous ne pouvez pas voir cette commande');
        }

        return $this->render('order/show.html.twig', [
            'order' => $order,
        ]);
    }

    #[Route('/{id}/annuler', name: 'app_order_cancel', methods: ['POST'])]
    public function cancel(Order $order, EntityManagerInterface $em): Response
    {
        // Vérifier que l'utilisateur est le propriétaire de la commande
        if ($order->getClient() !== $this->getUser()) {
            throw $this->createAccessDeniedException('Vous ne pouvez pas annuler cette commande');
        }

        // On ne peut annuler que les commandes en attente ou confirmées
        if (!in_array($order->getStatut(), [Order::STATUS_PENDING, Order::STATUS_CONFIRMED])) {
            $this->addFlash('error', 'Cette commande ne peut plus être annulée');
            return $this->redirectToRoute('app_order_show', ['id' => $order->getId()]);
        }

        $order->setStatut(Order::STATUS_CANCELLED);
        $order->setUpdatedAt(new \DateTimeImmutable());
        $em->flush();

        $this->addFlash('success', 'Votre commande a été annulée');
        return $this->redirectToRoute('app_orders');
    }
}
