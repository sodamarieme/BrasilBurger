<?php

namespace App\Command;

use App\Entity\User;
use App\Entity\Category;
use App\Entity\Burger;
use App\Entity\Complement;
use App\Entity\Menu;
use App\Entity\Zone;
use App\Entity\Livreur;
use App\Entity\Order;
use App\Entity\OrderItem;
use App\Entity\Delivery;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Console\Attribute\AsCommand;
use Symfony\Component\Console\Command\Command;
use Symfony\Component\Console\Input\InputInterface;
use Symfony\Component\Console\Output\OutputInterface;
use Symfony\Component\Console\Style\SymfonyStyle;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;

#[AsCommand(
    name: 'app:seed-data',
    description: 'Crée les données de test pour Brasil Burger',
)]
class SeedDataCommand extends Command
{
    public function __construct(
        private EntityManagerInterface $em,
        private UserPasswordHasherInterface $passwordHasher
    ) {
        parent::__construct();
    }

    protected function execute(InputInterface $input, OutputInterface $output): int
    {
        $io = new SymfonyStyle($input, $output);
        
        $io->title('Création des données de test Brasil Burger');

        // 1. Admin
        $io->section('Création des utilisateurs');
        $existingAdmin = $this->em->getRepository(User::class)->findOneBy(['email' => 'admin@brasilburger.com']);
        if (!$existingAdmin) {
            $admin = new User();
            $admin->setEmail('admin@brasilburger.com');
            $admin->setNom('Admin');
            $admin->setPrenom('Super');
            $admin->setRoles(['ROLE_ADMIN']);
            $admin->setPassword($this->passwordHasher->hashPassword($admin, 'admin123'));
            $admin->setTelephone('77 000 00 00');
            $admin->setAdresse('Dakar, Sénégal');
            $this->em->persist($admin);
            $io->success('Admin créé: admin@brasilburger.com / admin123');
        } else {
            $io->warning('Admin existe déjà');
        }

        // Client
        $existingClient = $this->em->getRepository(User::class)->findOneBy(['email' => 'client@test.com']);
        if (!$existingClient) {
            $client = new User();
            $client->setEmail('client@test.com');
            $client->setNom('Diallo');
            $client->setPrenom('Mamadou');
            $client->setRoles(['ROLE_USER']);
            $client->setPassword($this->passwordHasher->hashPassword($client, 'client123'));
            $client->setTelephone('77 111 11 11');
            $client->setAdresse('Almadies, Dakar');
            $this->em->persist($client);
            $io->success('Client créé: client@test.com / client123');
        }

        // 2. Catégories
        $io->section('Création des catégories');
        $categories = [
            ['nom' => 'Burgers Classiques', 'description' => 'Nos burgers traditionnels'],
            ['nom' => 'Burgers Premium', 'description' => 'Burgers avec ingrédients premium'],
            ['nom' => 'Burgers Spéciaux', 'description' => 'Créations exclusives du chef'],
        ];

        $categoryEntities = [];
        foreach ($categories as $cat) {
            $existing = $this->em->getRepository(Category::class)->findOneBy(['nom' => $cat['nom']]);
            if (!$existing) {
                $category = new Category();
                $category->setNom($cat['nom']);
                $category->setDescription($cat['description']);
                $this->em->persist($category);
                $categoryEntities[] = $category;
                $io->writeln("  ✅ {$cat['nom']}");
            } else {
                $categoryEntities[] = $existing;
            }
        }

        // 3. Burgers
        $io->section('Création des burgers');
        $burgers = [
            ['nom' => 'Classic Burger', 'description' => 'Le classique: steak de boeuf, salade, tomate, oignon, sauce maison', 'prix' => 2500, 'category' => 0, 'image' => 'images/products/burger-classic.jpg'],
            ['nom' => 'Cheese Burger', 'description' => 'Burger avec double cheddar fondu', 'prix' => 3000, 'category' => 0, 'image' => 'images/products/burger-cheese.jpg'],
            ['nom' => 'Bacon Burger', 'description' => 'Burger avec bacon croustillant', 'prix' => 3500, 'category' => 1, 'image' => 'images/products/burger-bacon.jpg'],
            ['nom' => 'Double Meat', 'description' => 'Double steak pour les gros appétits', 'prix' => 4500, 'category' => 1, 'image' => 'images/products/burger-double.jpg'],
            ['nom' => 'BBQ Burger', 'description' => 'Sauce BBQ, oignons caramélisés, bacon', 'prix' => 4000, 'category' => 2, 'image' => 'images/products/burger-bbq.jpg'],
        ];

        $burgerEntities = [];
        foreach ($burgers as $b) {
            $existing = $this->em->getRepository(Burger::class)->findOneBy(['nom' => $b['nom']]);
            if (!$existing) {
                $burger = new Burger();
                $burger->setNom($b['nom']);
                $burger->setDescription($b['description']);
                $burger->setPrix($b['prix']);
                $burger->setImage($b['image']);
                $burger->setDisponible(true);
                $burger->setArchived(false);
                if (isset($categoryEntities[$b['category']])) {
                    $burger->setCategory($categoryEntities[$b['category']]);
                }
                $this->em->persist($burger);
                $burgerEntities[] = $burger;
                $io->writeln("  ✅ {$b['nom']} - {$b['prix']} FCFA");
            } else {
                $burgerEntities[] = $existing;
            }
        }

        // 4. Compléments
        $io->section('Création des compléments');
        $complements = [
            ['nom' => 'Frites', 'description' => 'Frites croustillantes', 'prix' => 1000, 'type' => 'frites', 'image' => 'images/products/frites.jpg'],
            ['nom' => 'Nuggets (6pcs)', 'description' => 'Nuggets de poulet', 'prix' => 1500, 'type' => 'snack', 'image' => 'images/products/nuggets.jpg'],
            ['nom' => 'Onion Rings', 'description' => 'Oignons frits', 'prix' => 1200, 'type' => 'snack', 'image' => 'images/products/onion-rings.jpg'],
            ['nom' => 'Coleslaw', 'description' => 'Salade de chou', 'prix' => 800, 'type' => 'salade', 'image' => null],
            ['nom' => 'Boisson (33cl)', 'description' => 'Coca, Fanta, Sprite', 'prix' => 500, 'type' => 'boisson', 'image' => 'images/products/boisson.jpg'],
        ];

        $complementEntities = [];
        foreach ($complements as $c) {
            $existing = $this->em->getRepository(Complement::class)->findOneBy(['nom' => $c['nom']]);
            if (!$existing) {
                $complement = new Complement();
                $complement->setNom($c['nom']);
                $complement->setDescription($c['description']);
                $complement->setPrix($c['prix']);
                $complement->setType($c['type']);
                $complement->setImage($c['image']);
                $complement->setDisponible(true);
                $complement->setArchived(false);
                $this->em->persist($complement);
                $complementEntities[] = $complement;
                $io->writeln("  ✅ {$c['nom']} - {$c['prix']} FCFA");
            } else {
                $complementEntities[] = $existing;
            }
        }

        // 5. Menus
        $io->section('Création des menus');
        $menus = [
            ['nom' => 'Menu Classic', 'description' => 'Classic Burger + Frites + Boisson', 'prix' => 4000, 'image' => 'images/products/menu-classic.jpg'],
            ['nom' => 'Menu Cheese', 'description' => 'Cheese Burger + Frites + Boisson', 'prix' => 4500, 'image' => 'images/products/menu-cheese.jpg'],
            ['nom' => 'Menu Double', 'description' => 'Double Meat + Frites + Nuggets + Boisson', 'prix' => 7000, 'image' => 'images/products/menu-double.jpg'],
        ];

        foreach ($menus as $m) {
            $existing = $this->em->getRepository(Menu::class)->findOneBy(['nom' => $m['nom']]);
            if (!$existing) {
                $menu = new Menu();
                $menu->setNom($m['nom']);
                $menu->setDescription($m['description']);
                $menu->setPrix($m['prix']);
                $menu->setImage($m['image']);
                $menu->setDisponible(true);
                $menu->setArchived(false);
                
                if (count($burgerEntities) > 0) {
                    $menu->addBurger($burgerEntities[0]);
                }
                if (count($complementEntities) > 1) {
                    $menu->addComplement($complementEntities[0]);
                    $menu->addComplement($complementEntities[4]);
                }
                
                $this->em->persist($menu);
                $io->writeln("  ✅ {$m['nom']} - {$m['prix']} FCFA");
            }
        }

        // 6. Zones
        $io->section('Création des zones de livraison');
        $zones = [
            ['nom' => 'Plateau', 'frais' => 500],
            ['nom' => 'Almadies', 'frais' => 1000],
            ['nom' => 'Ouakam', 'frais' => 800],
            ['nom' => 'Mermoz', 'frais' => 700],
            ['nom' => 'Sacré-Coeur', 'frais' => 600],
        ];

        $zoneEntities = [];
        foreach ($zones as $z) {
            $existing = $this->em->getRepository(Zone::class)->findOneBy(['nom' => $z['nom']]);
            if (!$existing) {
                $zone = new Zone();
                $zone->setNom($z['nom']);
                $zone->setPrixLivraison($z['frais']);
                $this->em->persist($zone);
                $zoneEntities[] = $zone;
                $io->writeln("  ✅ {$z['nom']} - {$z['frais']} FCFA");
            } else {
                $zoneEntities[] = $existing;
            }
        }

        // 7. Livreurs
        $io->section('Création des livreurs');
        $livreurs = [
            ['nom' => 'Fall', 'prenom' => 'Ibrahima', 'telephone' => '77 222 22 22'],
            ['nom' => 'Ndiaye', 'prenom' => 'Ousmane', 'telephone' => '77 333 33 33'],
            ['nom' => 'Sow', 'prenom' => 'Amadou', 'telephone' => '77 444 44 44'],
        ];

        foreach ($livreurs as $l) {
            $existing = $this->em->getRepository(Livreur::class)->findOneBy(['telephone' => $l['telephone']]);
            if (!$existing) {
                $livreur = new Livreur();
                $livreur->setNom($l['nom']);
                $livreur->setPrenom($l['prenom']);
                $livreur->setTelephone($l['telephone']);
                $livreur->setDisponible(true);
                if (count($zoneEntities) > 0) {
                    $livreur->addZone($zoneEntities[0]);
                    if (isset($zoneEntities[1])) {
                        $livreur->addZone($zoneEntities[1]);
                    }
                }
                $this->em->persist($livreur);
                $io->writeln("  ✅ {$l['prenom']} {$l['nom']}");
            }
        }

        $this->em->flush();

        // 8. Commandes de test
        $io->section('Création des commandes de test');
        
        $client = $this->em->getRepository(User::class)->findOneBy(['email' => 'client@test.com']);
        $zones = $this->em->getRepository(Zone::class)->findAll();
        $livreurs = $this->em->getRepository(Livreur::class)->findAll();
        $burgers = $this->em->getRepository(Burger::class)->findAll();
        
        // Vérifier si des commandes existent déjà
        $existingOrders = $this->em->getRepository(Order::class)->findAll();
        
        if (count($existingOrders) === 0 && $client && count($burgers) > 0) {
            $ordersData = [
                ['ref' => 'CMD-001', 'type' => 'livraison', 'statut' => Order::STATUS_PENDING, 'total' => 4500, 'adresse' => 'Almadies, Villa 12, Dakar'],
                ['ref' => 'CMD-002', 'type' => 'sur_place', 'statut' => Order::STATUS_CONFIRMED, 'total' => 7500, 'adresse' => 'Sur place - Table 5'],
                ['ref' => 'CMD-003', 'type' => 'livraison', 'statut' => Order::STATUS_PREPARING, 'total' => 6000, 'adresse' => 'Sacré-Cœur 3, Immeuble Serigne Fallou'],
                ['ref' => 'CMD-004', 'type' => 'a_emporter', 'statut' => Order::STATUS_READY, 'total' => 3500, 'adresse' => 'À emporter'],
                ['ref' => 'CMD-005', 'type' => 'livraison', 'statut' => Order::STATUS_READY, 'total' => 9500, 'adresse' => 'Mermoz, Pyrotechnie'],
                ['ref' => 'CMD-006', 'type' => 'livraison', 'statut' => Order::STATUS_DELIVERING, 'total' => 5500, 'adresse' => 'Ouakam, Cité Police'],
                ['ref' => 'CMD-007', 'type' => 'sur_place', 'statut' => Order::STATUS_DELIVERED, 'total' => 4000, 'adresse' => 'Sur place - Table 2'],
                ['ref' => 'CMD-008', 'type' => 'livraison', 'statut' => Order::STATUS_DELIVERED, 'total' => 8000, 'adresse' => 'Plateau, Avenue Pompidou'],
            ];

            $orderIndex = 0;
            foreach ($ordersData as $od) {
                $order = new Order();
                $order->setReference($od['ref']);
                $order->setType($od['type']);
                $order->setStatut($od['statut']);
                $order->setTotalProduits((string)($od['total'] - 500));
                $order->setFraisLivraison('500');
                $order->setTotal((string)$od['total']);
                $order->setAdresseLivraison($od['adresse']);
                $order->setClient($client);
                $order->setCreatedAt(new \DateTimeImmutable('-' . (7 - $orderIndex) . ' days'));
                
                // Ajouter un article à chaque commande
                if (isset($burgers[$orderIndex % count($burgers)])) {
                    $burger = $burgers[$orderIndex % count($burgers)];
                    $orderItem = new OrderItem();
                    $orderItem->setCommande($order);
                    $orderItem->setType('burger');
                    $orderItem->setProduitId($burger->getId());
                    $orderItem->setProduitNom($burger->getNom());
                    $orderItem->setQuantite(rand(1, 3));
                    $orderItem->setPrixUnitaire((string)$burger->getPrix());
                    $orderItem->setSousTotal((string)($burger->getPrix() * $orderItem->getQuantite()));
                    $this->em->persist($orderItem);
                }
                
                $this->em->persist($order);
                
                // Créer une livraison pour les commandes en livraison
                if ($od['type'] === 'livraison' && count($zones) > 0) {
                    $zone = $zones[$orderIndex % count($zones)];
                    $delivery = new Delivery();
                    $delivery->setCommande($order);
                    $delivery->setZone($zone);
                    $delivery->setPrixLivraison((string)$zone->getPrixLivraison());
                    
                    if ($od['statut'] === Order::STATUS_DELIVERING || $od['statut'] === Order::STATUS_DELIVERED) {
                        $delivery->setStatut(Delivery::STATUS_ASSIGNED);
                        if (count($livreurs) > 0) {
                            $delivery->setLivreur($livreurs[$orderIndex % count($livreurs)]);
                            $delivery->setAssignedAt(new \DateTimeImmutable());
                        }
                    }
                    if ($od['statut'] === Order::STATUS_DELIVERED) {
                        $delivery->setStatut(Delivery::STATUS_DELIVERED);
                        $delivery->setDeliveredAt(new \DateTimeImmutable());
                    }
                    
                    $this->em->persist($delivery);
                }
                
                $io->writeln("  ✅ {$od['ref']} - {$od['type']} - {$od['total']} FCFA");
                $orderIndex++;
            }
            
            $this->em->flush();
        } else if (count($existingOrders) > 0) {
            $io->warning('Des commandes existent déjà (' . count($existingOrders) . ' commandes)');
        }

        $io->newLine();
        $io->success('Données créées avec succès!');
        $io->table(['Type', 'Email', 'Mot de passe'], [
            ['Admin', 'admin@brasilburger.com', 'admin123'],
            ['Client', 'client@test.com', 'client123'],
        ]);

        return Command::SUCCESS;
    }
}
