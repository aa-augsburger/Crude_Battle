# Projet CRUDE BATTLE
Augsburger - Jaures

## 1. Introduction
### 1.1 Présentation du jeu (Pitch)
### 1.2 Règles du jeu et contrôles
### 1.3 Objectifs techniques

## 2. Architecture et Choix Techniques
### 2.1 Technologies utilisées
#### 2.1.1 GDX2D
### 2.2 Structure globale du code
### 2.3 Gestion des écrans (Menus, Jeu, Game Over)

## 3. Développement du Jeu
### 3.1 Boucle principale à machine d'état, Terrain et Constantes
#### `GameLogic` (Gestion des états du jeu)
#### `Maps` (Gestion du terrain)
#### `Physic` (Trait des constantes physiques : gravité, vent, poussée)

### 3.2 Contrôles et Entrées Utilisateur
#### `GameInput` (Gestion et capture des entrées clavier)

### 3.3 Gestion des entités (Joueur, Ennemis, Projectiles)
#### `Tank` (Modèle de base pour les tanks)
#### `AutoTank` (Comportement autonome des ennemis)
#### `Weapon` (Gestion des différentes armes)
#### `Shot` (Physique des projectiles et calcul de trajectoire)
#### `Enum` (Définitions des types de données fixes)

### 3.4 Interface et Affichage graphique (Rendu)
#### `GameGUI` (Éléments d'interface utilisateur en jeu)
#### `DrawableTank` (Rendu visuel des tanks)
#### `DrawableShot` (Rendu visuel des tirs)


## 4. Tests et Problèmes
### 4.1 Liste des tests effectués
### 4.2 Bugs connus et limites du jeu

## 5. Conclusion
### 5.1 Bilan du projet (Points forts / Points faibles)
### 5.2 Améliorations futures possibles