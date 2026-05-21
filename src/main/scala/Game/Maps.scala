package Game

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
sealed trait TerrainType

object TerrainType {
  case object Air extends TerrainType
  case object Terre extends TerrainType
  case object Roche extends TerrainType
}
//enum TerrainType(solidite: Int, color: Color):
//case Air extends Type(0)
//case Terre extends Color(1)
//case Pierre extends Color(2)
class Maps{
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
        if (y < 10) {
          Roche
        }
        else if (y < 500) {
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
          g.setColor(Color.GREEN)
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
  //fonction destruction de terrain
  //defndestruction(x: Int, y: Int, radius: Int): Unit = {
  //  for (i <- -radius to radius;
  //       j <- -radius to radius) {
  //    val dist = math.sqrt(i * i + j * j)
  //    if (dist <= radius) {
  //      setBlock(x + i, y + j, Air)
  //    }
  //
  //
  //
}