package Game

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

class Main extends PortableApplication(1920, 1080) { // On passe la taille de la fenêtre ici
  var monTerrain: Maps = _

  override def onInit(): Unit = {
    setTitle("Crude Battle")
    monTerrain = new Maps()
    monTerrain.initMaps()
  }
  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()
    if (monTerrain != null) {
      monTerrain.refreshMaps(g)
    }
  }
}
object Main {
  def main(args: Array[String]): Unit = {
    new Main()
  }
}