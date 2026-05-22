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

  def initFire(tankX: Float, tankY: Float, angle: Float): Unit = {
    print("init Fire")

    // https://www.omnicalculator.com/fr/physique/calculateur-trajectoire-parabolique
    val correctedAngle = (angle+90).toRadians
    X = tankX
    Y = tankY
    Vx = (Vo * Math.cos(correctedAngle)).toFloat
    Vy = (Vo * Math.sin(correctedAngle)).toFloat
    println(Vx)
    isFired = true
  }



  def updateShot(): Unit = {
    X += Vx
    Y += Vy
    Vy -= 0.02f //gravité

  }
}
