CREATE DATABASE `midtermg1`;
use `midtermg1`;

CREATE TABLE customer (
    id VARCHAR(36) NOT NULL,
    created_time TIMESTAMP(6) NOT NULL,
    name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    status VARCHAR(10) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE')),
    updated_time TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE product (
    id INT NOT NULL AUTO_INCREMENT,
    created_time TIMESTAMP(6) NOT NULL,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(38,2) NOT NULL,
    status VARCHAR(10) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE')),
    updated_time TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE invoice (
    id VARCHAR(36) NOT NULL,
    created_time TIMESTAMP(6) NOT NULL,
    invoice_amount DECIMAL(38,2) NOT NULL,
    invoice_date TIMESTAMP(6) NOT NULL,
    updated_time TIMESTAMP(6) NOT NULL,
    customer_id VARCHAR(36) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE invoice_product (
    invoice_id VARCHAR(36) NOT NULL,
    product_id INT NOT NULL,
    amount DECIMAL(38,2) NOT NULL,
    created_time TIMESTAMP(6) NOT NULL,
    price DECIMAL(38,2) NOT NULL,
    quantity INT NOT NULL,
    updated_time TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (invoice_id, product_id),
    FOREIGN KEY (invoice_id) REFERENCES invoice(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);