-- Update Coca drink with real product image
UPDATE complements SET image_url = 'https://i0.wp.com/commechezmams.fr/wp-content/uploads/2021/01/COCA-33cl.jpg?fit=800%2C800&ssl=1' WHERE LOWER(nom) LIKE '%coca%';

-- Update sauce with generic Unsplash image
UPDATE complements SET image_url = 'https://images.unsplash.com/photo-1596040994633-923ee6c90785?w=400&h=300&fit=crop' WHERE LOWER(nom) LIKE '%sauce%';
