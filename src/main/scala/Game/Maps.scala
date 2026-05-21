package Game

import ch.hevs.gdx2d.lib.GdxGraphics


class Maps(length: Int = 1920, height: Int = 1080) {

  val world: Array[Float] = new Array[Float](length)

  def initMaps(): Unit = {
   val r = (Math.random()*10)%5+1
    for(x <- world.indices) {
      val a = x * (0.001f)*r
      val s = Math.sin(a) + Math.sin(Math.E * a) + Math.sin(Math.PI * a)
      println(s)
      world(x) = 400f + s.toFloat*100f
    }
  }


    def refreshMaps(g: GdxGraphics) = {
      for(x <- world.indices) {
        g.drawLine(x,0, x, world(x))
      }
    }

}