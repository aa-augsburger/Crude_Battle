package PGame

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table.Debug
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, TextButton, TextField}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

trait GameGUI {
  this: Game =>

  def initGUI(): Unit = {
    setTitle("Crude Battle")

    stage = new Stage()
    Gdx.input.setInputProcessor(stage)
    skin =
      new Skin(Gdx.files.internal("examples/ui/uiskin.json"))
    textArea = new TextField("", skin)
    textArea.setSize(250, 40)
    textArea.setPosition(getWindowWidth / 2f - 125, getWindowHeight * 0.75f)

    textArea.setMessageText("Nom du joueur")
    newGameButton =
      new TextButton("Nouvelle Partie", skin)
    newGameButton.setSize(220, 45)
    newGameButton.setPosition(getWindowWidth / 2f - 110, getWindowHeight * 0.55f)

    newGameButton.addListener(
      new ClickListener {
        override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
          guiState = GUIState.INIT_GAME
        }
      }
    )

    quitButton = new TextButton("Quitter", skin)

    quitButton.setSize(220, 45)
    quitButton.setPosition(getWindowWidth / 2f - 110, getWindowHeight * 0.42f)

    quitButton.addListener(
      new ClickListener {
        override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
          Gdx.app.exit()
        }
      }
    )

    stage.addActor(textArea)
    stage.addActor(newGameButton)
    stage.addActor(quitButton)
  }

  def updateStage(g: GdxGraphics): Boolean = {
    if (!gameStarted) {
      stage.act()
      stage.draw()
      g.drawStringCentered(
        getWindowHeight / 4f,
        s"Joueur : ${textArea.getText}"
      )
      g.drawFPS()
      return true
    }
    false
  }

  def updateGUIGame(g: GdxGraphics): Unit = {

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
  }
}
