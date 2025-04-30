-- Insert sample products
INSERT INTO products (name, description, is_available) 
VALUES ('Current Account', 'Basic current account for everyday banking', 0);

INSERT INTO products (name, description, is_available) 
VALUES ('Savings Account', 'High-interest savings account', 1);

INSERT INTO products (name, description, is_available) 
VALUES ('Credit Card Gold', 'Premium credit card with rewards', 1);

INSERT INTO products (name, description, is_available) 
VALUES ('Credit Card Platinum', 'Elite credit card with exclusive perks', 1);

INSERT INTO products (name, description, is_available) 
VALUES ('Mortgage', 'Home mortgage loan product', 1);

INSERT INTO products (name, description, is_available) 
VALUES ('Personal Loan', 'Unsecured personal loan', 1);

-- Sample client products (these would typically be created through the API)
-- Assuming client IDs 1 and 2 exist in the client service
INSERT INTO client_products (client_id, product_id, product_name, account_number)
VALUES (1, 1, 'Current Account', '10000001');

INSERT INTO client_products (client_id, product_id, product_name, account_number)
VALUES (1, 2, 'Savings Account', '20000001');

INSERT INTO client_products (client_id, product_id, product_name, account_number)
VALUES (2, 1, 'Current Account', '10000002'); 