# Projet - Chat

## Lancement

Le serveur peut être lancé à l'aide de la commande suivante :

```sh
java Chat/server/Server <port>
```

Ou lancer à l'aide de l'IDE le fichier `Server` ([ici](src/server/Server.java)).

Un client peut être lancé à l'aide de la commande suivante :

```sh
java Chat/client/Client <port>
```

Ou lancer à l'aide de l'IDE le fichier `Client` ([ici](src/client/Client.java)).

## Fonctionnement

### Serveur

Le serveur tourne sur le port spécifié en argument ou 5000 par défaut.

Il peut gérer plusieurs clients en même temps grâce à des threads dédiés.

Lorsqu'un client se connecte, le serveur lui envoie un message de bienvenue et prévient les autres clients.

Lorsqu'un client envoie un message, le serveur le transmet à tous les autres clients.

Lorsqu'un client se déconnecte, le serveur le signale à tous les autres clients.

Les messages du serveur sont des instances de la classe **Message** ([ici](src/util/Message.java)) sérialisées.

Le serveur affiche des logs dans la console.

### Client

Le client se connecte au serveur sur le port spécifié en argument ou 5000 par défaut.

Deux threads sont lancés pour gérer les entrées *(réception des messages du serveur)* et les sorties *(envoi des
messages au serveur)*.

Lorsqu'un message est reçu, il est envoyé dans le `MessageHandler` ([ici](src/client/MessageHandler.java)) du client. Le
seul MessageHandler actuellement implémenté est
le `ConsoleMessageHandler` ([ici](src/client/ConsoleMessageHandler.java)) qui formate et affiche le message dans la console.