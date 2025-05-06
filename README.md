# Backend de Banque Digitale

## Introduction

Dans l'ère numérique actuelle, la transformation digitale du secteur bancaire est devenue une nécessité incontournable. Ce projet de Backend de Banque Digitale représente une solution moderne et robuste, conçue pour répondre aux besoins croissants des institutions financières en matière de digitalisation. En utilisant les technologies Java Enterprise Edition (JEE) et Spring Boot, cette application offre une base solide pour le développement de services bancaires en ligne, combinant performance, sécurité et scalabilité.

Ceci est l'implémentation backend d'un système de Banque Digitale utilisant Spring Boot et suivant les meilleures pratiques JEE (Java Enterprise Edition).

## Aperçu du Projet

Le backend de Banque Digitale est un système bancaire robuste qui implémente diverses opérations bancaires incluant :
- Gestion des Comptes
- Gestion des Clients
- Opérations Bancaires
- Sécurité et Authentification

## Technologies Utilisées

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security
- Base de données MySQL
- Maven
- JUnit pour les Tests

## Structure du Projet

Le projet suit une architecture en couches :

```
src/main/java/com/ebanking
├── config/         # Classes de configuration
├── dto/           # Objets de Transfert de Données
├── entities/      # Entités JPA
├── enums/         # Énumérations
├── exceptions/    # Exceptions personnalisées
├── repositories/  # Repositories JPA
├── services/      # Couche logique métier
└── web/          # Contrôleurs REST
```

## Fonctionnalités Principales

1. **Gestion des Comptes**
   - Compte Courant
   - Compte Épargne
   - Opérations sur Compte (Dépôt, Retrait, Transfert)

2. **Gestion des Clients**
   - Inscription des Clients
   - Gestion des Informations Client
   - Relation Client-Compte

3. **Sécurité**
   - Contrôle d'Accès basé sur les Rôles
   - Authentification JWT
   - Gestion Sécurisée des Mots de Passe

4. **Opérations Bancaires**
   - Gestion des Transactions
   - Relevés de Compte
   - Historique des Opérations

## Conception de la Base de Données

Le projet utilise trois stratégies d'héritage principales pour les entités JPA :

1. **SINGLE_TABLE**
   - Utilisé pour les hiérarchies d'héritage simples
   - Toutes les classes de la hiérarchie sont stockées dans une seule table
   - Utilise une colonne discriminante pour différencier les types

2. **TABLE_PER_CLASS**
   - Chaque classe concrète a sa propre table
   - Toutes les propriétés de la classe sont stockées dans sa table
   - Inclut les propriétés héritées

3. **JOINED**
   - Chaque classe a sa propre table
   - Les tables sont jointes par des clés étrangères
   - Approche la plus normalisée

## Pour Commencer

### Prérequis

- Java 17 ou supérieur
- Maven 3.6 ou supérieur
- MySQL 8.0 ou supérieur

### Installation

1. Cloner le dépôt :
```bash
git clone https://github.com/Ouma49/E-banking-app.git
```

2. Configurer la base de données :
   - Créer une base de données MySQL
   - Mettre à jour `application.properties` avec vos identifiants de base de données

3. Construire le projet :
```bash
mvn clean install
```

4. Lancer l'application :
```bash
mvn spring-boot:run
```

## Documentation API

La documentation de l'API est disponible à :
```
http://localhost:8080/swagger-ui.html
```

## Tests

Exécuter les tests avec :
```bash
mvn test
```

## Conclusion

Ce projet de Backend de Banque Digitale démontre l'application des meilleures pratiques de développement JEE et Spring Boot dans le contexte bancaire. Il offre une base solide et extensible pour le développement de services bancaires numériques, avec une attention particulière portée à la sécurité, la performance et la maintenabilité. La modularité de l'architecture permet une évolution facile des fonctionnalités, tandis que la documentation complète facilite l'intégration et le déploiement. Ce projet constitue une excellente base pour le développement de solutions bancaires modernes et sécurisées.



