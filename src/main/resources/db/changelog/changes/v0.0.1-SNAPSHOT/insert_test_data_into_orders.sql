--liquibase formatted sql

--changeset liquibase:5

insert into orders (order_id, status, created_at, completed_at, user_id)
values
    (1, 'COMPLETED', '2024-09-20 13:00:00', '2024-09-20 14:00:00', 1),
    (2, 'COMPLETED', '2024-09-20 13:00:00', '2024-09-20 14:00:00', 2);

insert into orders (order_id, status, created_at, user_id)
values
    (3, 'CREATED', '2024-09-10 13:00:00', 1),
    (4, 'CREATED', '2024-09-10 15:00:00', 2)

