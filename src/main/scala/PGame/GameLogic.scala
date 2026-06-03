package PGame

import PGame.GameState.{AIMING, CHANGE_PLAYER, FLYING, LANDSLIDING}
import ch.hevs.gdx2d.lib.GdxGraphics

/** Cette classe gère la logique du jeu*/


trait GameLogic {
  this: Game =>

  /** Initialisation des tanks*/


  def initTank(): Unit = {
    //création des joueurs
    var pos = 200
    for(i <- 0 until nbPlayer) {
      println("on init les tanks player")

      tankArray.addOne(new Tank(pos, nameArray(i), colorArray(i), myMaps))
      pos += 500
    }
    for(i <- 0 until nbBot) {
      println("on init les tanks bot")

      tankArray.addOne(new Tank(pos, nameArray(nbPlayer + i), colorArray(nbPlayer + i), myMaps) with AutoTank {})
      pos += 200
    }
  }

  /** Quand on change de joueur */

  def change_player(): Unit = {
    idxActivePlayer += 1
    idxActivePlayer %= nbPlayer
    currTank = tankArray(idxActivePlayer)
    turnState = AIMING
  }

  /** Mise à jour de tous les tanks */


  def updateTankArray(g: GdxGraphics): Unit = {
    for (tank <- tankArray) {
      tank.updateTank()
      tank.drawTank(g)
      updateUITank(g, tank)
    }
  }

  /** */


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
    currTank.shot.updateShot()

    //Gestion des limites de la maps
    if (currTank.shot.isFired && currTank.shot.X > -currTank.shot.Vx && currTank.shot.X < WIN_WIDTH - currTank.shot.Vx) {
      currTank.shot.drawShot(g, currTank)
    }
    if (currTank.shot.isFired && currTank.shot.X < 0 || currTank.shot.X > WIN_WIDTH) {
      turnState = CHANGE_PLAYER
      return
    }
    // COLLISION
    // Collision avec les tanks ennemis et le sols
    collisionWithGround()
    collisionWithTank()

    if(currTank.shot.Y > 3000 | currTank.shot.Y < 0) turnState = CHANGE_PLAYER

  }

  /** Gestion de la collison avec un tank ennemi*/


  def collisionWithTank(): Unit = {
    for(tank <- tankArray)
      if(tank != currTank) {
        if (currTank.shot.checkCollision(tank) && !currTank.shot.hasAlreadyHit) {

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

  def collisionWithGround(): Unit = {
    if(currTank.shot.isFired) {
      if (currTank.shot.Y < myMaps.surface(currTank.shot.X.toInt) ) {
        currTank.shot.isFired = false
        myMaps.explosion(currTank.shot.X.toInt, myMaps.surface(currTank.shot.X.toInt).toInt, currTank.shot.radius)
        turnState = LANDSLIDING
      }
    }
  }

}
