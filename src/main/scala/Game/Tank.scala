package Game

import ch.hevs.gdx2d.lib.GdxGraphics

class Tank {
  val length = 60

  var posX = 500
  var speed = 3
  var turretAngle = 0f

  def moveLeft(): Unit = {
    if(posX > speed + length/2) posX -= speed
  }

  def moveRight(): Unit = {
    if(posX < 1920-speed - length/2) posX += speed

  }

  def turretUp(): Unit = {
    turretAngle += 0.05f
  }

  def turretDown(): Unit = {
    turretAngle -= 0.05f
  }



  def getPos: Int = posX


}