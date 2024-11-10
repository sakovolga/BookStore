insert into books (book_id, author, title, year_of_publication, publishing_house, price, reminder, category)
values
    (1, 'Joahn Roaling', 'Harry Potter', 1999, 'British Print', 45.55, 25, 'ADVENTURE');

-- password 12345
insert into users (user_id, email, first_name, second_name, created_at, password)
values
    (1, 'petr@mail.com', 'Petr', 'Petrov', NOW(), '$2y$12$IvfS8Eqyi2PYtrytChzQguWufOvEcBSuNqKD.NV7pfBoeiqN0bmy6'),
    (2, 'anna@mail.com', 'Anna', 'Bolszova', NOW(), '$2y$12$IvfS8Eqyi2PYtrytChzQguWufOvEcBSuNqKD.NV7pfBoeiqN0bmy6'),
    (3, 'jurij@mail.com', 'Yurij', 'Semenov', NOW(), '$2y$12$IvfS8Eqyi2PYtrytChzQguWufOvEcBSuNqKD.NV7pfBoeiqN0bmy6');

insert into user_roles (user_id, role_name)
values
    (1, 'CUSTOMER'),
    (2, 'CUSTOMER'),
    (2, 'MANAGER'),
    (3, 'CUSTOMER'),
    (3, 'MANAGER'),
    (3, 'ADMIN');

insert into cart_items (cart_item_id, quantity, created_at, book_id, user_id)
values
    (1, 2, '2024-09-20 12:00:00', 1, 1),
    (2, 1, '2024-09-20 12:15:00', 1, 2);

insert into orders (order_id, status, created_at, completed_at, user_id)
values
    (1, 'COMPLETED', '2024-09-20 13:00:00', '2024-09-20 14:00:00', 1),
    (2, 'COMPLETED', '2024-09-20 13:00:00', '2024-09-20 14:00:00', 2);

insert into orders (order_id, status, created_at, user_id)
values
    (3, 'CREATED', '2024-09-10 13:00:00', 1),
    (4, 'CREATED', '2024-09-10 15:00:00', 2);

insert into order_details (order_detail_id, quantity, book_id, order_id)
values
    (1, 1, 1, 1),
    (2, 1, 1, 2),
    (3, 1, 1, 3),
    (4, 1, 1, 4);

