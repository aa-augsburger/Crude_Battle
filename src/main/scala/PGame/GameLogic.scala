package PGame

import PGame.GameState.{FLYING, LANDSLIDING}
import ch.hevs.gdx2d.lib.GdxGraphics

trait GameLogic {
  this: Game =>

  def aiming(): Unit = {
    //  println("STATE AIMING")
    if (tankInput()) {
      myTank.shot.hasAlreadyHit = false
      turnState = FLYING
    }
  }

  def flying(g: GdxGraphics): Unit = {
    // println(("STATE FLYING"))
    if (myTank.shot.isFired && myTank.shot.X > -myTank.shot.Vx && myTank.shot.X < WIN_WIDTH - myTank.shot.Vx) {
      myTank.shot.updateShot()
      myTank.shot.drawShot(g, myTank)
    }
    // COLLISION
    // Collision avec enemy tank
    collisionWithTank()
    collisionWithGround()
  }

  private def collisionWithTank(): Unit = {
    if (myTank.shot.checkCollision(autoTank) && !myTank.shot.hasAlreadyHit) {

      println("ENEMY TOUCHE")

      // dégâts
      autoTank.takeDamage(20)
      // arrêter projectile
      myTank.shot.hasAlreadyHit = true
    }
  }

  def collisionWithGround(): Unit = {
    if (myTank.shot.Y < myMaps.surface(myTank.shot.X.toInt) && myTank.shot.isFired) {
      myTank.shot.isFired = false
      myMaps.explosion(myTank.shot.X.toInt, myMaps.surface(myTank.shot.X.toInt).toInt, 80)
      turnState = LANDSLIDING
    }
  }

}
