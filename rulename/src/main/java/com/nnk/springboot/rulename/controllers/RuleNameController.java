package com.nnk.springboot.rulename.controllers; // Déclare l'emplacement du fichier dans le microservice rulename

// Importations des modèles et outils Spring
import com.nnk.springboot.rulename.domain.RuleName; // Importe le modèle de données RuleName
import com.nnk.springboot.rulename.repositories.RuleNameRepository; // Importe l'accès à la base de données
import jakarta.validation.Valid; // Pour activer la vérification automatique des champs (@NotBlank, etc.)
import org.springframework.beans.factory.annotation.Autowired; // Pour l'injection automatique des composants
import org.springframework.http.ResponseEntity; // Pour construire des réponses HTTP personnalisées (200 OK, 400 Bad Request)
import org.springframework.validation.BindingResult; // Pour capturer et lire les erreurs de validation
import org.springframework.web.bind.annotation.*; // Pour gérer les routes Web (GET, POST, etc.)

import java.util.List; // Pour manipuler des listes d'objets

@RestController // Dit à Spring que cette classe est une API REST : elle produit du JSON pour les autres services
public class RuleNameController {

    @Autowired // Demande à Spring d'injecter automatiquement l'accès à la base de données
    private RuleNameRepository ruleNameRepository;

    // --- LIRE TOUTE LA LISTE ---
    @GetMapping("/rulename/test") // Route appelée par le portail UI pour afficher le tableau des règles
    public List<RuleName> test() {
        // Appelle le repository pour récupérer toutes les lignes de la table 'rulename'
        return ruleNameRepository.findAll();
    }

    // --- AJOUTER UNE NOUVELLE RÈGLE ---
    @PostMapping("/rulename/add") // Reçoit une requête de création avec les données en JSON
    public ResponseEntity<?> add(@Valid @RequestBody RuleName ruleName, BindingResult result) {
        // @Valid : déclenche la vérification des contraintes Java.
        // @RequestBody : transforme le texte JSON reçu en objet Java "ruleName".

        if (result.hasErrors()) {
            // Si des champs obligatoires sont vides ou mal remplis :
            // On renvoie une erreur 400 (Bad Request) avec le détail des erreurs
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        // Si les données sont valides, on enregistre dans H2/MySQL et on renvoie un code 200 (OK)
        return ResponseEntity.ok(ruleNameRepository.save(ruleName));
    }

    // --- RÉCUPÉRER UNE RÈGLE PAR SON ID ---
    @GetMapping("/rulename/get/{id}") // Route utilisée pour remplir le formulaire de modification (Edit)
    public RuleName get(@PathVariable("id") Integer id) {
        // @PathVariable : extrait l'ID directement depuis l'adresse URL
        // Cherche en base, et si l'ID n'existe pas, renvoie une exception
        return ruleNameRepository.findById(id).orElseThrow();
    }

    // --- METTRE À JOUR UNE RÈGLE ---
    @PostMapping("/rulename/update") // Reçoit les modifications pour une règle déjà existante
    public ResponseEntity<?> update(@Valid @RequestBody RuleName ruleName, BindingResult result) {
        if (result.hasErrors()) {
            // Si la modification rend l'objet invalide, on refuse l'enregistrement
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        // Save() détecte automatiquement s'il doit faire un INSERT (nouvel ID) ou un UPDATE (ID existant)
        return ResponseEntity.ok(ruleNameRepository.save(ruleName));
    }

    // --- SUPPRIMER UNE RÈGLE ---
    @GetMapping("/rulename/delete/{id}") // Route de suppression appelée par le bouton rouge du portail UI
    public void delete(@PathVariable("id") Integer id) {
        // Supprime définitivement la ligne correspondante dans la base de données
        ruleNameRepository.deleteById(id);
    }
}

/*
À quoi sert-il ?
C'est le Gestionnaire de protocoles. Dans l'application Poseidon, les "RuleNames" définissent les noms et les règles des échanges financiers. Ce contrôleur permet de gérer ces règles (les créer, les voir, les changer ou les supprimer) de manière sécurisée.

Ce qu'il faut retenir :

    Architecture Découplée : Ce code est totalement indépendant de l'interface graphique. Il ne traite que de la donnée pure. Cela permet de changer le design du site sans jamais toucher à ce fichier.

    Double Vérification (@Valid) : Il assure l'intégrité des données métier. Une règle sans nom ou sans description est inutile et dangereuse pour la banque, donc ce code les interdit.

    Communication fluide : En renvoyant du JSON, il permet à la Gateway et au Portail UI de récupérer des informations structurées très rapidement.
 */