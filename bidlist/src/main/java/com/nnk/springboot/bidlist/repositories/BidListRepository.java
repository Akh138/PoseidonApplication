package com.nnk.springboot.bidlist.repositories; // Déclare l'emplacement du fichier dans le projet

// Importations des outils Spring Data JPA
import com.nnk.springboot.bidlist.domain.BidList; // Importe l'Entité qu'on va manipuler
import org.springframework.data.jpa.repository.JpaRepository; // Importe l'interface parente qui contient tout le SQL
import org.springframework.stereotype.Repository; // Annotation pour que Spring reconnaisse cette classe comme un accès aux données

@Repository // Dit à Spring : "Ceci est un composant d'accès à la base de données (DAO)"
public interface BidListRepository extends JpaRepository<BidList, Integer> {

    /*
       Magie de Spring Data JPA :
       En héritant de JpaRepository<BidList, Integer>, cette interface possède
       déjà toutes les méthodes SQL de base sans que tu n'aies à les écrire :
       - .save()   -> pour INSERT et UPDATE
       - .findAll() -> pour SELECT *
       - .findById() -> pour SELECT by ID
       - .deleteById() -> pour DELETE

       <BidList, Integer> signifie :
       1. On travaille sur la table "BidList"
       2. La clé primaire (ID) est de type "Integer"
    */
}

/*À quoi sert-il ?
C'est la Passerelle de données. Ce fichier fait le lien direct entre ton code Java et ton moteur de base de données (H2 ou MySQL).

Ce qu'il faut retenir :

    Zéro SQL à écrire : C'est la puissance de Spring Data JPA. Tu n'as pas besoin d'écrire des requêtes comme SELECT * FROM bidlist, Spring comprend tout seul ce que tu veux faire grâce aux méthodes héritées.

    L'interface : Remarque que c'est une interface et non une class. Tu ne crées pas l'implémentation toi-même, c'est Spring qui génère le code pour parler à la base de données au démarrage de l'application.

    L'annotation @Repository : Elle permet à Spring de "scanner" ce fichier et de l'injecter automatiquement (via @Autowired) dans ton Controller.
*/