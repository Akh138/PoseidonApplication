package com.nnk.springboot.user.controllers; // Déclare l'emplacement dans le microservice user

// Importations des modèles et outils Spring
import com.nnk.springboot.user.domain.User; // Importe le modèle de données User
import com.nnk.springboot.user.repositories.UserRepository; // Importe l'accès à la base de données
import jakarta.validation.Valid; // Pour activer la vérification des contraintes (@NotBlank sur le mot de passe, etc.)
import org.springframework.beans.factory.annotation.Autowired; // Pour l'injection automatique
import org.springframework.http.ResponseEntity; // Objet pour construire des réponses HTTP (200 OK, 400 Bad Request)
import org.springframework.validation.BindingResult; // Pour capturer et renvoyer les erreurs de saisie
import org.springframework.web.bind.annotation.*; // Pour les routes Web

import java.util.List; // Utilitaire pour les listes

@RestController // Dit à Spring que c'est une API REST : elle produit du JSON pour l'interface UI
public class UserController {

    @Autowired // Branche automatiquement le Repository pour parler à la base de données
    private UserRepository userRepository;

    // --- LIRE TOUT ---
    @GetMapping("/user/test") // Route pour voir tous les utilisateurs (pour test ou admin)
    public List<User> test() {
        return userRepository.findAll(); // SELECT * FROM users
    }

    // --- AJOUTER AVEC SÉCURITÉ ---
    @PostMapping("/user/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, BindingResult result) {

        // 1. On vérifie si la requête arrive bien ici
        System.out.println("DEBUG : Une tentative d'inscription est arrivée pour : " + user.getUsername());

        // 2. On vérifie si les annotations @NotBlank ont détecté une erreur
        if (result.hasErrors()) {
            System.out.println("DEBUG : Erreurs de validation trouvées : " + result.getAllErrors());
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        // 3. Si on arrive ici, c'est que la validation est passée.
        // On sauvegarde en base
        User utilisateurSauvegarde = userRepository.save(user);
        System.out.println("DEBUG : Utilisateur sauvegardé avec succès avec l'ID : " + utilisateurSauvegarde.getId());

        return ResponseEntity.ok(utilisateurSauvegarde);
    }

    // --- LIRE UN SEUL ---
    @GetMapping("/user/get/{id}") // Récupère un utilisateur précis via son ID (ex: pour l'édition)
    public User getUser(@PathVariable("id") Integer id) {
        // @PathVariable : extrait l'ID de l'URL
        return userRepository.findById(id).orElseThrow(); // Renvoie l'utilisateur ou une erreur si pas trouvé
    }

    // --- METTRE À JOUR ---
    @PostMapping("/user/update") // Reçoit les modifications d'un compte existant
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            // Refuse la mise à jour si les nouvelles données sont invalides (ex: nom vide)
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        // Hibernate fait un UPDATE car l'objet possède déjà un ID
        return ResponseEntity.ok(userRepository.save(user));
    }

    // --- SUPPRIMER ---
    @GetMapping("/user/delete/{id}") // Route de suppression
    public void deleteUser(@PathVariable("id") Integer id) {
        userRepository.deleteById(id); // Supprime la ligne dans la base de données
    }

    // --- MÉTHODE SPÉCIALE SÉCURITÉ ---
    @GetMapping("/user/getByUsername/{username}") // Appelée par le portail UI lors du LOGIN
    public User getByUsername(@PathVariable("username") String username) {
        // Cette méthode est vitale : c'est elle qui permet à l'interface de vérifier
        // si le nom tapé au login existe dans la base de données
        return userRepository.findByUsername(username).orElse(null);
    }
}

/*
À quoi sert-il ?
C'est le Gestionnaire d'identité. Dans ton architecture, c'est le microservice "coffre-fort" qui contient les comptes de la banque. Il permet de gérer les employés (Admin ou User) et fournit les informations nécessaires à la page de connexion.

Ce qu'il faut retenir :

    Le lien avec la Sécurité : La méthode getByUsername est le pont entre ton interface et ta base de données. Sans elle, le formulaire de login ne pourrait jamais savoir si l'utilisateur "admin" existe.

    Validation Pro (ResponseEntity) : Tu as appris à utiliser ResponseEntity<?>. C'est très propre car cela permet de répondre au portail UI de deux manières : "Voici le résultat (200)" ou "Voici ce qui ne va pas dans ton formulaire (400)".

    Isolation des secrets : Ce contrôleur ne parle qu'en JSON. Il ne connaît pas les pages HTML. C'est l'interface UI qui se charge de rendre ces informations
 */