<?php

namespace App\Command;

use App\Entity\Burger;
use App\Entity\Complement;
use App\Entity\Menu;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Console\Attribute\AsCommand;
use Symfony\Component\Console\Command\Command;
use Symfony\Component\Console\Input\InputInterface;
use Symfony\Component\Console\Output\OutputInterface;
use Symfony\Component\Console\Style\SymfonyStyle;

#[AsCommand(
    name: 'app:update-images',
    description: 'Met à jour les images des produits',
)]
class UpdateImagesCommand extends Command
{
    public function __construct(private EntityManagerInterface $em)
    {
        parent::__construct();
    }

    protected function execute(InputInterface $input, OutputInterface $output): int
    {
        $io = new SymfonyStyle($input, $output);
        
        $io->title('Mise à jour des images produits');

        // Images des burgers
        $burgerImages = [
            'Classic Burger' => 'images/products/burger-classic.jpg',
            'Cheese Burger' => 'images/products/burger-cheese.jpg',
            'Bacon Burger' => 'images/products/burger-bacon.jpg',
            'Double Meat' => 'images/products/burger-double.jpg',
            'BBQ Burger' => 'images/products/burger-bbq.jpg',
        ];

        $io->section('Burgers');
        foreach ($burgerImages as $nom => $image) {
            $burger = $this->em->getRepository(Burger::class)->findOneBy(['nom' => $nom]);
            if ($burger) {
                $burger->setImage($image);
                $io->writeln("  ✅ {$nom} → {$image}");
            }
        }

        // Images des compléments
        $complementImages = [
            'Frites' => 'images/products/frites.jpg',
            'Nuggets (6pcs)' => 'images/products/nuggets.jpg',
            'Onion Rings' => 'images/products/onion-rings.jpg',
            'Boisson (33cl)' => 'images/products/boisson.jpg',
        ];

        $io->section('Compléments');
        foreach ($complementImages as $nom => $image) {
            $complement = $this->em->getRepository(Complement::class)->findOneBy(['nom' => $nom]);
            if ($complement) {
                $complement->setImage($image);
                $io->writeln("  ✅ {$nom} → {$image}");
            }
        }

        // Images des menus
        $menuImages = [
            'Menu Classic' => 'images/products/menu-classic.jpg',
            'Menu Cheese' => 'images/products/menu-cheese.jpg',
            'Menu Double' => 'images/products/menu-double.jpg',
        ];

        $io->section('Menus');
        foreach ($menuImages as $nom => $image) {
            $menu = $this->em->getRepository(Menu::class)->findOneBy(['nom' => $nom]);
            if ($menu) {
                $menu->setImage($image);
                $io->writeln("  ✅ {$nom} → {$image}");
            }
        }

        $this->em->flush();

        $io->success('Images mises à jour avec succès!');

        return Command::SUCCESS;
    }
}
