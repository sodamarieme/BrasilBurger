<?php

namespace App\Repository;

use App\Entity\Complement;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Complement>
 */
class ComplementRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Complement::class);
    }

    public function findAvailable(): array
    {
        return $this->createQueryBuilder('c')
            ->andWhere('c.disponible = :disponible')
            ->andWhere('c.archived = :archived')
            ->setParameter('disponible', true)
            ->setParameter('archived', false)
            ->orderBy('c.type', 'ASC')
            ->addOrderBy('c.nom', 'ASC')
            ->getQuery()
            ->getResult();
    }

    public function findByType(string $type): array
    {
        return $this->createQueryBuilder('c')
            ->andWhere('c.type = :type')
            ->andWhere('c.disponible = :disponible')
            ->andWhere('c.archived = :archived')
            ->setParameter('type', $type)
            ->setParameter('disponible', true)
            ->setParameter('archived', false)
            ->orderBy('c.nom', 'ASC')
            ->getQuery()
            ->getResult();
    }
}
