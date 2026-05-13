package com.nnk.springboot.curvepoint.repositories; // Déclare l'emplacement du fichier dans le microservice curvepoint

// Importations des outils Spring Data JPA
import com.nnk.springboot.curvepoint.domain.CurvePoint; // Importe l'Entité qu'on va manipuler (le modèle CurvePoint)
import org.springframework.data.jpa.repository.JpaRepository; // Importe l'interface mère qui contient toutes les méthodes SQL
import org.springframework.stereotype.Repository; // Annotation pour que Spring gère cette classe comme un accès aux données

@Repository // Dit à Spring : "Ceci est un composant d'accès à la base de données (DAO)"
// Il permet à Spring de détecter cette interface et de l'injecter dans le Controller via @Autowired.
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

    /*
       PUISSANCE DE SPRING DATA JPA :
       Comme pour BidList, en héritant de JpaRepository<CurvePoint, Integer>,
       Spring génère automatiquement à ta place le code pour :
       - .save(point)     -> Sauvegarder ou mettre à jour un point de courbe
       - .findAll()       -> Lister tous les points
       - .findById(id)    -> Chercher un point spécifique
       - .deleteById(id)  -> Supprimer un point

       Le premier paramètre <CurvePoint> désigne la classe à gérer.
       Le deuxième paramètre <Integer> désigne le type de la clé primaire (@Id).
    */
}

/*
À quoi sert-il ?
C'est la Passerelle SQL automatique pour les courbes de taux. C'est l'outil qui permet au microservice d'enregistrer physiquement les points de courbe dans ta base de données H2 ou MySQL.

Ce qu'il faut retenir :

    Gain de temps : Tu n'écris pas de requêtes INSERT INTO... ou UPDATE.... Tu appelles juste des méthodes Java, et Spring Data JPA s'occupe de traduire ça en SQL correct pour ta base de données.

    Abstraction : Si tu décides de changer de base de données (passer de H2 à PostgreSQL ou Oracle par exemple), tu n'as pas besoin de toucher à ce fichier. Il est universel.

    Isolation : Ce repository ne s'occupe que de la table curvepoint. Il ne peut pas voir les tables des autres microservices (comme trade), ce qui garantit l'étanchéité de ton architecture.
 */