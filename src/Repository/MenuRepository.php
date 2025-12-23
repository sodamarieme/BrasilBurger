<?php

namespace App\Repository;

use App\Entity\Menu;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Menu>
 */
class MenuRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Menu::class);
    }

    public function findAvailable(): array
    {
        return $this->createQueryBuilder('m')
            ->andWhere('m.disponible = :disponible')
            ->andWhere('m.archived = :archived')
            ->setParameter('disponible', true)
            ->setParameter('archived', false)
            ->orderBy('m.nom', 'ASC')
            ->getQuery()
            ->getResult();
    }

    public function findWithDetails(): array
    {
        return $this->createQueryBuilder('m')
            ->leftJoin('m.burgers', 'b')
            ->leftJoin('m.complements', 'c')
            ->addSelect('b', 'c')
            ->andWhere('m.disponible = :disponible')
            ->andWhere('m.archived = :archived')
            ->setParameter('disponible', true)
            ->setParameter('archived', false)
            ->orderBy('m.nom', 'ASC')
            ->getQuery()
            ->getResult();
    }
}
