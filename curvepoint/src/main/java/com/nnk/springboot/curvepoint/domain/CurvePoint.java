package com.nnk.springboot.curvepoint.domain; // Déclare l'emplacement du fichier dans le microservice curvepoint

// Importations des bibliothèques de persistance (Lien base de données)
import jakarta.persistence.*; // Utilise Jakarta (au lieu de javax) pour la compatibilité Spring Boot 3
import jakarta.validation.constraints.NotNull; // Outil de validation pour interdire les valeurs nulles
// Importations de Lombok pour simplifier le code
import lombok.AllArgsConstructor; // Génère un constructeur avec tous les champs
import lombok.Data; // Génère automatiquement Getters, Setters, toString, equals et hashCode
import lombok.NoArgsConstructor; // Génère un constructeur vide (obligatoire pour Hibernate/JPA)

import java.sql.Timestamp; // Pour gérer les dates avec précision (heure/minute/seconde)

@Entity // Dit à Spring : "Cette classe est une Entité, elle représente une table en base de données"
@Table(name = "curvepoint") // Précise le nom de la table SQL associée
@Data // Lombok : plus besoin d'écrire les Getters/Setters, ils sont créés automatiquement en mémoire
@NoArgsConstructor // Lombok : crée le constructeur par défaut sans paramètres
@AllArgsConstructor // Lombok : crée le constructeur complet
public class CurvePoint {

    @Id // Déclare que ce champ est la Clé Primaire (unique)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémentation (1, 2, 3...) gérée par la base de données
    private Integer id;

    @NotNull(message = "Curve Id est obligatoire") // Sécurité : Java refusera d'enregistrer si ce champ est vide
    private Integer curveId;

    private Timestamp asOfDate; // Date de référence pour la courbe de taux

    @NotNull(message = "Le terme est obligatoire") // Empêche la saisie d'un terme nul
    private Double term; // Durée associée au point de la courbe (ex: 1 mois, 10 ans)

    @Column(name = "curve_value") // On renomme la colonne en SQL car "VALUE" est un mot réservé qui fait planter H2/MySQL
    @NotNull(message = "La valeur est obligatoire") // Empêche la saisie d'une valeur nulle
    private Double value; // La valeur numérique du taux d'intérêt à ce point précis

    private Timestamp creationDate; // Date à laquelle l'enregistrement a été créé

    // Constructeur personnalisé (utilisé manuellement dans tes tests ou ton code)
    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}

/*
À quoi sert-il ?
C'est le Plan d'architecture des données pour les courbes de taux. Ce fichier dit à Hibernate comment transformer une ligne de ta base de données (SQL) en un objet Java que ton programme peut manipuler.

Ce qu'il faut retenir :

    Migration Jakarta : Tu as bien remplacé javax.persistence par jakarta.persistence, ce qui est indispensable pour faire tourner ce projet sur Spring Boot 3 et Java 17.

    Validation intégrée : Avec les @NotNull, tu as "blindé" ton modèle. Si un utilisateur essaie d'envoyer un point de courbe sans valeur, ton application s'arrêtera avant de faire une erreur en base de données.

    Résolution du conflit SQL : L'utilisation de @Column(name = "curve_value") est un geste de pro. Elle permet d'éviter l'erreur que nous avons eue au début, car le mot value est interdit dans beaucoup de langages SQL.
 */