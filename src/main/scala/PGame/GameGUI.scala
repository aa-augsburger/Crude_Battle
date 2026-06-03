package PGame

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.scenes.scene2d.ui.Table.Debug
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, TextButton, TextField}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

trait GameGUI {
  this: Game =>

  private var optimus40: BitmapFont = _

  def initGUI(): Unit = {

    setTitle("Crude Battle")
    initFont()
    stage = new Stage()
    Gdx.input.setInputProcessor(stage)
    skin =
      new Skin(Gdx.files.internal("examples/ui/uiskin.json"))
    textArea = new TextField("", skin)
    textArea.setSize(250, 40)
    textArea.setPosition(getWindowWidth / 2f - 125, getWindowHeight * 0.75f)

    textArea.setMessageText("Nom du joueur")
    initButton

    stage.addActor(textArea)
    stage.addActor(newGameButton)
    stage.addActor(quitButton)
  }

  private def initButton = {
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
  }

  def initFont() = {

    val optimusF = Gdx.files.internal("examples/font/Timeless.ttf")
    val parameter = new FreeTypeFontParameter
    var generator = new FreeTypeFontGenerator(optimusF)
    parameter.size = generator.scaleForPixelHeight(40)
    parameter.color = Color.BLACK
    optimus40 = generator.generateFont(parameter)

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

  def updateGUIGame(g: GdxGraphics, currTank: Tank): Unit = {
    val x = 20
    val space = 150
    val y = WIN_HEIGHT-50
    val size = 23
    g.setColor(Color.BLACK)
    g.drawString(x, y, currTank.tankName, optimus40)
    g.drawString(x + space, y,  f"Health : ${currTank.health}", optimus40)
    g.drawString(x + 3 * space, y, f"Power :   ${currTank.shot.Vo}%.2f", optimus40)
    g.drawString(x + 5 * space, y, f"Angle :   ${currTank.turretAngle}%.2f", optimus40)
  }

  def updateUITank(g: GdxGraphics, tank: Tank) = {
    g.setColor(Color.BLACK)
    g.drawString(tank.posX, tank.posY+50, "Tank " + tank.tankName + " HP : " + tank.health)

    if (tank.health <= 0) {
      g.drawString(WIN_WIDTH / 2 - 100, WIN_HEIGHT / 2, "VICTOIRE JOUEUR")
    }
  }
}
