# Objectif du fichier Report

Donner une analyse critique de notre travail au travers de son architecture (Dev & Ops) en montrant ses forces et ses faiblesses

## I. Architecture

Notre projet est composé de 4 modules :
1. **Mutation** qui correspond au module disposant des mutations implémentées
2. **Sample** qui correspond au projet d'entrée, celui sur lequel sera appliqué les mutations
3. **xmlAnalyzer** qui correspond au module d'analyse des fichiers XML
4. **HTMLGenerated** qui correspond au module du rapport HTML (template etc...)

### I.1 module Mutation

### I.2 module Sample

### I.3 module XMLAnalyzer

### I.4 module HTMLGenerated

Le but du rapport HTML étant de proposer des informations pertinentes sur le projet passé en entrée, nous avions du
réfléchir à certains points tels que l'ergonomie et les données exposées. 

Pour obtenir une Interface Homme Machine (IHM) fonctionnelle et tout de même plaisante nous avons utilisé le framework
Bootstrap pour structurer notre page. Ce framework nous a ainsi permis de gagner concernant le positionnement des objets
puisqu'il propose déjà une grille de 12 colonnes. De plus certaines nous aurons été très utiles d'un point de vue **ergonomie**

#### Les fenêtres modales

Lorsque l'on clique sur une des lignes du tableau récapitulatif des mutants, une fenêtre modale apparaît
pour nous donner plus d'informations sur le mutant en question. 

![fenetreModale](./Ressources/images/fenetreModale.png)

Ainsi tout se déroule dans une seule et même fenêtre sans pour autant rendre la navigation désagréable. La navigation entre
les mutants est facilitée.