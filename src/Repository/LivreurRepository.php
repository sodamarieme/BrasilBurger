<?php

namespace App\Repository;

use App\Entity\Livreur;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Livreur>
 */
class LivreurRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Livreur::class);
    }

    public function findAvailable(): array
    {
        return $this->createQueryBuilder('l')
            ->andWhere('l.disponible = :disponible')
            ->andWhere('l.active = :active')
            ->setParameter('disponible', true)
            ->setParameter('active', true)
            ->orderBy('l.nom', 'ASC')
            ->getQuery()
            ->getResult();
    }

    public function findByZone(int $zoneId): array
    {
        return $this->createQueryBuilder('l')
            ->innerJoin('l.zones', 'z')
            ->andWhere('z.id = :zoneId')
            ->andWhere('l.disponible = :disponible')
            ->andWhere('l.active = :active')
            ->setParameter('zoneId', $zoneId)
            ->setParameter('disponible', true)
            ->setParameter('active', true)
            ->orderBy('l.nom', 'ASC')
            ->getQuery()
            ->getResult();
    }
}
