-- Mettre à jour les images des menus
UPDATE menus SET image_url = 'https://res.cloudinary.com/dnw5idv6v/image/upload/v1/menu_classique.jpg' WHERE id = 1;
UPDATE menus SET image_url = 'https://res.cloudinary.com/dnw5idv6v/image/upload/v1/menu_special.jpg' WHERE id = 2;
UPDATE menus SET image_url = 'https://res.cloudinary.com/dnw5idv6v/image/upload/v1/menu_duo.jpg' WHERE id = 3;

-- Mettre à jour les images des burgers
UPDATE burgers SET image_url = 'https://res.cloudinary.com/dnw5idv6v/image/upload/v1/classic_burger.jpg' WHERE id = 1;
UPDATE burgers SET image_url = 'https://res.cloudinary.com/dnw5idv6v/image/upload/v1/spicy_burger.jpg' WHERE id = 2;
UPDATE burgers SET image_url = 'https://res.cloudinary.com/dnw5idv6v/image/upload/v1/double_burger.jpg' WHERE id = 3;
UPDATE burgers SET image_url = 'https://res.cloudinary.com/dnw5idv6v/image/upload/v1/chicken_burger.jpg' WHERE id = 4;
UPDATE burgers SET image_url = 'https://res.cloudinary.com/dnw5idv6v/image/upload/v1/cheese_burger.jpg' WHERE id = 5;

-- Mettre à jour les images des compléments
UPDATE complements SET image_url = 'https://res.cloudinary.com/dnw5idv6v/image/upload/v1/frites.jpg' WHERE id = 1;
UPDATE complements SET image_url = 'https://res.cloudinary.com/dnw5idv6v/image/upload/v1/salade.jpg' WHERE id = 2;
UPDATE complements SET image_url = 'https://res.cloudinary.com/dnw5idv6v/image/upload/v1/boisson_gazeuse.jpg' WHERE id = 3;
UPDATE complements SET image_url = 'https://res.cloudinary.com/dnw5idv6v/image/upload/v1/jus_frais.jpg' WHERE id = 4;
UPDATE complements SET image_url = 'https://res.cloudinary.com/dnw5idv6v/image/upload/v1/sauce_bbq.jpg' WHERE id = 5;
