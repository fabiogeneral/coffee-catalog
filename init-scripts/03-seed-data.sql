-- Seed Flavor Notes
INSERT INTO flavor_notes (name, category) VALUES
-- Fruity
('Blueberry', 'Fruity'),
('Strawberry', 'Fruity'),
('Cherry', 'Fruity'),
('Lemon', 'Citrus'),
('Orange', 'Citrus'),
('Apple', 'Fruity'),
-- Chocolatey/Nutty
('Dark Chocolate', 'Chocolatey'),
('Milk Chocolate', 'Chocolatey'),
('Caramel', 'Sweet'),
('Almond', 'Nutty'),
('Hazelnut', 'Nutty'),
-- Floral
('Jasmine', 'Floral'),
('Rose', 'Floral'),
('Lavender', 'Floral'),
-- Spicy/Earthy
('Cinnamon', 'Spicy'),
('Earthy', 'Earthy'),
('Tobacco', 'Earthy');

-- Seed Test User
INSERT INTO users (email, password_hash, username, first_name, last_name, role)
VALUES
('admin@coffee.com', '$2a$10$dummyhash', 'admin', 'Admin', 'User', 'ADMIN'),
('roaster@coffee.com', '$2a$10$dummyhash', 'roaster1', 'Coffee', 'Roaster', 'ROASTER');

-- Seed Test Roaster
INSERT INTO roasters (user_id, company_name, description, location, is_verified)
VALUES
(2, 'Artisan Coffee Roasters', 'Premium small-batch coffee roasting', 'Portland, OR', true);

-- You can add more seed data here