package com.nnk.springboot.ui.config; // Déclare l'emplacement du fichier dans le projet portail UI

// Importation de l'outil de hachage officiel de Spring Security
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * CETTE CLASSE EST UN UTILITAIRE DE DÉVELOPPEMENT.
 * Elle ne fait pas partie du fonctionnement du site, elle sert à l'administrateur
 * pour préparer les mots de passe avant de les mettre dans la base de données.
 */
public class BCryptGenerator {

    // Méthode principale permettant de lancer ce fichier comme un petit programme indépendant
    public static void main(String[] args) {

        // 1. On crée une instance de l'encodeur BCrypt.
        // C'est le même algorithme que celui utilisé par ton application pour vérifier les logins.
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // 2. On transforme le texte clair en "Hash" (code secret).
        // Ici, on transforme le mot "admin123" en une empreinte numérique unique.
        // Note : Le résultat sera différent à chaque exécution, c'est une sécurité de BCrypt (le salage).
        String result = encoder.encode("admin123");

        // 3. On affiche le résultat dans la console d'IntelliJ.
        // Ce code (commençant par $2a$10...) est celui que tu dois copier et coller
        // dans ton fichier data.sql ou dans ta base MySQL via Workbench.
        System.out.println("COPIE CE CODE : " + result);
    }
}
/*
À quoi sert-il ?
C'est ton Usine à clés. En sécurité informatique, on ne doit jamais connaître le mot de passe d'un utilisateur. On utilise donc ce programme pour transformer un mot de passe simple (comme "admin123") en un code indéchiffrable.

Ce qu'il faut retenir :

    L'algorithme BCrypt : C'est le standard mondial. Il est "lent" par conception pour empêcher les pirates de tester des millions de combinaisons par seconde.

    Le Hachage à sens unique : Une fois que tu as généré le code $2a$10..., il est techniquement impossible de revenir en arrière pour retrouver "admin123". C'est pour ça qu'on stocke ce code en base de données
 */