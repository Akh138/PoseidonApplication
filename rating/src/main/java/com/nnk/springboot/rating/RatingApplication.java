package com.nnk.springboot.rating; // Déclare le package racine du microservice Rating

// Importations des outils de base de Spring Boot
import org.springframework.boot.SpringApplication; // Classe qui fournit la méthode de lancement
import org.springframework.boot.autoconfigure.SpringBootApplication; // L'annotation qui active toute l'intelligence de Spring

@SpringBootApplication // C'est l'annotation "fondation" de ton microservice. Elle active :
// 1. @EnableAutoConfiguration : Spring configure automatiquement ton serveur web, JPA, etc.
// 2. @ComponentScan : Spring cherche les @Controllers et @Repositories dans ce dossier et ses sous-dossiers.
// 3. @Configuration : Permet d'ajouter des réglages Java personnalisés.
public class RatingApplication {

    public static void main(String[] args) {
        // C'est le point d'entrée du programme quand tu lances le conteneur Docker.
        // Cette ligne exécute les actions suivantes :
        // - Elle lance un serveur web interne (Tomcat) sur le port 8083.
        // - Elle crée le lien avec ta base de données (H2 ou MySQL).
        // - Elle enregistre le service dans l'annuaire Consul (Discovery).
        // - Elle télécharge la configuration depuis le Config Server.
        SpringApplication.run(RatingApplication.class, args);
    }
}
/*
À quoi sert-il ?
C'est la Clé de contact du microservice Rating. C'est l'un des 8 "moteurs" indépendants de ton usine Poseidon. Sans ce fichier, le microservice ne peut pas s'allumer ni écouter les demandes qui arrivent sur le port 8083.

Ce qu'il faut retenir :

    Indépendance : Ce fichier permet au service rating de vivre sa propre vie. Tu peux le démarrer ou l'arrêter sans que cela n'impacte le service trade ou bidlist.

    Orchestration : Au moment où cette application démarre, elle va immédiatement lire ton fichier application.properties pour savoir comment rejoindre le reste de ton architecture (Consul, Config Server).

    Simplicité : En seulement quelques lignes, Spring Boot s'occupe de toute la complexité technique (démarrage du serveur, gestion de la mémoire, etc.), te laissant te concentrer uniquement sur le code métier.
 */