package Game

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

trait DrawableTank {
  this: Tank =>
  def drawTank(g: GdxGraphics, myMaps: Maps, c: Color) = {
    val deltaY = myMaps.surface(posX + height) - myMaps.surface(posX - height)

    val halfHeight: Float = height / 2

    tankAngle = Math.atan2(deltaY, length).toFloat

    val cx = (-halfHeight * Math.cos(1.57 - tankAngle)).toFloat
    val cy = (halfHeight * Math.sin(1.57 - tankAngle)).toFloat

    // Corps
    g.drawFilledRectangle(posX + cx, myMaps.surface(posX) + cy, length, height, Math.toDegrees(tankAngle).toFloat, c)

    // Tourelle
    updateTurretAngle()

    val hTLength = turrentLenght / 2

    val dx = hTLength * Math.cos(turretAngle.toRadians)
    val dy = hTLength * Math.sin(turretAngle.toRadians)
    val turretX: Float = (posX + cx + dx).toFloat
    val turretY = (myMaps.surface(posX) + cy + dy).toFloat

    g.drawFilledRectangle(turretX, turretY, turrentLenght, turrentWidth, turretAngle, Color.BLACK)

  }
}
