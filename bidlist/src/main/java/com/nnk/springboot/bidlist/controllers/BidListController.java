package com.nnk.springboot.bidlist.controllers; // Déclare l'emplacement du fichier dans la structure du projet

// Importations des outils nécessaires
import com.nnk.springboot.bidlist.domain.BidList; // Importe le modèle de données (la classe BidList)
import com.nnk.springboot.bidlist.repositories.BidListRepository; // Importe l'interface qui parle à la base de données
import jakarta.validation.Valid; // Outil pour déclencher la vérification des champs (@NotBlank, etc.)
import org.springframework.beans.factory.annotation.Autowired; // Permet l'injection automatique des dépendances
import org.springframework.http.ResponseEntity; // Objet pour personnaliser la réponse HTTP (code 200, 400, etc.)
import org.springframework.validation.BindingResult; // Objet qui stocke les erreurs de validation
import org.springframework.web.bind.annotation.*; // Importe toutes les annotations Web (Get, Post, etc.)

import java.util.List; // Utilitaire pour gérer des listes d'objets

@RestController // Dit à Spring que cette classe est une API qui renvoie du JSON (pas du HTML)
public class BidListController {

    @Autowired // Spring va créer et injecter automatiquement une instance du repository ici
    private BidListRepository bidListRepository;

    // --- LIRE TOUT ---
    @GetMapping("/bidList/test") // Route pour récupérer toutes les lignes (utilisée par le portail UI)
    public List<BidList> getAllBids() {
        return bidListRepository.findAll(); // Appelle la base de données pour lister tout le contenu
    }

    // --- AJOUTER ---
    @PostMapping("/bidList/add") // Reçoit une demande de création via la méthode POST
    public ResponseEntity<?> addBidList(@Valid @RequestBody BidList bidList, BindingResult result) {
        // @Valid : vérifie les contraintes (ex: pas vide). @RequestBody : transforme le JSON reçu en objet Java.

        if (result.hasErrors()) {
            // Si le formulaire envoyé par l'UI est mal rempli (erreurs de validation)
            // On renvoie un code 400 (Bad Request) avec la liste des erreurs
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        // Si tout est OK, on enregistre en base H2/MySQL et on renvoie un code 200 (OK)
        return ResponseEntity.ok(bidListRepository.save(bidList));
    }

    // --- SUPPRIMER ---
    @GetMapping("/bidList/delete/{id}") // Route pour supprimer une ligne précise via son ID
    public void deleteBid(@PathVariable("id") Integer id) {
        // @PathVariable : extrait le chiffre {id} qui se trouve dans l'URL
        bidListRepository.deleteById(id); // Supprime définitivement la ligne en base de données
    }

    // --- LIRE UN SEUL ---
    @GetMapping("/bidList/get/{id}") // Route pour récupérer les infos d'une seule ligne (utilisée pour remplir le formulaire Edit)
    public BidList getBidList(@PathVariable("id") Integer id) {
        // Cherche l'objet par son ID, ou renvoie une erreur si l'ID n'existe pas
        return bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id:" + id));
    }

    // --- METTRE À JOUR ---
    @PostMapping("/bidList/update") // Reçoit les modifications d'une ligne existante
    public ResponseEntity<?> updateBidList(@Valid @RequestBody BidList bidList, BindingResult result) {
        if (result.hasErrors()) {
            // En cas d'erreur de saisie, on refuse la mise à jour
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        // Hibernate utilise la méthode save() : si l'ID existe déjà, il fait un UPDATE au lieu d'un INSERT
        return ResponseEntity.ok(bidListRepository.save(bidList));
    }
}

/* À quoi sert-il ?
Ce fichier est le Cerveau de données du microservice BidList. C'est lui qui gère la logique "métier" et l'accès à la base de données.

Ce qu'il faut retenir :

    Indépendance : Il ne connaît pas l'existence du portail UI. Il se contente de recevoir des ordres (JSON) et de répondre (JSON). C'est le principe même du découpage en microservices.

    Sécurité (Validation) : Il vérifie que les données sont valides (@Valid) avant de toucher à la base de données. C'est le dernier rempart contre les mauvaises données.

    Flexibilité (ResponseEntity) : Il utilise un format de réponse pro qui permet de dire à l'appelant si tout s'est bien passé ou s'il y a eu une erreur de saisie.
 */