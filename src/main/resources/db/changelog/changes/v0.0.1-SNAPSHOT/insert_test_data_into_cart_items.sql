--liquibase formatted sql

--changeset liquibase:2

insert into cart_items (cart_item_id, quantity, created_at, book_id, user_id)
values
    (1, 2, '2024-09-20 12:00:00', 1, 1),
    (2, 1, '2024-09-20 12:15:00', 1, 2);











