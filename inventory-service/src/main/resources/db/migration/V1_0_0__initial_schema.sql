CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS products;

CREATE TABLE products (
    id uuid NOT NULL,
    version int NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    stocks int NOT NULL,
    CONSTRAINT products_pkey PRIMARY KEY (id)
);