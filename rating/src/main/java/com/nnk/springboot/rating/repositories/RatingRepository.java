package com.nnk.springboot.rating.repositories; // Déclare l'emplacement du fichier dans le projet

// Importations des composants Spring Data JPA
import com.nnk.springboot.rating.domain.Rating; // Importe l'Entité Rating (le modèle de données)
import org.springframework.data.jpa.repository.JpaRepository; // Importe l'interface parente qui contient toute la logique SQL
import org.springframework.stereotype.Repository; // Annotation pour marquer cette interface comme un composant d'accès aux données

@Repository // Dit à Spring : "Ceci est un composant DAO (Data Access Object)".
// Il permet à Spring de traduire les erreurs SQL complexes en exceptions Java plus lisibles.
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    /*
       LA PUISSANCE DU REPOSITORY :
       En héritant de JpaRepository<Rating, Integer>, tu n'as PAS BESOIN d'écrire de code.
       Spring Boot va générer automatiquement les implémentations pour :

       1. .save(rating)   -> Génère un INSERT ou un UPDATE SQL.
       2. .findAll()      -> Génère un SELECT * FROM rating.
       3. .findById(id)   -> Génère un SELECT * WHERE id = ?.
       4. .deleteById(id) -> Génère un DELETE FROM rating WHERE id = ?.

       <Rating, Integer> précise :
       - On gère des objets de type "Rating".
       - La clé primaire (@Id) définie dans la classe Rating est de type "Integer".
    */
}
/*
À quoi sert-il ?
C'est le Moteur SQL invisible du microservice Rating. Il sert d'intermédiaire entre ton code Java (ton Controller) et ta base de données (H2 ou MySQL).

Ce qu'il faut retenir :

    Productivité : C'est le coeur du concept "Spring Data JPA". On définit une interface, et Spring s'occupe de toute la plomberie SQL à ta place.

    Séparation des dossiers : Ce fichier reste dans le dossier repositories. Cela permet de bien séparer la logique du Web (Controllers), la logique des données (Models/Domain) et l'accès technique à la base (Repositories).

    Extensibilité : Si plus tard tu as besoin d'une recherche complexe (ex: chercher une note par son nom Moody's), c'est ici que tu ajouterais une méthode personnalisée.
 */