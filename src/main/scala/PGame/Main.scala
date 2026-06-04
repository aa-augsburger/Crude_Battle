package PGame

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics


object Main {
  def main(args: Array[String]): Unit = {
    val k = 2
    new Game(WIN_WIDTH = 1920*k, WIN_HEIGHT = 1080*k,k, 0,5, true )
  }
}