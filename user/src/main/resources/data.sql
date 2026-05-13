-- ======================================================================================
-- CE FICHIER SERT À L'INITIALISATION DES COMPTES UTILISATEURS (SEEDING)
-- ======================================================================================

-- 1. NETTOYAGE PRÉALABLE
-- On vide la table 'users' pour repartir sur une base propre à chaque démarrage.
-- Cela évite les erreurs de type "Doublon" si le script se lance deux fois.
DELETE FROM users;

-- 2. CRÉATION DU COMPTE ADMINISTRATEUR
-- Cet utilisateur aura tous les droits, notamment l'accès au menu "Users".
INSERT INTO users (fullname, username, password, role)
VALUES (
           'Administrator', -- Nom complet affiché dans l'interface
           'admin',         -- Identifiant utilisé pour se connecter
           -- MOT DE PASSE HACHÉ (BCrypt) : On ne stocke jamais de texte en clair pour la sécurité.
           -- Ce code correspond au mot de passe que tu as généré avec ton programme Java.
           '$2a$10$rcV.wphTsQkv8g1e3Yc9i.nRLfr5i7FfShRq1jVXrYA07EiCwRNwO',
           'ADMIN'          -- Rôle utilisé par Spring Security pour autoriser l'accès aux pages sensibles
       );

-- 3. CRÉATION DU COMPTE UTILISATEUR STANDARD
-- Cet utilisateur pourra voir les données mais sera bloqué par la page 403 pour les menus Admin.
INSERT INTO users (fullname, username, password, role)
VALUES (
           'User',
           'user',
           -- Autre code haché BCrypt pour l'utilisateur standard.
           '$2a$10$aGIKxebq13ZpS7CKsfV6X.dm/DV116e7nrWeSsoiTRQy90zYyXo1e',
           'USER'           -- Rôle avec des permissions limitées
       );


/*
 À quoi sert-il ?
C'est le Générateur d'identifiants. Dans ton architecture, il est le point de départ de toute la Sécurité. Sans ce fichier, ton application démarrerait avec une base de données vide, et comme toutes les pages sont protégées par un mot de passe, tu resterais bloqué à la porte du Login.

Ce qu'il faut retenir :

    La Sécurité par le Hachage : Ce fichier illustre une règle d'or du développement pro : le développeur ne connaît pas les mots de passe des utilisateurs. On ne stocke que la "signature" (le hash) du mot de passe.

    L'initialisation du RBAC (Role-Based Access Control) : En créant un ADMIN et un USER dès le début, tu permets de tester immédiatement ton système de permissions (comme la page d'erreur 403 que nous avons créée).

    Persistance MySQL : Comme ton microservice User est maintenant branché sur MySQL, ces données sont injectées dans ton conteneur poseidon-db. Tu peux les voir et les modifier directement avec MySQL Workbench
 */