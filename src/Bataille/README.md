# Bataille

## Lancement

Exécuter la commande suivante dans le dossier `src` :

```bash
java Bataille.Main
```

Ou lancer à l'aide de l'IDE le fichier `src/Bataille/Main.java` ([ici](Main.java)).

Aucun argument n'est nécessaire.

## Déroulement

### Initialisation

Tout d'abord, le jeu demande le nom des deux joueurs.
Après cette étape, le jeu commence.

### Le Jeu

Nous disposons d'un paquet de **52 cartes**, qui sont mélangées aléatoirement.
Chaque joueur reçoit **26 cartes** et le jeu commence.

Le jeu se déroule en plusieurs manches.

#### Manche

À chaque manche, chaque joueur pioche une carte de son paquet.

Le joueur qui a la carte la plus forte gagne la manche.

En cas de victoire, le gagnant ajoute ces cartes à sa pile de côté ainsi que les potentielles cartes placées au milieu.

Si les deux cartes ont la même valeur, alors il y a une *bataille*.

#### Bataille

Dans cette situation, les deux cartes sont ajoutées à la pile du milieu.

#### Main vide

Si un joueur n'a plus de cartes dans sa main, il récupère toutes les cartes gagnées et les mélange.

Dans le cas où celle-ci est aussi vide, alors le jeu s'arrête.

### Fin du jeu

Le jeu s'arrête lorsque l'un des deux joueurs n'a plus de cartes dans sa main, ni dans sa pile de côté.
Le gagnant est celui qui a toujours des cartes en sa possession.