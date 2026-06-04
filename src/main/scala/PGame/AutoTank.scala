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
  var radar = 500
  speed = 0
  private var direction = 1
  var nextPosX = posX
  var nextEnnemy: Tank = _
  var distEnemy: Int = _

  //



    def moveTo(destX: Int): Boolean = {
      val dist = posX - destX
      // println("DIST " + dist)
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

  def bestPos(offensivePerc: Int): Int = {
    var minRadar = posX-radar
    var maxRadar = posX + radar
    var bestScore = -1000000f
    var bestPos = 0f
    val securityDistance = 150

    //on evite de chercher en dehors de la maps
    if(minRadar < 0 ) minRadar = 0
    if(maxRadar > myMaps.WIN_WIDTH) maxRadar = myMaps.WIN_WIDTH
    //on scanne la zone
    for(x <- minRadar to maxRadar) {
      val currAltitude = myMaps.surface(x)
      var dx = Math.abs(x - nextEnnemy.posX) //distance entr  entre nous et l'ennemi
      if(Math.abs(dx) > securityDistance) { //on evite de sapproche trop proche du tank ennemi
        val dy = currAltitude - nextEnnemy.posY //différence altitude entre nous et l'ennemi
        if (dx == 0) dx = 1 //eviter division par zero
        val offensiveRatio = (dy / dx)*100 //ratio offensif on cherche le point le plus haut et le plus proche
        val depth = (myMaps.WIN_HEIGHT - currAltitude) // profondeur du trou
        val defensiveRatio = (10*depth  * dx )/100000 //ratio defensif, on cherche le point le plus profond (2* pour prpivliégier le trou)  et le plus éloigné
        val currScore = offensivePerc * offensiveRatio + (100 - offensivePerc) * defensiveRatio // on calcule le meilleure score
        println(f"current score : $currScore / offensive ratio : $offensiveRatio / defensive ratio : $defensiveRatio / Initial POS : $posX - curr : $x")
        if (currScore > bestScore) { // si on trouve un meilleur score on lapplique
          bestScore = currScore
          bestPos = x
        }
      }
    }
    println("BEST POS  " + bestPos )
    bestPos.toInt
  }


  def chooseEnnemy(tankArray: ArrayBuffer[Tank]): Tank = {
    var choosenTank: Tank = null
    //on parcours le tableau des tannks
    for(tank <- tankArray) {
      if(tank != this) { //on evite de se choisir, on n'est pas suicidaire
        distEnemy = this.posX - tank.posX //calcule de la distanec
        choosenTank = tank
        return choosenTank
      }
    }
  choosenTank
  }
}