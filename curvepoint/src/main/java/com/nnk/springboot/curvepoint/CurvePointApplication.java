package com.nnk.springboot.curvepoint; // Déclare le package racine de ce microservice spécifique

// Importations des outils essentiels de Spring Boot
import org.springframework.boot.SpringApplication; // La classe utilitaire pour lancer l'application
import org.springframework.boot.autoconfigure.SpringBootApplication; // L'annotation de configuration automatique

@SpringBootApplication // L'annotation la plus importante. Elle active trois fonctions clés :
// 1. @EnableAutoConfiguration : Spring configure tout seul le projet (Web, JPA, H2) selon le pom.xml.
// 2. @ComponentScan : Spring cherche les @Controllers et @Repositories dans tout ce dossier "curvepoint".
// 3. @Configuration : Permet de définir des réglages personnalisés si besoin.
public class CurvePointApplication {

    public static void main(String[] args) {
        // C'est le point d'entrée du programme Java.
        // Cette ligne déclenche tout le processus :
        // - Elle démarre le serveur web interne (Tomcat) sur le port 8082.
        // - Elle initialise la connexion à la base de données H2.
        // - Elle enregistre le service dans l'annuaire Consul.
        SpringApplication.run(CurvePointApplication.class, args);
    }
}

/*
À quoi sert-il ?
C'est la Bougie d'allumage du microservice CurvePoint. Dans une architecture microservices, chaque service possède son propre "moteur" indépendant. Ce fichier est celui qui permet à la brique "Courbes de taux" de démarrer toute seule, sans dépendre du lancement des autres.

Ce qu'il faut retenir :

    Indépendance : C'est ce fichier qui fait que curvepoint est un microservice et non une simple bibliothèque. Il possède sa propre méthode main.

    Serveur Web Embarqué : Au lancement, Spring Boot crée un serveur web complet (Tomcat) uniquement pour ce service.

    Port 8082 : En lançant ce fichier, Java va lire ton application.properties et ouvrir la porte 8082 de ton ordinateur (ou du conteneur Docker) pour écouter les demandes.
 */