-- ======================================================================================
-- CE FICHIER SERT À REMPLIR AUTOMATIQUEMENT LA BASE DE DONNÉES AU DÉMARRAGE (SEEDING)
-- ======================================================================================

-- Note : Dans une architecture microservices, ce fichier ne contient QUE les données
-- concernant "BidList". On a supprimé les INSERT des autres services (Trade, User, etc.)
-- pour respecter le principe d'isolation : une base de données par service.

-- --- PREMIÈRE LIGNE DE TEST ---
-- On insère une offre (Bid) avec toutes ses informations financières et administratives.
INSERT INTO bidlist (account, type, bid_quantity, ask_quantity, bid, ask, benchmark, commentary, security, status, trader, book, creation_name, side)
VALUES (
           'Account Test 1', -- Le nom du compte
           'Type A',         -- Le type d'offre
           100.0,            -- Quantité proposée
           110.0,            -- Quantité demandée
           10.5,             -- Prix proposé
           12.0,             -- Prix demandé
           'Benchmark 1',    -- Indicateur de référence
           'No comment',     -- Commentaire libre
           'Sec 1',          -- Nom du titre (Security)
           'Open',           -- Statut : Ouvert
           'Trader Joe',     -- Nom du trader responsable
           'Book A',         -- Nom du carnet d'ordres
           'Admin',          -- Créateur de l'entrée
           'Buy'             -- Côté de la transaction (Achat)
       );

-- --- DEUXIÈME LIGNE DE TEST ---
-- On insère une deuxième offre avec des valeurs différentes pour tester l'affichage en liste.
INSERT INTO bidlist (account, type, bid_quantity, ask_quantity, bid, ask, benchmark, commentary, security, status, trader, book, creation_name, side)
VALUES (
           'Account Test 2',
           'Type B',
           200.0,
           210.0,
           20.5,
           22.0,
           'Benchmark 2',
           'Priority',
           'Sec 2',
           'Closed',
           'Trader Mike',
           'Book B',
           'Admin',
           'Sell'
       );


/*
 À quoi sert-il ?
C'est le fichier d'Initialisation des données. Il permet de ne pas avoir une application vide au tout premier lancement. C'est très utile pour les tests et pour faire des démonstrations.

Ce qu'il faut retenir :

    Indépendance : C'est la preuve que ton microservice est autonome. Il ne contient que ses propres données et ne dépend d'aucune autre table pour fonctionner.

    Automatisation : Grâce au réglage spring.sql.init.mode=always dans ton fichier properties, ce script est lu à chaque fois que tu lances Docker, garantissant que tes données de test sont toujours là.

    Standard SQL : Les noms des colonnes (ex: bid_quantity) sont écrits en snake_case (avec des tirets bas), car c'est ainsi que Java transforme automatiquement tes noms de variables CamelCase (bidQuantity) pour la base de données.
 */