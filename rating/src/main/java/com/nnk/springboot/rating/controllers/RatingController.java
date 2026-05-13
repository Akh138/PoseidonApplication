package com.nnk.springboot.rating.controllers; // Déclare l'emplacement du fichier dans le microservice rating

// Importations des modèles et outils Spring
import com.nnk.springboot.rating.domain.Rating; // Importe le modèle de données Rating
import com.nnk.springboot.rating.repositories.RatingRepository; // Importe l'accès à la base de données
import jakarta.validation.Valid; // Pour activer la vérification des champs (@NotBlank, etc.)
import org.springframework.beans.factory.annotation.Autowired; // Pour l'injection automatique de composants
import org.springframework.http.ResponseEntity; // Pour construire des réponses HTTP personnalisées
import org.springframework.validation.BindingResult; // Pour capturer les erreurs de validation
import org.springframework.web.bind.annotation.*; // Pour les routes Web (GET, POST, etc.)

import java.util.List; // Pour gérer les listes d'objets

@RestController // Dit à Spring que c'est une API REST : elle renvoie du texte (JSON) et non du HTML
public class RatingController {

    @Autowired // Spring crée l'objet RatingRepository et le branche ici automatiquement
    private RatingRepository ratingRepository;

    // --- LIRE TOUT ---
    @GetMapping("/rating/test") // Route utilisée par l'UI pour afficher le tableau des notations
    public List<Rating> test() {
        return ratingRepository.findAll(); // Récupère toutes les lignes de la table Rating en SQL
    }

    // --- AJOUTER ---
    @PostMapping("/rating/add") // Reçoit les données d'une nouvelle notation en format JSON
    public ResponseEntity<?> add(@Valid @RequestBody Rating rating, BindingResult result) {
        // @Valid : déclenche les contrôles (ex: pas de texte vide).
        // @RequestBody : transforme le JSON reçu en objet Java "rating".

        if (result.hasErrors()) {
            // Si le formulaire envoyé par l'utilisateur est mal rempli
            // On renvoie une erreur 400 (Bad Request) avec la liste des fautes
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        // Si tout est valide, on enregistre en base de données et on renvoie un code 200 (OK)
        return ResponseEntity.ok(ratingRepository.save(rating));
    }

    // --- LIRE UN SEUL ---
    @GetMapping("/rating/get/{id}") // Route pour récupérer une notation précise via son identifiant
    public Rating get(@PathVariable("id") Integer id) {
        // @PathVariable : extrait le chiffre {id} de l'URL
        // Cherche en base, et si l'ID n'existe pas, renvoie une erreur
        return ratingRepository.findById(id).orElseThrow();
    }

    // --- METTRE À JOUR ---
    @PostMapping("/rating/update") // Reçoit les modifications pour une notation déjà existante
    public ResponseEntity<?> update(@Valid @RequestBody Rating rating, BindingResult result) {
        if (result.hasErrors()) {
            // Si les nouvelles données sont invalides, on refuse la mise à jour
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        // Hibernate utilise save() : comme l'objet a un ID existant, il fait un UPDATE en SQL
        return ResponseEntity.ok(ratingRepository.save(rating));
    }

    // --- SUPPRIMER ---
    @GetMapping("/rating/delete/{id}") // Route de suppression (appelée par l'interface UI)
    public void delete(@PathVariable("id") Integer id) {
        ratingRepository.deleteById(id); // Supprime physiquement la ligne dans la base H2/MySQL
    }
}

/*
À quoi sert-il ?
C'est le Gestionnaire de données pour les notations financières (Moody's, S&P, etc.). Ce fichier est le coeur du microservice "Rating". Il définit comment on peut interagir avec les données des notations à travers le réseau.

Ce qu'il faut retenir :

    Indépendance Totale : Ce contrôleur ne s'occupe que des notations. Il ne sait pas que "BidList" ou "Trade" existent. C'est le principe de la séparation des responsabilités.

    Sécurité Applicative (@Valid) : Il garantit que personne ne pourra enregistrer une notation vide en base de données. Il fait office de "garde-fou".

    Standard JSON : En tant que @RestController, il parle le langage universel du web (JSON), ce qui permet à ton Portail UI de récupérer les données très facilement.
 */