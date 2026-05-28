package PGame

import PGame.GUIState.{GUIState, INIT_GAME, IN_MENU, PLAYING}
import PGame.GameState.{AIMING, FLYING, LANDSLIDING, TurnState}

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, TextButton, TextField}

class Game(
            WIDTH: Int = 1920,
            HEIGHT: Int = 1080,
            nbPlayer: Int = 1,
            nbBot: Int = 1,
            val debug: Boolean = true
          ) extends PortableApplication(WIDTH, HEIGHT)
  with GameInput
  with GameGUI {

  var tour: Int = 0

  var myMaps: Maps = _
  var myTank: Tank = _
  var autoTank: Tank with AutoTank = _
  var gameStarted = false

  var guiState: GUIState =
    if (debug) INIT_GAME else IN_MENU

  var turnState: TurnState = AIMING

  var stage: Stage = _
  var skin: Skin = _

  var newGameButton: TextButton = _
  var quitButton: TextButton = _
  var textArea: TextField = _

  override def onInit(): Unit = {

    initGUI()

    if (debug)
      guiState = INIT_GAME
  }

  def initGame(): Unit = {

    stage.clear()
    gameStarted= true

    myMaps = new Maps(WIDTH, HEIGHT)

    myTank = new Tank(300, myMaps)

    autoTank =
      new Tank(1500, myMaps) with AutoTank {}

    myMaps.initMaps()

    guiState = PLAYING
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {

    guiState match {

      case IN_MENU =>
        if (updateStage(g)) return

      case INIT_GAME =>
        initGame()

      case PLAYING =>
        playing(g)
    }
  }

  def playing(g: GdxGraphics): Unit = {

    g.clear(Color.LIGHT_GRAY)

    gameInput()

    turnState match {

      case AIMING =>
        aiming()

      case FLYING =>
        flying(g)

      case LANDSLIDING =>
        if (myMaps.landsliding(g, finished = false))
          turnState = AIMING
    }

    myMaps.refreshMaps(g)

    myTank.Y = myMaps.surface(myTank.posX)
    autoTank.Y = myMaps.surface(autoTank.posX)

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
        WIDTH / 2 - 100,
        HEIGHT / 2,
        "VICTOIRE JOUEUR"
      )
    }

    g.drawFPS()
  }

  def aiming(): Unit = {

    if (tankInput())
      turnState = FLYING
  }

  def flying(g: GdxGraphics): Unit = {

    if (
      myTank.shot.isFired &&
        myTank.shot.X > -myTank.shot.Vx &&
        myTank.shot.X < WIDTH - myTank.shot.Vx
    ) {

      // Update projectile
      myTank.shot.updateShot()

      // Draw projectile
      myTank.shot.drawShot(g, myTank)

      // Collision avec enemy tank
      if (myTank.shot.checkCollision(autoTank)) {

        println("ENEMY TOUCHE")

        // dégâts
        autoTank.takeDamage(20)

        // arrêter projectile
        myTank.shot.isFired = false

        // explosion
        myMaps.explosion(
          myTank.shot.X.toInt,
          myTank.shot.Y.toInt,
          50
        )

        turnState = LANDSLIDING
      }
    }

    // Collision
    if (
      myTank.shot.Y <
        myMaps.surface(myTank.shot.X.toInt) &&
        myTank.shot.isFired
    ) {
      myTank.shot.isFired = false
      myMaps.explosion(
        myTank.shot.X.toInt,
        myMaps.surface(myTank.shot.X.toInt).toInt,
        80
      )

      turnState = LANDSLIDING
    }
  }

  override def onDispose(): Unit = {

    super.onDispose()

    if (stage != null)
      stage.dispose()

    if (skin != null)
      skin.dispose()
  }
}