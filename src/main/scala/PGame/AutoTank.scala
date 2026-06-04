package PGame

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

import scala.collection.mutable.ArrayBuffer

/** Trait qui permet d'implémtener un tank automatique qui bouge tout seul
 */

trait AutoTank {

  this: Tank =>

  val isMovable: Boolean = true
  override val isBot = true
  var radar = 400
  speed = 0
  private var direction = 1
  var nextPosX = posX
  var nextEnnemy: Tank = _
  var distEnemy: Int = _

  //



    def moveTo(destX: Int): Boolean = {
      val dist = posX - destX
      println("DIST " + dist)
      if(Math.abs(dist) <= 5) return true
      if(dist > 0) { // on va a gauche
        speed = 2
        val slopeSpeed = adaptSpeedAngle(isRight = false)
        if(posX > speed + length/2+5) {
          posX -= speed
        }
        else
          return true
      }
      else { //on va a droite
        speed = 2
        val slopeSpeed = adaptSpeedAngle(isRight = true)
        if(posX < 1920-speed - length/2-5) {
          posX += speed
        }
        else
          return true
      }
      false
  }

  def findTop(): Int = {
    var minRadar = posX-radar
    var maxRadar = posX + radar
    var maxTop: Int = posX
    var bestRatio = 0f
    var bestPos = 0f


    if(minRadar < 0 ) minRadar = 0
    if(maxRadar > myMaps.WIN_WIDTH) maxRadar = myMaps.WIN_WIDTH
    for(x <- minRadar to maxRadar) {
      val currAltitude = myMaps.surface(x)
      var dx = Math.abs(x - nextEnnemy.posX)
      val dy = Math.abs(currAltitude-nextEnnemy.posY)
      if(dx == 0) dx = 1
      val ratio = dy/dx
      if(ratio > bestRatio) {
        bestRatio = ratio
        bestPos = x
      }
    }
    println("TOP  " + posX )
    bestPos.toInt
  }

  def findDown(): Int = {
    var minRadar = posX-radar
    var maxRadar = posX + radar
    var min: Int = posX

    if(minRadar < 0 ) minRadar = 0
    if(maxRadar > myMaps.WIN_WIDTH) maxRadar = myMaps.WIN_WIDTH
    for(x <- minRadar to maxRadar) {
      if(myMaps.surface(x) < myMaps.surface(min) ) min = x
    }
    println("DOWN "+ posX )
    min
  }

  def chooseEnnemy(tankArray: ArrayBuffer[Tank]): Tank = {
    var choosenTank: Tank = null
    for(tank <- tankArray) {
      if(tank != this) {
        distEnemy = this.posX - tank.posX
        choosenTank = tank
        return choosenTank
      }
    }
  choosenTank
  }
}