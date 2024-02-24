CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS customers;

CREATE TABLE customers (
      id uuid NOT NULL,
      version int NOT NULL,
      username character varying COLLATE pg_catalog."default" NOT NULL,
      full_name character varying COLLATE pg_catalog."default" NOT NULL,
      balance numeric(10,2) NOT NULL,
      CONSTRAINT customers_pkey PRIMARY KEY (id)
);