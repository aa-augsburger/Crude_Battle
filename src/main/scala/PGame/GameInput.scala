package PGame

import com.badlogic.gdx.{Gdx, Input}

trait GameInput {
  this: Game =>
  def tankInput(): Boolean = {
    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) myTank.moveLeft()
    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) myTank.moveRight()
    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) myTank.turretUp()
    if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) myTank.turretDown()
    if (Gdx.input.isKeyPressed(Input.Keys.Q)) myTank.pwrDown()
    if (Gdx.input.isKeyPressed(Input.Keys.W)) myTank.pwrUp()
    if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
      myTank.fire(myMaps.dirt(myTank.posX))
      return true
    }
    false
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
