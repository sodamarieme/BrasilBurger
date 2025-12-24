<?php

namespace App\Repository;

use App\Entity\Order;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Order>
 */
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

    public function findByStatut(string $statut): array
    {
        return $this->createQueryBuilder('o')
            ->andWhere('o.statut = :statut')
            ->setParameter('statut', $statut)
            ->orderBy('o.createdAt', 'DESC')
            ->getQuery()
            ->getResult();
    }

    public function findByDateRange(\DateTimeImmutable $start, \DateTimeImmutable $end): array
    {
        return $this->createQueryBuilder('o')
            ->andWhere('o.createdAt >= :start')
            ->andWhere('o.createdAt <= :end')
            ->setParameter('start', $start)
            ->setParameter('end', $end)
            ->orderBy('o.createdAt', 'DESC')
            ->getQuery()
            ->getResult();
    }

    public function findPendingOrders(): array
    {
        return $this->createQueryBuilder('o')
            ->andWhere('o.statut NOT IN (:excludedStatuts)')
            ->setParameter('excludedStatuts', [Order::STATUS_DELIVERED, Order::STATUS_CANCELLED])
            ->orderBy('o.createdAt', 'ASC')
            ->getQuery()
            ->getResult();
    }

    public function getStatistics(): array
    {
        $today = new \DateTimeImmutable('today');
        
        // Commandes en cours (en_attente, confirmee, en_preparation, en_livraison)
        $pending = $this->createQueryBuilder('o')
            ->select('COUNT(o.id)')
            ->andWhere('o.statut IN (:pendingStatuts)')
            ->andWhere('o.createdAt >= :today')
            ->setParameter('pendingStatuts', [Order::STATUS_PENDING, Order::STATUS_CONFIRMED, Order::STATUS_PREPARING, Order::STATUS_DELIVERING])
            ->setParameter('today', $today)
            ->getQuery()
            ->getSingleScalarResult();

        // Commandes validées/livrées aujourd'hui
        $completed = $this->createQueryBuilder('o')
            ->select('COUNT(o.id)')
            ->andWhere('o.statut = :delivered')
            ->andWhere('o.createdAt >= :today')
            ->setParameter('delivered', Order::STATUS_DELIVERED)
            ->setParameter('today', $today)
            ->getQuery()
            ->getSingleScalarResult();

        // Recettes journalières
        $revenue = $this->createQueryBuilder('o')
            ->select('SUM(o.total)')
            ->andWhere('o.statut = :delivered')
            ->andWhere('o.createdAt >= :today')
            ->setParameter('delivered', Order::STATUS_DELIVERED)
            ->setParameter('today', $today)
            ->getQuery()
            ->getSingleScalarResult();

        // Commandes annulées aujourd'hui
        $cancelled = $this->createQueryBuilder('o')
            ->select('COUNT(o.id)')
            ->andWhere('o.statut = :cancelled')
            ->andWhere('o.createdAt >= :today')
            ->setParameter('cancelled', Order::STATUS_CANCELLED)
            ->setParameter('today', $today)
            ->getQuery()
            ->getSingleScalarResult();

        return [
            'pending' => (int) $pending,
            'completed' => (int) $completed,
            'revenue' => (float) ($revenue ?? 0),
            'cancelled' => (int) $cancelled,
            'topProducts' => [],
        ];
    }
}
