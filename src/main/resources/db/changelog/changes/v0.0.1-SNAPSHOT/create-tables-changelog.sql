--liquibase formatted sql

--changeset liquibase:1

CREATE TABLE IF NOT EXISTS books (
    book_id BIGSERIAL unique NOT NULL,
    author VARCHAR(128) NOT NULL,
    title VARCHAR(128) NOT NULL,
    year_of_publication SMALLINT NOT NULL,
    publishing_house VARCHAR(128) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    reminder BIGINT,
    review_count INT,
    book_rating DECIMAL(10,2),
    category VARCHAR(128),
    PRIMARY KEY (book_id)
);

create TABLE IF NOT EXISTS users (
    user_id	BIGSERIAL unique NOT NULL,
    email varchar(58) NOT NULL,
    first_name	varchar(58) NOT NULL,
    second_name	varchar(58) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    password VARCHAR(128) NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_name VARCHAR(128) NOT NULL,
    PRIMARY KEY (user_id, role_name),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

create TABLE IF NOT EXISTS cart_items (
    cart_item_id BIGSERIAL unique NOT NULL,
    quantity	SMALLINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    book_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (cart_item_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

create TABLE IF NOT EXISTS orders (
    order_id	BIGSERIAL unique NOT NULL,
    status	VARCHAR(58) NOT NULL,
    created_at	timestamp NOT NULL,
    completed_at	timestamp,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (order_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

create TABLE IF NOT EXISTS order_details (
    order_detail_id	BIGSERIAL unique NOT NULL,
    quantity	SMALLINT NOT NULL,
    book_id	BIGINT NOT NULL,
    order_id	BIGINT NOT NULL,
    PRIMARY KEY (order_detail_id),
    FOREIGN KEY (book_id) REFERENCES books (book_id),
    FOREIGN KEY (order_id) REFERENCES orders (order_id)
);

create TABLE IF NOT EXISTS reviews (
    review_id	BIGSERIAL unique NOT NULL,
    created_at	timestamp NOT NULL,
    rating	VARCHAR(58),
    comment text,
    user_id	BIGINT NOT NULL,
    book_id	BIGINT NOT NULL,
    PRIMARY KEY (review_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (book_id) REFERENCES books (book_id)
)


