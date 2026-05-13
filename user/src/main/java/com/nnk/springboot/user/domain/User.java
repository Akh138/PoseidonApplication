package com.nnk.springboot.user.domain; // Déclare l'emplacement de la classe dans le microservice "User"

// Importations des bibliothèques de persistance (Lien base de données)
import jakarta.persistence.*; // Utilise Jakarta (standard Spring Boot 3) pour transformer cette classe en table SQL
// Importation pour la validation des données (Data Integrity)
import jakarta.validation.constraints.NotBlank; // Interdit les champs vides ou remplis uniquement d'espaces
// Importations Lombok pour réduire la quantité de code répétitif
import lombok.AllArgsConstructor; // Génère automatiquement le constructeur avec tous les champs
import lombok.Data;              // Génère automatiquement les Getters, Setters, toString, equals et hashCode
import lombok.NoArgsConstructor;  // Génère automatiquement le constructeur vide (OBLIGATOIRE pour Hibernate/JPA)

@Entity // Dit à Spring : "Cette classe représente une table réelle dans ma base de données"
@Table(name = "users") // Précise que le nom de la table SQL associée est "users"
@Data // Magie de Lombok : crée tous les Getters et Setters de façon invisible pour garder le fichier court
@NoArgsConstructor // Lombok : crée le constructeur par défaut nécessaire au fonctionnement interne de JPA
@AllArgsConstructor // Lombok : crée le constructeur complet, très pratique pour créer un utilisateur rapidement
public class User {

    @Id // Déclare que ce champ est la Clé Primaire unique de la table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // La base de données gère l'ID toute seule (Auto-incrément : 1, 2, 3...)
    private Integer id;

    @NotBlank(message = "L'identifiant est obligatoire") // Sécurité : refuse l'enregistrement si l'identifiant est absent
    private String username; // Le nom d'utilisateur (utilisé pour la connexion/login)

    @NotBlank(message = "Le mot de passe est obligatoire") // Sécurité : garantit qu'un mot de passe est bien saisi
    private String password; // Le mot de passe (stocké sous forme hachée BCrypt pour la sécurité)

    @NotBlank(message = "Le nom complet est obligatoire") // Sécurité : oblige à renseigner l'identité réelle
    private String fullname; // Le prénom et nom de l'utilisateur

    @NotBlank(message = "Le rôle est obligatoire") // Sécurité : chaque utilisateur doit avoir un rôle (ex: ADMIN ou USER)
    private String role; // Le niveau d'accès accordé dans l'application

}
/*
À quoi sert-il ?
C'est le Plan de construction des comptes utilisateurs. Dans ton architecture, ce fichier définit exactement quelles informations sont nécessaires pour qu'une personne puisse accéder à la plateforme Poseidon. Chaque variable ici devient une colonne dans ta table MySQL ou H2.

Ce qu'il faut retenir :

    Le lien avec la Sécurité : C'est ce fichier que le microservice UI va venir "consulter" au moment du Login pour vérifier si l'identifiant et le mot de passe correspondent.

    L'annotation @NotBlank : C'est ton premier rempart de sécurité. Elle garantit qu'il n'y aura jamais d'utilisateur "fantôme" (sans nom ou sans mot de passe) dans ton système.

    Modernisation : En utilisant jakarta.persistence et Lombok, tu as un code conforme aux standards actuels de l'industrie, ce qui rend le projet facile à lire et à faire évoluer pour d'autres développeurs
 */