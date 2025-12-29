<?php

namespace App\Repository;

use App\Entity\Burger;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

class BurgerRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Burger::class);
    }

    public function findAvailable(): array
    {
        return $this->createQueryBuilder('b')
            ->andWhere('b.disponible = :disponible')
            ->andWhere('b.archived = :archived')
            ->setParameter('disponible', true)
            ->setParameter('archived', false)
            ->orderBy('b.nom', 'ASC')
            ->getQuery()
            ->getResult();
    }

    public function findByCategory(int $categoryId): array
    {
        return $this->createQueryBuilder('b')
            ->andWhere('b.category = :categoryId')
            ->andWhere('b.disponible = :disponible')
            ->andWhere('b.archived = :archived')
            ->setParameter('categoryId', $categoryId)
            ->setParameter('disponible', true)
            ->setParameter('archived', false)
            ->orderBy('b.nom', 'ASC')
            ->getQuery()
            ->getResult();
    }

    public function search(string $query): array
    {
        return $this->createQueryBuilder('b')
            ->andWhere('b.nom LIKE :query OR b.description LIKE :query')
            ->andWhere('b.disponible = :disponible')
            ->andWhere('b.archived = :archived')
            ->setParameter('query', '%' . $query . '%')
            ->setParameter('disponible', true)
            ->setParameter('archived', false)
            ->orderBy('b.nom', 'ASC')
            ->getQuery()
            ->getResult();
    }
}
