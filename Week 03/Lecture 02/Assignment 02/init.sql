-- Create table for customers
CREATE TABLE Customer (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(15)
);

-- Create table for cashiers
CREATE TABLE Cashier (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Create table for invoices
CREATE TABLE Invoice (
    id INT PRIMARY KEY,
    customer_id INT,
    cashier_id INT,
    amount DECIMAL(10, 2),
    created_date TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES Customer(id),
    FOREIGN KEY (cashier_id) REFERENCES Cashier(id)
);

-- Create table for products
CREATE TABLE Product (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2)
);

-- Create table for invoice details
CREATE TABLE InvoiceDetail (
    id INT PRIMARY KEY,
    quantity INT NOT NULL,
    product_id INT,
    product_price DECIMAL(10, 2),
    invoice_id INT,
    amount DECIMAL(10, 2),
    FOREIGN KEY (product_id) REFERENCES Product(id),
    FOREIGN KEY (invoice_id) REFERENCES Invoice(id)
);