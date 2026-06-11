# CRUDE BATTLE

CRUDE BATTLE est un jeu 2D inspiré de Scorched Earth et Worms développé en Scala avec la bibliothèque GDX2D.

Le joueur contrôle un tank sur un terrain généré aléatoirement et destructible. Le but est d'éliminer les tanks adverses en utilisant différentes armes tout en tenant compte de la gravité, du vent et de la trajectoire des projectiles.

## Fonctionnalités

- Terrain généré procéduralement à partir de fonctions sinusoïdales
- Terrain destructible avec gestion des éboulements
- Physique balistique réaliste
- Point de vie et condition de victoire pour le tank
- Plusieurs armes aux comportements différents
- Mode joueur contre joueur
- Mode joueur contre bot
- Intelligence artificielle pour les tanks ennemis
- Gestion du vent et de la gravité
- Interface graphique réalisée avec GDX2D

## Contrôles

| **Touche** | **Action** |
| --- | --- |
| Flèche gauche | Déplacer le tank à gauche |
| Flèche droite | Déplacer le tank à droite |
| Flèche haut | Augmenter l'angle de tir |
| Flèche bas | Diminuer l'angle de tir |
| Q | Diminuer la puissance |
| W | Augmenter la puissance |
| A | Arme précédente |
| S | Arme suivante |
| Espace | Tirer |

<img width="1924" height="1134" alt="image" src="https://github.com/user-attachments/assets/681b59e4-a2fe-47a4-ab5c-aa2fb876e294" />


## Technologies utilisées

- Scala
- SBT
- GDX2D
- Programmation orientée objet
- Machines à états
- Calculs trigonométriques et balistiques

## Lancement du projet

```bash
git clone <repository>
cd crude-battle
```
Cloner le projet puis l'ouvrir le projet dans IntelliJ IDEA puis exécuter la classe principale "Main".

## Architecture

Le projet est composé de plusieurs modules :

- `GameLogic` : gestion des états du jeu
  - `GameInput` : gestion des entrées/sorties
- `Maps` : génération et destruction de la map
- `Tank` : gestion des joueurs 
- `AutoTank` : Gestion des bots
- `Weapon` : gestion des armes
- `Shot` : calcul des trajectoires
- `GameGUI` : interface utilisateur

## Améliorations futures

- Nouvelles armes et bonus
- Effets visuels avancés
- Multijoueur en réseau
- Cartes plus complexes (bruit de Perlin)
- Meilleure intelligence artificielle pour le bot

## Auteurs

- Antoine Augsburger
- Benjamin Jaures
