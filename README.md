L'application "mes_chums" est un manager de contacts qui associe des adresses à tous les contacts et te permet de calculer une liste de contacts du plus proche au plus loin d'un contact spécifique

Sur l'application, tu peux:
1. Ajouter des contacts avec une ou plusieures adresses et coordonés associés.
2. Modifier les adresses d'un contact.
3. Supprimer un contact ajouté.
4. Chercher un contact parmis tout les autres.
5. Recevoir une liste complète de tout les contacts ajoutés.
6. Recevoir une liste de contacts du plus proche au plus loin en relation à un contact spécifié.

Listez les différentes technologies utilisées :

mes_chums utilise Maven, SQLite JDBC, Jackson, JUnit et HicariCP.

L'application est programmé en Java et utilise Git et GitHub pour la gestion de projet.
Les dépendance du projet sont le Oracle JDKe et Apache Maven.
L'application crée un serveur sur le port 8080 par défault et utilise SQLite pour la base de donnés
L'application utilise des requêtes GET, POST, DELETE et PATCH.

Concepts Clés du Projet
1. Endpoints REST :
   Listez et décrivez les endpoints implémentés.
   Incluez des exemples de requêtes, en détaillant l'information à fournir.

**Endpoints REST**
La pluparts des requêtes peuvent être faits avec un endpoint ``"/chums/mes_chums"``, sauf pour la requête pour changer un favoris, qui utilise ``"/chums/mes_chums/favoris"`` et le calcul à distance, qui utilise ``"/chums/mes_chums/proximite"``

Exemples:
Requête GET
````GET localhost:8080/chums/mes_chums````
Cette requête retournera tout les contacts dans l'application
Requête POST
````
"POST localhost:8080/chums/mes_chums"
body: 
{
    "nom":"examplename",
    "prenom":"examplename",
    "isFavoris":false/true,
    "adresses":{
      "rue":"88e ave",
      "ville":"Montreal",
      "codePostal":"T5HO2L",
      "pays":"Canada",
      "coordonnees":{
      "longitude":30,
      "laitude":-80,
      }
    },
}
````
2. Architecture Logicielle :
   Expliquez l'architecture en couches de votre application.
   Précisez les responsabilités de chaque couche, leur importance dans le projet. Accompagnez vos explications d'exemple tirés du code.
````
La structure de l'application: 
projet
|-Controller
| |-ChumsController.java
|-DAO
| |-ContactDAO.java
| |-GenericDAO.java
|-DTO
| |-AdresseDTO.java
| |-ContactDTO.java
| |-CoordoneesDTO.java
|-Mapper
| |-ContactMapper.java
|-Modele
| |-Adresse.java
| |-Contact.java
| |-Coordonnees.java
|-Service
| |-CacheService.java
| |-CalculateurDistance.java
|-ChumsServer
````
3. Mécanisme de Cache :
   Décrivez comment votre système de cache est mis en place, et comment les données sont maintenues à jour.

Plus précisément, décrivez au mieux l'impact sur la Cache, de marquer un contact comme favoris ou d'enlever cette marque.
Un diagramme de séquences serait idéal pour cette partie.

4. Utilisation de Géolocalisation :
   Décrivez votre utilisation de l'API de géocodage pour obtenir des coordonnées GPS.

Incluez des blocs de code commentés décrivant l'envoi de vos requêtes et le traitement des réponses.

5. Injection de Dépendances
   Si vous pensez avoir mis en place ce concept, décrivez comment l'instanciation de certains des objets permet de rendre votre application plus flexible et maintenable.





