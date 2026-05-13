package com.nnk.springboot.trade.repositories; // Déclare l'emplacement du fichier dans le projet

// Importations des composants Spring Data JPA
import com.nnk.springboot.trade.domain.Trade; // Importe l'Entité Trade (le modèle de données)
import org.springframework.data.jpa.repository.JpaRepository; // Importe l'interface parente qui contient toute la logique SQL
import org.springframework.stereotype.Repository; // Annotation pour marquer cette interface comme un composant d'accès aux données

@Repository // Dit à Spring : "Ceci est un composant DAO (Data Access Object)".
// Il permet à Spring de traduire les erreurs SQL complexes en exceptions Java plus lisibles pour le développeur.
public interface TradeRepository extends JpaRepository<Trade, Integer> {

    /*
       LA PUISSANCE DE SPRING DATA JPA :
       En héritant de JpaRepository<Trade, Integer>, tu n'as PAS BESOIN d'écrire de code SQL.
       Spring Boot va générer automatiquement les implémentations pour :

       1. .save(trade)     -> Génère un INSERT (création) ou un UPDATE (modification) SQL.
       2. .findAll()       -> Génère un SELECT * FROM trade.
       3. .findById(id)    -> Génère un SELECT * FROM trade WHERE trade_id = ?.
       4. .deleteById(id)  -> Génère un DELETE FROM trade WHERE trade_id = ?.

       Le premier paramètre <Trade> désigne la classe (la table) à gérer.
       Le deuxième paramètre <Integer> désigne le type de la clé primaire (@Id).
    */
}
/*
À quoi sert-il ?
C'est la Passerelle SQL automatique du microservice Trade. C'est le tunnel qui permet à ton code Java d'enregistrer, lire ou supprimer des transactions financières dans ta base de données (H2 ou MySQL).

Ce qu'il faut retenir :

    Gain de productivité : C'est le cœur du développement moderne. Au lieu d'écrire des lignes de code SQL compliquées et risquées, tu appelles juste des méthodes Java simples.

    Abstraction : Ce fichier cache la complexité de la base de données. Que tu utilises MySQL, PostgreSQL ou Oracle, ce fichier ne changera jamais.

    Isolation (Microservices) : Ce repository ne s'occupe que de la table trade. Dans ton architecture, il est physiquement impossible pour ce fichier d'aller modifier un utilisateur ou une courbe de taux. Cela garantit que les données financières sont bien isolées.
 */