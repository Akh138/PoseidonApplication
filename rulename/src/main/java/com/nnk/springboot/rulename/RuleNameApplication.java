package com.nnk.springboot.rulename; // Déclare le package racine de ce microservice spécifique

// Importations des outils fondamentaux de Spring Boot
import org.springframework.boot.SpringApplication; // La classe qui contient la logique pour démarrer l'application
import org.springframework.boot.autoconfigure.SpringBootApplication; // L'annotation qui active toute la puissance de Spring Boot

@SpringBootApplication // C'est l'annotation "pilote" de ton microservice. Elle active trois fonctions automatiques :
// 1. @EnableAutoConfiguration : Spring configure tout seul l'application selon les bibliothèques présentes (Web, JPA, etc.).
// 2. @ComponentScan : Spring cherche automatiquement les @Controllers et @Repositories dans ce dossier et ses sous-dossiers.
// 3. @Configuration : Permet d'enregistrer des réglages personnalisés (beans) si nécessaire.
public class RuleNameApplication {

    public static void main(String[] args) {
        // C'est le point d'entrée du programme Java exécuté par le conteneur Docker.
        // Cette ligne déclenche les actions suivantes :
        // - Elle lance un serveur web interne (Tomcat) sur le port 8084.
        // - Elle initialise la connexion à la base de données (H2 ou MySQL).
        // - Elle enregistre le service dans l'annuaire Consul (Discovery).
        // - Elle va chercher ses réglages sur le serveur de configuration (Config Server).
        SpringApplication.run(RuleNameApplication.class, args);
    }
}

/*
À quoi sert-il ?
C'est la Clé de contact du microservice RuleName. Dans ton architecture Poseidon, chaque microservice est comme un petit logiciel séparé. Ce fichier est celui qui permet à la brique "Règles métier" de s'allumer de façon autonome sans attendre les autres.

Ce qu'il faut retenir :

    Indépendance : Ce fichier prouve que ton application est découpée. Tu peux lancer RuleNameApplication tout seul, ce qui facilite les tests et la maintenance.

    Démarrage du Serveur : Dès que tu lances cette classe, Spring Boot crée un environnement complet (un serveur Tomcat) à l'intérieur du service. C'est ce serveur qui recevra les ordres de la Gateway (port 9090).

    Lecture de Configuration : C'est à ce moment précis que le service contacte le Config Server pour savoir s'il doit utiliser H2 ou MySQL, et quels messages il doit afficher.
 */