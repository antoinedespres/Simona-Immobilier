# Simona Immobilier

Par :

- CHEN Xing
- DESPRES Antoine
- ARNOULT Aymeric
- BRAL Laurie
- DANG Mélanie
- EA Jean-Jacques

Le 11/04/2023

## Objectif

L'objectif est de créer une application de location de logement basée sur l’architecture des microservices. L'application doit être facile à utiliser et offrir une expérience fluide pour les locataires et les propriétaires.

## Fonctionnalités implémentées

- opérations CRUD sur les logements
- opérations CRUD sur les locations
- l'authentification par Json Web Token (JWT)
- documentation Swagger
- déploiement par Docker

## Dépendances

| Nom                                                                                  | Description                                                                                                          |
| ------------------------------------------------------------------------------------ | -------------------------------------------------------------------------------------------------------------------- |
| Spring Boot 2.7.10                                                                   | Framework Java qui permet de développer rapidement des applications Web et des microservices.                        |
| [Spring Cloud Gateway](https://cloud.spring.io/spring-cloud-gateway/reference/html/) | Une API Gateway qui se construit sur l'écosystème Spring, à savoir Spring 5, Spring boot 2 et Projet Reactor.        |
| Eureka                                                                               | mettre en place un registre content toutes les instances de chaque microservice déployé dans des serveurs différents |
| Feign                                                                                | permet aux microservices de communiquer entre-eux en faisant des requêtes REST                                       |
| PostgreSQL                                                                           | base de données SQL                                                                                                  |

## Description des microservices

L'application est découpé en 4 microservices dont 1 API Gateway :

- API Gateway
- Auth service
- Housing service
- Rental service

Je vais vous détaillé les services un par un dans la partie qui suit.

**Notes**

- Chaque service a sa propre base de données PostgreSQL respective : `simona_account_service`, `simona_housing_service`, `simona_rental_service`.

- Chaque service communique à l'aide de la bibliothèque Feign, un client REST déclaratif.

### Exemple de requête

Diagram

### API Gateway

C'est le point d'entrée du système. Il permet de diriger les requêtes vers les microservices correspondants. Il peut également faire office de la gestion d'authentification.

Nous avons choisi d'utiliser la bibliothèque [Spring Cloud Gateway](https://cloud.spring.io/spring-cloud-gateway/reference/html/) car elle est présent dans l'écosystème de Spring.

La configuration de l'acheminement des routes API se trouve dans `application.yml`:

```yml
spring:
  config:
    import: 'optional:configserver:'
  application:
    name: gateway
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
      routes:
        - id: rental-service
          uri: lb://rental-service
          predicates:
            - Path=/rental/api/v1/**
          filters:
            - AuthFilter
            - RewritePath=/rental/api/v1/(?<segment>.*), /$\{segment}
```

`routes`: définir les routes à acheminer

`routes.id`: définir l'id du service vers lequel Gateway achemine les requêtes

`routes.uri`: définir son uri. `lb` est précisé afin d'utiliser un load balancer pour répartir les requêtes.

`routes.predicates`: un prédicat permet de tester si une requête vérifie une condition. `Path` indique que le chemin de la requête doit commencer par `rental/api/v1`. Concrètement, lorsqu'on fait une requête à l'url `/rental/api/v1/`, le Gateway va acheminer la requête vers le service de location.

`routes.filter`: un filtre permet de faire des modifications sur la requête. `AuthFilter` est un filtre d'authentification. Il va vérifier s'il y a la présence d'un token dans l'en-tête `Authorization`.

`RewritePath` permet de réécrire le chemin. En réalité l'endpoint exposé par rental-service est `localhost:8083/rentals/*`.

Considérons que nous souhaitons recevoir la liste des locations avec `localhost:8080/rental/api/v1/rentals`:

TODO
Schema: `localhost:8080/rental/api/v1/rentals` --> `localhost:8083/rentals`

`RewritePath` indique que Gateway va récupérer `rentals` et fait une requête à rental-service avec l'endpoint `localhost:8083/rentals`

En réalité, `RewritePath` permet d'ajouter un préfixe afin de **versionner** l'API de rental-service. Nous pouvons très bien laisser l'endpoint `/rentals` avec la config suivante :

```yml
routes:
  - id: rental-service
    uri: lb://rental-service
    predicates:
      - Path=/rentals/**
    filters:
      - AuthFilter
```

![image](https://user-images.githubusercontent.com/93189167/227244975-ea603e87-b5bc-4abe-97ee-c4c604e52405.png)

### Auth service

### Housing service

### Rental service

## Gestion des interactions entre les services

Feign

## Gestion de l'authentification

JWT

## Gestion de déploiement

Docker + docker compose
