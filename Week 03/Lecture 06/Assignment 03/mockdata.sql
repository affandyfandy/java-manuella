-- Insert mock data into Customer table
INSERT INTO Customer (id, name, phone) VALUES
(1, 'Alice', '123-456-7890'),
(2, 'Bob', '234-567-8901'),
(3, 'Charlie', '345-678-9012');

-- Insert mock data into Cashier table
INSERT INTO Cashier (id, name) VALUES
(1, 'John'),
(2, 'Jane');

-- Insert mock data into Product table
INSERT INTO Product (id, name, price) VALUES
(1, 'Math book', 50.00),
(2, 'Physics book', 60.00),
(3, 'Biology book', 70.00),
(4, 'Chemistry book', 80.00);

-- Insert mock data into Invoice table
INSERT INTO Invoice (id, customer_id, cashier_id, amount, created_date) VALUES
(1, 1, 1, 110.00, '2023-06-01 10:00:00'),
(2, 2, 2, 150.00, '2023-06-02 11:00:00'),
(3, 3, 1, 130.00, '2023-06-03 12:00:00');

-- Insert mock data into InvoiceDetail table
INSERT INTO InvoiceDetail (id, quantity, product_id, product_price, invoice_id, amount) VALUES
(1, 1, 1, 50.00, 1, 50.00),
(2, 1, 2, 60.00, 1, 60.00),
(3, 1, 3, 70.00, 2, 70.00),
(4, 1, 4, 80.00, 2, 80.00),
(5, 1, 1, 50.00, 3, 50.00),
(6, 1, 4, 80.00, 3, 80.00);