package Game

class Shot {
  var X: Float = 0f
  var Y: Float = 0f

  var Xo: Float = 0f
  var Yo: Float = 0f

  var Vx: Float = 0f
  var Vy: Float = 0f


  var Vo: Float = 5f
  var isFired: Boolean = false

  def initFire(tankX: Float, tankY: Float, tankAngle: Float, turretAngle: Float): Unit = {
    print("init Fire")

    // https://www.omnicalculator.com/fr/physique/calculateur-trajectoire-parabolique
    val radAngle = (turretAngle ).toRadians
    val cx = (-15f*Math.cos(1.57-tankAngle)).toFloat
    val cy = (15f*Math.sin(1.57-tankAngle)).toFloat
    var dx = 30f*Math.cos(turretAngle.toRadians).toFloat
    var dy = 30*Math.sin(turretAngle.toRadians).toFloat
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
    Vy -= 0.1f //gravité

  }
}
