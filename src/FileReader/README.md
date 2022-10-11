# Projet - FileReader

## Lancement

Chaque reader peut être lancé individuellement à l'aide de la commande suivante :

```sh
java FileReader.Readers.<reader> [args]
```

Ou lancer à l'aide de l'IDE le fichier `src/FileReader/Readers/<reader>.java` ([ici](Readers)).

Il est possible de donner le chemin du fichier à lire en argument, sinon leur fichier par défaut sera utilisé.

Le _DiffReader_ lui prend deux fichiers en argument.

Attention, le fichier doit être présent dans le dossier ``files/FileReader``.

## Les readers

### NormalReader

Comme son nom l'indique, ce reader se contente de renvoyer le contenu du fichier entré.

### ReverseReader

Ce reader renvoie le contenu du fichier entré, mais à l'envers. 

> Exemple :
> ```
> Hello world
> Bonjour le monde
> ```
> Renvoie :
> ```
> Bonjour le monde
> Hello world
> ```

### PalindromeReader

Ce reader renvoie le contenu du fichier entré, mais en inversant les caractères de chaque ligne.

> Exemple :
> ```
> Hello world
> Bonjour le monde
> ```
> Renvoie :
> ```
> dlrow olleH
> ednom el ruojnoB
> ```

### DiffReader

Je n'ai malheureusement pas réussi à faire fonctionner ce reader parfaitement malgré mes différentes tentatives.