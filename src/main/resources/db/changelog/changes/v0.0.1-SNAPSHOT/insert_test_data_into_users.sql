--liquibase formatted sql

--changeset liquibase:11

-- password 12345
insert into users (user_id, email, first_name, second_name, created_at, password)
values
    (1, 'petr@mail.com', 'Petr', 'Petrov', NOW(), '$2y$12$IvfS8Eqyi2PYtrytChzQguWufOvEcBSuNqKD.NV7pfBoeiqN0bmy6'),
    (2, 'anna@mail.com', 'Anna', 'Bolszova', NOW(), '$2y$12$IvfS8Eqyi2PYtrytChzQguWufOvEcBSuNqKD.NV7pfBoeiqN0bmy6'),
    (3, 'jurij@mail.com', 'Yurij', 'Semenov', NOW(), '$2y$12$IvfS8Eqyi2PYtrytChzQguWufOvEcBSuNqKD.NV7pfBoeiqN0bmy6')