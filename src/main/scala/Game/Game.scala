package Game

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, TextButton, TextField}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

class Game(WIDTH: Int = 1920, HEIGHT: Int = 1080, val debug: Boolean = true) extends PortableApplication(WIDTH, HEIGHT) with GameInput with GameGUI {

  var myMaps: Maps = _
  var myTank: Tank = _
  var enemyTank: EnemyTank = _

  var stage: Stage = _
  var skin: Skin = _

  var newGameButton: TextButton = _
  var quitButton: TextButton = _
  var textArea: TextField = _

  // false = menu
  // true = jeu
  var gameStarted = false

  override def onInit(): Unit = {
    initGUI()
  }

  def initGame(): Unit = {
    gameStarted = true
    stage.clear()
    myMaps = new Maps(WIDTH, HEIGHT)
    myTank = new Tank()
    enemyTank = new EnemyTank()
    myMaps.initMaps()
  }

  def drawPlayerTank(g: GdxGraphics): Unit = {

    val deltaY = myMaps.surface(myTank.posX + myTank.height) - myMaps.surface(myTank.posX - myTank.height)

    val halfHeight: Float = myTank.height / 2

    myTank.tankAngle = Math.atan2(deltaY, myTank.length).toFloat

    val cx = (-halfHeight * Math.cos(1.57 - myTank.tankAngle)).toFloat
    val cy = (halfHeight * Math.sin(1.57 - myTank.tankAngle)).toFloat

    // Corps
    g.drawFilledRectangle(myTank.posX + cx, myMaps.surface(myTank.posX) + cy, myTank.length, myTank.height, Math.toDegrees(myTank.tankAngle).toFloat, Color.RED)

    // Tourelle
    myTank.updateTurretAngle()

    val hTLength = myTank.turrentLenght / 2

    val dx = hTLength * Math.cos(myTank.turretAngle.toRadians)
    val dy = hTLength * Math.sin(myTank.turretAngle.toRadians)
    val turretX: Float = (myTank.posX + cx + dx).toFloat
    val turretY = (myMaps.surface(myTank.posX) + cy + dy).toFloat

    g.drawFilledRectangle(turretX, turretY, myTank.turrentLenght, myTank.turrentWidth, myTank.turretAngle, Color.BLACK)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {

    g.clear(Color.LIGHT_GRAY)

    if (!gameStarted) {
      stage.act()
      stage.draw()
      g.drawStringCentered(
        getWindowHeight / 4f,
        s"Joueur : ${textArea.getText}"
      )
      g.drawFPS()
      return
    }

    tankInput()
    gameInput()

    myMaps.refreshMaps(g)
    drawPlayerTank(g)
    enemyTank.updateEnemy()
    enemyTank.draw(g, myMaps)

    if (myTank.shot.isFired && myTank.shot.X > -myTank.shot.Vx && myTank.shot.X < WIDTH - myTank.shot.Vx
    ) {

      myTank.shot.updateShot()

      g.drawFilledCircle(
        myTank.shot.X,
        myTank.shot.Y,
        5,
        Color.BLACK
      )
    }
    // COLLISION
    if (myTank.shot.Y < myMaps.surface(myTank.shot.X.toInt) && myTank.shot.isFired) {
      myTank.shot.isFired = false
      myMaps.explosion(myTank.shot.X.toInt, myMaps.surface(myTank.shot.X.toInt).toInt, 40)
    }
    g.drawFPS()
  }

  override def onDispose(): Unit = {
    super.onDispose()
    if (stage != null) stage.dispose()
    if (skin != null) skin.dispose()
  }
}