package PGame

/** Cette classe permet de gérer les tirs en fonction du type
 * d'arme utiliser
 * */


class Shot extends DrawableShot with Physic {

  // Position du tir
  var X: Float = 0f
  var Y: Float = 0f

  // Vitesse du tir
  var Vx: Float = 0f
  var Vy: Float = 0f

  // Vitesse initiale
  var Vo: Float = 5f

  var weight: Float = _
  var radius: Int = _
  var damage: Int = _

  var hasAlreadyHit: Boolean = false
  var isFired: Boolean = false

  def initFire(
                tankX: Float,
                tankY: Float,
                tankAngleDeg: Float,
                turretAngleDeg: Float,
                tankLenght: Float,
                turrentLenght: Float,
                pPower: Float,
                pWeight: Float,
                pDamage: Int,
                pRadius: Int
              ): Unit = {

    println("init Fire")

    //Configuration des parametres de tir
    Vo = pPower
    weight = pWeight
    damage = pDamage
    radius = pRadius

    val turretAngleRad = turretAngleDeg.toRadians
    val tankAngleRad = tankAngleDeg.toRadians

//Calcul de la position du début du tir
    val half = tankLenght / 2
    val cx = (-half * Math.cos(1.57 - tankAngleRad)).toFloat
    val cy = (half * Math.sin(1.57 - tankAngleRad)).toFloat
    val dx = turrentLenght * Math.cos(turretAngleRad).toFloat
    val dy = turrentLenght * Math.sin(turretAngleRad).toFloat

    // Position initiale
    X = tankX + cx + dx
    Y = tankY + cy + dy

    // Vitesse
    Vx = (Vo * Math.cos(turretAngleRad)).toFloat
    Vy = (Vo * Math.sin(turretAngleRad)).toFloat



    println(Vx)

    isFired = true
  }

  def updateShot(): Unit = {

    //Changement de position en fonction du temps
    X += Vx
    Y += Vy

    // Gravité
    if(weight != 0) Vy += (G * weight)




    // Vent

    if(weight != 0) Vx -= (wind / weight)

    // Thrust
    Vx *= thrust
    Vy *= thrust
  }

  // Collision avec un tank
  def checkCollision(tank: Tank): Boolean = {

    val dx = X - tank.posX
    val dy = Y - tank.myMaps.surface(tank.posX)

    val distance = Math.sqrt(dx * dx + dy * dy).toFloat

    println("DISTANCE = " + distance)

    if(distance < tank.length) true
    else false
  }
}