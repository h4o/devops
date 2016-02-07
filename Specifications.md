# Specifications du projet

## Sujet

Specifications.md : Document de description de votre framework (~3 pages)
Description de la chaine de build en positionnant les artefacts manipul�s et les outils associ�s
Quelles mutations, o� les appliquer, comment les appliquer ?

## Description de la chaine de build
![description](./Ressources/images/description.png)

Le syst�me va �tre compos� de 4 parties:
Un script bash qui va s�occuper de coordonner les diff�rentes parties:
configurer le processor spoon pour g�n�rer les bons mutants
appeler maven pour lancer la chaine mutation -> compilation -> test
r�cup�rer les rapports de JUnit et du processor pour g�n�rer le rapport plus tard
appeler l�outil d�analyse des rapports une fois la boucle de mutation et test termin�e

un maven configur� pour appeler spoon � l��tape generate-source avec le processor configur� par le script bash
un processor qui va g�n�rer les mutations
un programme Java pour analyser les diff�rents rapports et g�n�rer un rapport au format html pour l�utilisateur final


## Op�rateurs de mutations applicables

#### ABS - Absolute value insertion
**Description :** 

**Exemple :**

#### AOR - Arithmetic operator replacement
**Description :** 

**Exemple :**

#### LCR - Logical connector replacement
**Description :** l�op�rateur LCR remplace un op�rateur logique ( AND, OR �) par un autre op�rateur logique.

#### ROR - Relational operator replacement
**Description :** l�op�rateur ROR va remplacer un op�rateur relationnel par un autre.

**Exemple :**

Code original : 	`if ( i < 10 )`

Mutant ROR : 		`if ( i >= 10 )`

#### UOI - Unary operator insertion
**Description :** l�op�rateur de mutation UOI va simplement ins�rer des op�rateurs unaires dans le code

#### AND - Argument Number Decrease 
**Description :** L�op�rateur de mutation AND supprime un des param�tres lors de l�appel de la fonction. Ainsi, soit la fonction n�existe pas (et donc l�erreur est obligatoire) soit la fonction avec un param�tre de moins existe ce qui entraine l�appel d�une fonction diff�rente. Le mutant devrait donc �tre lui aussi tu� si les tests unitaires sont corrects, autrement, cel� voudrait dire que le param�tre supprim� ne servait � rien. 

**Exemple :**

Code original : 	`Trace.trace(Trace.Event,this,sccsid);`

AND mutant 1 : 		`Trace.trace(this,sccsid);` 

AND mutant 2 :		`Trace.trace(Trace.Event,sccsid);`
etc.

#### OMR - Overriding Method declaration Removal
 **Description :** Cet op�rateur supprime la d�claration d�une m�thode red�finie (override) ainsi la m�thode non red�finie sera utilis�e dans la suite du programme.


##M�thode d�application des mutations

Pour chaque it�ration nous modifierons qu�une classe � la fois. Nous appliquerons n mutation al�atoires (o� n est un nombre donn� par l�utilisateur) au code de la classe � modifier � des emplacements al�atoires. Il est aussi possible de modifier 
