CREATE TABLE books
(
    book_id   BINARY(16) PRIMARY KEY, //책 아이디
    book_name VARCHAR(20)  NOT NULL, //책 이름
    category     VARCHAR(50)  NOT NULL, //카테고리
    price        bigint       NOT NULL, //가격
    description  VARCHAR(500) NOT NULL, //설명
    created_at   datetime(6)  NOT NULL,
    );

CREATE TABLE orders
(
    order_id     BINARY(16) PRIMARY KEY,
    name         VARCHAR(20) NOT NULL,
    email        VARCHAR(50)  NOT NULL,
    address      VARCHAR(200) NOT NULL,
    postcode     VARCHAR(200) NOT NULL,
    order_status VARCHAR(50)  NOT NULL,
    created_at   datetime(6)  NOT NULL,
);

CREATE TABLE order_items
(
    seq        bigint      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id   BINARY(16)  NOT NULL,
    book_id    BINARY(16)  NOT NULL,
    category   VARCHAR(50) NOT NULL,
    price      bigint      NOT NULL,
    quantity   int         NOT NULL,
    created_at datetime(6) NOT NULL,
    INDEX (order_id),
    CONSTRAINT fk_order_items_to_order FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE,
    CONSTRAINT fk_order_items_to_book FOREIGN KEY (book_id) REFERENCES books (book_id)
)
