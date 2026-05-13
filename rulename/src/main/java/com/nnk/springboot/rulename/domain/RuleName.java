package com.nnk.springboot.rulename.domain; // Déclare l'emplacement du fichier dans le microservice

// Importations des bibliothèques de persistance (Jakarta remplace javax pour Spring Boot 3)
import jakarta.persistence.*;
// Importation pour interdire les champs vides (chaînes de caractères)
import jakarta.validation.constraints.NotBlank;
// Importations Lombok pour supprimer le code répétitif
import lombok.*;

@Entity // Dit à Spring : "Cette classe représente une table dans ma base de données"
@Table(name = "rulename") // Définit le nom exact de la table dans le système SQL
@Data // Magie de Lombok : génère Getters, Setters, toString, equals et hashCode automatiquement
@NoArgsConstructor // Génère le constructeur vide (obligatoire pour le fonctionnement de JPA/Hibernate)
@AllArgsConstructor // Génère le constructeur avec tous les champs (utile pour créer des objets complets)
public class RuleName {

    @Id // Déclare ce champ comme étant la Clé Primaire de la table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Laisse la base de données gérer l'ID (Auto-incrément : 1, 2, 3...)
    private Integer id;

    @NotBlank(message = "Name is mandatory") // Sécurité : Java refusera d'enregistrer si le nom est vide
    @Column(name = "name") // Mappe la variable Java vers la colonne SQL "name"
    private String name; // Stocke le nom de la règle métier

    @NotBlank(message = "Description is mandatory") // Sécurité : assure qu'une explication de la règle est fournie
    @Column(name = "description") // Mappe vers la colonne SQL "description"
    private String description; // Stocke la description textuelle de la règle

    // --- CHAMPS TECHNIQUES (Non obligatoires pour la validation simple) ---
    private String json; // Stocke d'éventuelles configurations au format JSON
    private String template; // Stocke le modèle de texte associé à la règle
    private String sqlStr; // Stocke la chaîne de caractères SQL complète
    private String sqlPart; // Stocke un fragment de code SQL
}

/*
À quoi sert-il ?
C'est le Modèle des règles de gestion. Dans l'application Poseidon, ce fichier sert de "dictionnaire" technique. Il définit comment sont stockées les règles qui régissent les échanges financiers. Chaque variable de cette classe devient une colonne dans ta table rulename.

Ce qu'il faut retenir :

    L'annotation @Data : C'est ce qui rend ton projet "pro". Au lieu d'avoir un fichier de 150 lignes avec des Getters/Setters partout, tu as un code court, lisible et moderne.

    L'annotation @NotBlank : C'est la garde-barrière. Elle garantit que tes données métier les plus importantes (name et description) ne seront jamais corrompues par des textes vides.

    Standardisation : En utilisant jakarta.persistence, le code est mis aux dernières normes mondiales de développement Java (Spring Boot 3).
 */