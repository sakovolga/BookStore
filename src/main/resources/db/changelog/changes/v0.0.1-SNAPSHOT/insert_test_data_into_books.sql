--liquibase formatted sql

--changeset liquibase:10

insert into books (book_id, author, title, year_of_publication, publishing_house, price, reminder, category)
values
    (1, 'Joahn Roaling', 'Harry Potter', 1999, 'British Print', 45.55, 25, 'ADVENTURE')
