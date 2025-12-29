<?php

namespace App\Repository;

use App\Entity\Order;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

class OrderRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Order::class);
    }

    public function findByClient(int $clientId): array
    {
        return $this->createQueryBuilder('o')
            ->andWhere('o.client = :clientId')
            ->setParameter('clientId', $clientId)
            ->orderBy('o.createdAt', 'DESC')
            ->getQuery()
            ->getResult();
    }

    public function getStatistics(): array
    {
        return [
            'total_orders' => $this->count([]),
            'pending_orders' => $this->count(['statut' => Order::STATUS_PENDING]),
            'confirmed_orders' => $this->count(['statut' => Order::STATUS_CONFIRMED]),
            'preparing_orders' => $this->count(['statut' => Order::STATUS_PREPARING]),
            'ready_orders' => $this->count(['statut' => Order::STATUS_READY]),
            'delivering_orders' => $this->count(['statut' => Order::STATUS_DELIVERING]),
            'delivered_orders' => $this->count(['statut' => Order::STATUS_DELIVERED]),
            'cancelled_orders' => $this->count(['statut' => Order::STATUS_CANCELLED]),
        ];
    }
}
