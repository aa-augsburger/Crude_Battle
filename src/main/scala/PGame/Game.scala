package PGame

import PGame.GUIState.{GUIState, INIT_GAME, IN_MENU, PLAYING}
import PGame.GameState.{AIMING, FLYING, LANDSLIDING, TurnState}
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, TextButton, TextField}

class Game(WIDTH: Int = 1920, HEIGHT: Int = 1080, nbPlayer: Int = 1, nbBot: Int = 1, val debug: Boolean = true) extends PortableApplication(WIDTH, HEIGHT) with GameInput with GameGUI {

  var tour: Int = 0

  var myMaps: Maps = _
  var myTank: Tank = _
  var autoTank: Tank with AutoTank = _

  var guiState: GUIState = if(debug) INIT_GAME else IN_MENU
  var turnState: TurnState = AIMING

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
    if (debug) {
      guiState = INIT_GAME
    }
  }

  def initGame(): Unit = {
    stage.clear()
    myMaps = new Maps(WIDTH, HEIGHT)
    myTank = new Tank(300, myMaps)
    autoTank = new Tank(1500, myMaps) with AutoTank {}
    myMaps.initMaps()
    guiState = PLAYING
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    guiState match {
      case IN_MENU => if (updateStage(g)) return
      case INIT_GAME => initGame()
      case PLAYING => playing(g)
    }
  }

  def playing(g: GdxGraphics): Unit = {
    g.clear(Color.LIGHT_GRAY)

    gameInput()
    turnState match {
      case AIMING => aiming()
      case FLYING => flying(g)
      case LANDSLIDING => if (myMaps.landsliding(g, finished = false)) turnState = AIMING
    }

        myMaps.refreshMaps(g)
        myTank.drawTank(g, Color.RED)
        autoTank.updateEnemy()
        autoTank.drawTank(g, Color.GREEN)

        g.drawFPS()

  }

  def aiming(): Unit = {
    //  println("STATE AIMING")
    if (tankInput()) turnState = FLYING
  }

  def flying(g: GdxGraphics): Unit = {
    // println(("STATE FLYING"))
    if (myTank.shot.isFired && myTank.shot.X > -myTank.shot.Vx && myTank.shot.X < WIDTH - myTank.shot.Vx) {
      myTank.shot.updateShot()
      myTank.shot.drawShot(g, myTank)
    }
    // COLLISION
    if (myTank.shot.Y < myMaps.surface(myTank.shot.X.toInt) && myTank.shot.isFired) {
      myTank.shot.isFired = false
      myMaps.explosion(myTank.shot.X.toInt, myMaps.surface(myTank.shot.X.toInt).toInt, 80)
      turnState = LANDSLIDING
    }
  }


  override def onDispose(): Unit = {
    super.onDispose()
    if (stage != null) stage.dispose()
    if (skin != null) skin.dispose()
  }
}
