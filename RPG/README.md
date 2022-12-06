# Projet - RPG

## Lancement

Le jeu peut être lancé à l'aide de la commande suivante :

```sh
java RPG/ui/<ui>
```

### UI

Deux interfaces utilisateurs sont disponibles :

- `ConsoleUI` : interface en ligne de commande ([ici](src/ui/ConsoleUI.java))
- `SwingUI` : interface graphique Swing ([ici](src/ui/SwingUI.java))

#### ConsoleUI

L'interface en ligne de commande est intentionnellement moche et peu pratique. Elle est là pour tester le jeu et pour
montrer que l'interface graphique est possible.
Cependant, elle est complètement fonctionnelle.

#### SwingUI

L'interface graphique Swing est plus jolie et plus pratique. Elle est aussi complètement fonctionnelle.

Pour se déplacer, il suffit de cliquer sur une case adjacente.

Lors d'un combat, il faut cliquer sur l'attaque à utiliser.

Pour utiliser un objet, faire un clic droit sur celui-ci dans l'inventaire. Acheter un objet fonctionne de la même
manière.

Je comptais initialement ajouter un système de traduction, mais il m'aurait fallu aussi changer toutes les chaînes de
caractères dans le moteur. J'ai cependant utilisé des bundles pour les chaînes de caractères de l'interface graphique
mais la langue est bloquée en anglais.

## Déroulement du jeu

### Création du personnage

Le joueur doit créer un personnage en choisissant son pseudo et sa classe :

|  Classe   | Attaque physique | Attaque magique | Défense physique | Défense magique | Vitesse | PV  | Arme initiale |
|:---------:|:----------------:|:---------------:|:----------------:|:---------------:|:-------:|:---:|:-------------:|
| *Warrior* |        2         |        1        |        2         |        0        |    5    | 15  |  Fourchette   |
|  *Mage*   |        1         |        2        |        0         |        2        |    5    | 12  |   Cuillère    |
|  *Thief*  |        1         |        1        |        1         |        1        |   10    | 10  |    Couteau    |
|  *Tank*   |        1         |        1        |        3         |        1        |    3    | 20  |   Assiette    |

### Contenu du jeu

Pour finir le jeu, le joueur doit survivre à tous les niveaux.

Pour cela, il doit aller jusqu'à la sortie du niveau.

Différentes actions sont possibles pour y arriver :

- Se déplacer
    - Haut
    - Bas
    - Gauche
    - Droite
- Accéder à l'inventaire
    - Utiliser un consommable
    - Équiper une arme
- Accéder au magasin
    - Acheter un objet
- Voir son profil

Chaque niveau est composé de différents types de cases :

- `*` : Mur → impossible de passer dessus **(noir)**
- ` ` : Sol **(vert)**
- `E` : Sortie → fin du niveau/du jeu **(blanc)**
- `B` : Buisson → peut cacher un ennemi et dans de rare cas, un objet **(jaune)**
- `C` : Coffre → contient de l'or et une arme aléatoire **(bleu)**

En cas de rencontre avec un ennemi, un combat débute.

#### Combat

Le combat se déroule en tour par tour.

À chaque tour, le joueur peut :

- Attaquer
    - Choisir une des attaques de son arme
- Utiliser un consommable
- Équiper une nouvelle arme

Si le joueur réussi à vaincre les ennemies, il gagne de l'or et peut continuer à jouer. Sinon s'il meurt, la partie est
terminée.

### Ennemis

À chaque niveau, les ennemis sont plus forts. À partir du niveau 2, il est possible d'affronter deux ennemis en même
temps.

Les ennemis ont 10% de chance d'avoir un niveau en moins et 10% de chance d'avoir un niveau en plus.

Exemples d'ennemis :

|   Nom    | Niveaux |                                Caractéristique                                 |
|:--------:|:-------:|:------------------------------------------------------------------------------:|
| *Slime*  |  Tous   |                                     Aucune                                     |
| *Cactus* | 2 et 3  | N'attaque pas mais inflige des débats perçants à chaque attaque physique reçue |

> D'autres monstres sont à venir

### Objets

Tout comme les ennemis, les objets sont plus efficaces à chaque niveau.

#### Consommables

Exemples de consommables au niveau 1 :

|      Nom       |                   Effet                   | Prix |
|:--------------:|:-----------------------------------------:|:----:|
|    *Potion*    |              Récupère 10 PV               |  5   |
| *Stat upgrade* |        Améliore une stat du joueur        |  10  |
| *Seau de lait* |  Supprime tous les effets sur le joueur   |  10  |
|    *Poison*    | Applique un effet poison à l'ennemi ciblé |  15  |

> D'autres consommables sont à venir

Certains consommables ne peuvent être utilisés qu'en combat.

#### Armes

Armes par défaut vues dans la présentation des classes :

Ce tableau présente les caractéristiques des attaques normales.

|     Nom      | Attaque physique | Attaque magique | Prix |
|:------------:|:----------------:|:---------------:|:----:|
| *Fourchette* |        2         |        0        |  5   |
|  *Cuillère*  |        0         |        2        |  5   |
|  *Couteau*   |        2         |        0        |  5   |
|  *Assiette*  |        2         |        0        |  5   |

Ce tableau présente les caractéristiques des attaques spéciales.

|     Nom      | Attaque physique | Attaque magique |                                   Effet(s)                                   | Prix |
|:------------:|:----------------:|:---------------:|:----------------------------------------------------------------------------:|:----:|
| *Fourchette* |        0         |        0        |                  50% de chance de faire 3 dégâts physiques                   |  5   |
|  *Cuillère*  |        0         |        1        |                  75% de chance de rendre les ennemis confus                  |  5   |
|  *Couteau*   |        0         |        0        | 50% de chance de one shot un ennemi<br/>mais 100% de chance de perdre l'arme |  5   |
|  *Assiette*  |        0         |        0        |   80% de chance de réduire de moitié les prochains dégâts physiques reçus    |  5   |

> D'autres armes existent et sont à venir

### Magasin

Le magasin est accessible à tout moment, cependant, son contenu change à chaque niveau et chaque objet ne peut être
acheté qu'une seule fois.

Le prix des objets augmente à chaque niveau. Les objets sont également plus puissants.