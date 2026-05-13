package com.nnk.springboot.bidlist; // Déclare le package racine de ce microservice

// Importations des outils de démarrage de Spring Boot
import org.springframework.boot.SpringApplication; // La classe qui lance l'application
import org.springframework.boot.autoconfigure.SpringBootApplication; // L'annotation qui active toute la magie de Spring

@SpringBootApplication // C'est l'annotation "3-en-1" :
// 1. @Configuration : Permet d'enregistrer des beans.
// 2. @EnableAutoConfiguration : Spring configure tout seul le projet (H2, JPA, Web...) selon les dépendances du pom.xml.
// 3. @ComponentScan : Spring cherche automatiquement les Controllers, Services et Repositories dans ce dossier.
public class BidListApplication {

    public static void main(String[] args) {
        // C'est la méthode principale qui est exécutée au démarrage du programme
        // Elle lance le moteur Spring Boot et démarre le serveur web embarqué (Tomcat) sur le port 8081
        SpringApplication.run(BidListApplication.class, args);
    }
}

/*
À quoi sert-il ?
C'est la Bougie d'allumage (ou la clé de contact) du microservice BidList. Sans ce fichier, ton microservice n'est qu'une collection de fichiers inertes. C'est lui qui donne l'ordre à Java de "réveiller" Spring Boot.

Ce qu'il faut retenir :

    Lancement du Serveur : Ce fichier démarre un serveur web (Tomcat) invisible à l'intérieur de ton microservice. C'est grâce à ça que l'interface UI pourra appeler http://bidlist:8081.

    Configuration Automatique : Grâce à @SpringBootApplication, Spring Boot devine que tu as besoin d'une base de données H2 et de JPA en lisant ton fichier pom.xml, et il prépare tout pour toi sans que tu n'aies à écrire une seule ligne de configuration complexe.

    Point d'entrée unique : Il n'y a qu'un seul fichier comme celui-ci par microservice. C'est l'origine de tout le processus de démarrage.
 */