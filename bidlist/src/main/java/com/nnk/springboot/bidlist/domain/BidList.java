package com.nnk.springboot.bidlist.domain; // Déclare l'emplacement du fichier

// Importations des bibliothèques Jakarta Persistence (JPA) pour le lien avec la base de données
import jakarta.persistence.*;
// Importations de Lombok pour réduire la quantité de code à écrire
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// Importation pour la gestion des dates et heures précises
import java.sql.Timestamp;

@Entity // Dit à Java : "Cette classe correspond à une table dans la base de données"
@Table(name = "bidlist") // Précise le nom de la table SQL (en minuscules)
@Data // Magique : Lombok génère automatiquement les Getters, Setters, toString(), equals() et hashCode()
@NoArgsConstructor // Génère un constructeur vide (obligatoire pour Hibernate/JPA)
@AllArgsConstructor // Génère un constructeur avec tous les champs (pratique pour les tests)
public class BidList {

    @Id // Déclare que ce champ est la Clé Primaire (unique) de la table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Dit à la base de données de gérer l'ID toute seule (1, 2, 3...)
    private Integer bidListId;

    // --- CHAMPS MÉTIER (Les données de l'application) ---
    private String account; // Le nom du compte associé à l'offre
    private String type;    // Le type d'offre (ex: Achat ou Vente)
    private Double bidQuantity; // La quantité proposée
    private Double askQuantity; // La quantité demandée
    private Double bid; // Le prix proposé
    private Double ask; // Le prix demandé
    private String benchmark; // L'indicateur de référence
    private Timestamp bidListDate; // La date de l'offre
    private String commentary; // Commentaires libres
    private String security; // Le nom du titre financier (Action, Obligation...)
    private String status; // L'état de l'offre (Ouverte, Fermée, En cours...)
    private String trader; // Le nom du trader qui a créé l'offre
    private String book; // Le carnet d'ordres associé
    private String creationName; // Qui a créé l'enregistrement dans le système
    private Timestamp creationDate; // Quand l'enregistrement a été créé
    private String revisionName; // Qui a modifié l'enregistrement en dernier
    private Timestamp revisionDate; // Quand a eu lieu la dernière modification
    private String dealName; // Le nom du contrat final
    private String dealType; // Le type de contrat
    private String sourceListId; // Identifiant dans la liste source
    private String side; // Le côté de la transaction (Achat/Vente)

}

/* À quoi sert-il ?
C'est une Entité. C'est le "plan de construction" de ta table dans la base de données. Sans ce fichier, Java ne saurait pas comment transformer tes données SQL en objets manipulables par le code.

Ce qu'il faut retenir :

    L'annotation @Entity : C'est le pont entre le monde du code (Java) et le monde des données (SQL).

    Lombok (@Data) : C'est l'outil qui rend ton code propre. Sans lui, ce fichier ferait 200 lignes à cause des Getters et Setters.

    La structure : Chaque variable (private String account, etc.) devient une colonne dans ton tableau MySQL ou H2.
*/