package PGame

import PGame.GameState.{AIMING, BOT_AIMING, INIT_BOT, CHANGE_PLAYER, FLYING, LANDSLIDING}
import ch.hevs.gdx2d.lib.GdxGraphics

/** Cette classe gère la logique du jeu*/


trait GameLogic {
  this: Game =>

  /** Initialisation des tanks */


  def initTank(): Unit = {
    //création des joueurs
    var pos = 200
    for (i <- 0 until nbPlayer) {
      println("on init les tanks player")

      tankArray.addOne(new Tank(pos, nameArray(i), colorArray(i), myMaps))
      pos += 500
    }
    for (i <- 0 until nbBot) {
      println("on init les tanks bot")

      tankArray.addOne(new Tank(pos, nameArray(nbPlayer + i), colorArray(nbPlayer + i), myMaps) with AutoTank {})
      pos += 200
    }
  }

  /** Vérifie si la partie est terminée (un seul tank encore en vie).
   * Si oui, désigne le gagnant et passe à l'écran GAME OVER. */

  def checkVictory(): Boolean = {
    val aliveTanks = tankArray.filter(_.isAlive)
    if (tankArray.size > 1 && aliveTanks.size <= 1) {
      winner = if (aliveTanks.nonEmpty) aliveTanks.head else currTank
      println("VICTOIRE DE : " + winner.tankName)
      guiState = GUIState.WON
      return true
    }
    false
  }

  /** Quand on change de joueur */

  def change_player(): Unit = {
    // si la partie est finie, on ne change plus de joueur
    if (checkVictory()) return

    // on passe au joueur suivant en sautant les tanks détruits
    do {
      idxActivePlayer += 1
      idxActivePlayer %= tankArray.size
    } while (!tankArray(idxActivePlayer).isAlive)

    currTank = tankArray(idxActivePlayer)
    if(idxActivePlayer < nbPlayer) {
      turnState = AIMING
    }
    else {
      turnState = INIT_BOT
    }
  }
//Cette fonction permet d'initalisation le bot selon le type
  def init_bot(): Unit = {
    currTank match {
      case bot: AutoTank => {
        //println("INIT BOT PLAYER")
        val offensivePer = bot.health
        bot.nextEnnemy = bot.chooseEnnemy(tankArray)
        bot.nextPosX = bot.bestPos(offensivePer)
        }
      }
    turnState = BOT_AIMING
  }

  //Cette fonction est appelé quand c'est au tour du bot de tire

  def bot_aiming(): Unit = {
    currTank match {
      case bot: AutoTank => {
      //  println("BOT PLAYER")
        if (bot.moveTo(bot.nextPosX)) {
          val distX = bot.nextEnnemy.posX-bot.posX
          val distY = bot.nextEnnemy.posY-bot.posY
          val dist = Math.sqrt(distX*distX + distY * distY)
          println("dist  " + dist)
          bot.turretAngle = (Math.signum(distX)*Math.atan2(distY, distX).toDegrees).toFloat
          bot.updateTurretAngle()
          val ratio = (bot.currWeapon.maxPwr - bot.currWeapon.minPwr) / 100
          bot.currWeapon.power = ratio * (dist.toFloat*500/myMaps.WIN_WIDTH) + bot.currWeapon.minPwr
          bot.fire(myMaps.surface(bot.posX))
          bot.shot.hasAlreadyHit = false
          turnState = FLYING
        }
      }
      }
    }


  /** Mise à jour de tous les tanks */
  def updateTankArray(g: GdxGraphics): Unit = {
    for (tank <- tankArray) {
      tank.updateTank()
      tank.drawTank(g)
      updateUITank(g, tank)
    }
  }

  /** Appelé quand c'est au tour de joueur*/


  def aiming(): Unit = {
    //  println("STATE AIMING")
    if (tankInput(currTank)) {
      currTank.shot.hasAlreadyHit = false
      turnState = FLYING
    }
  }


  /** Méthode appelé quand un tir est effectué */

  def flying(g: GdxGraphics): Unit = {
    // println(("STATE FLYING"))
    if (currTank.shot.isFired) {
      currTank.shot.updateShot()

      //Gestion des limites de la maps
      if (currTank.shot.isFired && currTank.shot.X > -currTank.shot.Vx && currTank.shot.X < WIN_WIDTH - currTank.shot.Vx) {
        currTank.shot.drawShot(g, currTank)
      }
      if (currTank.shot.X < 0 || currTank.shot.X > WIN_WIDTH) {
        turnState = CHANGE_PLAYER
        return
      }
      if (currTank.shot.Y > 3000 | currTank.shot.Y < 0) turnState = CHANGE_PLAYER
      // COLLISION
      // on check les collisions avec les tanks ennemis et le sols
      collisionWithGround()
      collisionWithTank()


    }
    else {
      if (currTank.currRound < currTank.currWeapon.round && currTank.currWeapon.multipleRound) {
        if(myMaps.landsliding(g, false)) {
          currTank.shot.hasAlreadyHit = false
          println("il reste encore des balles à tirer")
          currTank.currRound += 1 // si il reste encore des balles à tirer
          currTank.currWeapon.power -= 5
          currTank.turretAngle -= 1
          currTank.shot.initFire(currTank.posX, currTank.posY, currTank.tankAngle, currTank.turretAngle, currTank.height, currTank.turrentLenght, currTank.getPower, currTank.currWeapon.weight, currTank.currWeapon.damage, currTank.currWeapon.blastRadius)
        }
      }

      else {
        turnState = LANDSLIDING
      }

    }

    /** Gestion de la collison avec un tank ennemi */


    def collisionWithTank(): Unit = {
      for (tank <- tankArray)
        if (tank != currTank && tank.isAlive) {
          if (currTank.shot.checkCollision(tank) && !currTank.shot.hasAlreadyHit) { //on check la collision si le tank n'a encore pas touché un ennemi

            println("ENEMY TOUCHE")

            // dégâts
            tank.takeDamage(currTank.shot.damage)
            // arrêter projectile
            currTank.shot.isFired = false
            currTank.shot.hasAlreadyHit = true
            val collisionX = (currTank.shot.X).toInt
            val collisionY = (currTank.shot.Y).toInt
            myMaps.explosion(collisionX, collisionY, currTank.shot.radius)
          }
        }
    }

    /** Gestion de la  collision avec le sol */


    def collisionWithGround(): Unit = {
      if (currTank.shot.isFired) {
        if (currTank.shot.Y < myMaps.surface(currTank.shot.X.toInt)) {
          currTank.shot.isFired = false
          myMaps.explosion(currTank.shot.X.toInt, myMaps.surface(currTank.shot.X.toInt).toInt, currTank.shot.radius)
          if (currTank.currRound == currTank.currWeapon.round) turnState = LANDSLIDING
        }
      }
    }
  }
}
