package PGame

class Shot extends DrawableShot {

  // Position du tir
  var X: Float = 0f
  var Y: Float = 0f

  // Vitesse du tir
  var Vx: Float = 0f
  var Vy: Float = 0f

  // Physique
  var G = -0.1f
  var weight: Float = 0f
  var wind: Float = 0f
  var thrust: Float = 1f

  // Vitesse initiale
  var Vo: Float = 5f

  var hasAlreadyHit: Boolean = false
  var isFired: Boolean = false

  def initFire(
                tankX: Float,
                tankY: Float,
                tankAngleDeg: Float,
                turretAngleDeg: Float,
                tankLenght: Float,
                turrentLenght: Float
              ): Unit = {

    println("init Fire")

    val turretAngleRad = turretAngleDeg.toRadians
    val tankAngleRad = tankAngleDeg.toRadians

    val half = tankLenght / 2

    val cx =
      (-half * Math.cos(1.57 - tankAngleRad)).toFloat

    val cy =
      (half * Math.sin(1.57 - tankAngleRad)).toFloat

    val dx =
      turrentLenght * Math.cos(turretAngleRad).toFloat

    val dy =
      turrentLenght * Math.sin(turretAngleRad).toFloat

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

    X += Vx
    Y += Vy

    // Gravité
    Vy += G

    // Poids
    Vy += weight

    // Vent
    Vx -= wind

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