package com.nnk.springboot.user; // Déclare le package racine de ce microservice spécifique (User)

// Importations des outils fondamentaux de Spring Boot pour le démarrage
import org.springframework.boot.SpringApplication; // La classe utilitaire qui contient la logique de lancement
import org.springframework.boot.autoconfigure.SpringBootApplication; // L'annotation qui active toute la puissance de Spring

@SpringBootApplication // L'annotation "pilote" de ton microservice. Elle active trois fonctions automatiques :
// 1. @EnableAutoConfiguration : Spring configure tout seul l'application selon les bibliothèques présentes (Web, JPA, MySQL/H2).
// 2. @ComponentScan : Spring cherche automatiquement les @Controllers, @Services et @Repositories dans ce dossier et ses sous-dossiers.
// 3. @Configuration : Permet d'enregistrer des réglages personnalisés (beans) si nécessaire.
public class UserApplication {

    public static void main(String[] args) {
        // C'est la méthode principale (le point d'entrée) exécutée par Java lors du lancement du conteneur Docker.
        // Cette ligne déclenche tout le cycle de vie du microservice User :
        // - Elle démarre le serveur web interne (Tomcat) sur le port 8086.
        // - Elle établit la connexion à la base de données (H2 ou MySQL).
        // - Elle enregistre le service dans l'annuaire Consul pour qu'il soit trouvable par la Gateway.
        // - Elle récupère la configuration centralisée sur GitHub via le Config Server.
        SpringApplication.run(UserApplication.class, args);
    }
}
/*
À quoi sert-il ?
C'est la Clé de contact du microservice User. Dans ton architecture Poseidon, ce fichier est vital car il lance la brique responsable de la Sécurité et de l'Identité. Sans lui, personne ne pourrait se connecter à l'application car la base des utilisateurs ne serait pas accessible.

Ce qu'il faut retenir :

    Indépendance : Comme pour les autres services, ce fichier permet au service user d'exister de façon autonome. Tu peux le redémarrer sans toucher aux services trade ou bidlist.

    Démarrage du Serveur : Dès le lancement, Spring Boot ouvre le port 8086 pour écouter les demandes du Portail UI (quand quelqu'un tape son mot de passe au login par exemple).

    Lien avec l'écosystème : C'est ici que Java commence à lire ton fichier application.properties pour savoir comment rejoindre Consul et le Config Server
 */