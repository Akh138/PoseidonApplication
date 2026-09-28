# 🔱 Poseidon - Trading & Risk Management Platform

Une plateforme robuste de gestion des risques et des transactions financières, conçue avec une **architecture distribuée en Microservices** pour garantir scalabilité, intégrité des données et haute disponibilité.

---
## 🏗️ Architecture Technique

> Le projet repose sur une approche **Microservices** pour une maintenance et une évolution facilitées. Chaque service est indépendant et communique via une passerelle API.
>
*   **Gateway (9090)** : Point d'accès unique pour l'utilisateur.
*   **Portail UI (8080)** : Interface web (Thymeleaf / Bootstrap).
*   **Config Server (8071)** : Centralisation des configurations via un dépôt GitHub.
*   **Consul (8500)** : Annuaire pour la découverte automatique des services.
*   **Services Métier** : BidList, Trade, CurvePoint, Rating, RuleName et User.
*   **Base de données** : MySQL (persistant) et H2 (développement).

---
## La Migration : Du Monolithe vers les Microservices

Le point de départ était un projet monolithique sous Spring Boot 2.2 et Java 8.
J'ai effectué une refonte complète pour séparer chaque domaine métier dans son propre service.


### Avant : Architecture Monolithique et après : Architecture Microservices
|                                                           Ancienne structure                                                            | Nouvelle structure |
|:---------------------------------------------------------------------------------------------------------------------------------------:| :---: |
| L'ancien projet regroupait toutes <br/>les fonctionnalités dans un seul bloc de code. <img src="./screenshots/old-monolith.png" width="400"> |Chaque service est désormais indépendant<br/> et possède son propre environnement. <img src="./screenshots/new-microservices.png" width="400"> |
---
---
## 🚀 Services Principaux

| Service | Rôle métier |
| :--- | :--- |
| **`identity-service`** | Authentification sécurisée (JWT, Spring Security). |
| **`rating-service`** | Gestion des notations et risques. |
| **`bidlist-service`** | Gestion des offres d'achat/vente (Bids). |
| **`trade-service`** | Workflow de transactions financières. |
| **`wallet-service`** | Gestion du solde et historique transactionnel. |

---

## 🛠️ Stack Technique

* **Développement :**
  ![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
  ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
  ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

* **Outils & Infrastructure :**
  ![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
  ![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
  ![IntelliJ](https://img.shields.io/badge/IntelliJ-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)

---

## 🔒 Sécurité & Intégrité
> * **Authentification :** Gestion des rôles (Dresseur/Manager/Admin) via **JWT (JSON Web Token)** et chiffrement **BCrypt**.
> * **Transactions :** Utilisation de transactions SQL atomiques pour garantir l'intégrité financière.
> * **Résilience :** Implémentation du pattern **Circuit Breaker** (Resilience4j) pour éviter les pannes en cascade.
> * **Validation :** Validation des données côté serveur avec `Spring Boot Validation`.

---
## 📸 Aperçu de l'interface

| Dashboard Principal | Gestion des Panne (Circuit Breaker) |
| :---: | :---: |
| <img src="screenshots/dashboard.png" width="400"> | <img src="screenshots/circuit-breaker.png" width="400"> |

| Liste de Données | Gestion des Rôles (403) |
| :---: | :---: |
| <img src="screenshots/list-view.png" width="400"> | <img src="screenshots/error-403.png" width="400"> |


### Installation rapide
1. Cloner le projet :
   `git clone https://github.com/Akh138/PoseidonApplication.git`

2. Compiler tous les modules :
   `mvn clean package -DskipTests`

3. Lancer l'infrastructure complète :
   `docker-compose up -d`

### Accès
*   **Portail Web** : http://localhost:9090
*   **Annuaire Consul** : http://localhost:8500
*   **Identifiants par défaut** : admin / admin123


##  Note de fin
Ce projet a été entièrement réalisé par **Habib (Akh138)**.
Il représente une étape majeure dans mon apprentissage des microservices.
Mon plus grand défi a été de configurer la communication sécurisée entre les 8 conteneurs 
et de gérer la découverte dynamique via Consul.
J'ai utilisé l'**IA comme un mentor technique**. Elle m'a accompagné pour structurer la documentation et m'a proposé des exercices de validation à chaque étape clé. Cette méthode m'a permis de m'assurer que chaque concept complexe (comme l'API Gateway, le Service Discovery avec Consul ou la résilience via Resilience4j) était parfaitement compris et maîtrisé avant d'être implémenté.
N'hésitez pas à consulter mon profil GitHub pour voir l'évolution de mes autres projets