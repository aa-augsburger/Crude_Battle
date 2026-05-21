package Game


import Game.Terrain.Air
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color


object Terrain extends Enumeration {
  case class TerrainVal(solidity: Int, color: Color) extends super.Val {
    def drawBlock(g: GdxGraphics, x: Int, y: Int): Unit = {
      g.setColor(color)
      g.drawFilledRectangle(
        x.toFloat,
        y.toFloat,
        blockSize.toFloat,
        blockSize.toFloat,
        0f
      )
    }
  }

  import scala.language.implicitConversions
  implicit def valueToPlanetVal(x: Value): TerrainVal = x.asInstanceOf[TerrainVal]

  val blockSize = 2
  val Dirt = TerrainVal(1, Color.GREEN)
  val Rock = TerrainVal(3, Color.GRAY)
  val Air = TerrainVal(0, Color.BLUE)
}


class Maps {

  val largeur: Int = 2048
  val hauteur: Int = 1080
  val tailleBloc: Int = 4
  val grille: Array[Array[Terrain.TerrainVal]] =
    Array.fill(largeur, hauteur)(Air)

  def initMaps(): Unit = {
    for (x <- 0 until largeur; y <- 0 until hauteur) {
      grille(x)(y) =
        if (y < 50) {
          Terrain.Rock
        }
        else if (y >= 50 && y < 500) {
          Terrain.Dirt
        }
        else {
          Terrain.Air
        }
    }
    println(s"Map créée : ${largeur}x${hauteur}")
  }

  def refreshMaps(g: GdxGraphics): Unit = {
    for (x <- 0 until largeur by tailleBloc;
         y <- 0 until hauteur by tailleBloc) {
      grille(x)(y).drawBlock(g, x, y)
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