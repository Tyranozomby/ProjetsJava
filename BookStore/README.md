# Projet - BookStore

Pour réaliser ce projet, j'ai décidé d'utiliser le modèle MVC pour séparer les différentes parties de mon application.

J'ai préféré utiliser Bootstrap pour le style pour pouvoir me concentrer sur le JEE.

## Lancement

Ce projet a été fait avec Tomcat 8.5

Je n'ai pas testé avec des versions supérieures.

J'utilise donc javax et non jakarta.

> **Important :** Bien penser à reload/sync Maven pour que les dépendances soient bien chargées.

## Composants

### Header

Inclus dans toutes les pages, le fichier [header.jsp](src/main/webapp/WEB-INF/jsp/includes/header.jsp) contient le
header.

Celui-ci contient une navbar menant vers les pages [home](#accueil) et [books](#liste-des-livres).

Une barre de recherche est présente qui permet de trouver un livre plus facilement.

Pour finir, il y a un accès au panier, ainsi qu'un badge indiquant le nombre d'articles dans le panier.

### Head

Inclus, lui aussi dans toutes les pages, le fichier [head.jsp](src/main/webapp/WEB-INF/jsp/includes/head.jsp) contient
la balise `<head>`.

Des paramètres sont passés à la page pour définir le titre de la page, ainsi que certains scripts à charger.

Bootstrap et FontAwesome sont chargés par défaut.

### Footer

Encore une fois inclus dans toutes les pages, le fichier [footer.jsp](src/main/webapp/WEB-INF/jsp/includes/footer.jsp)
contient évidemment le footer.

Aucune information utile n'est présente.

## Pages

### Accueil

| URL |  Fichier  | Servlet |
|:---:|:---------:|:-------:|
|  /  | index.jsp |  Aucun  |

Appelé dans la page d'accueil [(index.jsp)](src/main/webapp/index.jsp), celui-ci ne contient que de simples informations
sur le projet, ainsi qu'un lien vers la liste des livres.

### Liste des livres

|  URL   |    Fichier     |    Servlet     |
|:------:|:--------------:|:--------------:|
| /books | books_list.jsp | BookController |

Cette page contient simplement la liste des livres. À noter que les livres sont triés par ordre alphabétique (titre).

Il est possible de faire une recherche plus précise en utilisant le champ de recherche présent dans le header.

Si jamais aucun livre ne correspond à la recherche, un message d'erreur est affiché.

Quand un livre est dans le panier, un petit badge est affiché à côté du prix du livre.

#### Livre spécifique

|       URL       | Fichier  |    Servlet     |
|:---------------:|:--------:|:--------------:|
| /books?bookId=3 | book.jsp | BookController |

Si l'utilisateur clique sur un livre, il est redirigé vers la page du livre.

> **Exemple** : http://localhost:8080/BookStore/books?id=7

Ici, les informations du livre sont affichées, ainsi qu'un joli bouton permettant de l'ajouter au panier.

### Panier

|      URL       |      Fichier      |        Servlet         |
|:--------------:|:-----------------:|:----------------------:|
| /shopping-list | shopping_list.jsp | ShoppingListController |

Cette page contient le panier de l'utilisateur.

On peut y retrouver l'ensemble des livres ajoutés, ainsi que le prix total.

Il est possible de supprimer un livre du panier.

### Commande

|   URL    |   Fichier   |      Servlet      |
|:--------:|:-----------:|:-----------------:|
| /payment | payment.jsp | PaymentController |

Cette page contient un faux formulaire de paiement.

On y retrouve encore le prix total et une liste minimale des livres ajoutés.

## API

### ShoppingListAPI

Ce fichier est le seul correspondant à une API REST

Il permet de récupérer le panier de l'utilisateur, ainsi que de le modifier (ajout, suppression d'élément).

## Images

Les images des couvertures de livres sont stockées dans le dossier [covers](src/main/webapp/covers).