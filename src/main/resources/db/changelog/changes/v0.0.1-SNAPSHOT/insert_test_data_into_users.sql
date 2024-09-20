--liquibase formatted sql

--changeset liquibase:11

insert into users (user_id, email, first_name, second_name, created_at)
values
    (1, 'petr@mail.com', 'Petr', 'Petrov', NOW()),
    (2, 'anna@mail.com', 'Anna', 'Bolszova', NOW()),
    (3, 'jurij@mail.com', 'Yurij', 'Semenov', NOW())