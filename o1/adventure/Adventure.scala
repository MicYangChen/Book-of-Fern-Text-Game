package o1.adventure

import o1.*

/** The class `Adventure` represents text adventure games. An adventure consists of a player and
  * a number of areas that make up the game world. It provides methods for playing the game one
  * turn at a time and for checking the state of the game.
  *
  * N.B. This version of the class has a lot of “hard-coded” information that pertains to a very
  * specific adventure game that involves a small trip through a twisted forest. All newly created
  * instances of class `Adventure` are identical to each other. To create other kinds of adventure
  * games, you will need to modify or replace the source code of this class. */
class Adventure:

  /** the name of the game */
  val title = "Book of Fern"

  //Areas in the game
  private val fernflower       = Area("Fernflower Village", "- Fernflower Village - A calm village, that was the former hero's hometown.")

  private val ramiaWest        = Area("Ramia Woods (West)", "- Ramia Woods (West) - Beloved forest named after the druid called Ramia.")
  private val ramiaNorth       = Area("Ramia Woods (North)", "- Ramia Woods (North) - The northern part of the forest.")
  private val ramiaEast        = Area("Ramia Woods (East)", "- Ramia Woods (East) - A nest of goblins live here.")
  private val ramiaSouth       = Area("Ramia Woods (South)", "- Ramia Woods (South) - Southern part of the woods.")
  private val ramiaCenter      = Area("Ramia Woods (Center)", "- Ramia Woods (Center) - The center of the forest. Home to many creatures.")
  private val ramiaNW          = Area("Ramia Woods (Northwest)", "- Ramia Woods (NW) - Slightly cold, but flowers seem to be dancing to the wind blowing.")
  private val ramiaNE          = Area("Ramia Woods (Northeast)", "- Ramia Woods (NE) - Cute slimes are bouncing here.")
  private val ramiaSE          = Area("Ramia Woods (Southeast)", "- Ramia Woods (SE) - A nest of goblins live here.")
  private val ramiaSW          = Area("Ramia Woods (Southwest)", "- Ramia Woods (SW) - Also known as the fruit forest.")

  private val toClifflands     = Area("Passage to the Cliffs", "- Passage to Cliffs - Level 3 or above to enter.")
  private val clifflands1      = Area("Clifflands1", "- Clifflands (Area 1) - High up on the mountains.")
  private val clifflands2      = Area("Clifflands2", "- Clifflands (Area 2) - A lot of Yetis live here.")
  private val clifflands3      = Area("Clifflands3", "- Clifflands (Area 3) - Very high up on the mountains.")
  private val clifflands4      = Area("Clifflands4", "- Clifflands (Area 4) - Area filled with snow.")
  private val goldPeak         = Area("Gold Peak", "- Gold Peak - Gorgeous view.")
  private val freezingPeak     = Area("Freezing Peak", "- Freezing Peak - Insanely cold place.")
  private val snowholdTown     = Area("Snowhold Town", "- Snowhold Town - A cozy town filled with strong people, who can live in these conditions.")

  private val dawnHold         = Area("Dawnhold Mines (Entrance)", "- Entrance to the Mines - An abandoned mine. Use dawnhold key to open the gate to the mines.")
  private val dawnHold1        = Area("Dawnhold Mines Area 1", "- Dawnhold Mines (Area 1) - A mine that is a labyrinth. Can't navigate properly.")
  private val dawnHold2        = Area("Dawnhold Mines Area 2", "- Dawnhold Mines (Area 2) - You seem to be lost.")
  private val dawnHold3        = Area("Dawnhold Mines Area 3", "- Dawnhold Mines (Area 3) - You seem to be very lost.")
  private val dawnHold4        = Area("Dawnhold Mines Area 4", "- Dawnhold Mines (Area 4) - You seem to be lost. At least you are not hungry.")
  private val dawnHold5        = Area("Dawnhold Mines Area 5", "- Dawnhold Mines (Area 5) - Where am I?")
  private val dawnHold6        = Area("Dawnhold Mines Area 6", "- Dawnhold Mines (Area 6) - You seem to be lost.")
  private val dawnHold7        = Area("Dawnhold Mines Area 7", "- Dawnhold Mines (Area 7) - Please... Where am I?")
  private val dawnHold8        = Area("Dawnhold Mines Area 8", "- Dawnhold Mines (Area 8) - I can't be lost here forever!")
  private val dawnHold9        = Area("Dawnhold Mines Area 9", "- Dawnhold Mines (Area 9) - Ominous energy is getting stronger.")
  private val dawnHoldBoss     = Area("Dawnhold Mines Area (Boss Room)", "- Baldur's Lair -")
  private val hidden1          = Area("Dawnhold Mines Secret 1", "- Dawnhold Mines (Secret 1) - Secret area")
  private val hidden2          = Area("Dawnhold Mines Secret 2", "- Dawnhold Mines (Secret 2) - Secret area")

  private val destination  = dawnHoldBoss

  /** Areas in the world: */

  /** The starting village: */
  fernflower   .setNeighbors(Vector(                          "east" -> ramiaWest                                                                ))

  /** Ramia Woods: */
  ramiaWest    .setNeighbors(Vector("north" -> ramiaNW,       "east" -> ramiaCenter,    "south" -> ramiaSW,        "west" -> fernflower          ))
  ramiaNorth   .setNeighbors(Vector("north" -> toClifflands,  "east" -> ramiaNE,        "south" -> ramiaCenter,    "west" -> ramiaNW             ))
  ramiaSouth   .setNeighbors(Vector("north" -> ramiaCenter,   "east" -> ramiaSE,        "south" -> dawnHold,       "west" -> ramiaSW             ))
  ramiaEast    .setNeighbors(Vector("north" -> ramiaNE,                                 "south" -> ramiaSE,        "west" -> ramiaCenter         ))
  ramiaCenter  .setNeighbors(Vector("north" -> ramiaNorth,    "east" -> ramiaEast,      "south" -> ramiaSouth,     "west" -> ramiaWest           ))
  ramiaNW      .setNeighbors(Vector(                          "east" -> ramiaNorth,     "south" -> ramiaWest                                     ))
  ramiaNE      .setNeighbors(Vector(                                                    "south" -> ramiaEast,      "west" -> ramiaNorth          ))
  ramiaSE      .setNeighbors(Vector("north" -> ramiaEast,                                                          "west" -> ramiaSouth          ))
  ramiaSW      .setNeighbors(Vector("north" -> ramiaWest,     "east" -> ramiaSouth                                                               ))

  /** Clifflands: */
  toClifflands .setNeighbors(Vector(                                                    "south" -> ramiaNorth                                    ))
  clifflands1  .setNeighbors(Vector(                          "east" -> clifflands2,    "south" -> toClifflands,   "west" -> clifflands4         ))
  clifflands2  .setNeighbors(Vector("north" -> freezingPeak,  "east" -> clifflands3,                               "west" -> clifflands1         ))
  clifflands3  .setNeighbors(Vector(                          "east" -> snowholdTown,                              "west" -> clifflands2         ))
  clifflands4  .setNeighbors(Vector(                          "east" -> clifflands1,                               "west" -> goldPeak            ))
  goldPeak     .setNeighbors(Vector(                          "east" -> clifflands4                                                              ))
  freezingPeak .setNeighbors(Vector(                                                    "south" -> clifflands2                                   ))
  snowholdTown .setNeighbors(Vector(                                                                               "west" -> clifflands3         ))

  /** Dawnhold Mines: */
  dawnHold     .setNeighbors(Vector("north" -> ramiaSouth                                                                                        ))
  dawnHold1    .setNeighbors(Vector("north" -> dawnHold,      "east" -> dawnHold6,      "south" -> dawnHold8,      "west" -> dawnHold4           ))
  dawnHold2    .setNeighbors(Vector("north" -> dawnHold4,     "east" -> dawnHold8,      "south" -> dawnHold9,      "west" -> dawnHold6           ))
  dawnHold3    .setNeighbors(Vector("north" -> dawnHold6,     "east" -> dawnHold4,      "south" -> dawnHold7,      "west" -> dawnHold8           ))
  dawnHold4    .setNeighbors(Vector("north" -> dawnHold5,     "east" -> dawnHold1,      "south" -> dawnHold2,      "west" -> dawnHold3           , "hidden" -> hidden1))
  hidden1      .setNeighbors(Vector("return" -> dawnHold4))
  dawnHold5    .setNeighbors(Vector("north" -> dawnHold8,     "east" -> dawnHold7,      "south" -> dawnHold4,      "west" -> dawnHold9           ))
  dawnHold6    .setNeighbors(Vector("north" -> dawnHold7,     "east" -> dawnHold2,      "south" -> dawnHold3,      "west" -> dawnHold1           ))
  dawnHold7    .setNeighbors(Vector("north" -> dawnHold3,     "east" -> dawnHold9,      "south" -> dawnHold6,      "west" -> dawnHold5           , "hidden" -> hidden2))
  hidden2      .setNeighbors(Vector("return" -> dawnHold7))
  dawnHold8    .setNeighbors(Vector("north" -> dawnHold1,     "east" -> dawnHold3,      "south" -> dawnHold5,      "west" -> dawnHold2           ))
  dawnHold9    .setNeighbors(Vector("north" -> dawnHold2,     "east" -> dawnHold5,      "south" -> dawnHold7,      "west" -> dawnHoldBoss        ))
  dawnHoldBoss .setNeighbors(Vector(                          "east" -> dawnHold9                                                                ))



  /** Equipments / items that are to be added to different areas: */
  // secret area items:
  this.hidden1.addItem(Item("ring of vitality", "Empowers your character with 100 HP"))
  this.hidden2.addItem(Item("ring of power", "Empowers your character with 20 ATT"))


  /** NPC's that are to be added to different areas. */
  this.ramiaCenter.addNPC(NPC("elva", "A druid that loves the Ramia Woods."))
  this.goldPeak.addNPC(NPC("golden fairy", "A fairy that loves gold. Despises ice and cold temperature."))
  this.snowholdTown.addNPC(NPC("walden", "The chief of the Snowhold Town. The strongest in the village, even though he is at the age of 80."))

  /** Enemies that are to be added to different areas: */
  // name, health, lowest dmg, highest dmg, item
  // Ramia Woods:
  this.ramiaWest.addMonster(Enemy("purple slime", 7, 2, 3, Item("purple slime liquid", "Kindy sticky"), 5))
  this.ramiaWest.addMonster(Enemy("green slime", 5, 1, 2, Item("green slime liquid", "Kindy sticky"), 3))
  this.ramiaWest.addMonster(Enemy("red slime", 5, 1, 2, Item("red slime liquid", "Kindy sticky"), 3))
  this.ramiaSW.addMonster(Enemy("blue slime", 5, 1, 2, Item("blue slime liquid", "Kindy sticky"), 3))
  this.ramiaSW.addMonster(Enemy("yellow slime", 5, 1, 2, Item("yellow slime liquid", "Kinda sticky"), 3))
  this.ramiaSW.addMonster(Enemy("bad slime", 7, 2, 4, Item("knuckles", "Slime knuckles: +1 ATT"), 5))
  this.ramiaNW.addMonster(Enemy("poison slime", 8, 3, 5, Item("poison slime liquid", "Kindy sticky"), 6))
  this.ramiaNW.addMonster(Enemy("fruit slime", 10, 4, 7, Item("fruit slime liquid", "Kindy sticky"), 7))
  this.ramiaEast.addMonster(Enemy("fat goblin", 20, 3, 6, Item("fatty bones", "Feels disgusting, why do I have this"), 10))
  this.ramiaEast.addMonster(Enemy("skinny goblin", 12, 5, 8, Item("skinny bones", "Like sticks"), 10))
  this.ramiaSouth.addMonster(Enemy("goblin", 15, 5, 8, Item("rough bones", "Hard as a rock"), 10))
  this.ramiaSouth.addMonster(Enemy("goblin warrior", 20, 6, 12, Item("shield", "Adds 10 HP"), 13))
  this.ramiaSE.addMonster(Enemy("orc", 17, 5, 9, Item("orc hide", "Smelly"), 12))
  this.ramiaSE.addMonster(Enemy("furious orc", 22, 7, 13, Item("furious pants", "Pants that are furious. Adds 10 HP"), 15))
  this.ramiaNE.addMonster(Enemy("rotten groot", 30, 8, 14, Item("rotten roots", "Surprisingly strong"), 20))
  this.ramiaNorth.addMonster(Enemy("gatekeeper", 35, 10, 15, Item("amulet of the strong", "It just looks good"), 25))
  // Clifflands:
  this.clifflands4.addMonster(Enemy("iron golem", 42, 13, 16, Item("iron helmet", "Adds 10 HP"), 36))
  this.clifflands1.addMonster(Enemy("frost golem", 39, 12, 15, Item("frozen shard", "Very shiny"), 33))
  this.clifflands1.addMonster(Enemy("ice bat", 35, 9, 14, Item("frozen wings", "How could it fly with those?"), 24))
  this.clifflands2.addMonster(Enemy("frost lizard", 37, 11, 15, Item("frostsparkers", "You feel fast. Adds 10 HP"), 26))
  this.clifflands3.addMonster(Enemy("dark yeti", 45, 14, 17, Item("dark fur", "Feels really cozy"), 42))
  this.clifflands3.addMonster(Enemy("light yeti", 50, 14, 17, Item("light fur", "Feels really cozy"), 42))
  this.clifflands3.addMonster(Enemy("yeti king", 55, 16, 20, Item("king coat", "Heavy! But it adds 20 HP"), 52))
  this.freezingPeak.addMonster(Enemy("frost fairy", 130, 18, 23, Item("frost wand", "Magic frost wand that I can't use!"), 65))
  // Dawnhold Mines:
  this.dawnHold1.addMonster(Enemy("cave yeti", 110, 23, 28, Item("stone fur", "Fur filled with stone"), 45))
  this.dawnHold1.addMonster(Enemy("golem", 100, 20, 24, Item("stone block", "Quite a heavy block of stone."), 40))
  this.dawnHold2.addMonster(Enemy("wight", 135, 25, 30, Item("wight dust", "So dirty."), 55))
  this.dawnHold2.addMonster(Enemy("skeleton", 90, 20, 23, Item("bones", "It's just bones."), 30))
  this.dawnHold3.addMonster(Enemy("pikachu", 220, 30, 36, Item("pikachu", "It seems quite familiar. Nice job catching it... ehm..."), 90))
  this.dawnHold4.addMonster(Enemy("cave troll", 110, 20, 24, Item("troll hide", "Troll's hide, nothing too special."), 45))
  this.dawnHold4.addMonster(Enemy("black spider", 145, 24, 28, Item("spider web", "It's quite durable."), 60))
  this.dawnHold5.addMonster(Enemy("wraith", 154, 22, 25, Item("wraith dust", "Extremely dirty."), 64))
  this.dawnHold5.addMonster(Enemy("undead wood elf", 152, 26, 31, Item("elf ear", "Ear of an elf."), 62))
  this.dawnHold6.addMonster(Enemy("ghoul", 130, 22, 27, Item("ghoul leg", "Ghoul's leg."), 52))
  this.dawnHold6.addMonster(Enemy("cave troll", 110, 20, 24, Item("dirty troll hide", "Troll's hide, nothing too special."), 45))
  this.dawnHold7.addMonster(Enemy("skeleton archer", 80, 22, 24, Item("skeleton bow", "It's a bow. Sadly you can't use it."), 30))
  this.dawnHold7.addMonster(Enemy("skeleton", 90, 20, 23, Item("dirty bones", "It's just dirty bones."), 30))
  this.dawnHold8.addMonster(Enemy("lich", 120, 22, 25, Item("staff", "Staff used by the lich. Seems to be useless for a close combatant."), 50))
  this.dawnHold8.addMonster(Enemy("ghoul", 130, 22, 27, Item("ghoul arm", "Ghoul's arm."), 52))
  this.dawnHold9.addMonster(Enemy("jiangshi", 178, 29, 34, Item("jiangshi hat", "Jiangshi's hat is beautiful looking. Nice souvenir)"), 80))
  // Boss Battle:
  this.dawnHoldBoss.addMonster(Enemy("baldur", 1000, 25, 50, Item("demonic orb", "You are truly strong."), 100))


  /** The character that the player controls in the game. */
  val player = Player(fernflower)

  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 500
  /** Player's HP */

  /** Plays a turn by executing the given in-game command, such as “go west”. Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String) =
    val action = Action(command)
    val outcomeReport = action.execute(this.player)
    if gateOpen then
        dawnHold.setNeighbors(Vector("north" -> ramiaSouth, "mines" -> dawnHold1))
        println(".")
    if player.level > 2 then
        toClifflands.setNeighbors(Vector("clifflands" -> clifflands1, "south" -> ramiaNorth))
    if outcomeReport.isDefined then
      if command == "help" then
        this.turnCount += 0
      else
      if command == "help1" then
        this.turnCount += 0
      else
      if command == "help2" then
        this.turnCount += 0
      else
      if command == "inventory" then
        this.turnCount += 0
      else
      if command == "map" then
        this.turnCount += 0
      else
      if command == "lore" then
        this.turnCount += 0
      else
      if command == "status" then
        this.turnCount += 0
      else
      if command == "xp" then
        this.turnCount += 0
      else
        this.turnCount += 1
    outcomeReport.getOrElse(s"Unknown command: \"$command\".")



  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = (this.player.location == this.destination) && (this.player.has("demonic orb"))

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit || this.player.isDead

  def gateOpen = player.location.name == "Dawnhold Mines (Entrance)" && player.areaUnlock

  /** Most of the event messages are displayed down below. */

  /** Music is in AdventureGUI.scala, lore in Player.scala, WelcomeMessage etc. in Adventure.scala. */
  /** Game start message: */
  def welcomeMessage = "The Village's prophet claims that a monster of great danger has awoken in the deep below the Dawnhold Mines." +
    "\n\nA hero from long time ago built this village. Thus, some people from Fernflower Village possess great power." +
    "\n\n(Tip: Write help for more details how to beat the game. Write help1 to know the commands.)" +
    "\n\n(Music in the game were made by Michael Chen, one of the two devs. Please turn sounds on.)"

  /** Game end message: */
  def goodbyeMessage =
    if this.isComplete then
      "You have defeated one of the twelve great demons! The world is at peace once again. Thank you player!"
    else if this.turnCount == this.timeLimit then
      "Baldur from the depths of this world. It is impossible to stop him now.\n\nGame over!"
    else if this.player.isDead then
      "This world is not suited for weaklings...\n\nGame over!"
    else  // game over due to player quitting
      "Noob!"


  
  
end Adventure

