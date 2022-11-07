# Jeu

> ⚠ Ce fichier contient une première ébauche du contenu du jeu.
> Le jeu est susceptible de changer au cours du développement et ainsi ne pas correspondre à ce fichier. ⚠
> 
> Se fier au contenu du fichier [README.md](README.md) à la racine du projet.

## Début

Création du perso

## Déroulement

Plusieurs niveaux

### Cases

- Point de départ
- Exploration
    - Vide
    - Obstacle
    - Buisson
        - Mob
        - Objet
    - Coffre random
- Fin de niveau : boss

# Combat

Phase du jeu durant laquelle doit survivre et tuer un ou deux monstres s'opposant à lui.

Le joueur a des pv. S'ils tombent à 0, il meurt.

Les monstres ont des pv. S'ils tombent à 0, ils meurent.

Le combat est terminé quand tous les monstres sont morts ou que le joueur est mort.

Pour faire baisser les pv, il faut faire des dégâts. Ceux-ci peuvent être physiques ou magiques.

Il y a donc deux stats d'attaque et de défense.

La stat de défense se soustrait aux dégâts du même type infligés.

À chaque tour, un combattant peut attaquer, se défendre, utiliser un consommable ou changer d'arme.

L'ordre des tours est défini par les stats de vitesse des combattants, au début du combat.

À la fin du combat, le joueur gagne de l'or et peut looter des objets.

## Attaque

Les attaques disponibles sont définies par l'arme équipée.

Une attaque est sûre de toucher sa cible, mais pas de faire des dégâts.

Les attaques spéciales ont une probabilité d'appliquer un ou plusieurs effets.

## Défense

Un combattant peut se défendre contre les attaques physiques ou magiques durant un tour.

La stat de défense choisie est multipliée par 2.

## Consommables

Les consommables sont à usage unique.

Ils peuvent être utilisés en combat ou en dehors.

Ceux-ci ont des effets sur le joueur ou sur les monstres.

Ils sont stockés dans l'inventaire du joueur.

# Armes

Les armes sont des objets qui peuvent être équipées et qui définissent les attaques disponibles.

Un combattant ne peut avoir qu'une seule arme équipée.

Toutes les armes ont une attaque principale. Certaines possèdent une attaque spéciale.

## Attaque

Une attaque peut faire des dégâts physiques et/ou magiques.

Elle peut avoir une probabilité d'appliquer un ou plusieurs effets.

Une attaque peut demander une cible ou être automatique.

## Effets

Moments d'activation :

- Immédiat
- Début du tour
- Fin du tour
- Avant l'attaque
- Après l'attaque