package com.nnk.springboot.configserver; // Déclare l'emplacement du fichier. Le package doit correspondre à la structure des dossiers pour que Java le trouve.

// Importations des outils de base de Spring Boot
import org.springframework.boot.SpringApplication; // La classe qui contient la méthode pour lancer l'application
import org.springframework.boot.autoconfigure.SpringBootApplication; // L'annotation qui active la configuration automatique du projet

// Importation spécifique à Spring Cloud
import org.springframework.cloud.config.server.EnableConfigServer; // L'outil qui transforme cette application en un serveur de configuration

@SpringBootApplication // Active trois fonctionnalités :
// 1. @Configuration : Permet d'enregistrer des composants (beans).
// 2. @EnableAutoConfiguration : Spring configure tout seul le serveur web (Tomcat) et les outils Cloud.
// 3. @ComponentScan : Spring cherche les autres composants dans ce dossier.

@EnableConfigServer // <--- C'est l'annotation la plus importante de ce fichier !
// Elle active les super-pouvoirs de "Config Server".
// C'est grâce à elle que ce microservice devient capable de lire tes fichiers .properties sur GitHub
// et de les redistribuer aux autres microservices (BidList, Trade, etc.) quand ils démarrent.

public class ConfigServerApplication {

    public static void main(String[] args) {
        // C'est le point d'entrée du programme.
        // Quand tu lances ce service, cette ligne démarre tout le moteur Spring Boot.
        // Le serveur va se mettre à écouter sur le port 8071 (défini dans ton application.properties).
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}

/*
À quoi sert-il ?
C'est le Bibliothécaire de ton usine. Son seul travail est de centraliser tous les réglages de l'architecture. Au lieu que chaque microservice garde ses secrets (mots de passe, URLs, messages) dans son propre code, ils sont tous stockés sur GitHub et ce serveur les distribue à la demande.

Ce qu'il faut retenir :

    @EnableConfigServer : C'est la ligne qui fait tout. Sans elle, c'est une application Spring Boot vide. Avec elle, c'est un serveur de configuration intelligent.

    Rôle de Centralisation : C'est le premier service que tu dois lancer (d'où le healthcheck dans ton Docker Compose). Si le bibliothécaire est absent, les autres services ne savent pas comment travailler et ils s'arrêtent.

    Lien avec Git : Ce fichier travaille main dans la main avec ton fichier application.properties qui contient l'adresse de ton repo GitHub Poseidon-config.
 */