package Game

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.graphics.Color

class Game(WIDTH: Int = 1920,HEIGHT: Int = 1080) extends PortableApplication(WIDTH, HEIGHT) { // On passe la taille de la fenêtre ici
  var myMaps: Maps = _
  var myTank: Tank = _

  //Lors de l'initialisation du jeu

  override def onInit(): Unit = {
    setTitle("Crude Battle")

    myMaps = new Maps(WIDTH, HEIGHT)
    myTank = new Tank()
    myMaps.initMaps()
  }


  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.LIGHT_GRAY)

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
    val deltaY = myMaps.surface(myTank.posX+myTank.height)-myMaps.surface(myTank.posX-myTank.height)
    val halfHeight: Float = myTank.height/2
    myTank.tankAngle = Math.atan2(deltaY, myTank.length).toFloat // angle du tank en radian
    val cx = (-halfHeight*Math.cos(1.57-myTank.tankAngle)).toFloat
    val cy = (halfHeight*Math.sin(1.57-myTank.tankAngle)).toFloat
    g.drawFilledRectangle(myTank.posX+cx, myMaps.surface(myTank.posX)+cy, myTank.length, myTank.height, Math.toDegrees(myTank.tankAngle).toFloat, Color.RED)

    //Draw the tourette
    myTank.updateTurretAngle()
    val hTLength = myTank.turrentLenght/2
    var dx = hTLength*Math.cos(myTank.turretAngle.toRadians)
    var dy = hTLength*Math.sin(myTank.turretAngle.toRadians)
    var turretX: Float = (myTank.posX+cx+dx).toFloat
    var turretY = (myMaps.surface(myTank.posX)+cy+dy).toFloat
    g.drawFilledRectangle(turretX,turretY , myTank.turrentLenght, myTank.turrentWidth, myTank.turretAngle, Color.BLACK)
    if (Gdx.input.isKeyPressed(Input.Keys.D)) println(s"angle : ${myTank.tankAngle}, X : $turretX, Y : $turretY --- tank : ${myTank.posX} - ${myMaps.dirt(myTank.posX)}")

    //Draw the shot
    if(myTank.shot.isFired && myTank.shot.X > -myTank.shot.Vx && myTank.shot.X < WIDTH-myTank.shot.Vx) {
      myTank.shot.updateShot()
      g.drawFilledCircle(myTank.shot.X, myTank.shot.Y, 5, Color.BLACK)
    }

    //COllision
    if(myTank.shot.Y < myMaps.surface(myTank.shot.X.toInt) && myTank.shot.isFired) {
      myTank.shot.isFired = false
      myMaps.explosion(myTank.shot.X.toInt, myMaps.surface(myTank.shot.X.toInt).toInt, 40)
    }
  }
}

