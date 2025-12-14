-- Mettre à jour avec des images placeholder qui existent vraiment
UPDATE menus SET image_url = 'https://via.placeholder.com/400x300?text=Menu+Classique' WHERE id = 1;
UPDATE menus SET image_url = 'https://via.placeholder.com/400x300?text=Menu+Special' WHERE id = 2;
UPDATE menus SET image_url = 'https://via.placeholder.com/400x300?text=Menu+Duo' WHERE id = 3;

UPDATE burgers SET image_url = 'https://via.placeholder.com/300x300?text=Classic+Burger' WHERE id = 1;
UPDATE burgers SET image_url = 'https://via.placeholder.com/300x300?text=Spicy+Burger' WHERE id = 2;
UPDATE burgers SET image_url = 'https://via.placeholder.com/300x300?text=Double+Burger' WHERE id = 3;
UPDATE burgers SET image_url = 'https://via.placeholder.com/300x300?text=Chicken+Burger' WHERE id = 4;
UPDATE burgers SET image_url = 'https://via.placeholder.com/300x300?text=Cheese+Burger' WHERE id = 5;

UPDATE complements SET image_url = 'https://via.placeholder.com/200x200?text=Frites' WHERE id = 1;
UPDATE complements SET image_url = 'https://via.placeholder.com/200x200?text=Salade' WHERE id = 2;
UPDATE complements SET image_url = 'https://via.placeholder.com/200x200?text=Boisson' WHERE id = 3;
UPDATE complements SET image_url = 'https://via.placeholder.com/200x200?text=Jus' WHERE id = 4;
UPDATE complements SET image_url = 'https://via.placeholder.com/200x200?text=Sauce+BBQ' WHERE id = 5;
