package Game

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
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
          initGame()
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

    if(debug) initGame()
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

}
