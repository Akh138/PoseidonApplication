package com.nnk.springboot.rating.domain; // Déclare l'emplacement du fichier dans le microservice rating

// Importations des bibliothèques Jakarta Persistence (JPA) pour le lien avec la base de données
import jakarta.persistence.*; // Utilise Jakarta (standard Spring Boot 3) pour transformer la classe en table SQL
// Importations pour la validation des données (Data Integrity)
import jakarta.validation.constraints.NotBlank; // Interdit les textes vides ou composés uniquement d'espaces
import jakarta.validation.constraints.NotNull;   // Interdit les valeurs nulles pour les nombres
// Importations de Lombok pour simplifier le code source
import lombok.AllArgsConstructor; // Génère automatiquement le constructeur avec tous les champs
import lombok.Data;              // Génère automatiquement les Getters, Setters, toString, equals et hashCode
import lombok.NoArgsConstructor;  // Génère automatiquement le constructeur vide (obligatoire pour Hibernate/JPA)

@Entity // Dit à Spring : "Cette classe est une entité, elle représente une table dans la base de données"
@Table(name = "rating") // Précise le nom réel de la table SQL associée
@Data // Magie de Lombok : génère tout le code répétitif (getters/setters) de façon invisible
@NoArgsConstructor // Lombok : crée le constructeur sans argument nécessaire au fonctionnement interne de JPA
@AllArgsConstructor // Lombok : crée le constructeur complet pour faciliter les tests et les instanciations
public class Rating {

    @Id // Déclare que ce champ est la Clé Primaire unique de la table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // La base de données gère l'ID toute seule (Auto-incrément : 1, 2, 3...)
    private Integer id;

    @Column(name = "moodys_rating") // Mappe la variable Java vers la colonne SQL "moodys_rating" (snake_case)
    @NotBlank(message = "Moody's Rating is mandatory") // Sécurité : Java refusera d'enregistrer si le texte est absent
    private String moodysRating; // Stocke la note de l'agence Moody's

    @Column(name = "sandp_rating")
    @NotBlank(message = "S&P Rating is mandatory")
    private String sandPRating; // Stocke la note de l'agence Standard & Poor's

    @Column(name = "fitch_rating")
    @NotBlank(message = "Fitch Rating is mandatory")
    private String fitchRating; // Stocke la note de l'agence Fitch

    @Column(name = "order_number")
    @NotNull(message = "Order Number is mandatory") // Sécurité : assure qu'un chiffre est bien présent pour l'ordre d'affichage
    private Integer orderNumber; // Chiffre utilisé pour trier les notations par importance

    // Constructeur personnalisé manuel (souvent utilisé pour créer un objet sans l'ID avant de le sauver en base)
    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}

/*
À quoi sert-il ?
C'est le Modèle métier (ou Entité) pour les notations financières. Il sert de dictionnaire à Hibernate pour savoir comment transformer un objet Java en ligne SQL et vice-versa.

Ce qu'il faut retenir :

    Validation Robuste : Grâce à @NotBlank et @NotNull, tu garantis que les notations financières (qui sont des données sérieuses) seront toujours complètes. Si un utilisateur oublie une note, le système le bloque immédiatement.

    Look Moderne (Lombok) : Ce fichier est très court car tu as délégué la création des 50 lignes de Getters/Setters à Lombok. C'est le standard actuel en entreprise pour gagner en productivité.

    Naming Convention : Remarque l'utilisation de @Column(name = "..."). Elle permet de respecter les conventions Java (camelCase : moodysRating) tout en respectant les conventions SQL (snake_case : moodys_rating).
 */