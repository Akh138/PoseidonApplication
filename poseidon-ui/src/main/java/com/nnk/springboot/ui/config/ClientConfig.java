package com.nnk.springboot.ui.config; // Déclare l'emplacement du fichier dans le projet portail UI

// Importation pour la découverte de services intelligente
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
// Importations de base pour la configuration Spring
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// Importation de l'outil qui permet de passer des appels HTTP (le "téléphone")
import org.springframework.web.client.RestTemplate;

@Configuration // Dit à Spring : "Ceci est un fichier de réglages prioritaires au démarrage"
public class ClientConfig {

    /**
     * CRÉATION DU "TÉLÉPHONE" DE COMMUNICATION
     * Cette méthode crée un objet RestTemplate qui sera partagé dans toute l'application.
     */
    @Bean // Enregistre cet objet dans le moteur de Spring pour qu'on puisse l'utiliser avec @Autowired

    @LoadBalanced // <--- C'EST LA LIGNE MAGIQUE DE CONSUL !
    /*
       Cette annotation transforme un simple "téléphone" en "téléphone intelligent" :
       1. Elle connecte le RestTemplate à l'annuaire Consul.
       2. Elle permet d'appeler les services par leur NOM (ex: http://bidlist) au lieu de leur IP.
       3. S'il y a plusieurs copies du même service, elle choisit automatiquement la moins occupée.
    */
    public RestTemplate restTemplate() {
        // On retourne une nouvelle instance de RestTemplate.
        // Grâce à @LoadBalanced, Spring va lui ajouter un intercepteur qui traduit les noms en adresses réelles.
        return new RestTemplate();
    }
}
/*
À quoi sert-il ?
C'est le Poste de Communication de ton Portail UI. Sans ce fichier, ton interface serait "sourde et muette" : elle ne pourrait pas appeler les microservices pour récupérer les données. C'est l'outil qui permet au Portail (port 8080) de parler aux Cuisines (ports 8081, 8082, etc.).

Ce qu'il faut retenir :

    L'outil RestTemplate : C'est le client HTTP standard de Spring. On l'utilise pour envoyer des requêtes (GET, POST, DELETE) d'un microservice vers un autre.

    L'intelligence @LoadBalanced : C'est le lien avec Consul. C'est grâce à cette ligne que tu as pu supprimer les numéros de ports (:8081) dans tes Controllers. Ton code est devenu "propre" car il utilise des noms logiques.

    Centralisation : En créant ce Bean ici, tu évites de faire new RestTemplate() dans chaque Controller. Tu le configures une fois, et tu l'injectes partout
 */