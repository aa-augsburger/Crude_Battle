package PGame


object GameState extends Enumeration {
  type TurnState = Value
  val AIMING, FLYING, LANDSLIDING = Value
}

object GUIState extends Enumeration {
  type GUIState = Value
  val IN_MENU,INIT_GAME, PLAYING, PAUSED, WON = Value
}