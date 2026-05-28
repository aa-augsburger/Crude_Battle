package PGame

class Shot extends DrawableShot {
  //Position du tir
  var X: Float = 0f
  var Y: Float = 0f

//Vitesse du tir
  var Vx: Float = 0f
  var Vy: Float = 0f

  //Variable physique
  var G = -0.1f  //gravite
  var weight: Float = -0f //poid addition
  var wind: Float = 0f //vent add
  var thrust: Float = 1f // mutliplicaton
  //Vitesse initiaile
  var Vo: Float = 5f
  var isFired: Boolean = false

  def initFire(tankX: Float, tankY: Float, tankAngleDeg: Float, turretAngleDeg: Float, tankLenght: Float, turrentLenght: Float): Unit = {
    print("init Fire")
    val turretAngleRad = turretAngleDeg.toRadians
    val tankAngleRad = tankAngleDeg.toRadians
    val half = tankLenght / 2

    // https://www.omnicalculator.com/fr/physique/calculateur-trajectoire-parabolique
    val cx = (-half*Math.cos(1.57-tankAngleRad)).toFloat
    val cy = (half*Math.sin(1.57-tankAngleRad)).toFloat
    var dx = turrentLenght*Math.cos(turretAngleRad).toFloat
    var dy = turrentLenght*Math.sin(turretAngleRad).toFloat

    // Calcul des paramètres du tir

    X = tankX + cx + dx
    Y = tankY + cy + dy
    Vx = (Vo * Math.cos(turretAngleRad)).toFloat
    Vy = (Vo * Math.sin(turretAngleRad)).toFloat
    println(Vx)
    isFired = true
  }



  def updateShot(): Unit = {
    X += Vx
    Y += Vy
    Vy += G //gravité
    Vy += weight //weight
    Vx -= wind //wind
    //Thrust
    Vx *= thrust
    Vy *= thrust
  }
}
