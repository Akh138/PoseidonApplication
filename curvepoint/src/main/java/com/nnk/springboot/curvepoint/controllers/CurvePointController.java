package com.nnk.springboot.curvepoint.controllers; // Déclare l'emplacement du fichier dans le microservice curvepoint

// Importations des outils nécessaires
import com.nnk.springboot.curvepoint.domain.CurvePoint; // Importe le modèle (Entity) CurvePoint
import com.nnk.springboot.curvepoint.repositories.CurvePointRepository; // Importe l'interface d'accès aux données
import jakarta.validation.Valid; // Pour activer la vérification des contraintes (@NotNull, etc.)
import org.springframework.beans.factory.annotation.Autowired; // Pour l'injection automatique
import org.springframework.http.ResponseEntity; // Pour renvoyer des réponses HTTP structurées (200 OK, 400 Bad Request)
import org.springframework.validation.BindingResult; // Objet qui contient les résultats de la validation
import org.springframework.web.bind.annotation.*; // Importe les outils pour créer une API Web

import java.util.List; // Utilitaire pour gérer les listes

@RestController // Dit à Spring que c'est une API REST : elle renvoie des données brutes (JSON) et non des pages HTML
public class CurvePointController {

    @Autowired // Injecte automatiquement le Repository pour interagir avec la base de données
    private CurvePointRepository curvePointRepository;

    // --- LIRE TOUT ---
    @GetMapping("/curvePoint/test") // Route appelée par le Portail UI pour remplir son tableau
    public List<CurvePoint> test() {
        return curvePointRepository.findAll(); // Récupère tous les points de courbe en base de données
    }

    // --- AJOUTER ---
    @PostMapping("/curvePoint/add") // Reçoit une requête POST avec un objet JSON dans le corps (Body)
    public ResponseEntity<?> add(@Valid @RequestBody CurvePoint curvePoint, BindingResult result) {
        // @Valid : Déclenche la vérification des champs.
        // @RequestBody : Convertit le JSON reçu en objet Java "curvePoint".

        if (result.hasErrors()) {
            // Si l'utilisateur a envoyé des données invalides (ex: CurveId manquant)
            // On renvoie une erreur 400 avec le détail des fautes
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        // Si c'est valide, on enregistre dans la base H2/MySQL et on renvoie l'objet créé avec un code 200
        return ResponseEntity.ok(curvePointRepository.save(curvePoint));
    }

    // --- LIRE UN SEUL ---
    @GetMapping("/curvePoint/get/{id}") // Route pour récupérer un point précis via son ID
    public CurvePoint get(@PathVariable("id") Integer id) {
        // @PathVariable : Récupère le chiffre {id} écrit dans l'adresse URL
        // Cherche en base, et si l'ID n'existe pas, renvoie une erreur
        return curvePointRepository.findById(id).orElseThrow();
    }

    // --- METTRE À JOUR ---
    @PostMapping("/curvePoint/update") // Reçoit les nouvelles valeurs pour un point existant
    public ResponseEntity<?> update(@Valid @RequestBody CurvePoint curvePoint, BindingResult result) {
        if (result.hasErrors()) {
            // Si les modifications sont invalides, on rejette la mise à jour
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        // Hibernate comprend que si l'objet possède un ID qui existe déjà, il doit faire un UPDATE
        return ResponseEntity.ok(curvePointRepository.save(curvePoint));
    }

    // --- SUPPRIMER ---
    @GetMapping("/curvePoint/delete/{id}") // Route de suppression (appelée par le bouton Delete de l'UI)
    public void delete(@PathVariable("id") Integer id) {
        curvePointRepository.deleteById(id); // Supprime la ligne correspondante dans la base de données
    }
}

/*
À quoi sert-il ?
C'est le Gestionnaire de données pour tout ce qui concerne les courbes de taux (CurvePoint). C'est un pur microservice Backend : il ne "voit" pas l'utilisateur, il ne parle qu'à l'interface (UI) via des messages JSON.

Ce qu'il faut retenir :

    Architecture REST : Il utilise des méthodes standards (GET pour lire, POST pour écrire) pour que n'importe quelle application (ton portail UI ou même une application mobile) puisse consommer ses données.

    Sécurité des données (@Valid) : Il ne fait pas confiance aveuglément à ce qu'il reçoit. Il vérifie chaque donnée avant d'autoriser l'écriture en base de données.

    Réponses Pro (ResponseEntity) : Il renvoie des codes d'état clairs (Ex: 200 si ça marche, 400 si l'utilisateur a fait une erreur). C'est indispensable pour que le Portail UI puisse savoir s'il doit afficher un message de succès ou un message d'erreur.
 */