package com.nnk.springboot.ui.config; // Emplacement dans le projet portail UI

// Importations des outils de sécurité de Spring
import org.springframework.context.annotation.Bean; // Pour créer des composants gérés par Spring
import org.springframework.context.annotation.Configuration; // Marque cette classe comme une source de configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // L'objet qui permet de configurer la sécurité HTTP
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Active la sécurité web dans le projet
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // L'algorithme de hachage des mots de passe
import org.springframework.security.crypto.password.PasswordEncoder; // L'interface pour le codage des mots de passe
import org.springframework.security.web.SecurityFilterChain; // Le filtre final qui sera appliqué à chaque requête

@Configuration // Dit à Spring : "Lis ce fichier au démarrage pour configurer les règles de sécurité"
@EnableWebSecurity // Active la protection de l'application (toutes les URLs sont bloquées par défaut)
public class SecurityConfig {

    /**
     * CONFIGURATION DES RÈGLES DE FILTRAGE
     * Cette méthode définit qui a le droit d'accéder à quoi sur ton site.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. PROTECTION CSRF
                // On désactive la protection CSRF pour simplifier les tests entre microservices dans Docker.
                .csrf(csrf -> csrf.disable())

                // 2. AUTORISATIONS DES REQUÊTES
                .authorizeHttpRequests(auth -> auth
                        // PAGES PUBLIQUES : Tout le monde peut voir l'accueil, le design (CSS),
                        // le lien de test, la page d'inscription et la page d'erreur.
                        .requestMatchers("/", "/css/**", "/user/user/test", "/register/**", "/403").permitAll()

                        // ACCÈS RESTREINT (RBAC) : Seuls les utilisateurs ayant le rôle 'ADMIN'
                        // peuvent accéder aux pages commençant par /users/.
                        .requestMatchers("/users/**").hasRole("ADMIN")

                        // TOUT LE RESTE : Demande obligatoirement d'être connecté (authentifié).
                        .anyRequest().authenticated()
                )

                // 3. CONFIGURATION DU FORMULAIRE DE CONNEXION (LOGIN)
                .formLogin(form -> form
                        .loginPage("/login") // On utilise notre propre page HTML personnalisée
                        .defaultSuccessUrl("/", true) // Une fois connecté, on arrive sur le beau Dashboard
                        .permitAll() // On laisse tout le monde accéder à la page de login
                )

                // 4. CONFIGURATION DE LA DÉCONNEXION (LOGOUT)
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // Après le logout, on revient à l'accueil public
                        .permitAll()
                )

                // 5. GESTION DES ERREURS D'ACCÈS
                .exceptionHandling(exception -> exception
                        // Si un 'USER' essaie d'aller sur une page 'ADMIN', on l'envoie sur la page 403 (Accès Refusé).
                        .accessDeniedPage("/403")
                );

        return http.build(); // On construit et on active la chaîne de sécurité
    }

    /**
     * CONFIGURATION DE L'ENCODEUR DE MOT DE PASSE
     * Dit à Spring d'utiliser BCrypt pour comparer les mots de passe tapés au clavier
     * avec ceux qui sont stockés (hachés) dans la base de données.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Algorithme de hachage standard et sécurisé
    }
}

/*
À quoi sert-il ?
C'est le Vigile à l'entrée du château. Ce fichier est le plus important pour la sécurité de ton application. Il définit les zones publiques du site et les zones privées. Il force également l'utilisation d'un mot de passe crypté.

Ce qu'il faut retenir :

    Le Filtrage (authorizeHttpRequests) : Tu as appris à créer une hiérarchie. L'accueil est ouvert à tous, mais la gestion des utilisateurs est un coffre-fort réservé aux administrateurs.

    La Gestion des Rôles (hasRole) : C'est ce qu'on appelle le RBAC. Ton application est capable de faire la différence entre un simple employé et un patron (Admin).

    La Page de Login Personnalisée : Tu as dit à Spring d'arrêter d'utiliser sa page grise et moche pour utiliser ta propre interface Bootstrap.

    Le Hachage (BCrypt) : C'est la garantie que même si quelqu'un vole ta base de données, il ne pourra pas lire les mots de passe des utilisateurs car ils sont codés
 */