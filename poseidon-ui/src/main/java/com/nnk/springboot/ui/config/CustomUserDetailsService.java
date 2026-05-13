package com.nnk.springboot.ui.config; // Emplacement dans le projet portail UI

// Importations des modèles et outils de sécurité
import com.nnk.springboot.ui.domain.User; // Importe ton modèle utilisateur local
import com.nnk.springboot.ui.proxies.UserProxy; // Importation du nouveau Proxy Feign (le smartphone)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails; // L'objet "badge" que Spring Security comprend
import org.springframework.security.core.userdetails.UserDetailsService; // L'interface standard de Spring Security
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Erreur si l'utilisateur n'existe pas
import org.springframework.stereotype.Service; // Marque cette classe comme un service métier

@Service // Dit à Spring : "C'est cette classe qui doit vérifier l'identité des gens au Login"
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired // Injecte le Proxy Feign qui communique avec le microservice User
    private UserProxy userProxy;

    /**
     * MÉTHODE DE VÉRIFICATION DU LOGIN
     * Spring Security appelle cette méthode automatiquement quand tu cliques sur "Sign in".
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // 1. APPEL AU MICROSERVICE BACKEND VIA LE PROXY
            // On ne gère plus d'URL compliquée. On appelle juste la méthode du Proxy.
            // Feign va demander à Consul : "Où est le service 'user' ?" et faire l'appel.
            User user = userProxy.getByUsername(username);

            // 2. VÉRIFICATION DE L'EXISTENCE
            if (user == null) {
                // Si le microservice répond vide, on lance une erreur de sécurité
                throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
            }

            // 3. TRANSFORMATION POUR SPRING SECURITY
            // On prend les données de ta base (username, password haché, role)
            // et on les emballe dans un objet "User" que le moteur de Spring Security sait lire.
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword()) // Le mot de passe crypté BCrypt reçu du microservice
                    // On ajoute "ROLE_" devant le rôle (ex: ADMIN -> ROLE_ADMIN)
                    // C'est une exigence stricte de Spring Security pour la gestion des droits.
                    .authorities("ROLE_" + user.getRole())
                    .build();

        } catch (Exception e) {
            // Si le microservice "user" est éteint ou si la communication échoue
            throw new UsernameNotFoundException("Erreur de communication avec le service User via OpenFeign", e);
        }
    }
}

/*
==========================================================================================
RÉSUMÉ DU FICHIER :
==========================================================================================
À quoi sert-il ?
C'est le "Vérificateur d'identité" de ton portail. Dans une architecture microservices, l'interface
UI n'a pas accès à la base de données. Ce fichier est le messager qui utilise OpenFeign
pour demander au microservice "User" si l'utilisateur existe.

Ce qu'il faut retenir :
1. Transition RestTemplate vers OpenFeign : On a supprimé toute la plomberie des URLs.
   Le code est devenu "déclaratif" : on appelle une méthode Java, et Feign s'occupe du réseau.

2. Centralisation de la Sécurité : Le portail UI centralise l'authentification pour tous
   les autres services. Une fois le "badge" (UserDetails) créé ici, l'utilisateur est
   reconnu dans toute l'application.

3. Mapping de Sécurité : Ce fichier fait la conversion entre ton objet métier (User.java)
   et l'objet technique de Spring (UserDetails). C'est le point de passage obligatoire pour le Login.
==========================================================================================
*/