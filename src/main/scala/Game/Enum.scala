package Game

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

object GameState extends Enumeration {
  case class GameStateVal() extends super.Val

  import scala.language.implicitConversions
  implicit def valueToPlanetVal(x: Value): GameStateVal = x.asInstanceOf[GameStateVal]


  val IN_MENU = GameStateVal
  val PLAYING = GameStateVal
  val PAUSED = GameStateVal
  val WON = GameStateVal
  val LOST = GameStateVal
}


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

  val blockSize = 4
  val Dirt = TerrainVal(1, Color.GREEN)
  val Rock = TerrainVal(3, Color.GRAY)
  val Air = TerrainVal(0, Color.BLUE)
}
