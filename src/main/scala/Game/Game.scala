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
    val deltaY = myMaps.dirt(myTank.posX+30)-myMaps.dirt(myTank.posX-30)
    myTank.tankAngle = Math.atan2(deltaY, myTank.length).toFloat // angle du tank en radian
    val cx = (-15f*Math.cos(1.57-myTank.tankAngle)).toFloat
    val cy = (15f*Math.sin(1.57-myTank.tankAngle)).toFloat
    g.drawFilledRectangle(myTank.posX+cx, myMaps.dirt(myTank.posX)+cy, myTank.length, 30, Math.toDegrees(myTank.tankAngle).toFloat, Color.RED)

    //Draw the tourette
    myTank.updateTurretAngle()
    var dx = 15f*Math.cos(myTank.turretAngle.toRadians)
    var dy = 15*Math.sin(myTank.turretAngle.toRadians)
    var turretX: Float = (myTank.posX+cx+dx).toFloat
    var turretY = (myMaps.dirt(myTank.posX)+cy+dy).toFloat
    g.drawFilledRectangle(turretX,turretY , 30, 5, myTank.turretAngle, Color.GREEN)
    if (Gdx.input.isKeyPressed(Input.Keys.D)) println(s"angle : ${myTank.tankAngle}, X : $turretX, Y : $turretY --- tank : ${myTank.posX} - ${myMaps.dirt(myTank.posX)}")

    //Draw the shot
    if(myTank.shot.isFired) {
      myTank.shot.updateShot()
      g.drawFilledCircle(myTank.shot.X, myTank.shot.Y, 5, Color.BLACK)
    }

    //COllision
    if(myTank.shot.Y < myMaps.dirt(myTank.shot.X.toInt)) myTank.shot.isFired = false

  }
}

