<?php

namespace App\Repository;

use App\Entity\Delivery;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Delivery>
 */
class DeliveryRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Delivery::class);
    }

    public function findByZone(int $zoneId): array
    {
        return $this->createQueryBuilder('d')
            ->andWhere('d.zone = :zoneId')
            ->setParameter('zoneId', $zoneId)
            ->orderBy('d.createdAt', 'DESC')
            ->getQuery()
            ->getResult();
    }

    public function findByLivreur(int $livreurId): array
    {
        return $this->createQueryBuilder('d')
            ->andWhere('d.livreur = :livreurId')
            ->setParameter('livreurId', $livreurId)
            ->orderBy('d.createdAt', 'DESC')
            ->getQuery()
            ->getResult();
    }

    public function findPending(): array
    {
        return $this->createQueryBuilder('d')
            ->andWhere('d.statut = :statut')
            ->setParameter('statut', Delivery::STATUS_PENDING)
            ->orderBy('d.createdAt', 'ASC')
            ->getQuery()
            ->getResult();
    }

    public function findPendingByZone(int $zoneId): array
    {
        return $this->createQueryBuilder('d')
            ->andWhere('d.zone = :zoneId')
            ->andWhere('d.statut = :statut')
            ->setParameter('zoneId', $zoneId)
            ->setParameter('statut', Delivery::STATUS_PENDING)
            ->orderBy('d.createdAt', 'ASC')
            ->getQuery()
            ->getResult();
    }
}
