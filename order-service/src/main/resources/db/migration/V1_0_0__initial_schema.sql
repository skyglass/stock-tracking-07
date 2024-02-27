CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS orders;

CREATE TABLE orders (
    id uuid NOT NULL,
    version int NOT NULL,
    customer_id uuid NOT NULL,
    product_id uuid NOT NULL,
    price numeric(10,2) NOT NULL,
    quantity int NOT NULL,
    status character varying COLLATE pg_catalog."default" NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT orders_pk PRIMARY KEY (id)
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