#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE USER $INVENTORY_DB_USER WITH PASSWORD '$INVENTORY_DB_PASSWORD';
    CREATE DATABASE $INVENTORY_DB_NAME;
    GRANT ALL PRIVILEGES ON DATABASE $INVENTORY_DB_NAME TO $INVENTORY_DB_USER;
    ALTER DATABASE $INVENTORY_DB_NAME SET TIMEZONE='Europe/Amsterdam';
EOSQL
