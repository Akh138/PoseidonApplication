package com.nnk.springboot.user.repositories; // Déclare l'emplacement du fichier dans le microservice "User"

// Importations des composants Spring Data JPA et utilitaires Java
import com.nnk.springboot.user.domain.User; // Importe le modèle User
import org.springframework.data.jpa.repository.JpaRepository; // Interface parente avec toutes les méthodes SQL de base
import org.springframework.stereotype.Repository; // Marqueur de composant Spring pour l'accès aux données

import java.util.Optional; // Conteneur qui peut ou non contenir une valeur (évite les erreurs si l'utilisateur n'existe pas)

@Repository // Dit à Spring : "Ceci est la porte d'accès à la table des utilisateurs"
public interface UserRepository extends JpaRepository<User, Integer> {

    /*
       MÉTHODE PERSONNALISÉE : LA DÉCOUVERTE PAR NOM D'UTILISATEUR
       Cette méthode est vitale pour ton système de sécurité (Login).

       Spring Data JPA est très intelligent : il voit le nom "findByUsername".
       Il comprend tout seul qu'il doit générer la requête SQL suivante :
       "SELECT * FROM users WHERE username = ?"
    */
    Optional<User> findByUsername(String username);

    /*
       RAPPEL : Comme tu hérites de JpaRepository<User, Integer>, tu as aussi :
       - .save()       -> Créer / Modifier un utilisateur
       - .findAll()    -> Lister tous les utilisateurs
       - .deleteById() -> Supprimer un compte
    */
}
/*
À quoi sert-il ?
C'est le Moteur de recherche des utilisateurs. En plus des fonctions classiques (créer, supprimer), ce fichier possède une fonction de recherche par nom. C'est l'outil que l'application utilise pour "vérifier l'identité" d'une personne qui essaie de se connecter.

Ce qu'il faut retenir :

    La puissance des "Query Methods" : pas écrit une seule ligne de code pour chercher dans la base de données. Le simple fait de nommer ta méthode findByUsername suffit à Spring pour créer le code SQL à ta place.

    L'utilisation d'Optional : Remarque le type de retour Optional<User>. C'est une protection. Si tu cherches un utilisateur qui n'existe pas, Java ne va pas "crasher", il va te renvoyer un objet vide que tu pourras gérer proprement dans ton Controller.

    Le lien avec la Sécurité : C'est ce repository qui fournit l'information finale au Portail UI pour dire si le mot de passe est correct ou non
 */