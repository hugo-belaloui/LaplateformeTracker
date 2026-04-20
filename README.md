# LaPlateformeTracker

LaPlateformeTracker est une application de gestion scolaire moderne conçue pour suivre les étudiants, les enseignants et les classes. Elle utilise une interface graphique JavaFX élégante basée sur le thème AtlantaFX et une base de données PostgreSQL.

## Fonctionnalités

- Système d'Authentification : Gestion des rôles (Admin, Enseignant, Étudiant).
- Tableaux de bord personnalisés :
  - Admin : Gestion globale du système.
  - Enseignant : Gestion des classes et des notes.
  - Étudiant : Consultation des notes et informations personnelles.
- Base de données persistante : Utilisation de PostgreSQL pour le stockage des données.
- Initialisation automatique : Création automatique des tables au premier lancement.
- Tests Automatisés : Suite de tests unitaires et d'intégration.
- Couverture de code : Rapports de test avec JaCoCo.

## Stack Technique

- Langage : Java 21
- Interface Graphique : JavaFX 21 avec AtlantaFX (Primer Light)
- Base de données : PostgreSQL
- Gestionnaire de dépendances : Maven
- Tests : JUnit 5, Mockito
- Qualité de code : JaCoCo (Coverage)

## Prérequis

Avant de commencer, assurez-vous d'avoir installé :
- JDK 21
- Maven
- PostgreSQL

## Configuration

1. Cloner le dépôt :
   ```bash
   git clone https://github.com/votre-utilisateur/LaplateformeTracker.git
   cd LaplateformeTracker
   ```

2. Configurer la base de données :
   - Localisez le fichier src/main/resources/database.properties.template.
   - Copiez-le et renommez-le en src/main/resources/database.properties.
   - Modifiez les informations de connexion :
     ```properties
     db.url=jdbc:postgresql://localhost:5432/votre_base_de_donnees
     db.user=votre_utilisateur
     db.password=votre_mot_de_passe
     ```

3. Créer la base de données :
   Créez une base de données vide dans PostgreSQL correspondant au nom indiqué dans votre fichier .properties.

## Build et Installation

Pour compiler le projet et installer les dépendances :
```bash
mvn clean install
```

## Exécution

Pour lancer l'application :
```bash
mvn javafx:run
```

## Tests et Qualité

### Exécuter les tests unitaires
```bash
mvn test
```

### Générer le rapport de couverture (JaCoCo)
Le rapport est généré automatiquement lors de la phase de packaging, mais vous pouvez le forcer :
```bash
mvn jacoco:report
```
Le rapport sera disponible dans target/site/jacoco/index.html.

## Structure du Projet

- src/main/java/ : Code source Java.
  - controller/ : Contrôleurs FXML.
  - model/ : Classes de données (User, Student, Teacher, ClassName).
  - db/ : Initialisation de la base de données.
  - utils/ : Gestion de la connexion, des sessions et des fenêtres.
- src/main/resources/ : Fichiers de configuration et ressources FXML.
  - View/ : Vues FXML de l'interface.
- src/test/java/ : Tests unitaires et d'intégration.

## Contributeurs

- [Hugo Belaloui](https://github.com/hugo-belaloui)
- [Andre Rodrigues Cruz](https://github.com/andrebtw)
- [Achraf Nait Belkacem](https://github.com/achraf-nait-belkacem)

## Licence

Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus de détails.
