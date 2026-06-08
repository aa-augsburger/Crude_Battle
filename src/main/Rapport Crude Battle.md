# Projet CRUDE BATTLE
Augsburger - Jaures

## 1. Introduction
### 1.1 Présentation du jeu (Pitch)
### 1.2 Règles du jeu et contrôles
### 1.3 Objectifs techniques

## 2. Architecture et Choix Techniques
### 2.1 Technologies utilisées
#### 2.1.1 GDX2D
Nous avons utilisé la librairie GDX2D avec la langage de progrmammation Scala tel que demandé par le cahier des charges
### 2.2 Structure globale du code

### 2.3 Gestion des écrans (Menus, Jeu, Game Over)

## 3. Développement du Jeu
### 3.1 Boucle principale à machine d'état, Terrain et Constantes
#### `GameLogic` (Gestion des états du jeu)
Nous avons utilisé deux machines à états afin de structurer et gérer efficacement les différentes phases de jeu.

##### Machine à état : GuiState (gère l'interface)
Cette machine à état permet de générer le programmme de manière générale
* IN_MENU : Quand le joueur est dans le menu.
* INIT_GAME : permet d'initaliser la maps et de créer les différents joueurs 1 fois
* PLAYING : Etat pour quand le jeu est entrain dêtre jouer

##### Machine à état : turnState (gère les différentes phases de jeux)
AIMING : est appelé en boucle et gère les inputs afin que le tanl du joueur puisse se déplacer, change dangle de tir gérer sapuissance changer d'arme et tirer
* FLYING : est appelé dès que le projectiles est tiré 
* LANDSLIDING : permet de gérer les mises a joueur de la maps après un tour tel que les eboulments
* CHANG_PLAYER : permet de changer de joueur
* INIT_BOT : Est appelé une fois, permet au bot de choisir une cible et une position
* BOT_AIMING : Est appelé en boucle, permet au bot de se déplacer vers un emplacement, de tirer



#### `Maps` (Gestion du terrain)
La carte est simple tableau de floatqui conteint les valeurs des hauteurs. Nous avons fait ce choix plutot que d'un tbaleau a deux dimension afin que ca soit beaucoup plus simple et rapide à developper et plus performant à afficher.
Nous avons utilisé la manière de la superpostion des sinus tel que PI, E  avec des paramètres aléatoires afin de générer des maps différentes mais cohérentes inspiré de cette exemple https://www.geogebra.org/graphing/yzgxvd8q. 
Nous avons mis des valeurs randoms pondéré dans les paramètres afin de pouvoir générer des maps aléatoires à chaque tour.
Afin de pouvoir simuler une maps 2D avec des cavernes qui s'effrondre, nous avons utilisé trois tableaux de float à 1 
dimension. Le premier "dirt" est la hauteur de la terre
##### Table des attributs

| Attribut | Type / Signature | Description                                   |
| :--- | :--- |:----------------------------------------------|
| `WIN_WIDTH` | *Int* | Largeur de la fenetre                         |
| `WIN_HEIGHT` | *Int* | Hauteur de la fenêtre                         |
| `dirt` | *Array[Float]* | Tableau qui contient la hauteur de la terre   |
| `surface` | *Array[Float]* | Tableau qui contient la hauteur des plafonds  |
| `ceiling` | *Array[Float]* | aTableau qui contient la hauteur des surfaces |

##### Table des méthodes

| Méthode | Signature complète | Description                                                                                    |
| :--- | :--- |:-----------------------------------------------------------------------------------------------|
| `initMaps` | `initMaps(): Unit` | Cette fonction permet d'initialiser la mas                                                     |
| `refreshMaps` | `refreshMaps(g: GdxGraphics): Unit` | Cette méthode est appelé à chaque frame afin d'afficher la maps                                |
| `explosion` | `explosion(posX: Int, posY: Int, radius: Int): Unit` | Cette fonction permet de détruire la maps |
| `landsliding` | `landsliding(g: GdxGraphics, finished: Boolean): Boolean` | Cette méthode est appelé afin de gérer les éboulements des cavernes creusé par les projectiles |

#### `Physic` (Trait des constantes physiques : gravité, vent, poussée)
Ce trait permet de définir les constantes physiques tel que la gravité et le vent 

### 3.2 Contrôles et Entrées Utilisateur
#### `GameInput` (Gestion et capture des entrées clavier)
Ce trait permet de gérer les actions sur le clavier et d'appeler les bonnes fonctions en conséquence
Voici le différents mappage des touches: 
Q : diminuer la puissance de l'arme
W : augmentenre la puissance
A : CHANGER D'ARME
S : CHANGER D'ARME
Flèche haut : augmenter l'angle de tir
Flèche bas : diminuer l'angle de tir
Flèche droit : Aller à droite
Fléche gauc : Aller a gauche

### 3.3 Gestion des entités (Joueur, Ennemis, Projectiles)
#### `Tank` (Modèle de base pour les tanks)
##### Table des attributs

| Attribut | Type / Signature | Description                                                                              |
| :--- | :--- |:-----------------------------------------------------------------------------------------|
| `length` | *Int* (60) | Longeur du tank                                                                          |
| `height` | *Int* (30) | Largeur du tank                                                                          |
| `idxWeapon` | *Int* | Index de l'arme actuellemtn sélectionné                                                  |
| `health` | *Int* (100) | Vie du tank                                                                              |
| `isAlive` | *Boolean* | Flag si le tak est en vie                                                                |
| `isBot` | *Boolean* | Flag mis à true si c'est un bot                                                          |
| `weaponArray` | *Array[Weapon]* | Tableau qui contient les différents armes                                                |
| `currWeapon` | *Weapon* | Arme actuellement sélectionné                                                            |
| `turrentLenght` | *Int* (30) | Longeur de la tourette en pixel                                                          |
| `turrentWidth` | *Int* (5) | LArgent de la tourette en pixel                                                          |
| `shot` | *Shot* | Tir du tank                                                                              |
| `posX` | *Int* | Position horizontale du tank                                                             |
| `posY` | *Float* | Positon vertical du tank                                                                 |
| `speed` | *Int* (3) | Vitesse du tank                                                                          |
| `tankAngle` | *Float* | Angle du tank sur la maps                                                                |
| `turretAngle` | *Float* | Angle de la tourette                                                                     |
| `currRound` | *Int* | Utilisé lors des armes qui tire plusieurs coups, permet de savoir quel est le tir actuel |

##### Table des méthodes

| Méthode | Signature complète | Description                                                                                                           |
| :--- | :--- |:----------------------------------------------------------------------------------------------------------------------|
| `updateTank` | `updateTank(): Unit` | Est appelé pour mettre a jour la position vertical du tank                                                            |
| `takeDamage` | `takeDamage(damage: Int): Unit` | Permet au tank de dprendre des dégat et de diminuer la vie                                                            |
| `adaptSpeedAngle` | `adaptSpeedAngle(isRight: Boolean): Int` | Permet au tank de ralentir et daccélrer en fonction de la pente                                                       |
| `prevWeapon` | `prevWeapon(): Unit` | Permet de choisir l'arme précédente                                                                                   |
| `nextWeapon` | `nextWeapon(): Unit` | Permet de choisir l'arme suivante                                                                                     |
| `moveLeft` | `moveLeft(): Unit` | Permet de déplacer le tank à gauche                                                                                   |
| `moveRight` | `moveRight(): Unit` | Permet de déplacer le tank à droite                                                                                   |
| `pwrUp` | `pwrUp(): Unit` | Permet de dimunuer la puissane                                                                                        |
| `pwrDown` | `pwrDown(): Unit` | Permet d0augmenter la pzuissance                                                                                      |
| `fire` | `fire(tankY: Float): Unit` | Permet au tank de tirer                                                                                               |
| `getPower` | `getPower: Float` | Permet d'obtenir la valeur de la puissance normalisé pour le tir afin que le joueur puissre la choisir entre 0 et 100 |
| `turretUp` | `turretUp(): Unit` | Permet d'augmenter l'angle de la tourette                                                                             |
| `turretDown` | `turretDown(): Unit` | Permet de diminuer l'angle de la tourette<br/>                                                                        |
| `updateTurretAngle` | `updateTurretAngle(): Unit` | Permet de mettre a jour l'angle de la tourette                                                                        |
| `getTankAngle` | `getTankAngle(x: Int): Float` | Permet d'obtenir l'angle du tanl sur la maps                                                                          |


#### `AutoTank` (Comportement autonome des ennemis)


#### `Weapon` (Gestion des différentes armes)

Cette classe permet de gérer les différents armes, nous avons fait le choix d'impléemter trois armes au fonctionnalité très varié a l aphysique très difgérentes, 1 canon lourd, 1 laser en ligne droit et une mitralieuse

##### Table des attributs (Classe de base `Weapon`)

| Attribut | Type / Signature | Description                                          |
| :--- | :--- |:-----------------------------------------------------|
| `name` | *String* | nom affiché de larme                                 |
| `damage` | *Int* | degat de l'arme contre un tank ennemi                |
| `weight` | *Float* | poid de l'arme, cela influence la trajectoire en vol |
| `blastRadius` | *Int* | Rayon de dégat de l'arme                             |
| `speed` | *Float* | Vitesse initalie de l'arme                           |
| `round` | *Int* (1) | Nombre de tir que fait l'arme a chaque tour          |
| `trace` | *Boolean* (false) |                                                      |
| `pwrIsSettable` | *Boolean* (true) | Permet de savoir si la puissance est modifiable      |
| `minPwr` | *Float* (0f) | Puissance minimal de l'arme                          |
| `maxPwr` | *Float* (15f) | Puissance maximal de l'arme                          |
| `power` | *Float* (10f) | Puissance actuelle de larme                          |
| `multipleRound` | *Boolean* (false) | Flag si c'est une arme à  tir multiple               |

##### Liste des classes dérivées (Spécifications des armes)
Ce tableau regroupe les configurations uniques de chaque arme implémentée en jeu, mettant en évidence leurs comportements et leurs équilibrages physiques.

| Arme (`name`) | Dégâts (`damage`) | Poids (`weight`) | Rayon d'explosion (`blastRadius`) | Vitesse (`speed`) | Tirs par salve (`round`) | Puissance Min/Max (`minPwr` / `maxPwr`) | Rafale unique (`multipleRound`) | Affichage trajectoire (`trace`) |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Laser** | 25 | 0.0f | 10 | 20.0f | 1 | 50.0f / 60.0f | *False* | *True* |
| **Canon** | 50 | 2.0f | 100 | 10.0f | 1 | 2.0f / 15.0f | *False* | *True* |
| **MachineGun** | 10 | 1.5f | 20 | 10.0f | 3 | 15.0f / 30.0f | *True* | *True* |

#### `Shot` (Physique des projectiles et calcul de trajectoire)
Cette classe permet de gérer les projectiles. Nous avons utilisé les formules trignométrique de la ballistiques afin de determiner 
les vitesses verticales et horitontale en fonction de langle du cannon
A chaque frame, la position en mis  ajour en fonction des deux vitesses. La gravité( qui dépend proporitonnlemment  du poid u prijectil) influence la vitesse verticale,
Le vent (qui dépend de l'inverse du poid) nmodifie la vitesse verticale

##### Table des attributs

| Attribut | Type / Signature | Description                                                    |
| :--- | :--- |:---------------------------------------------------------------|
| `X` | *Float* | Position horizontale du tir                                    |
| `Y` | *Float* | Position vertical du tir                                       |
| `Vx` | *Float* | Vitesse horizontale                                            |
| `Vy` | *Float* | Vitesse verticale                                              |
| `Vo` | *Float* (5f) | Vitesse initiale du project                                    |
| `weight` | *Float* | Poid du projectif                                              |
| `radius` | *Int* | Rayon de l'explosion                                           |
| `damage` | *Int* | Dégat du tir contre un tank ennemi                             |
| `hasAlreadyHit` | *Boolean* (false) | Flag mis a True une fois que le projectile  a touché une cible |
| `isFired` | *Boolean* (false) | Flag mis a True une fois que le projectile est tiré            |

##### Table des méthodes

| Méthode | Signature complète | Description                                                                                                               |
| :--- | :--- |:--------------------------------------------------------------------------------------------------------------------------|
| `initFire` | `initFire(tankX: Float, tankY: Float, tankAngleDeg: Float, turretAngleDeg: Float, tankLenght: Float, turrentLenght: Float, pPower: Float, pWeight: Float, pDamage: Int, pRadius: Int): Unit` |                                                                                                                           |
| `updateShot` | `updateShot(): Unit` | Appelé à chaque frame, elle permet de mettre a jour la position du projectil en fonction de la gravité, du poids, du vent |
| `checkCollision` | `checkCollision(tank: Tank): Boolean` |                                                                                                                           |

#### `Enum` (Définitions des types de données fixes)
Ce fichier gère les différentes énumérations


### 3.4 Interface et Affichage graphique (Rendu)
#### `GameGUI` (Éléments d'interface utilisateur en jeu)
##### Table des attributs

| Attribut | Type / Signature | Description |
| :--- | :--- | :--- |
| `optimus40` | *BitmapFont* | |

##### Table des méthodes

| Méthode | Signature complète | Description |
| :--- | :--- | :--- |
| `initGUI` | `initGUI(): Unit` | |
| `initButton` | `initButton: Unit` | |
| `initFont` | `initFont(): Unit` | |
| `updateStage` | `updateStage(g: GdxGraphics): Boolean` | |
| `updateGUIGame` | `updateGUIGame(g: GdxGraphics, currTank: Tank): Unit` | |
| `updateUITank` | `updateUITank(g: GdxGraphics, tank: Tank): Unit` | |

#### `DrawableTank` (Rendu visuel des tanks)

Ce trait permet de dessiner le tanl sur la map. Afin d'afficher le tank à la bonne hauteur et position sur la maps, nous
du utilisé les fomrules trigonométrique suivantes afin de décaler le tanl proportioellement en fonction de langle
##### Table des méthodes
| Méthode | Signature complète | Description                                            |
| :--- | :--- |:-------------------------------------------------------|
| `drawTank` | `drawTank(g: GdxGraphics): Unit` | Permet de dessiner le rectangle du tank à chaque frame |
#### `DrawableShot` (Rendu visuel des tirs)
Ce trait permet de dessiner le tir 
##### Table des méthodes

| Méthode | Signature complète | Description |
| :--- | :--- | :--- |
| `drawShot` | `drawShot(g: GdxGraphics, myTank: Tank): Unit` | |


## 4. Tests et Problèmes
### 4.1 Liste des tests effectués
### 4.2 Bugs connus et limites du jeu

## 5. Conclusion
### 5.1 Bilan du projet (Points forts / Points faibles)
### 5.2 Améliorations futures possibles