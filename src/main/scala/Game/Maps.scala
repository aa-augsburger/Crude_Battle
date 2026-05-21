package Game

import ch.hevs.gdx2d.lib.GdxGraphics


class Maps(lenght: Int = 1920, height: Int = 1080) {

  val world: Array[Float] = new Array[Float](lenght)

  def initMaps(): Unit = {
    for(x <- world.indices) {
      world(x) = 500f
    }

  }


    def refreshMaps(g: GdxGraphics) = {
      for(x <- world.indices) {
        g.drawLine(x,0, x, world(x))
      }
    }

}