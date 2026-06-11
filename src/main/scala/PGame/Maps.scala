package PGame

import PGame.GameState.AIMING
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color


class Maps(length: Int, height: Int, k:Int) {

  val WIN_WIDTH = length
  val WIN_HEIGHT = height
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
     // println(s)
      val h = 400f*k+ s.toFloat*100f
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

  //Cette fonction permet de faire explosion à une position X donné selon un rayon donné

  def explosion(posX: Int, posY: Int, radius:Int) = {
    // Calcul des positions minimum et maximum de l'explosion selon le rayon
    var minX = posX - radius
    var maxX = posX + radius
    //Gestion des cas limites des bordures
    if (minX < 0) minX = 0
    if (maxX > length) maxX = length
    // On itére chaque positon dans le rayon de l'explosion
    for (x <- minX to maxX) {
      //
      val dx = Math.abs(posX - x)
      //La distance verticale selon le point x est calculé selon pythagore
      val y: Float = Math.sqrt(radius * radius - dx * dx).toInt
      //Calcul du point bas et du haut
      val lowPoint = posY-y
      val highPoint = posY+y
      //Si la surface est plus haute que le point haut, on doit créer une grotte
      if(surface(x) > highPoint) {
        dirt(x) = lowPoint //on met la terre au point bas
        ceiling(x) = highPoint //on met le plafond au point haut
      }
      //si la surface est plus haut que le point bas, on doit juste creuser sans créer de grotte
      if(surface(x) > lowPoint && surface(x) < highPoint) {
        surface(x) = lowPoint
        dirt(x) = lowPoint
        ceiling(x) = lowPoint
      }
    }
  }

  //Cette fonction permet de créer un éboulement
  def landsliding(g: GdxGraphics, finished: Boolean): Boolean = {
    // println(("STATE LANDSLING")
    val speed = 1 //vitesse de l'eboulement
    var sameLevel: Boolean = true
    //on itère toutes les colonnes de la surface
    for (x <- surface.indices ) {
      //Si il reste encore des grottes
      if (ceiling(x) < surface(x)) {
        sameLevel = false
        val qtn = surface(x) - ceiling(x) //on calcule la hauteur du plafond
        val newLevel = dirt(x) + qtn //et on calcule le nouveau niveau de la terre
        //tant que la surface est plus grande que le nouveau niveau
        if (surface(x) > newLevel) {  //
          surface(x) -= speed //on réduit la hauteur de la surface et du plafond
          ceiling(x) -= speed
          dirt(x) += speed //et on la rajoute a la terre
        }
        else { //pour être sur que tout est au meme niveau
          dirt(x) = newLevel
          surface(x) = newLevel
          ceiling(x) = newLevel
        }

     //  println(s"il y a du plafond a effronder  $qtn")
      }

    }
    sameLevel //Boolean qui permet de savoir quand tout est au meme niveau
  }
}