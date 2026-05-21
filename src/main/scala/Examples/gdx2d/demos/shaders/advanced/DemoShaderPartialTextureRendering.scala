package Examples.gdx2d.demos.shaders.advanced

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * Shows how to interwind shaders and normal GDX operations.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoShaderPartialTextureRendering extends PortableApplication {

  private var t = 0.0

  override def onInit(): Unit = {
    setTitle("Partial screen shader demo, mui 2013")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    if (g.getShaderRenderer == null) {
      g.setShader("examples/shader/bicolor.fp", 200, 200)
    }

    g.clear()
    g.drawFPS()
    g.drawShader(256, (256 + 128.0 * math.sin(t)).toInt, 0f)
    g.drawSchoolLogo()

    t += 0.05
  }
}

object DemoShaderPartialTextureRendering {
  def main(args: Array[String]): Unit = {
    new DemoShaderPartialTextureRendering()
  }
}
