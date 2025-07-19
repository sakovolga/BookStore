INSERT INTO books (author, title, year_of_publication, publishing_house, price, reminder, category)
VALUES
    ('Joahn Roaling', 'Harry Potter', 1999, 'British Print', 45.55, 25, 'ADVENTURE'),
    ('Agatha Christie', 'Murder on the Orient Express', 1934, 'Collins Crime Club', 30.00, 15, 'MYSTERY'),
    ('Jules Verne', 'Journey to the Center of the Earth', 1864, 'Pierre-Jules Hetzel', 20.99, 10, 'ADVENTURE'),
    ('Jane Austen', 'Pride and Prejudice', 1813, 'T. Egerton, Whitehall', 25.50, 20, 'ROMANCE');

-- password 12345
INSERT INTO users (email, first_name, second_name, created_at, password)
VALUES
    ('petr@mail.com', 'Petr', 'Petrov', NOW(), '$2y$12$IvfS8Eqyi2PYtrytChzQguWufOvEcBSuNqKD.NV7pfBoeiqN0bmy6'),
    ('anna@mail.com', 'Anna', 'Bolszova', NOW(), '$2y$12$IvfS8Eqyi2PYtrytChzQguWufOvEcBSuNqKD.NV7pfBoeiqN0bmy6'),
    ('jurij@mail.com', 'Yurij', 'Semenov', NOW(), '$2y$12$IvfS8Eqyi2PYtrytChzQguWufOvEcBSuNqKD.NV7pfBoeiqN0bmy6');

INSERT INTO user_roles (user_id, role_name)
VALUES
    (1, 'CUSTOMER'),
    (2, 'CUSTOMER'),
    (2, 'MANAGER'),
    (3, 'CUSTOMER'),
    (3, 'MANAGER'),
    (3, 'ADMIN');

INSERT INTO cart_items (quantity, created_at, book_id, user_id)
VALUES
    (2, '2024-09-20 12:00:00', 1, 1),
    (1, '2024-09-20 12:15:00', 1, 2);

INSERT INTO orders (status, created_at, completed_at, user_id)
VALUES
    ('COMPLETED', '2024-09-20 13:00:00', '2024-09-20 14:00:00', 1),
    ('COMPLETED', '2024-09-20 13:00:00', '2024-09-20 14:00:00', 2);

--INSERT INTO orders (status, created_at, user_id)
--VALUES
--    ('CREATED', '2024-09-10 13:00:00', 1),
--    ('CREATED', '2024-09-10 15:00:00', 2);

INSERT INTO order_details (quantity, book_id, order_id)
VALUES
    (1, 1, 1),
    (1, 1, 2);
--    (1, 1, 3),
--    (1, 1, 4);

INSERT INTO reviews (created_at, rating, user_id, book_id)
VALUES
    ('2024-09-20 13:30:00', '5', 1, 1),
    ('2024-09-20 14:00:00', '4', 2, 1);

