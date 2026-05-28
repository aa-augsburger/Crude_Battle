package PGame

import PGame.GUIState.{GUIState, INIT_GAME, IN_MENU, PLAYING}
import PGame.GameState.{CHANGE_PLAYER, AIMING, FLYING, LANDSLIDING, TurnState}
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, TextButton, TextField}
import com.sun.tools.javac.code.TypeTag

import scala.collection.mutable.ArrayBuffer

class Game(val WIN_WIDTH: Int = 1920, val WIN_HEIGHT: Int = 1080, val nbPlayer: Int = 3, val nbBot: Int = 1, val debug: Boolean = true) extends PortableApplication(WIN_WIDTH, WIN_HEIGHT) with GameInput with GameGUI with GameLogic {

  var idxActivePlayer: Int = 0

  var myMaps: Maps = _
  val colorArray: Array[Color] = Array(Color.GREEN,Color.MAGENTA, Color.YELLOW, Color.CYAN, Color.PINK, Color.DARK_GRAY)

  val tankArray: ArrayBuffer[Tank] = ArrayBuffer[Tank]()
  var currTank : Tank = _
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

  def initTank(): Unit = {
    //création des joueurs
    var pos = 200
    for(i <- 0 until nbPlayer) {
      println("on init les tanks player")

      tankArray.addOne(new Tank(pos, colorArray(i), myMaps))
      pos += 500
    }
    for(i <- 0 until nbBot) {
      println("on init les tanks bot")

      tankArray.addOne(new Tank(pos, colorArray(nbPlayer + i), myMaps) with AutoTank {})
      pos += 200
    }
  }


  def initGame(): Unit = {
    stage.clear()
    myMaps = new Maps(WIN_WIDTH, WIN_HEIGHT)

    myMaps.initMaps()
    initTank()
    currTank = tankArray(0)
    guiState = PLAYING
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    guiState match {
      case IN_MENU => if (updateStage(g)) return
      case INIT_GAME => initGame()
      case PLAYING => playing(g)
    }
  }

  def change_player(): Unit = {
    idxActivePlayer += 1
    idxActivePlayer %= nbPlayer
    currTank = tankArray(idxActivePlayer)
    turnState = AIMING
  }

  def playing(g: GdxGraphics): Unit = {
    g.clear(Color.LIGHT_GRAY)
    myMaps.refreshMaps(g)

    gameInput()
    turnState match {
      case AIMING => aiming()
      case FLYING => flying(g)
      case LANDSLIDING => if (myMaps.landsliding(g, finished = false)) turnState = CHANGE_PLAYER
      case CHANGE_PLAYER => change_player()
    }

    for(tank <- tankArray) {
      tank.updateTank()
      tank.drawTank(g)
      updateUITank(g, tank)
    }
    g.drawFPS()
  }

  override def onDispose(): Unit = {
    super.onDispose()
    if (stage != null) stage.dispose()
    if (skin != null) skin.dispose()
  }
}
