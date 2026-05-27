package PGame

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

class Tank(initPos: Int = 300, val myMaps: Maps) extends DrawableTank {
  val length = 60
  val height = 30

  val turrentLenght = 30
  val turrentWidth = 5


  var shot = new Shot()
  var posX =  initPos
  var speed = 3
  var turretAngle = 0f
  var tankAngle: Float = 0

  def adaptSpeedAngle(isRight: Boolean): Int = {
    val angle = if(isRight) getTankAngle(posX+length/4) else getTankAngle(posX-length/4)
    println(angle)
    var newSpeed = speed
    // On bloque le tank si trop de pente
    if(isRight && angle >= 1) newSpeed = 0
    if(!isRight && angle <= -1) newSpeed = 0

//
//    //on acceler le tank si ca descend
//    if(isRight && angle < 1.2) newSpeed -
//    if(!isRight && angle < -1.2) newSpeed = 0

    newSpeed
  }

  def moveLeft(): Unit = {
    if(posX > speed + length/2) {
      posX -= adaptSpeedAngle(false)
    }
  }

  def moveRight(): Unit = {
    if(posX < 1920-speed - length/2) {
      posX += adaptSpeedAngle(true)
    }

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
    val angle = getTankAngle(posX)
    if(turretAngle < angle.toDegrees) turretAngle =angle.toDegrees
    if(turretAngle > (angle.toDegrees + 180)) turretAngle = angle.toDegrees + 180
  }

  def getTankAngle(x: Int = posX): Float = {
    val deltaY = myMaps.surface(x + length/2) - myMaps.surface(x - length/2)
    val result = Math.atan2(deltaY, length).toFloat
    result
  }

}