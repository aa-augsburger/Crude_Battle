package Game

import ch.hevs.gdx2d.lib.GdxGraphics

class Tank {
  val length = 60


  var shot = new Shot()
  var posX = 500
  var speed = 3
  var turretAngle = 0f

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
    shot.initFire(posX, tankY, turretAngle)
  }

  def turretUp(): Unit = {
    if(turretAngle < 90) turretAngle += 1
    println(turretAngle)
  }

  def turretDown(): Unit = {
    if(turretAngle > -90) turretAngle -= 1
    println(turretAngle)

  }

}