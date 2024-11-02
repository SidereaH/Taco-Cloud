-- Drop constraints if they exist
ALTER TABLE IF EXISTS ingredient_ref 
    DROP CONSTRAINT IF EXISTS fk_ingredient_ref_ingredient;
ALTER TABLE IF EXISTS ingredient_ref 
    DROP CONSTRAINT IF EXISTS fk_ingredient_ref_taco;
ALTER TABLE IF EXISTS taco 
    DROP CONSTRAINT IF EXISTS fk_taco_order;

-- Drop tables in correct order (reverse dependency order)
DROP TABLE IF EXISTS ingredient_ref;
DROP TABLE IF EXISTS taco;
DROP TABLE IF EXISTS taco_order;
DROP TABLE IF EXISTS ingredient;

-- Create tables in correct order
CREATE TABLE IF NOT EXISTS ingredient (
    id VARCHAR(4) NOT NULL PRIMARY KEY,
    name VARCHAR(25) NOT NULL,
    type VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS taco_order (
    id SERIAL PRIMARY KEY,
    delivery_name VARCHAR(50) NOT NULL,
    delivery_street VARCHAR(50) NOT NULL,
    delivery_city VARCHAR(50) NOT NULL,
    delivery_state VARCHAR(2) NOT NULL,
    delivery_zip VARCHAR(10) NOT NULL,
    cc_number VARCHAR(16) NOT NULL,
    cc_expiration VARCHAR(5) NOT NULL,
    cc_cvv VARCHAR(3) NOT NULL,
    placed_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS taco (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    taco_order BIGINT NOT NULL,
    taco_order_key BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS ingredient_ref (
    ingredient VARCHAR(4) NOT NULL,
    taco BIGINT NOT NULL,
    taco_key BIGINT NOT NULL,
    PRIMARY KEY (ingredient, taco)
);

-- Add foreign key constraints
ALTER TABLE taco 
    ADD CONSTRAINT fk_taco_order
    FOREIGN KEY (taco_order) REFERENCES taco_order(id);

ALTER TABLE ingredient_ref 
    ADD CONSTRAINT fk_ingredient_ref_taco
    FOREIGN KEY (taco) REFERENCES taco(id);

ALTER TABLE ingredient_ref 
    ADD CONSTRAINT fk_ingredient_ref_ingredient
    FOREIGN KEY (ingredient) REFERENCES ingredient(id);