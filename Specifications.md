# Specifications du projet

## Description de la chaine de build
![description](./Ressources/images/description.png)

Le syst�me va �tre compos� de 4 parties:

- Un script bash qui va s�occuper de coordonner les diff�rentes parties:
    - configurer le processor spoon pour g�n�rer les bons mutants
    - appeler maven pour lancer la chaine mutation -> compilation -> test
    - r�cup�rer les rapports de JUnit et du processor pour g�n�rer le rapport plus tard
    - appeler l�outil d�analyse des rapports une fois la boucle de mutation et test termin�e
- un maven configur� pour appeler spoon � l��tape generate-source avec le processor configur� par le script bash
- un processor qui va g�n�rer les mutations
- un programme Java pour analyser les diff�rents rapports et g�n�rer un rapport au format html pour l�utilisateur final

L�utilisateur interface avec le framework via un script Bash qui va s�occuper de la boucle de cr�ation de mutant en configurant le processor qui cr�e le mutant et lan�ant Maven de fa�on r�p�t�e, il va aussi r�cup�rer les diff�rents rapports de JUnit et du processor afin de les stocker pour l�outil d�analyse. Une fois cette boucle termin�, il lance l�outil d�analyse auquel il fournis tout les rapports afin d�en extraire notre rapport final. 
Un processeur est un programme en Java utilis� par Spoon qui nous permet d�analyser et de modifier du code source, c�est notre processeur qui va nous permettre d�appliquer les diff�rentes mutations. Spoon est appel� � travers un plugin Maven qui lui m�me utilise notre processor. L�outil d�analyse sera con�u en Java et permettra d�obtenir un rapport d�execution du framework au format HTML.

## Op�rateurs de mutations applicables

#### ABS - Absolute value insertion
**Description :** l�op�rateur ABS ajoute une mise � la valeur absolue sur les variables applicables

**Exemple :** 

Code original: 	`int b = 3 * a ;`

mutant ABS:  		`int b = 3* Math.abs(a);`

mutant ABS:  		`int b = 3* - Math.abs(a);`

#### AOR - Arithmetic operator replacement
**Description :**  l�op�rateur AOR remplace un op�rateur arithm�tique ( *, +, - etc) par un autre op�rateur arithm�tique

#### LCR - Logical connector replacement
**Description : ** l�op�rateur LCR remplace un op�rateur logique ( AND, OR �) par un autre op�rateur logique.

#### ROR - Relational operator replacement
**Description : ** l�op�rateur ROR va remplacer un op�rateur relationnel par un autre.

**Exemple :**

Code original : 	`if ( i < 10 ) �`

Mutant ROR : 		`if ( i >= 10 ) ...`

#### UOI - Unary operator insertion
**Description :** l�op�rateur de mutation UOI va simplement ins�rer des op�rateurs unaires dans le code
**Exemple :**

Code original : `int x = 3 * a;`

Mutant UOI : `int x = 3 * -a;`

Mutant UOI(bis) : `int x = 3 * a++;`

#### CRT (Compatible Reference Type replacement)
**Description :** Cette mutation a pour effet de remplacer un type d�clar� par un autre type compatible. Cela permet de mettre en avant si les tests sont bien capables de distinguer deux types diff�rents mais compatibles. Cela permet aussi de faire ressortir les diff�rences entre ces types compatibles.

**Exemple :**

Classes :		
```
class T { � } 
interface K { � }
class S extends T implements K { � }
```

Code original:		`S s = new S();`

Mutations :		
```
T s = new S(); 
K s = new S();
```

#### ICE (class Instance Creation Expression changes)
**Description :** Cette mutation a pour effet de remplacer un type dynamique par un autre type compatible. Elle est tr�s proche de la mutation CRT mais permet en plus de faire ressortir les probl�mes lors de la cr�ation et de l�initialisation d�objets.

**Exemple :**

Classes :		
```
class S { � }
class U extends S { � }
```

Code original:		`S s = new S();`

Mutations :
```		
S s = new U();
S s = new S(1, �login�);
S s = new U(1, �login�, �Msg�);
```

#### AND - Argument Number Decrease 
**Description :** L�op�rateur de mutation AND supprime un des param�tres lors de l�appel de la fonction. Ainsi, soit la fonction n�existe pas (et donc l�erreur est obligatoire) soit la fonction avec un param�tre de moins existe ce qui entraine l�appel d�une fonction diff�rente. Le mutant devrait donc �tre lui aussi tu� si les tests unitaires sont corrects, autrement, cel� voudrait dire que le param�tre supprim� ne servait � rien. 

**Exemple :**

Code original : 	`Trace.trace(Trace.Event,this,sccsid);`

AND mutant 1 : 	`Trace.trace(this,sccsid); `

AND mutant 2 :	`Trace.trace(Trace.Event,sccsid);`


#### OMR - Overriding Method declaration Removal
**Description :** Cet op�rateur supprime la d�claration d�une m�thode red�finie (override) ainsi la m�thode non red�finie sera utilis�e dans la suite du programme.


##M�thode d�application des mutations

Pour chaque it�ration nous modifierons qu�une classe � la fois. Nous appliquerons n mutations al�atoires 
(o� n est un nombre donn� par l�utilisateur) au code de la classe � modifier � des emplacements al�atoires.  
Il est aussi possible d�appliquer tous les mutants (ou une partie de la liste de mutants) � tous les endroits possibles pour chaque classe, 
une mutation � la fois. Ou de g�n�rer un certain nombre de mutations al�atoires pour chaque classe que l�on fait muter.

## Outils utilis�s
- **Java** pour l�application de mutations, l�analyse des rapports et la cr�ation de rapports HTML
- **Bash** pour le script d�execution du framework
- **Maven** pour la chaine de build
- **Spoon** pour l��dition du java � la vol�e (avec le plugin maven de Spoon)
- **Bootstrap et Highchart** pour la mise en forme du rapport.
