package com.nnk.springboot.trade.domain; // Définit l'emplacement du fichier dans le microservice "Trade"

// Importations des bibliothèques Jakarta (JPA) pour le lien avec la base de données
import jakarta.persistence.*;
// Importations pour la validation des données (Data Integrity)
import jakarta.validation.constraints.NotBlank; // Interdit les textes vides
import jakarta.validation.constraints.NotNull;   // Interdit les valeurs nulles
import jakarta.validation.constraints.Positive; // Force un nombre strictement supérieur à zéro
// Importations Lombok pour un code court et propre
import lombok.*;
import java.sql.Timestamp; // Pour gérer les dates et heures avec précision

@Entity // Dit à Spring : "Cette classe est une Entité, elle représente une table SQL"
@Table(name = "trade") // Précise que le nom de la table en base de données est "trade"
@Data // Génère automatiquement : Getters, Setters, toString, equals et hashCode en mémoire
@NoArgsConstructor // Génère le constructeur vide obligatoire pour Hibernate/JPA
@AllArgsConstructor // Génère le constructeur avec tous les champs pour faciliter les tests
public class Trade {

    @Id // Déclare que ce champ est la Clé Primaire unique
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémentation (1, 2, 3...) gérée par la base
    private Integer tradeId;

    // --- CHAMPS OBLIGATOIRES AVEC RÈGLES DE VALIDATION ---

    @NotBlank(message = "Le compte (Account) est obligatoire") // Bloque l'enregistrement si le texte est vide
    private String account; // Nom du compte client ou bancaire

    @NotBlank(message = "Le type est obligatoire") // Sécurité : assure que la catégorie de trade est renseignée
    private String type; // Type de produit financier (ex: Action, Option)

    @NotNull(message = "La quantité d'achat est obligatoire") // Interdit une valeur nulle
    @Positive(message = "La quantité doit être supérieure à zéro") // Règle métier : on ne peut pas acheter 0 titre
    private Double buyQuantity; // Quantité de titres achetés

    // --- CHAMPS SECONDAIRES ET AUDIT ---

    private Double sellQuantity; // Quantité de titres vendus
    private Double buyPrice;     // Prix d'achat unitaire
    private Double sellPrice;    // Prix de vente unitaire
    private String benchmark;    // Indicateur de référence pour l'analyse
    private Timestamp tradeDate; // Date et heure de l'exécution de la transaction
    private String security;     // Nom du titre financier (ex: "Apple Inc.")
    private String status;       // État (ex: "OPEN", "CLOSED", "CANCELLED")
    private String trader;       // Nom du trader qui a passé l'ordre
    private String book;         // Nom du carnet d'ordres (portefeuille)
    private String creationName; // Nom de l'utilisateur ayant créé la ligne
    private Timestamp creationDate; // Heure de création dans le système
    private String revisionName; // Dernier utilisateur ayant modifié la ligne
    private Timestamp revisionDate; // Heure de la dernière modification
    private String dealName;     // Nom du contrat ou de l'accord final
    private String dealType;     // Type de deal
    private String sourceListId; // ID d'origine pour la traçabilité
    private String side;         // Indique le côté du marché (BUY ou SELL)
}
/*
À quoi sert-il ?
C'est le Modèle des transactions financières. C'est le fichier le plus important pour le suivi de l'argent dans Poseidon. Il définit comment une vente ou un achat est enregistré techniquement. Chaque variable ici devient une colonne dans ta table MySQL ou H2.

Ce qu'il faut retenir :

    Validation Bancaire : L'utilisation de @Positive est une vraie règle métier. Cela empêche d'avoir des transactions "absurdes" (quantité négative) dans ton système.

    Lombok Power : Sans les annotations @Data et @AllArgsConstructor, ce fichier ferait plus de 200 lignes. Grâce à Lombok, il reste compact et facile à lire pour un autre développeur.

    Identité (tradeId) : C'est le badge unique de chaque transaction. C'est lui que la Gateway et le Portail UI utilisent pour modifier ou supprimer un trade précis.
 */