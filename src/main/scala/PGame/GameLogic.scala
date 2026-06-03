package PGame

import PGame.GameState.{AIMING, CHANGE_PLAYER, FLYING, LANDSLIDING}
import ch.hevs.gdx2d.lib.GdxGraphics

trait GameLogic {
  this: Game =>

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

  def change_player(): Unit = {
    idxActivePlayer += 1
    idxActivePlayer %= nbPlayer
    currTank = tankArray(idxActivePlayer)
    turnState = AIMING
  }

  def updateTankArray(g: GdxGraphics): Unit = {
    for (tank <- tankArray) {
      tank.updateTank()
      tank.drawTank(g)
      updateUITank(g, tank)
    }
  }

  def aiming(): Unit = {
    //  println("STATE AIMING")
    if (tankInput(currTank)) {
      currTank.shot.hasAlreadyHit = false
      turnState = FLYING
    }
  }

  def flying(g: GdxGraphics): Unit = {
    // println(("STATE FLYING"))
    currTank.shot.updateShot()
    if (currTank.shot.isFired && currTank.shot.X > -currTank.shot.Vx && currTank.shot.X < WIN_WIDTH - currTank.shot.Vx) {
      currTank.shot.drawShot(g, currTank)
    }
    if (currTank.shot.isFired && currTank.shot.X < 0 || currTank.shot.X > WIN_WIDTH) {
      turnState = CHANGE_PLAYER
      return
    }
    // COLLISION
    // Collision avec enemy tank
    collisionWithTank()
    collisionWithGround()


  }



  def collisionWithTank(): Unit = {
    for(tank <- tankArray)
      if(tank != currTank) {
        if (currTank.shot.checkCollision(tank) && !currTank.shot.hasAlreadyHit) {

          println("ENEMY TOUCHE")

          // dégâts
          tank.takeDamage(20)
          // arrêter projectile
          currTank.shot.hasAlreadyHit = true
        }
      }
  }

  def collisionWithGround(): Unit = {
    if (currTank.shot.Y < myMaps.surface(currTank.shot.X.toInt) && currTank.shot.isFired) {
      currTank.shot.isFired = false
      myMaps.explosion(currTank.shot.X.toInt, myMaps.surface(currTank.shot.X.toInt).toInt, 80)
      turnState = LANDSLIDING
    }
  }

}
