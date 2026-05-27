import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.utils.Logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, TextButton, TextField}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}

/**
 * Very simple UI demonstration using libgdx scene2d.ui.
 *
 * @author Pierre-André Mudry (mui)
 * @version 2.0
 */
class MenuScreen extends PortableApplication {

  private var skin: Skin = _
  private var stage: Stage = _
  private var newGameButton: TextButton = _
  private var quitGameButton: TextButton = _
  private var textArea: TextField = _

  override def onInit(): Unit = {
    val buttonWidth = 180f
    val buttonHeight = 30f

    setTitle("Menu screen demonstration")

    stage = new Stage()
    Gdx.input.setInputProcessor(stage)

    skin = new Skin(Gdx.files.internal("examples/ui/uiskin.json"))

    newGameButton = new TextButton("Click me", skin)
    newGameButton.setWidth(buttonWidth)
    newGameButton.setHeight(buttonHeight)

    quitGameButton = new TextButton("Quitter", skin)
    quitGameButton.setWidth(buttonWidth)
    quitGameButton.setHeight(buttonHeight)

    newGameButton.setPosition(getWindowWidth / 2f - buttonWidth / 2f, getWindowHeight * 0.6f)
    quitGameButton.setPosition(getWindowWidth / 2f - buttonWidth / 2f, getWindowHeight * 0.7f)


    stage.addActor(newGameButton)
    stage.addActor(quitGameButton)
    stage.addActor(textArea)


  }
  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear(Color.BLACK)

    stage.act()
    stage.draw()

    g.drawStringCentered(getWindowHeight / 4f, s"Button status ${newGameButton.isChecked}")
    g.drawSchoolLogo()
    g.drawFPS()
  }

  override def onDispose(): Unit = {
    super.onDispose()
    stage.dispose()
    skin.dispose()
  }
}

object MenuScreen {
  def main(args: Array[String]): Unit = {
    new MenuScreen()
  }
}
