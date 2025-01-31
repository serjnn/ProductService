CREATE TABLE product (
    id SERIAL PRIMARY KEY, -- Auto-incrementing primary key
    name VARCHAR(255) NOT NULL, -- Name of the product
    description TEXT, -- Description of the product
    price NUMERIC(19, 4) NOT NULL, -- Price with precision and scale
    category VARCHAR(255) -- Assuming Category is stored as a string
);

CREATE TABLE subscribers (
    id SERIAL PRIMARY KEY, -- Auto-incrementing primary key
    product_id BIGINT NOT NULL, -- Foreign key to product table
    client_id BIGINT NOT NULL, -- Client ID
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE -- Foreign key constraint
);