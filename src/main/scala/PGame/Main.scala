package PGame

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics


object Main {
  def main(args: Array[String]): Unit = {
    new Game(WIN_WIDTH = 1920, WIN_HEIGHT = 1080, 3,0, true )
  }
}