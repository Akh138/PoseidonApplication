package com.nnk.springboot.trade.controllers; // Déclare l'emplacement du fichier dans le microservice trade

// Importations des modèles et outils Spring
import com.nnk.springboot.trade.domain.Trade; // Importe le modèle de données Trade
import com.nnk.springboot.trade.repositories.TradeRepository; // Importe l'interface d'accès à la base de données
import jakarta.validation.Valid; // Pour activer la vérification automatique des champs (@NotBlank, etc.)
import org.springframework.beans.factory.annotation.Autowired; // Pour l'injection automatique des composants
import org.springframework.http.ResponseEntity; // Pour construire des réponses HTTP personnalisées (200 OK, 400 Bad Request)
import org.springframework.validation.BindingResult; // Pour capturer et lire les erreurs de validation
import org.springframework.web.bind.annotation.*; // Pour gérer les routes Web (GET, POST, etc.)

import java.util.List; // Pour manipuler des listes d'objets

@RestController // Dit à Spring que cette classe est une API REST : elle produit du JSON pour l'interface UI
public class TradeController {

    @Autowired // Demande à Spring d'injecter automatiquement l'instance du Repository
    private TradeRepository tradeRepository;

    // --- LIRE TOUS LES TRADES ---
    @GetMapping("/trade/test") // Route appelée par le portail UI pour afficher le tableau des transactions
    public List<Trade> test() {
        // Appelle le repository pour récupérer toutes les lignes de la table 'trade'
        return tradeRepository.findAll();
    }

    // --- AJOUTER UN NOUVEAU TRADE ---
    @PostMapping("/trade/add") // Reçoit une requête de création via la méthode POST avec du JSON
    public ResponseEntity<?> add(@Valid @RequestBody Trade trade, BindingResult result) {
        // @Valid : déclenche la vérification des contraintes Java (ex: compte obligatoire).
        // @RequestBody : transforme le texte JSON reçu en objet Java "trade".

        if (result.hasErrors()) {
            // Si des champs obligatoires sont mal remplis :
            // On renvoie une erreur 400 (Bad Request) avec la liste des fautes
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        // Si les données sont valides, on enregistre dans H2/MySQL et on renvoie un code 200 (OK)
        return ResponseEntity.ok(tradeRepository.save(trade));
    }

    // --- RÉCUPÉRER UN TRADE PAR SON ID ---
    @GetMapping("/trade/get/{id}") // Route utilisée pour remplir le formulaire de modification (Edit)
    public Trade get(@PathVariable("id") Integer id) {
        // @PathVariable : extrait l'ID directement depuis l'adresse URL
        // Cherche en base, et si l'ID n'existe pas (orElseThrow), renvoie une erreur
        return tradeRepository.findById(id).orElseThrow();
    }

    // --- METTRE À JOUR UN TRADE ---
    @PostMapping("/trade/update") // Reçoit les modifications pour une transaction existante
    public ResponseEntity<?> update(@Valid @RequestBody Trade trade, BindingResult result) {
        if (result.hasErrors()) {
            // Si la modification rend l'objet invalide (ex: effacer le compte), on refuse
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        // Save() détecte automatiquement s'il doit créer ou modifier en fonction de l'ID présent
        return ResponseEntity.ok(tradeRepository.save(trade));
    }

    // --- SUPPRIMER UN TRADE ---
    @GetMapping("/trade/delete/{id}") // Route de suppression appelée par l'interface UI
    public void delete(@PathVariable("id") Integer id) {
        // Supprime physiquement la ligne correspondante dans la base de données
        tradeRepository.deleteById(id);
    }
}

/*
À quoi sert-il ?
C'est le Gestionnaire de transactions. Dans l'application Poseidon, c'est ce fichier qui est responsable de l'historique des ventes et achats réels. Il agit comme un serveur de données pur qui répond aux demandes de ton portail web (l'UI).

Ce qu'il faut retenir :

    Architecture REST : Il utilise les méthodes HTTP standards (GET, POST) pour que la communication soit fluide et facile à comprendre par les autres services.

    Intégrité des données : Grâce à l'utilisation combinée de @Valid et ResponseEntity, il protège ta base de données contre les saisies incorrectes. C'est le "Garde du corps" de tes données financières.

    Réponses Professionnelles : En utilisant ResponseEntity, il permet au Portail UI de savoir précisément si l'action a réussi ou pourquoi elle a échoué (erreur 400), ce qui permet d'afficher les fameux messages rouges à l'utilisateur.
 */