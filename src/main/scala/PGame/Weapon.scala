package PGame
/** Ces classes permettent de gérer les différentes armes du eu */



abstract class Weapon {
  var name: String = _
  var damage: Int = _
  var weight: Float = _
  var blastRadius: Int = _
  var speed: Float = _
  var round: Int = 1
  var trace: Boolean = false
  var pwrIsSettable = true
  var minPwr = 0f
  var maxPwr = 15f
  var power = 10f

}


class Laser extends Weapon {
  name = "Laser"
  damage = 25
  speed = 20f
  weight = 0
  blastRadius = 10
  round = 1
  minPwr = 50
  maxPwr = 60
  trace = true

}

class Canon extends Weapon {
  name = "Canon"
  damage = 50
  speed = 10f
  weight = 1.5f
  blastRadius = 100
  round = 1
  minPwr = 0f
  maxPwr = 15f
  trace = true
}

class MachineGun extends Weapon {
  name = "MachineGun"
  damage = 10
  speed = 10f
  weight = 1f
  blastRadius = 20
  round = 6
}

