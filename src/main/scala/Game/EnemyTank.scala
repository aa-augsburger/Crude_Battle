package Game

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

class EnemyTank extends Tank {

  posX = 1400

  speed = 2

  private var direction = 1

  def updateEnemy(): Unit = {

    posX += direction * speed

    if (posX < 1200)
      direction = 1

    if (posX > 1700)
      direction = -1
    turretAngle += 0.3f
  }
  def draw(
            g: GdxGraphics,
            myMaps: Maps
          ): Unit = {

    val deltaY =
      myMaps.surface(posX + height) -
        myMaps.surface(posX - height)

    val halfHeight: Float = height / 2

    tankAngle =
      Math.atan2(deltaY, length).toFloat

    val cx =
      (-halfHeight *
        Math.cos(1.57 - tankAngle)).toFloat

    val cy =
      (halfHeight *
        Math.sin(1.57 - tankAngle)).toFloat

    g.drawFilledRectangle(
      posX + cx,
      myMaps.surface(posX) + cy,
      length,
      height,
      Math.toDegrees(tankAngle).toFloat,
      Color.GREEN
    )

    updateTurretAngle()

    val hTLength = turrentLenght / 2

    val dx =
      hTLength * Math.cos(turretAngle.toRadians)

    val dy =
      hTLength * Math.sin(turretAngle.toRadians)

    val turretX: Float =
      (posX + cx + dx).toFloat

    val turretY =
      (myMaps.surface(posX) + cy + dy).toFloat

    g.drawFilledRectangle(
      turretX,
      turretY,
      turrentLenght,
      turrentWidth,
      turretAngle,
      Color.BLACK
    )
  }
}