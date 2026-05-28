package PGame

import PGame.GUIState.{GUIState, INIT_GAME, IN_MENU, PLAYING}
import PGame.GameState.{AIMING, FLYING, LANDSLIDING, TurnState}
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, TextButton, TextField}

class Game(val WIN_WIDTH: Int = 1920, val WIN_HEIGHT: Int = 1080, val nbPlayer: Int = 1, val nbBot: Int = 1, val debug: Boolean = true) extends PortableApplication(WIN_WIDTH, WIN_HEIGHT) with GameInput with GameGUI with GameLogic {

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
    myMaps = new Maps(WIN_WIDTH, WIN_HEIGHT)
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

    // Vie des tanks
    g.drawString(
      50,
      1000,
      "Tank Rouge HP : " + myTank.health
    )

    g.drawString(
      1400,
      1000,
      "Tank Vert HP : " + autoTank.health
    )

    // Game Over
    if (autoTank.health <= 0) {

      g.drawString(
        WIN_WIDTH / 2 - 100,
        WIN_HEIGHT / 2,
        "VICTOIRE JOUEUR"
      )
    }

    g.drawFPS()
  }



  override def onDispose(): Unit = {
    super.onDispose()
    if (stage != null) stage.dispose()
    if (skin != null) skin.dispose()
  }
}
