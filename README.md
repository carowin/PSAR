# PSAR 2020
# Observation de la variabilité dans un système réparti : collecte de traces sur la plate-forme PlanetLab

**L’objectif de ce projet est d’observer un système réparti réel.
Il s’agit d’enregistrer les variations de latences entre un ensemble de machines, 
les pertes message ainsi que les crashs éventuels de machines.**

>PlanetLab est une plate-forme de recherche internationale regroupant 1353 machines 
>réparties sur 717 sites à travers le monde. Cette plate-forme est un outil pour tester 
>des algorithmes distribués à grande échelle.

** Ce qu'on doit faire: **
Il s’agit de concevoir un programme réparti où chaque machine envoie des messages de vie
(heartbeat) aux autres à intervalle de temps régulier. Toutes les informations (numéro de messages
heartbeat, heures d’émission et de réception, nombre de sauts …) seront stockées dans un ensemble
de fichiers log locaux à chaque machine qui seront ensuite assemblés.
A partir de ces traces des algorithmes de détections de fautes pourront être testés. Les détecteurs
sont une brique de base en algorithmique répartie. Ils fournissent à chaque processus des
informations sur les processus défaillants. Ils doivent à terme détecter tous les processus
défaillants. Ils sont non fiables dans le sens où ils peuvent commettre des erreurs c'est-à-dire
faussement suspecter un processus correct.

[Site PlanetLab Europe](https://www.planet-lab.eu/)
