package com.nnk.springboot.gateway; // Déclare l'emplacement du fichier dans la structure du projet

// Importations des outils de démarrage de Spring Boot
import org.springframework.boot.SpringApplication; // La classe utilitaire pour lancer l'application
import org.springframework.boot.autoconfigure.SpringBootApplication; // L'annotation de configuration automatique

@SpringBootApplication // L'annotation magique qui active trois fonctions :
// 1. @Configuration : Permet d'enregistrer des composants système.
// 2. @EnableAutoConfiguration : Spring configure tout seul le moteur de routage (Gateway).
// 3. @ComponentScan : Spring cherche les autres composants (si besoin) dans ce dossier.
public class GatewayApplication {

    public static void main(String[] args) {
        // C'est le point d'entrée unique du microservice Gateway.
        // Cette ligne déclenche tout le processus :
        // - Elle démarre le moteur de routage intelligent (Spring Cloud Gateway).
        // - Elle ouvre le port 9090 (défini dans ton application.properties).
        // - Elle connecte la Gateway à Consul pour connaître les adresses des autres services.
        SpringApplication.run(GatewayApplication.class, args);
    }
}

/*
À quoi sert-il ?
C'est le Réceptionniste (ou le "Point d'entrée unique") de ton usine. Dans ton architecture, c'est le seul service que l'utilisateur appelle directement (via le port 9090). Son travail n'est pas de traiter des données, mais de rediriger les visiteurs vers le bon microservice.

Ce qu'il faut retenir :

    Porte d'entrée unique : C'est grâce à ce fichier que tu peux accéder à tout ton projet avec une seule adresse (http://localhost:9090).

    Sécurité physique : Comme c'est la seule porte ouverte vers l'extérieur, elle protège tes autres microservices qui restent "cachés" dans le réseau Docker.

    Lien avec l'Annuaire : Ce fichier lance un moteur qui "écoute" en permanence Consul. Dès que tu ajoutes un nouveau service (ex: trade), la Gateway le voit et sait comment lui envoyer du trafic sans que tu aies besoin de modifier ce code Java.
 */