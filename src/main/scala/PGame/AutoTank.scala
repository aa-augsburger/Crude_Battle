package PGame

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

trait AutoTank {

  this: Tank =>

  val isMovable: Boolean = true
  speed = 0
  private var direction = 1

  def updateEnemy(): Unit = {

    posX += direction * speed

    if(isMovable) {
      speed = 2
      if (posX < 1200) direction = 1
      if (posX > 1700) direction = -1
    }

    turretAngle += 0.3f
  }


}