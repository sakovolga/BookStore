-- Вставка данных в таблицу books (без указания book_id, так как оно автоинкрементируется)
INSERT INTO books (author, title, year_of_publication, publishing_house, price, reminder, category)
VALUES
    ('Joahn Roaling', 'Harry Potter', 1999, 'British Print', 45.55, 25, 'ADVENTURE');

-- Вставка данных в таблицу users (без указания user_id)
-- password 12345
INSERT INTO users (email, first_name, second_name, created_at, password)
VALUES
    ('petr@mail.com', 'Petr', 'Petrov', NOW(), '$2y$12$IvfS8Eqyi2PYtrytChzQguWufOvEcBSuNqKD.NV7pfBoeiqN0bmy6'),
    ('anna@mail.com', 'Anna', 'Bolszova', NOW(), '$2y$12$IvfS8Eqyi2PYtrytChzQguWufOvEcBSuNqKD.NV7pfBoeiqN0bmy6'),
    ('jurij@mail.com', 'Yurij', 'Semenov', NOW(), '$2y$12$IvfS8Eqyi2PYtrytChzQguWufOvEcBSuNqKD.NV7pfBoeiqN0bmy6');

-- Вставка данных в таблицу user_roles
INSERT INTO user_roles (user_id, role_name)
VALUES
    (1, 'CUSTOMER'),
    (2, 'CUSTOMER'),
    (2, 'MANAGER'),
    (3, 'CUSTOMER'),
    (3, 'MANAGER'),
    (3, 'ADMIN');

-- Вставка данных в таблицу cart_items (без указания cart_item_id)
INSERT INTO cart_items (quantity, created_at, book_id, user_id)
VALUES
    (2, '2024-09-20 12:00:00', 1, 1),
    (1, '2024-09-20 12:15:00', 1, 2);

-- Вставка данных в таблицу orders (без указания order_id)
INSERT INTO orders (status, created_at, completed_at, user_id)
VALUES
    ('COMPLETED', '2024-09-20 13:00:00', '2024-09-20 14:00:00', 1),
    ('COMPLETED', '2024-09-20 13:00:00', '2024-09-20 14:00:00', 2);

INSERT INTO orders (status, created_at, user_id)
VALUES
    ('CREATED', '2024-09-10 13:00:00', 1),
    ('CREATED', '2024-09-10 15:00:00', 2);

-- Вставка данных в таблицу order_details (без указания order_detail_id)
INSERT INTO order_details (quantity, book_id, order_id)
VALUES
    (1, 1, 1),
    (1, 1, 2),
    (1, 1, 3),
    (1, 1, 4);

-- Вставка данных в таблицу reviews (без указания review_id)
INSERT INTO reviews (created_at, rating, user_id, book_id)
VALUES
    ('2024-09-20 13:30:00', '5', 1, 1),
    ('2024-09-20 14:00:00', '4', 2, 1);

