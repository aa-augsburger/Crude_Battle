package Game

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color


class Maps(length: Int, height: Int) {

  val dirt: Array[Float] = new Array[Float](length)
  val surface: Array[Float] = new Array[Float](length)
  val ceiling: Array[Float] = new Array[Float](length)

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
      surface(x) = h
      ceiling(x) = h
    }
  }

    def refreshMaps(g: GdxGraphics): Unit = {
      for(x <- dirt.indices) {
        g.setColor(Color.BLUE)
        g.drawLine(x,0, x, dirt(x))

     if(surface(x) > ceiling(x)) {
       g.setColor(Color.BLUE)
         g.drawLine(x,ceiling(x), x, surface(x))
        }
      }
    }

  def explosion(posX: Int, posY: Int, r: Int) = {
    var minX = posX - r
    var maxX = posX + r
    if (minX < 0) minX = 0
    if (maxX > length) maxX = length
    for (x <- minX to maxX) {
      val dx = Math.abs(posX - x)
      val y: Float = Math.sqrt(r * r - dx * dx).toInt
      val lowPoint = posY-y
      val highPoint = posY+y
      if(surface(x) > highPoint) {
        dirt(x) = lowPoint
        ceiling(x) = highPoint
      }
      if(surface(x) > lowPoint && surface(x) < highPoint) {
        surface(x) = lowPoint
        dirt(x) = lowPoint
        ceiling(x) = lowPoint
      }

    }

  }

}