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



  override def onGraphicRender(g: GdxGraphics): Unit = {

    g.clear(Color.LIGHT_GRAY)

    if (updateStage(g)) return

    tankInput()
    gameInput()

    myMaps.refreshMaps(g)
    myTank.drawTank(g, myMaps, Color.RED)
    enemyTank.updateEnemy()
    enemyTank.drawTank(g, myMaps, Color.GREEN)

    if (myTank.shot.isFired && myTank.shot.X > -myTank.shot.Vx && myTank.shot.X < WIDTH - myTank.shot.Vx) {
      myTank.shot.updateShot()
      myTank.shot.drawShot(g, myTank)
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