package Game

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.graphics.Color

class Game extends PortableApplication(1920, 1080) { // On passe la taille de la fenêtre ici
  var myMaps: Maps = _
  var myTank: Tank = _

  //Lors de l'initialisation du jeu

  override def onInit(): Unit = {
    setTitle("Crude Battle")
    myMaps = new Maps()
    myTank = new Tank()
    myMaps.initMaps()
  }


  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.BLUE)

    //Check input
    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
      myTank.moveLeft()
    } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
      myTank.moveRight()
    }


    //Render the graphic

    if (myMaps != null) {
      myMaps.refreshMaps(g)
    }

    //Draw the tank
    var c = 5
    val deltaY = myMaps.dirt(myTank.posX+30)-myMaps.dirt(myTank.posX-30)
    val angle = Math.atan2(deltaY, myTank.length)
    println(Math.signum(angle))
    c = c * Math.signum(angle).toInt
    g.drawFilledRectangle(myTank.getPos-15, myMaps.dirt(myTank.getPos+c), myTank.length, 30, Math.toDegrees(angle).toFloat, Color.RED)

    //Draw the tourette

  }
}

