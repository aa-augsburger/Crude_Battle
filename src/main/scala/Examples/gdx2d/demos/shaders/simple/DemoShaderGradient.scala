package Examples.gdx2d.demos.shaders.simple

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

/**
 * Shows how to interwind shaders and normal GDX operations.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class DemoShaderGradient extends PortableApplication {

  override def onInit(): Unit = {
    setTitle("Gradient shader, no animation, mui 2013")
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    if (g.getShaderRenderer == null) {
      g.setShader("examples/shader/gradient.fp")
    }

    g.clear()
    g.drawFPS()
    g.drawShader()
    g.drawSchoolLogo()
  }
}

object DemoShaderGradient {
  def main(args: Array[String]): Unit = {
    new DemoShaderGradient()
  }
}
