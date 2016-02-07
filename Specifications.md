# Specifications du projet

## Sujet

Specifications.md : Document de description de votre framework (~3 pages)
Description de la chaine de build en positionnant les artefacts manipulés et les outils associés
Quelles mutations, où les appliquer, comment les appliquer ?

## Description de la chaine de build
![description](./Ressources/images/description.png)

Le système va être composé de 4 parties:
Un script bash qui va s’occuper de coordonner les différentes parties:
configurer le processor spoon pour générer les bons mutants
appeler maven pour lancer la chaine mutation -> compilation -> test
récupérer les rapports de JUnit et du processor pour générer le rapport plus tard
appeler l’outil d’analyse des rapports une fois la boucle de mutation et test terminée

un maven configuré pour appeler spoon à l’étape generate-source avec le processor configuré par le script bash
un processor qui va générer les mutations
un programme Java pour analyser les différents rapports et générer un rapport au format html pour l’utilisateur final


## Opérateurs de mutations applicables

#### ABS - Absolute value insertion
**Description :** 

**Exemple :**

#### AOR - Arithmetic operator replacement
**Description :** 

**Exemple :**

#### LCR - Logical connector replacement
**Description :** l’opérateur LCR remplace un opérateur logique ( AND, OR …) par un autre opérateur logique.

#### ROR - Relational operator replacement
**Description :** l’opérateur ROR va remplacer un opérateur relationnel par un autre.

**Exemple :**

Code original : 	`if ( i < 10 )`

Mutant ROR : 		`if ( i >= 10 )`

#### UOI - Unary operator insertion
**Description :** l’opérateur de mutation UOI va simplement insérer des opérateurs unaires dans le code

#### AND - Argument Number Decrease 
**Description :** L’opérateur de mutation AND supprime un des paramètres lors de l’appel de la fonction. Ainsi, soit la fonction n’existe pas (et donc l’erreur est obligatoire) soit la fonction avec un paramètre de moins existe ce qui entraine l’appel d’une fonction différente. Le mutant devrait donc être lui aussi tué si les tests unitaires sont corrects, autrement, celà voudrait dire que le paramètre supprimé ne servait à rien. 

**Exemple :**

Code original : 	`Trace.trace(Trace.Event,this,sccsid);`

AND mutant 1 : 		`Trace.trace(this,sccsid);` 

AND mutant 2 :		`Trace.trace(Trace.Event,sccsid);`
etc.

#### OMR - Overriding Method declaration Removal
 **Description :** Cet opérateur supprime la déclaration d’une méthode redéfinie (override) ainsi la méthode non redéfinie sera utilisée dans la suite du programme.


##Méthode d’application des mutations

Pour chaque itération nous modifierons qu’une classe à la fois. Nous appliquerons n mutation aléatoires (où n est un nombre donné par l’utilisateur) au code de la classe à modifier à des emplacements aléatoires. Il est aussi possible de modifier 
