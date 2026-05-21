package Game

import ch.hevs.gdx2d.lib.GdxGraphics

class Tank {
  var posX = 500
  var speed = 3

  def moveLeft(): Unit = {
    if(posX > speed) posX -= speed
  }

  def moveRight(): Unit = {
    if(posX < 1920-speed) posX += speed

  }

  def getPos: Int = posX


}