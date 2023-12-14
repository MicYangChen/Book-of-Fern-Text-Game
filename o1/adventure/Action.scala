package o1.adventure

/** The class `Action` represents actions that a player may take in a text adventure game.
  * @param input  a textual in-game command such as “go east” or “rest” */
class Action(input: String):

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim

  /** Causes the given player to take the action represented by this object, assuming
    * that the command was understood. Returns a description of what happened as a result
    * of the action (such as “You go west.”).*/
  def execute(actor: Player) = this.verb match
    case "attack"    => Some(actor.attack())
    case "a"         => Some(actor.attack())
    case "battle"    => Some(actor.battle(this.modifiers))
    case "b"         => Some(actor.battle(this.modifiers))
    case "drop"      => Some(actor.drop(this.modifiers))
    case "examine"   => Some(actor.examine(this.modifiers))
    case "get"       => Some(actor.get(this.modifiers))
    case "go"        => Some(actor.go(this.modifiers))
    case "g"         => Some(actor.go(this.modifiers))
    case "help"      => Some(actor.help())
    case "help1"     => Some(actor.helpp())
    case "help2"     => Some(actor.helppp())
    case "inspect"   => Some(actor.inspect(this.modifiers))
    case "inventory" => Some(actor.inventory())
    case "lore"      => Some(actor.lore())
    case "map"       => Some(actor.map())
    case "quit"      => Some(actor.quit())
    case "rest"      => Some(actor.rest())
    case "r"         => Some(actor.rest())
    case "status"    => Some(actor.playerStatus())
    case "s"         => Some(actor.playerStatus())
    case "talk"      => Some(actor.talk(this.modifiers))
    case "use"       => Some(actor.use(this.modifiers))
    case "sit"       => Some("You sit on the ground, but you wonder why you sat down.")
    case "xp"        => Some(actor.xpCheck())
    case other       => None

  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = s"$verb (modifiers: $modifiers)"

// Music is in AdventureGUI.scala, lore in Player.scala, WelcomeMessage etc. in Adventure.scala.

  
end Action
