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
  import TerrainType._
  val largeur: Int = 2048
  val hauteur: Int = 1080
  val tailleBloc: Int = 4
  private val grille: Array[Array[TerrainType]] =
    Array.fill(largeur, hauteur)(Air)
  def initMaps(): Unit = {
    for (x <- 0 until largeur;
         y <- 0 until hauteur) {
      grille(x)(y) =
        if (y < 200) {
          Roche
        }
        else if (y < 600) {
          Terre
        }
        else {
          Air
        }
    }
    println(s"Map créée : ${largeur}x${hauteur}")
  }
  def refreshMaps(g: GdxGraphics): Unit = {
    for (x <- 0 until largeur by tailleBloc;
         y <- 0 until hauteur by tailleBloc) {
      grille(x)(y) match {
        case Roche =>
          g.setColor(Color.GRAY)
          g.drawFilledRectangle(
            x.toFloat,
            y.toFloat,
            tailleBloc.toFloat,
            tailleBloc.toFloat,
            0f
          )
        case Terre =>
          g.setColor(new Color(0.5f, 0.35f, 0.2f, 1f))
          g.drawFilledRectangle(
            x.toFloat,
            y.toFloat,
            tailleBloc.toFloat,
            tailleBloc.toFloat,
            0f
          )
        case Air =>
        // videee
      }
    }
  }
  def setBlock(x: Int, y: Int, terrain: TerrainType): Unit = {
    if (x >= 0 && x < largeur &&
      y >= 0 && y < hauteur) {
      grille(x)(y) = terrain
    }
  }
  def getBlock(x: Int, y: Int): TerrainType = {
    if (x >= 0 && x < largeur &&
      y >= 0 && y < hauteur) {

      grille(x)(y)

    } else {

      Air
    }
  }
}