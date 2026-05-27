package Game

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

  def initFire(tankX: Float, tankY: Float, tankAngle: Float, turretAngle: Float, tankLenght: Float, turrentLenght: Float): Unit = {
    print("init Fire")
    val half = tankLenght / 2

    // https://www.omnicalculator.com/fr/physique/calculateur-trajectoire-parabolique
    val radAngle = (turretAngle ).toRadians
    val cx = (-half*Math.cos(1.57-tankAngle)).toFloat
    val cy = (half*Math.sin(1.57-tankAngle)).toFloat
    var dx = turrentLenght*Math.cos(turretAngle.toRadians).toFloat
    var dy = turrentLenght*Math.sin(turretAngle.toRadians).toFloat

    // Calcul des paramètres du tir

    X = tankX + cx + dx
    Y = tankY + cy + dy
    Vx = (Vo * Math.cos(radAngle)).toFloat
    Vy = (Vo * Math.sin(radAngle)).toFloat
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
