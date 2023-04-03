# Simona Immobilier

## Vue d'ensemble

Une application de location de logement basée sur l'architecture des microservices.

## Usage

Connect to the PostgreSQL interactive terminal `psq` with the built-in user `postgres`:

```bash
psql -U postgres
```

Create a database of each service:

```bash
create database "simona-housing-service";
create database "simona-rental-service";
```

type `\l` to show the list of created databases.

## Conception de l'application

L'application est composée des services suivants :

- logement : gérer la création ou suppresion d'un logements

- réservation : gérer la liste de réservation, la création ou suppresion d'une réservation
