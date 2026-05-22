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
    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) myTank.moveLeft()
    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) myTank.moveRight()
    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) myTank.turretUp()
    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) myTank.turretDown()
    if (Gdx.input.isKeyPressed(Input.Keys.Q)) myTank.pwrDown()
    if (Gdx.input.isKeyPressed(Input.Keys.W)) myTank.pwrUp()
    if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) myTank.fire(myMaps.dirt(myTank.posX))



    //Render the graphic

    if (myMaps != null) {
      myMaps.refreshMaps(g)
    }


    //Draw the tank
    var c = 5
    val deltaY = myMaps.dirt(myTank.posX+30)-myMaps.dirt(myTank.posX-30)
    val angle = Math.atan2(deltaY, myTank.length)
    c = c * Math.signum(angle).toInt
    g.drawFilledRectangle(myTank.posX-15, myMaps.dirt(myTank.posX+c), myTank.length, 30, Math.toDegrees(angle).toFloat, Color.RED)

    //Draw the tourette
    g.drawFilledRectangle(myTank.posX-15, myMaps.dirt(myTank.posX+15), 5, 30, myTank.turretAngle, Color.GREEN)

    //Draw the shot
    if(myTank.shot.isFired) {
      myTank.shot.updateShot()
      g.drawFilledCircle(myTank.shot.X, myTank.shot.Y, 5, Color.BLACK)
    }

    //COllision
    if(myTank.shot.Y < myMaps.dirt(myTank.shot.X.toInt)) myTank.shot.isFired = false

  }
}

