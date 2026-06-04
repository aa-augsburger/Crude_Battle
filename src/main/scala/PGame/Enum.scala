package PGame

/** Etats généraux pendant une partie de jeu*/

object GameState extends Enumeration {
  type TurnState = Value
  val AIMING, FLYING, LANDSLIDING,CHANGE_PLAYER, INIT_BOT, BOT_AIMING = Value
}
/** Etats généraux du programme */

object GUIState extends Enumeration {
  type GUIState = Value
  val IN_MENU,INIT_GAME, PLAYING, PAUSED, WON = Value
}