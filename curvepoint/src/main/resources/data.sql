-- ======================================================================================
-- CE FICHIER SERT À L'INITIALISATION DES DONNÉES DU MICROSERVICE CURVEPOINT
-- ======================================================================================

-- Note : Dans ce microservice, nous n'utilisons que la table "curvepoint".
-- Comme nous l'avons vu précédemment, Hibernate crée la table automatiquement
-- grâce à l'annotation @Entity dans le code Java.

-- --- INSERTION DES POINTS DE COURBE ---

-- On insère le premier point de la courbe n°10 (Terme: 1 an, Valeur: 10.5%)
-- Note technique : on utilise "curve_value" car le mot "value" est réservé en SQL.
INSERT INTO curvepoint (curve_id, term, curve_value) VALUES (10, 1.0, 10.5);

-- On insère le point de la courbe n°20 (Terme: 2 ans, Valeur: 20.8%)
INSERT INTO curvepoint (curve_id, term, curve_value) VALUES (20, 2.0, 20.8);

-- On insère le point de la courbe n°30 (Terme: 3 ans, Valeur: 35.5%)
INSERT INTO curvepoint (curve_id, term, curve_value) VALUES (30, 3.0, 35.5);

-- On insère le point de la courbe n°40 (Terme: 4 ans, Valeur: 42.0%)
INSERT INTO curvepoint (curve_id, term, curve_value) VALUES (40, 4.0, 42.0);

-- ======================================================================================
-- RAPPEL : Ces données sont stockées en mémoire RAM (H2) ou dans MySQL.
-- Elles permettent à ton tableau sur le port 8080 d'afficher des chiffres dès le départ.
-- ======================================================================================

/*
 À quoi sert-il ?
C'est le Seeder (le semeur) du microservice. Son rôle est de "peupler" la base de données avec des exemples réels. Sans ce fichier, ton tableau dans l'interface serait vide au premier lancement, ce qui rendrait les tests plus difficiles.

Ce qu'il faut retenir :

    Snake Case : Remarque que Java transforme ton nom de variable curveId en curve_id dans le SQL. C'est la convention standard que Spring Boot applique automatiquement entre le code Java et la base de données.

    Indépendance : Ce fichier ne contient que des données financières (points de courbes). Il ne sait pas qu'il existe des "Users" ou des "Trades". C'est le principe du Découpage par Domaine en microservices.

    Résolution de conflit : C'est ici que l'on voit l'importance d'avoir renommé la colonne en curve_value. Si on avait écrit value, la base de données aurait renvoyé une erreur de syntaxe.
 */