-- ======================================================================================
-- CE FICHIER SERT À L'INITIALISATION DES DONNÉES DU MICROSERVICE RATING
-- ======================================================================================

-- Note : Ce script est lu automatiquement par Spring au démarrage grâce au réglage 
-- "spring.sql.init.mode=always" dans ton fichier application.properties.

-- --- PREMIÈRE LIGNE DE NOTATION ---
-- On insère les notes de confiance maximales (Triple A) des trois grandes agences.
INSERT INTO rating (moodys_rating, sandp_rating, fitch_rating, order_number)
VALUES (
           'Aaa', -- Valeur pour l'agence Moody's
           'AAA', -- Valeur pour l'agence Standard & Poor's
           'AAA', -- Valeur pour l'agence Fitch
           1      -- Chiffre utilisé pour trier les notes (1 = priorité haute)
       );

-- --- DEUXIÈME LIGNE DE NOTATION ---
-- On insère la catégorie de note juste en dessous (Double A plus).
INSERT INTO rating (moodys_rating, sandp_rating, fitch_rating, order_number)
VALUES (
           'Aa1',
           'AA+',
           'AA+',
           2      -- Position numéro 2 dans le classement
       );

-- ======================================================================================
-- RAPPEL : Les noms de colonnes (ex: moodys_rating) utilisent des tirets bas (snake_case)
-- pour correspondre aux variables Java (ex: moodysRating) qui utilisent des majuscules.
-- ======================================================================================

/*
 À quoi sert-il ?
C'est le Générateur de données de départ. Il permet d'avoir des exemples concrets de notations financières dès que tu ouvres ton navigateur sur le port 8080. Sans lui, ton tableau serait vide et tu ne pourrais pas vérifier si ton code Java affiche bien les informations.

Ce qu'il faut retenir :

    Données Métier : Ce fichier contient des données réelles du monde de la finance (Moody's, S&P, Fitch). C'est ce qui donne du sens à l'application "Poseidon".

    Indépendance : Comme c'est une architecture microservices, ce fichier ne contient que des données de notation. Il est totalement séparé des fichiers SQL de "Trade" ou de "User".

    Fiabilité : En nettoyant et en remplissant la base à chaque démarrage, tu es sûr de toujours tester ton application dans les mêmes conditions, ce qui évite les bugs imprévus.
 */