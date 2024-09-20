--liquibase formatted sql

--changeset liquibase:4

insert into order_details (order_detail_id, quantity, book_id, order_id)
values
    (1, 1, 1, 1),
    (2, 1, 1, 2),
    (3, 1, 1, 3),
    (4, 1, 1, 4)