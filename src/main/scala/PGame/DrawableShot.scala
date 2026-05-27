package PGame

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color

trait DrawableShot {
  def drawShot(g: GdxGraphics, myTank: Tank) = {
    g.drawFilledCircle(
      myTank.shot.X,
      myTank.shot.Y,
      5,
      Color.BLACK
    )
  }

}
