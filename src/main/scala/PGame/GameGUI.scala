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

/** Ce trait permet de gérer l'affichage du jeu*/


trait GameGUI {
  this: Game with Physic =>

  val fieldWidth = 250
  val fieldHeight = 40
  var posX = WIN_WIDTH/2
  var posY = WIN_HEIGHT-20
  private var optimus40: BitmapFont = _

  def initGUI(): Unit = {

    val centerX = WIN_WIDTH / 2f - (fieldWidth / 2)

    setTitle("Crude Battle")
    initFont()
    stage = new Stage()
    Gdx.input.setInputProcessor(stage)
    skin =
      new Skin(Gdx.files.internal("examples/ui/uiskin.json"))
    playerName = new TextField("", skin)
    playerName.setSize(250, 40)
    playerName.setPosition(getWindowWidth / 2f - 125, getWindowHeight * 0.8f)
    playerName.setMessageText("Nom du joueur")

    //configuration des fields
    nbPlayerField = new TextField("", skin)
    nbPlayerField.setSize(fieldWidth, fieldHeight)
    nbPlayerField.setPosition(centerX, getWindowHeight * 0.75f)
    nbPlayerField.setMessageText("Nombre de joueurs (ex: 2)")
    nbBotField = new TextField("", skin)
    nbBotField.setSize(fieldWidth, fieldHeight)
    nbBotField.setPosition(centerX, getWindowHeight * 0.7f)
    nbBotField.setMessageText("Nombre de bots (ex: 1)")
    windField = new TextField("", skin)
    windField.setSize(fieldWidth, fieldHeight)
    windField.setPosition(centerX, getWindowHeight * 0.65f)
    windField.setMessageText("Force du vent")
    initButton

    stage.addActor(playerName)
    stage.addActor(nbPlayerField)
    stage.addActor(nbBotField)
    stage.addActor(windField)
    stage.addActor(newGameButton)
    stage.addActor(quitButton)
    stage.addActor(newGameButton)
    stage.addActor(quitButton)
  }
  // cette méthode permet d'initialiser les boutons
  private def initButton = {
    newGameButton =
      new TextButton("Nouvelle Partie", skin)
    newGameButton.setSize(220, 45)
    newGameButton.setPosition(getWindowWidth / 2f - 110, getWindowHeight * 0.35f)

    newGameButton.addListener(
      new ClickListener {
        override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
          val botInput = nbBotField.getText.trim
          val playerInput = nbPlayerField.getText.trim
          val windInput   = windField.getText.trim
          nbBot = if (botInput.matches("\\d+")) botInput.toInt else 1
          nbPlayer = if (playerInput.matches("\\d+")) playerInput.toInt else 2
          wind   = if (windInput.matches("-?\\d+")) windInput.toInt else 0
          guiState = GUIState.INIT_GAME
        }
      }
    )

    quitButton = new TextButton("Quitter", skin)

    quitButton.setSize(fieldWidth, fieldHeight)
    quitButton.setPosition(getWindowWidth / 2f - 110, getWindowHeight * 0.42f)

    quitButton.addListener(

      // quand on clique pour quitter
      new ClickListener {
        override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
          Gdx.app.exit()
        }
      }
    )
  }


  //cette methode permet d'initialiser les polices de caractères
  def initFont() = {

    val optimusF = Gdx.files.internal("examples/font/Timeless.ttf")
    val parameter = new FreeTypeFontParameter
    var generator = new FreeTypeFontGenerator(optimusF)
    parameter.size = generator.scaleForPixelHeight(40)
    parameter.color = Color.BLACK
    optimus40 = generator.generateFont(parameter)

  }


  //cette méthode est appelé a chaque frame afin de mettre à jour le ui
  def updateStage(g: GdxGraphics): Boolean = {
    if (!gameStarted) {
      stage.act()
      stage.draw()
      g.drawStringCentered(
        getWindowHeight / 4f,
        s"Joueur : ${playerName.getText}"
      )
      g.drawFPS()
      return true
    }
    false
  }

  /** Mettre a jour le HUD */

  def updateGUIGame(g: GdxGraphics, currTank: Tank): Unit = {
    val x = 20
    val space = 150
    val y = WIN_HEIGHT-50
    val size = 23
    g.setColor(Color.BLACK)
    g.drawString(x, y, currTank.tankName, optimus40)
    g.drawString(x + space, y,  f"Health : ${currTank.health}", optimus40)
    g.drawString(x + 3 * space, y, f"Power :   ${currTank.currWeapon.power}%.2f", optimus40)
    g.drawString(x + 5 * space, y, f"Angle :   ${currTank.turretAngle}%.2f", optimus40)
    g.drawString(x + 7 * space, y, f"Weapon :   ${currTank.currWeapon.name}", optimus40)
  }
  /** Mets a jour le texte sur les tank*/


  def updateUITank(g: GdxGraphics, tank: Tank) = {
    g.setColor(Color.BLACK)
    g.drawString(tank.posX, tank.posY+50, "Tank " + tank.tankName + " HP : " + tank.health)

    if (tank.health <= 0) {
      g.drawString(WIN_WIDTH / 2 - 100, WIN_HEIGHT / 2, "VICTOIRE JOUEUR")
    }
  }
}
