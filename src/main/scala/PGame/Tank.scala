package PGame

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

class Tank(initPos: Int = 300, val myMaps: Maps) extends DrawableTank {
  val length = 60
  val height = 30

  var health: Int = 400
  var isAlive: Boolean = true
  var X: Float = 0f
  var Y: Float = 0f


  val turrentLenght = 30
  val turrentWidth = 5


  var shot = new Shot()
  var posX = initPos
  var speed = 3
  var turretAngle = 0f
  var tankAngle: Float = 0

  def takeDamage(damage: Int): Unit = {
    health -= damage

    if (health <= 0) {
      health = 0
      isAlive = false
      println("Tank détruit")
    }

    println("Vie restante : " + health)
  }


  def adaptSpeedAngle(isRight: Boolean): Int = {
    val angle = if (isRight) getTankAngle(posX + length / 4) else getTankAngle(posX - length / 4)
    val absAngle = Math.abs(angle)
    println("next angle " + angle)
    var newSpeed = speed
    val maxAngle = 70
    val direction = if (isRight) 1 else -1

    //si il monte et la pente est trop raide, il est stoppé
    if (absAngle >= maxAngle) return 0

    //on détermine si c'est une montée ou une descente
    val estMonte = direction * Math.signum(angle  )

    //si il monte, il ralenti en fonction de la pente
    if (estMonte > 0) {
      val breaking = (Math.tan(absAngle.toRadians) * 2.0).toInt
      newSpeed -=  breaking
    }
    //si il desceend il accélère
    else  {
      val boost = (Math.tan(absAngle.toRadians) * 3.0).toInt
      newSpeed += boost
    }


//
//    //on acceler le tank si ca descend
//    if(isRight && angle < 1.2) newSpeed -
//    if(!isRight && angle < -1.2) newSpeed = 0
    println("new speed " + newSpeed)
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
    println("init Fire")
    shot.initFire(posX, tankY, tankAngle, turretAngle, height, turrentLenght)
  }

  def turretUp(): Unit = {
    tankAngle = getTankAngle(posX)
    if(turretAngle < tankAngle+180) turretAngle += 1
    else turretAngle = tankAngle+180
    println(turretAngle)
  }

  def turretDown(): Unit = {
    tankAngle = getTankAngle(posX)
    if(turretAngle > tankAngle) turretAngle -= 1
    else turretAngle = tankAngle
    println(turretAngle)
  }

  def updateTurretAngle(): Unit = {
    val angle = getTankAngle(posX)
    if(turretAngle < angle) turretAngle = angle
    if(turretAngle > (angle + 180)) turretAngle = angle + 180
  }

  def getTankAngle(x: Int = posX): Float = {
    val deltaY = myMaps.surface(x + length/2) - myMaps.surface(x - length/2)
    val result = Math.atan2(deltaY, length).toFloat
    result.toDegrees
  }

}