import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.desktop.PortableApplication

/**
 * A very simple demonstration on how to display something animated with the library
 *
 * @author Pierre-André Mudry (mui)
 * @author Steve Devènes (dst)
 * @version 1.0
 */
class DemoSimpleAnimation() extends PortableApplication {
  var radius = 6
  var speed = 1
  var screenHeight, screenWidth = 0

  override def onInit(): Unit = {
    // Sets the window title
    setTitle("Simple demo, mui 2013")

    screenHeight = Gdx.graphics.getHeight
    screenWidth = Gdx.graphics.getWidth
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    // Clears the screen
    g.clear()
    g.drawAntiAliasedCircle(screenWidth / 2, screenHeight / 2, radius, Color.BLUE)

    // If reaching max or min size, invert the growing direction
    if (radius >= 100 || radius <= 3) {
      speed *= -1
    }

    // Modify the radius
    radius += speed

    g.drawSchoolLogo()
  }
}

object DemoSimpleAnimation extends App{
  new DemoSimpleAnimation()
}