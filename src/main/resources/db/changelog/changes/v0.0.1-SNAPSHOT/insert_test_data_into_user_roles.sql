--liquibase formatted sql

--changeset liquibase:13

insert into user_roles (user_id, role_name)
values
    (1, 'CUSTOMER'),
    (2, 'CUSTOMER'),
    (2, 'MANAGER'),
    (3, 'CUSTOMER'),
    (3, 'MANAGER'),
    (3, 'ADMIN')
