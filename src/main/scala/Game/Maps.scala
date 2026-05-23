package Game

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color


class Maps(length: Int, height: Int) {

  val dirt: Array[Float] = new Array[Float](length)
  val rock: Array[Float] = new Array[Float](length)

  def initMaps(): Unit = {
    //https://www.geogebra.org/graphing/yzgxvd8q
   val r = (Math.random()*10)%5+1
    val d = (Math.random()*100)
    for(x <- dirt.indices) {
      val a = (x+d) * (0.001f)*r
      val s = Math.sin(a) + Math.sin(Math.E * a) + Math.sin(Math.PI * a)
      println(s)
      val h = 400f+ s.toFloat*100f
      dirt(x) = h
    }
  }

    def refreshMaps(g: GdxGraphics): Unit = {
      for(x <- dirt.indices) {
        g.drawLine(x,0, x, dirt(x))
      }
    }


}