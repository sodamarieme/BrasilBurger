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
        $qb = $this->createQueryBuilder('o');

        $totalOrders = $qb->select('COUNT(o.id)')
            ->getQuery()
            ->getSingleScalarResult();

        $totalRevenue = $this->createQueryBuilder('o')
            ->select('SUM(o.total)')
            ->andWhere('o.statut = :delivered')
            ->setParameter('delivered', Order::STATUS_DELIVERED)
            ->getQuery()
            ->getSingleScalarResult();

        $pendingOrders = $this->createQueryBuilder('o')
            ->select('COUNT(o.id)')
            ->andWhere('o.statut = :pending')
            ->setParameter('pending', Order::STATUS_PENDING)
            ->getQuery()
            ->getSingleScalarResult();

        $todayOrders = $this->createQueryBuilder('o')
            ->select('COUNT(o.id)')
            ->andWhere('o.createdAt >= :today')
            ->setParameter('today', new \DateTimeImmutable('today'))
            ->getQuery()
            ->getSingleScalarResult();

        return [
            'totalOrders' => $totalOrders,
            'totalRevenue' => $totalRevenue ?? 0,
            'pendingOrders' => $pendingOrders,
            'todayOrders' => $todayOrders,
        ];
    }
}
