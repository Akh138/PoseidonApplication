-- ======================================================================================
-- CE FICHIER SERT À L'INITIALISATION DES DONNÉES DU MICROSERVICE TRADE
-- ======================================================================================

-- Note : Ce script est exécuté automatiquement par Spring Boot au démarrage.
-- Il permet de "peupler" (remplir) la table "trade" avec des exemples concrets.

-- --- INSERTION D'UNE TRANSACTION DE TEST ---
-- On insère une ligne d'exemple pour vérifier l'affichage dans le tableau de l'interface UI.
INSERT INTO trade (account, type, buy_quantity)
VALUES (
           'Trade Account', -- Valeur pour la colonne 'account' (Compte client)
           'Type C',        -- Valeur pour la colonne 'type' (Catégorie de transaction)
           500.0            -- Valeur pour la colonne 'buy_quantity' (Quantité achetée)
       );

-- ======================================================================================
-- RAPPEL : Comme nous sommes dans une architecture microservices, ce fichier ne contient 
-- QUE les données concernant les transactions. Il ignore les utilisateurs ou les courbes.
-- ======================================================================================

/*
 À quoi sert-il ?
C'est le Seeder (Semeur) de données pour les transactions. Son rôle est de s'assurer que la base de données n'est pas vide lors du premier lancement de l'application. C'est ce qui permet à ton tableau sur le port 8080 d'afficher une ligne "Trade Account" dès que tu te connectes.

Ce qu'il faut retenir :

    Données minimales : Remarque que nous n'avons rempli que 3 colonnes (account, type, buy_quantity). Les autres colonnes de la table (comme buyPrice ou trader) resteront à NULL (vides) pour cet exemple, ce qui est autorisé par la base de données.

    Mappage Java/SQL : Le nom de la colonne buy_quantity dans ce fichier correspond à ta variable Java buyQuantity. Spring Boot fait la conversion automatiquement (il ajoute un tiret bas avant chaque majuscule).

    Utilité pour le Portfolio : Pour un recruteur, ce fichier montre que tu sais préparer un environnement de test automatisé. N'importe qui peut lancer ton projet et voir des données réelles sans avoir à les créer à la main.
 */