package Game

import Game.Terrain.Air
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color



class Maps(lenght: Int = 1920, height: Int = 1080, blockSize: Int = 4) {
  val grille: Array[Array[Terrain.TerrainVal]] =
    Array.fill(lenght, height)(Air)

  def initMaps(): Unit = {
    for (x <- 0 until lenght; y <- 0 until height) {
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
    println(s"Map créée : ${lenght}x${height}")
  }

  def refreshMaps(g: GdxGraphics): Unit = {
    for (x <- 0 until lenght by blockSize;
         y <- 0 until height by blockSize) {
      grille(x)(y).drawBlock(g, x, y)
    }
  }


  //  fonction destruction de terrain
  def destroyMaps(g: GdxGraphics, x: Int, y: Int, radius: Int): Unit = {
    for (i <- -radius to radius;
         j <- -radius to radius) {
      val dist = math.sqrt(i * i + j * j)
      if (dist <= radius) {
        grille(x)(y).drawBlock(g, x, y)
      }

    }
  }
}