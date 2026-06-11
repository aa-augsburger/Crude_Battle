package PGame

import PGame.GUIState.{GUIState, INIT_GAME, IN_MENU, PLAYING, WON}
import PGame.GameState.{AIMING, BOT_AIMING, CHANGE_PLAYER, FLYING, INIT_BOT, LANDSLIDING, TurnState}
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, TextButton, TextField}
import com.sun.tools.javac.code.TypeTag

import scala.collection.mutable.ArrayBuffer

class Game(val WIN_WIDTH: Int = 1920, val WIN_HEIGHT: Int = 1080,k: Int, var nbPlayer: Int = 3, var nbBot: Int = 1, val debug: Boolean = true) extends PortableApplication(WIN_WIDTH, WIN_HEIGHT) with GameInput with GameGUI with GameLogic with Physic {

  var idxActivePlayer: Int = 0

  var myMaps: Maps = _
  val colorArray: Array[Color] = Array(Color.GREEN,Color.MAGENTA, Color.YELLOW, Color.CYAN, Color.PINK, Color.DARK_GRAY)
  val nameArray: Array[String] = Array("Beast", "Muitank", "Princess", "Ultra2000", "Michel", "Gustav")

  val tankArray: ArrayBuffer[Tank] = ArrayBuffer[Tank]()
  var currTank : Tank = _
  var winner: Tank = _ // le tank gagnant de la partie
  var guiState: GUIState = if(debug) INIT_GAME else IN_MENU
  var turnState: TurnState = AIMING

  var stage: Stage = _
  var skin: Skin = _

  var newGameButton: TextButton = _
  var quitButton: TextButton = _
  var playerName: TextField = _
  var nbPlayerField: TextField = _
  var nbBotField: TextField = _
  var windField: TextField = _

  // false = menu
  // true = jeu
  var gameStarted = false

  override def onInit(): Unit = {
    initGUI()
    if (debug) {
      guiState = IN_MENU
    }
  }

  /**Initialisation du programme */

  def initGame(g: GdxGraphics): Unit = {
    stage.clear()
    myMaps = new Maps(WIN_WIDTH, WIN_HEIGHT, k)

    myMaps.initMaps()
    initTank()
    currTank = tankArray(0)
    if(nbPlayer == 0) turnState = INIT_BOT
    guiState = PLAYING
  }

  /** Cette fonction est appelé 60 fois par seconde */

  override def onGraphicRender(g: GdxGraphics): Unit = {
    guiState match {
      case IN_MENU => if (updateStage(g)) return
      case INIT_GAME => initGame(g)
      case PLAYING => playing(g)
      case WON => gameOver(g)
    }
  }

  /** Écran de fin de partie : affiche l'image Game Over et le gagnant */

  def gameOver(g: GdxGraphics): Unit = {
    g.clear(Color.LIGHT_GRAY)
    myMaps.refreshMaps(g)
    updateTankArray(g)
    drawGameOver(g, winner)

    // ENTER ou ESCAPE pour revenir au menu
    if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      returnToMenu()
    }
  }

  /** Réinitialise le jeu et retourne au menu principal */

  def returnToMenu(): Unit = {
    tankArray.clear()
    idxActivePlayer = 0
    turnState = AIMING
    winner = null
    gameStarted = false
    guiState = IN_MENU
    stage.addActor(playerName)
    stage.addActor(nbPlayerField)
    stage.addActor(nbBotField)
    stage.addActor(windField)
    stage.addActor(newGameButton)
    stage.addActor(quitButton)
  }

  /** Fonction principal du jeu*/

  def playing(g: GdxGraphics): Unit = {
    g.clear(Color.LIGHT_GRAY)
    myMaps.refreshMaps(g)

    gameInput()
    turnState match {
      case AIMING => aiming()
      case FLYING => flying(g)
      case LANDSLIDING => if (myMaps.landsliding(g, finished = false)) turnState = CHANGE_PLAYER
      case CHANGE_PLAYER => change_player()
      case INIT_BOT => init_bot()
      case BOT_AIMING => bot_aiming()
    }

    updateGUIGame(g, currTank)
    updateTankArray(g)
    g.drawFPS()
  }


  override def onDispose(): Unit = {
    super.onDispose()
    disposeGUI()
    if (stage != null) stage.dispose()
    if (skin != null) skin.dispose()
  }
}
