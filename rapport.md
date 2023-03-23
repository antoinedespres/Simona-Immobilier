# Simona Immobilier

## By ARNOULT Aymeric - BRAL Laurie - CHEN Xing - DANG Mélanie - DESPRES Antoine - EA Jean-Jacques

Trois micro-services, une permettant de gérer les données des utilisateurs, une pour gérer les habitations et une dernière pour la location de ces derniers :
Nous avons donc trois bases de données : Housing - Personne - Rentals

![image](https://user-images.githubusercontent.com/93189167/227244975-ea603e87-b5bc-4abe-97ee-c4c604e52405.png)


### Dépendances utilisées dans le FrontEnd :
| Nom          | Description                                                                                  | Version     |  
| ------------ | -------------------------------------------------------------------------------------------- | ----------- |
| react        | Fonctionnalités pour définir un compposant React, il dois être installer avec react-dom      | 18.1.0      |
| react-dom    | Rendre les composants sur le DOM(Document Object Model) HTML                                 | 18.1.0      |
| @mui/material ,@mui/lab<br>@emotion/styled, @emotion/react | bibliothèque Material UI de Google             | 5.8.2       |
| sass         | Utiliser le préprocesseur Sass afin d'écrire du CSS de manière plus pratique                 | 1.51.0      |
| typescript   | Language qui ajouter des syntaxes pour typer JavaScript                                      | 4.6.4       |
| react-icons  | Utiliser les icôns sous forme de composants React. Les icôns sont répertorié sur React icons | 4.3.1       |
| @types/react | Ajouter les définitions de types des méthodes de React.<br>La dépendance doit être installée avec typescript. | 18.0.9      |
| @types/react-dom | Ajouter les définitions de types des méthodesde react-dom.                               | 18.0.3      |
| @types/node  | Ajouter les définitions de types des méthodes de Node.js                                     | 16.11.33    |

### Dépendances utilisées dans le BackEnd :
| Nom         | Description                                                                                   | Version     |  
| ----------- | --------------------------------------------------------------------------------------------- | ----------- |
| SpringBoot  | Framework Java qui permet de développer rapidement des applications Web et des microservices. | 2.7.10      |
| node-fetch  | bibliothèque de récupération de ressources pour Node.js                                       | 2.6.7       |
