package PGame

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

/** Trait qui permet de dessiner le tank
 */

trait DrawableTank {
  this: Tank =>
  def drawTank(g: GdxGraphics) = {
    val tankAngleRad = getTankAngle().toRadians
    val turretAngleRad = turretAngle.toRadians
    val halfHeight = height/2

    /** Calculer la déclage du tank en fonction de l'angle du terrain */
    val cx = (-halfHeight * Math.cos(1.57 - tankAngleRad)).toFloat
    val cy = (halfHeight * Math.sin(1.57 - tankAngleRad)).toFloat

    // Dessin du corps du tank
    g.drawFilledRectangle(posX + cx, myMaps.surface(posX) + cy, length, height, Math.toDegrees(tankAngleRad).toFloat, tankColor)

    // On mets a jour la tourelle
    updateTurretAngle()

    val hTLength = turrentLenght / 2
    // Calculer le décalage et la postion de la tourette
    val dx = hTLength * Math.cos(turretAngleRad)
    val dy = hTLength * Math.sin(turretAngleRad)
    val turretX: Float = (posX + cx + dx).toFloat
    val turretY = (myMaps.surface(posX) + cy + dy).toFloat

    g.drawFilledRectangle(turretX, turretY, turrentLenght, turrentWidth, turretAngle, Color.BLACK)

  }
}
