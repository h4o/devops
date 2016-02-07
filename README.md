# DevOps 2 : Tests par mutation
	
## Présentation de l'équipe

Groupe DevOps : 2

- Alexandre Cazala : 4ème année - Dpt Sciences informatiques - Polytech Nice-Sophia
- Pierre Massanes : 4ème année - Dpt Sciences informatiques - Polytech Nice-Sophia
- Fabien Vicente : 4ème année - Dpt Sciences informatiques - Polytech Nice-Sophia

## Présentation du sujet

Dans le cadre de nos études en DevOps, nous devons réaliser un framework de tests par mutation. 
Ce framework permettra ainsi de pouvoir tester l'efficacité d'un banc de tests unitaires en Java.

Le framework prend donc en entrée des sources Java lui permettant donc de pouvoir s'appliquer sur n'importe quel programme.

Le nombre de mutants générés étant proportionnel à la taille du code passé en entrée, nous appliquerons certaines optimisations telles que :
- Coverage data : nous n'appliquerons que les tests concernant les mutations
- Parallel execution : Après configuration passée en paramètre du framework, le processus sera parrallelisé. 
- Selective mutation : Si l'option est demandée, nous n'appliquerons que les mutations qui sont généralement les plus intéressantes selon une moyenne basée sur projets réalistes communautaires. 
Ainsi le nombre de mutants générés sera plus faible mais ceux-ci seront les plus importants à observer.

Le rapport de l'execution de notre framework sera au format HTML.


