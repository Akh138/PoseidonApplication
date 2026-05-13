package com.nnk.springboot.trade; // Déclare le package racine de ce microservice spécifique

// Importations des outils fondamentaux de Spring Boot
import org.springframework.boot.SpringApplication; // La classe utilitaire pour lancer l'application
import org.springframework.boot.autoconfigure.SpringBootApplication; // L'annotation qui active toute la puissance de Spring

@SpringBootApplication // L'annotation "moteur" de ton microservice. Elle active trois fonctions automatiques :
// 1. @EnableAutoConfiguration : Spring configure tout seul l'application selon les dépendances (Web, JPA, MySQL).
// 2. @ComponentScan : Spring cherche automatiquement les @Controllers et @Repositories dans ce dossier et ses sous-dossiers.
// 3. @Configuration : Permet d'enregistrer des réglages personnalisés si nécessaire.
public class TradeApplication {

    public static void main(String[] args) {
        // C'est la méthode principale (point d'entrée) exécutée par Java lors du lancement du conteneur Docker.
        // Cette ligne déclenche tout le cycle de vie du microservice :
        // - Elle démarre le serveur web interne (Tomcat) sur le port 8085.
        // - Elle établit la connexion à la base de données (H2 ou MySQL).
        // - Elle enregistre le service dans l'annuaire Consul (Service Discovery).
        // - Elle récupère la configuration centralisée sur GitHub via le Config Server.
        SpringApplication.run(TradeApplication.class, args);
    }
}
/*
À quoi sert-il ?
C'est la Clé de contact (ou la bougie d'allumage) du microservice Trade. Dans ton architecture, chaque microservice est une entité autonome. Ce fichier est celui qui permet à la brique "Transactions" de s'allumer et de devenir opérationnelle sans dépendre du lancement manuel des autres.

Ce qu'il faut retenir :

    Indépendance : Ce fichier permet au service trade d'exister tout seul. Tu peux le redémarrer indépendamment de l'UI ou des autres services de données.

    Serveur Intégré : Au lancement, il crée son propre environnement web complet (un serveur Tomcat invisible). C'est ce qui lui permet d'écouter les ordres de la Gateway.

    Initialisation : C'est le moment où le code Java "découvre" son environnement (où est la base de données, où est le serveur de config) en lisant le fichier application.properties
 */