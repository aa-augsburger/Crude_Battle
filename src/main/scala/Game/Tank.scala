package Game

import ch.hevs.gdx2d.lib.GdxGraphics

class Tank {
  var posX = 500
  var speed = 3
  val length = 60

  def moveLeft(): Unit = {
    if(posX > speed + length/2) posX -= speed
  }

  def moveRight(): Unit = {
    if(posX < 1920-speed - length/2) posX += speed

  }

  def getPos: Int = posX


}