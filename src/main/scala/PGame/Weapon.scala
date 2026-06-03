package PGame

class Weapon {

  var damage: Int = _
  var weight: Int = _
  var blastRadius: Int = _
  var speed: Int = _
  var round: Int = 1

}


class Laser extends Weapon {
  damage = 25
  speed = 40
  weight = 1
  blastRadius = 3
  round = 1
}

class BigCanon extends Weapon {
  damage = 50
  speed = 10
  weight = 30
  blastRadius = 3
  round = 1
}
