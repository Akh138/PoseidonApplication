-- ======================================================================================
-- CE FICHIER SERT À L'INITIALISATION DES DONNÉES DU MICROSERVICE RULENAME
-- ======================================================================================

-- Note : Ce script est lu automatiquement par Spring au démarrage grâce au réglage 
-- "spring.sql.init.mode=always" que nous avons mis dans ton fichier properties.

-- --- INSERTION D'UNE RÈGLE MÉTIER ---
-- On insère une règle d'exemple avec tous ses paramètres techniques.
-- Ces données apparaîtront immédiatement dans ton tableau sur le port 8080.

INSERT INTO rulename (name, description, json, template, sql_str, sql_part)
VALUES (
           'Rule 1',        -- Valeur pour la colonne 'name'
           'Description 1', -- Valeur pour la colonne 'description'
           'json1',         -- Valeur pour la colonne 'json'
           'template1',     -- Valeur pour la colonne 'template'
           'sql1',          -- Valeur pour la colonne 'sql_str' (mappée depuis sqlStr en Java)
           'part1'          -- Valeur pour la colonne 'sql_part' (mappée depuis sqlPart en Java)
       );

-- ======================================================================================
-- RAPPEL : Hibernate transforme automatiquement les noms Java (ex: sqlStr) 
-- en noms SQL (ex: sql_str) en ajoutant des tirets bas devant les majuscules.
-- ======================================================================================

/*
 À quoi sert-il ?
C'est le "Semeur" (Seeder) de données. Son but est de remplir la base de données avec des exemples concrets de règles de gestion financière. Cela permet de tester l'affichage, la modification et la suppression dès que les conteneurs Docker sont allumés.

Ce qu'il faut retenir :

    Données de Test : Dans un projet professionnel, ce fichier permet de s'assurer que tous les développeurs de l'équipe travaillent avec les mêmes données de base.

    Isolation des Services : Ce fichier ne contient que des informations sur les règles (rulename). Il est totalement séparé des données des autres services (comme user ou curvepoint), respectant ainsi l'architecture microservices.

    Mappage des colonnes : Remarque que tu écris sql_str dans le SQL alors que ta variable Java s'appelle sqlStr. C'est la règle par défaut de Spring Boot pour faire le lien entre le code et la base de données.
 */