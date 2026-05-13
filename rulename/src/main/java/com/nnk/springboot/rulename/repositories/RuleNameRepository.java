package com.nnk.springboot.rulename.repositories; // Déclare l'emplacement du fichier dans le microservice rulename

// Importations des outils Spring Data JPA
import com.nnk.springboot.rulename.domain.RuleName; // Importe l'Entité RuleName (le modèle de données)
import org.springframework.data.jpa.repository.JpaRepository; // Importe l'interface mère qui contient la logique SQL automatique
import org.springframework.stereotype.Repository; // Annotation pour marquer cette interface comme un composant d'accès aux données

@Repository // Dit à Spring : "Ceci est un composant DAO (Data Access Object)".
// Cette annotation permet à Spring de créer une instance de cette classe et de l'injecter là où on en a besoin.
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {

    /*
       LA PUISSANCE DE JPA REPOSITORY :
       En héritant de JpaRepository<RuleName, Integer>, Spring Boot génère
       automatiquement tout le code SQL pour les opérations de base (CRUD) :

       1. .save(rule)      -> Crée ou modifie une règle en base de données.
       2. .findAll()       -> Récupère la liste complète des règles.
       3. .findById(id)    -> Cherche une règle spécifique par son numéro d'ID.
       4. .deleteById(id)  -> Supprime une règle de la base de données.

       Les paramètres <RuleName, Integer> indiquent :
       - Que l'on gère la classe "RuleName".
       - Que son identifiant (@Id) est de type "Integer".
    */
}
/*
À quoi sert-il ?
C'est le Moteur SQL automatique du microservice RuleName. Il sert de traducteur : il transforme tes commandes Java en requêtes SQL que la base de données (H2 ou MySQL) peut comprendre.

Ce qu'il faut retenir :

    Gain de productivité : C'est le cœur du développement moderne avec Spring. Tu n'as pas besoin d'écrire une seule ligne de code SQL manuellement. Spring s'occupe de tout.

    Séparation propre : Ce fichier reste dans le dossier repositories. Cela permet de séparer la gestion technique de la base de données du reste de l'application.

    Facilité de maintenance : Si tu dois changer de base de données plus tard, tu n'as rien à modifier ici. L'interface reste la même quel que soit le système SQL utilisé.
 */