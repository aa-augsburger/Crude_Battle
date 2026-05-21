package Game

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

sealed trait TerrainType
object TerrainType {
  case object Air extends TerrainType
  case object Terre extends TerrainType
  case object Roche extends TerrainType
}

class Maps {
  val largeur: Int = 2048
  val hauteur: Int = 1080
  // Création du tableau 2D avec le type de terrain
  private val grille: Array[Array[TerrainType]] = Array.ofDim[TerrainType](largeur, hauteur)
  println(s"Map created with dimensions: ${grille.length}x${grille(0).length}")

  //initionalisation de la map
  def initMaps(): Unit = {
    for (x <- 0 until largeur; y <- 0 until hauteur) {
      if (y < 200) {
        grille(x)(y) = TerrainType.Roche
      } else if (y < 600) {
        grille(x)(y) = TerrainType.Terre
      } else {
        grille(x)(y) = TerrainType.Air
      }
    }
    println("Map initialisée avec succès.")
  }

  //refresh de la map
  def refreshMaps(g: GdxGraphics): Unit = {
    for (x <- 0 until largeur; y <- 0 until hauteur) {
      grille(x)(y) match {
        case TerrainType.Terre =>
          g.setColor(new Color(0.5f, 0.35f, 0.2f, 1.0f)) // Marron pour la Terre
        //g.drawPoint(x, y)
        case TerrainType.Roche =>
          g.setColor(Color.GRAY)
        //g.drawPoint(x, y)
        case TerrainType.Air =>
          // On ne dessine rien (le fond d'écran ou le ciel s'affiche derrière)
          ()
      }
    }
  }
}