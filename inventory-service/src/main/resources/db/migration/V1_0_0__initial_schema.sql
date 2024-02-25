CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS products;

CREATE TABLE products (
    id uuid NOT NULL,
    version int NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    stocks int NOT NULL,
    CONSTRAINT products_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS out_box;

CREATE TABLE out_box (
     id UUID PRIMARY KEY,
     aggregate_type character varying COLLATE pg_catalog."default" NOT NULL,
     aggregate_id UUID NOT NULL,
     type character varying COLLATE pg_catalog."default" NOT NULL,
     payload JSONB NOT NULL
);

DROP TABLE IF EXISTS message_log;

CREATE TABLE message_log (
    id UUID PRIMARY KEY,
    received_at TIMESTAMP NOT NULL
);