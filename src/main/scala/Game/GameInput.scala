package Game

import com.badlogic.gdx.{Gdx, Input}

trait GameInput {
  this: Game =>
  def tankInput(): Unit = {
    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) myTank.moveLeft()
    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) myTank.moveRight()
    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) myTank.turretUp()
    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) myTank.turretDown()
    if (Gdx.input.isKeyPressed(Input.Keys.Q)) myTank.pwrDown()
    if (Gdx.input.isKeyPressed(Input.Keys.W)) myTank.pwrUp()
    if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) myTank.fire(myMaps.dirt(myTank.posX))
  }

  def gameInput(): Unit = {
    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      gameStarted = false
      stage.addActor(textArea)
      stage.addActor(newGameButton)
      stage.addActor(quitButton)
    }
  }

}
