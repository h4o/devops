# Objectif du fichier Report

Donner une analyse critique de notre travail au travers de son architecture (Dev & Ops) en montrant ses forces et ses faiblesses

## I. Architecture

Notre projet est composé de 4 modules :

1. **Mutation** qui correspond au module disposant des mutations implémentées
2. **Sample** qui correspond au projet d'entrée, celui sur lequel sera appliqué les mutations
3. **xmlAnalyzer** qui correspond au module d'analyse des fichiers XML
4. **HTMLGenerated** qui correspond au module du rapport HTML (template etc...)

### I.1 module Mutation

Le module de mutation est le coeur du framework, c'est celui contenant toutes les mutateurs et les sélecteurs pouvant être
utilisés sur le module Sample

### I.2 module Sample

Le module Sample est le projet sur lequel sera appliqué notre framework de mutation. Actuellement, nous utilisons notre 
projet Cobblestone implémenté dans le cadre de la matière OGL en SI3.

### I.3 module XMLAnalyzer

Maven test nous génère un rapport au format XML détaillant le bon déroulement ou non de tout les tests appliqués sur le projet.
Le module **XMLAnalyzer** se charge donc de **générer du contenu HTML** après avoir **parsé** ces fichiers. La conception de
ce module a été faite de telle manière que chaque objet réponde à un contrat (une interface) définie. Celà nous permet donc
de changer certains composants sans compromettre la santé du projet.

![fenetreModale](./Ressources/images/CompoHTML.jpg)

Nous utilisons une interface IGenerator pour le Generateur de sortes que l'on puisse générer de l'AngularJS (par exemple)
sans soucis. L'interface IParse définit que les objets qui transite entre le parseur et le Generateur sont des list d'objets
de TestReport. TestReport est notre propre structure de données contenant toutes les informations pertinente pour la génération
d'un rapport. G

### I.4 module HTMLGenerated

Le but du rapport HTML étant de proposer des informations pertinentes sur le projet passé en entrée, nous avions du
réfléchir à certains points tels que l'ergonomie et les données exposées. 

Pour obtenir une Interface Homme Machine (IHM) fonctionnelle et tout de même plaisante nous avons utilisé le framework
Bootstrap pour structurer notre page. Ce framework nous a ainsi permis de gagner concernant le positionnement des objets
puisqu'il propose déjà une grille de 12 colonnes. Combiné aux graphes de HighChart, cela nous donne le rendu que nous voulions.
De plus certaines fonctionnalités de Bootstrap nous aurons été très utiles d'un point de vue **ergonomie**

#### I.4.A Les fenêtres modales

Lorsque l'on clique sur une des lignes du tableau récapitulatif des mutants, une fenêtre modale apparaît
pour nous donner plus d'informations sur le mutant en question. 

![fenetreModale](./Ressources/images/fenetreModale.png)

Ainsi tout se déroule dans une seule et même fenêtre sans pour autant rendre la navigation désagréable. La navigation entre
les mutants est facilitée.

#### I.4.B HighChart

Concernant les graphes choisis, nous avons optés pour un graphe "pie" (camembert) pour avoir un aperçu immediat du nombre
de mutants tués et du nombre de mutants ne l'ayant pas été. 
L'efficacité des tests quant à eux seront affichés par le biais d'un graphe "Column with drilldown". Ainsi nous avons
affiché en premier les classes de tests puis lorsque l'on clique sur la colonne d'une classe de tests, les tests contenus
s'afficheront eux aussi dans ce meme graphe ("Drilldown").

