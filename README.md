# Simona Immobilier

## Vue d'ensemble

Une application de location de logement basée sur l'architecture des microservices.

## Getting started

You can generate a new microservice using [this configuration on Spring initializr](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.7.10&packaging=jar&jvmVersion=11&groupId=com.simonaimmobilier.rental&artifactId=rental-service&name=rental-service&description=Rental%20service&packageName=com.simona.rental&dependencies=web,data-jpa,postgresql)

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
