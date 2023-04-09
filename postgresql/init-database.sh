#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	CREATE USER docker;
	CREATE DATABASE simona_account_service;
	GRANT ALL PRIVILEGES ON DATABASE simona_account_service TO docker;
	CREATE DATABASE simona_housing_service;
	GRANT ALL PRIVILEGES ON DATABASE simona_housing_service TO docker;
	CREATE DATABASE simona_rental_service;
	GRANT ALL PRIVILEGES ON DATABASE simona_rental_service TO docker;
	
	\c simona_account_service
	
	CREATE TABLE IF NOT EXISTS role(
	   id SERIAL,
	   label VARCHAR,
	   PRIMARY KEY(id)
	);
	
	INSERT INTO role(label) VALUES('tenant');
	INSERT INTO role(label) VALUES('owner');

	CREATE TABLE IF NOT EXISTS account(
	   id SERIAL,
	   last_name VARCHAR,
	   first_name VARCHAR,
	   email VARCHAR,
	   password VARCHAR,
	   role_id INT DEFAULT 1,
	   PRIMARY KEY(id),
	   FOREIGN KEY(role_id) REFERENCES role(id)
	);

	\c simona_housing_service
	
	CREATE TABLE housing_type(
		id SERIAL,
   		label VARCHAR,
		PRIMARY KEY(id)
	);

	INSERT INTO housing_type(label) VALUES('apartment');
	INSERT INTO housing_type(label) VALUES('house');

	CREATE TABLE housing(
	   id SERIAL,
	   surface INT,
	   nb_rooms INT,
	   street VARCHAR,
	   postal_code VARCHAR,
	   city VARCHAR,
	   price NUMERIC,
	   landlord_id INT NOT NULL,
	   type_id INT NOT NULL,
	   PRIMARY KEY(id),
	   FOREIGN KEY(type_id) REFERENCES housing_type(id)
	);
	
	INSERT INTO Housing (surface, nb_rooms, street, postal_code, city, price, landlord_id, type_id) VALUES (60, 2, '9 rue du Chat qui Pêche', '75005', 'Paris', 350000.50, 1, 1);

	\c simona_rental_service

	CREATE TABLE rental(
		id SERIAL,
	    start_date DATE,
	    end_date DATE,
	    tenant_id INT NOT NULL,
	    housing_id INT NOT NULL,
	    PRIMARY KEY(id)
	);

	INSERT INTO rental (start_date, end_date, housing_id, tenant_id) VALUES('2023-04-02', '2023-05-02', 1, 1);
EOSQL