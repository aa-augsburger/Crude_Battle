package Game

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

class Tank extends DrawableTank {
  val length = 60
  val height = 30

  val turrentLenght = 30
  val turrentWidth = 5


  var shot = new Shot()
  var posX = 500
  var speed = 3
  var turretAngle = 0f
  var tankAngle: Float = 0

  def moveLeft(): Unit = {
    if(posX > speed + length/2) posX -= speed
  }

  def moveRight(): Unit = {
    if(posX < 1920-speed - length/2) posX += speed

  }

  def pwrUp(): Unit = {
    if(shot.Vo < 15) shot.Vo += 0.02f
    println(s"pwr ${shot.Vo}")
  }

  def pwrDown(): Unit = {
    if(shot.Vo > 0.3f) shot.Vo -= 0.02f
    println(s"pwr ${shot.Vo}")
  }

  def fire(tankY: Float): Unit = {
    print("init Fire")
    shot.initFire(posX, tankY, tankAngle, turretAngle, height, turrentLenght)
  }

  def turretUp(): Unit = {
    if(turretAngle < tankAngle.toDegrees+180) turretAngle += 1
    else turretAngle = tankAngle.toDegrees+180
    println(turretAngle)
  }

  def turretDown(): Unit = {
    if(turretAngle > tankAngle.toDegrees) turretAngle -= 1
    else turretAngle = tankAngle.toDegrees
    println(turretAngle)
  }

  def updateTurretAngle(): Unit = {
    if(turretAngle < tankAngle.toDegrees) turretAngle = tankAngle.toDegrees
    if(turretAngle > (tankAngle.toDegrees + 180)) turretAngle = tankAngle.toDegrees + 180
  }
  

}