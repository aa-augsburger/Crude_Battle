package PGame

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
/** Cette classe permet de gérer la logique de jeu des tanks
 *
 * */


class Tank(initPos: Int = 300, val tankName: String = "", val tankColor: Color, val myMaps: Maps) extends DrawableTank {
  val length = 60
  val height = 30

  var idxWeapon = 0

  var health: Int = 100
  var isAlive: Boolean = true
  var X: Float = 0f
  val isBot: Boolean = false

  val weaponArray: Array[Weapon] = Array(new Canon, new Laser, new MachineGun)
  var currWeapon = weaponArray(0)

  val turrentLenght = 30
  val turrentWidth = 5

  var shot = new Shot()
  var posX = initPos
  var posY = myMaps.surface(posX)
  var speed = 3
  var tankAngle: Float = getTankAngle(posX)
  var turretAngle = tankAngle + 90f
  var currRound: Int = 0
  def updateTank() = {
    posY = myMaps.surface(posX)
  }

  //permet au tank de prendre des dégats et de diminuer sa vie

  def takeDamage(damage: Int): Unit = {
    health -= damage

    if (health <= 0) {
      health = 0
      isAlive = false
      println("Tank détruit")
    }
    println("Vie restante : " + health)
  }

  def adaptSpeedAngle(isRight: Boolean): Int = {
    // on détermine l'angle en fonction de la direction
    val angle = if (isRight) getTankAngle(posX + length / 4) else getTankAngle(posX - length / 4)
    val absAngle = Math.abs(angle)
   // println("next angle " + angle)
    var newSpeed = speed
    val maxAngle = 80 // angle maximum que le tank peut franchir
    val direction = if (isRight) 1 else -1

    //si il monte et la pente est trop raide, il est stoppé
    if (absAngle >= maxAngle) return 0

    //on détermine si c'est une montée ou une descente
    val estMonte = direction * Math.signum(angle)

    //si il monte, il ralenti en fonction de la pente
    if (estMonte > 0) {
      val breaking = (Math.tan(absAngle.toRadians) * 2.0).toInt
      newSpeed -=  breaking
    }
    //si il desceend il accélère
    else  {
      val boost = (Math.tan(absAngle.toRadians) * 3.0).toInt
      newSpeed += boost
    }


//
//    //on acceler le tank si ca descend
//    if(isRight && angle < 1.2) newSpeed -
//    if(!isRight && angle < -1.2) newSpeed = 0
    // println("new speed " + newSpeed)
    newSpeed
  }
//ces fonctions permettent de changer d'arme
  def prevWeapon(): Unit = {
    idxWeapon -= 1
    if(idxWeapon < 0) idxWeapon = 0
    idxWeapon = idxWeapon % weaponArray.length
    currWeapon = weaponArray(idxWeapon)
  }

  def nextWeapon(): Unit = {
    idxWeapon += 1
    idxWeapon = idxWeapon % weaponArray.length
    currWeapon = weaponArray(idxWeapon)
  }


//permet de se déplacer a gauche
  def moveLeft(): Unit = {
    if(posX > speed + length/2+5) {
      posX -= adaptSpeedAngle(false)
    }
  }

//permet de se déplacer a droite
  def moveRight(): Unit = {
    if(posX < myMaps.WIN_WIDTH-speed - length/2-5) {
      posX += adaptSpeedAngle(true)
    }
  }

  //permet d'augmenter la puissance de l'arme
  def pwrUp(): Unit = {
    if(currWeapon.power < 100) currWeapon.power += 1
    println(s"pwr ${shot.Vo}")
  }

  def pwrDown(): Unit = {
    if(currWeapon.power > 0) currWeapon.power -= 1
    println(s"pwr ${shot.Vo}")
  }

  //permet au tank de tirer
  def fire(tankY: Float): Unit = {
    println("init Fire")
    val pwr: Float = getPower
    currRound = 0
    shot.initFire(posX, tankY, tankAngle, turretAngle, height, turrentLenght, pwr, currWeapon.weight, currWeapon.damage, currWeapon.blastRadius)
    }

//permet d'obtenir la puissance normalise
  def getPower = {
    val ratio = (currWeapon.maxPwr - currWeapon.minPwr) / 100
    val pwr = ratio * currWeapon.power + currWeapon.minPwr
    pwr
  }

//augmenter l'angle de la tourette
  def turretUp(): Unit = {
    tankAngle = getTankAngle(posX)
    if(turretAngle < tankAngle+180) turretAngle += 1
    else turretAngle = tankAngle+180
    println(turretAngle)
  }

//permet de diminuer l'angle de la tourette
  def turretDown(): Unit = {
    tankAngle = getTankAngle(posX)
    if(turretAngle > tankAngle) turretAngle -= 1
    else turretAngle = tankAngle
    println(turretAngle)
  }

  //permet de mettre a jour l'angle de la tourette
  def updateTurretAngle(): Unit = {
    val angle = getTankAngle(posX)
    if(turretAngle < angle) turretAngle = angle
    if(turretAngle > (angle + 180)) turretAngle = angle + 180
  }

  //permet d'obtenir l'angle du tank en fonction de la pente
  def getTankAngle(x: Int = posX): Float = {
    if(x-length/2> 0 && x+length/2+speed < myMaps.WIN_WIDTH) {
      val deltaY = myMaps.surface(x + length/2) - myMaps.surface(x - length/2)
      val result = Math.atan2(deltaY, length).toFloat
      return result.toDegrees
    }
0
  }


}