package o1.adventure

import o1.adventure.ui.AdventureGUI

import java.awt.Panel
import scala.collection.mutable.Map
import scala.util.Random
import scala.math.*
import o1.*

/** A `Player` object represents a player character controlled by the real-life user
  * of the program.
  * A player object’s state is mutable: the player’s location and possessions can change,
  * for instance.*/
class Player(startingArea: Area) extends Enemy("", 100, 1, 5, Item("family amulet", "This amulet has been passed down for generations."), 0):

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  private var itemsPickedUp = Map[String, Item]()   // the items that are picked up
  private var enemyStatus = Map[String, Enemy]()


  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven

  /** Returns the player’s current location. */
  def location = this.currentLocation


  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player’s current location towards the direction name.*/
  def go(direction: String) =
    if canGo == 0 then
      val destination = this.location.neighbor(direction)
      this.currentLocation = destination.getOrElse(this.currentLocation)
      if destination.isDefined then "You go " + direction + "." else "You can't go " + direction + "."
    else s"You have to finish what you started!"

  // variable for battle, 0 = overworld, 1 = in battle.
  var canGo = 0


  /** Causes the player to rest for a short while (this has no substantial effect in game terms).
    * Returns a description of what happened. */

  def help() = help1
  def helpp() = help2
  def helppp() = help3
  def xpCheck() = xpInfo


  /** Signals that the player wants to quit the game.*/
  def quit() =
    this.quitCommandGiven = true
    ""


  def get(itemName: String) =
    if canGo == 0 then
      val removedFromArea = this.currentLocation.removeItem(itemName)
      for remove <- removedFromArea do
        this.itemsPickedUp.put(remove.name, remove)
      if removedFromArea.isDefined then
        // Iron helmet, shield, pants:
        if itemName == "iron helmet" || itemName == "shield" || itemName == "furious pants" || itemName == "frostsparkers"  then
          maxHP += 10
        else
          maxHP += 0
        // Golden amulet, king coat:
        if itemName == "golden amulet" || itemName == "king coat" then
          maxHP += 20
        else
          maxHP += 0
        // Knuckle:
        if itemName == "knuckles" then
          playerLowestAtt += 1
          playerHighestAtt += 1
        else
          playerLowestAtt += 0
          playerHighestAtt += 0
        // Sword:
        if itemName == "sword" then
          playerLowestAtt += 10
          playerHighestAtt += 10
        else
          playerLowestAtt += 0
          playerHighestAtt += 0
        // Ring of vitality:
        if itemName == "ring of vitality" then
          maxHP += 100
        else
          maxHP += 0
        // Ring of power:
        if itemName == "ring of power" then
          playerLowestAtt += 20
          playerHighestAtt += 20
        else
          playerLowestAtt += 0
          playerHighestAtt += 0
        s"You pick up the ${itemName}."
      else
        s"There is no ${itemName} here to pick up."
    else
      "You can't do that while in battle!"


  def drop(itemName: String) =
    if canGo == 0 then
      if has(itemName) then
        this.currentLocation.addItem(this.itemsPickedUp(itemName))
        this.itemsPickedUp -= itemName
        // next few lines are for the equipment as they increase max stats.
        if itemName == "iron helmet" || itemName == "shield" || itemName == "furious pants" || itemName == "frostsparkers" then
          maxHP -= 10
        if itemName == "golden amulet" || itemName == "king coat" then
          maxHP -= 20
        if itemName == "ring of vitality" then
          maxHP -= 100
        if itemName == "knuckles" then
          playerLowestAtt -= 1
          playerHighestAtt -= 1
        if itemName == "sword" then
          playerLowestAtt -= 10
          playerHighestAtt -= 10
        if itemName == "ring of power" then
          playerLowestAtt -= 20
          playerHighestAtt -= 20
        s"You drop the ${itemName}."
      else
        "You don't have that!"
    else
      "You can't do that while in battle!"


  def inspect(itemName: String) =
    if canGo == 0 then
      if this.itemsPickedUp.contains(itemName) then
        s"You inspect the $itemName.\n${itemsPickedUp(itemName).description}."
      else
        "If you want to inspect something, you need to pick it up first."
    else
      "You can't do that while in battle!"


  // Determines if the player died
  def isDead: Boolean =
    this.playerHP <= 0


  def has(itemName: String) =
    this.itemsPickedUp.contains(itemName)


  def inventory(): String =
    if this.itemsPickedUp.isEmpty then
      "Inventory: You are empty-handed."
    else
      s"Inventory:\n${this.itemsPickedUp.keys.mkString("\n")}"


  var areaUnlock: Boolean = false
  def use(itemName: String) =
    if canGo == 0 then
      if has("dawnhold key") & currentLocation.fullDescription.contains("Entrance") then
        if itemName == "dawnhold key" then
          this.itemsPickedUp -= itemName
          areaUnlock = true
          s"You used the $itemName and opened the door to the mines."
        else
          s"You can't use that here!"
      else
        s"You don't have that or you can't use that!"
    else
      "You can't do that while in battle!"


  def talk(npcName: String) =
    if canGo == 0 then
      if this.currentLocation.fullDescription.contains(npcName) then
        if npcName == "elva" then
          s"You talk to $npcName." +
            elvaTalk
        else
        if npcName == "walden" then
          s"You talk to $npcName." +
            waldenTalk
        else
        if npcName == "golden fairy" then
          s"You talk to $npcName." +
            goldFairyTalk
        else
          s"You can't talk to $npcName"
      else
        s"NPC: $npcName is not here or does not exist."
    else
      "You can't do that while in battle!"


  def map() =
    if canGo == 0 then
      show(worldMap)
      s"The map of the world"
    else
      "You can't do that while in battle!"


  def lore(): String =
    if canGo == 0 then
      // Ramia lore:
      if this.currentLocation.fullDescription.contains("Ramia Woods") then
        ramiaLore
      else
      // Passage to cliffs lore:
      if this.currentLocation.fullDescription.contains("Passage to Cliffs") then
        passageLore
      else
      // Clifflands lore:
      if this.currentLocation.fullDescription.contains("Clifflands") then
        clifflandsLore
      else
      // Gold Peak lore:
      if this.currentLocation.fullDescription.contains("Gold Peak") then
        goldPeakLore
      else
      // Freezing Peak lore:
      if this.currentLocation.fullDescription.contains("Freezing Peak") then
        freezingPeakLore
      else
      // Snowhold Town lore:
      if this.currentLocation.fullDescription.contains("Snowhold Town") then
        snowHoldLore
      else
      // Dawnhold Entrance lore:
      if this.currentLocation.fullDescription.contains("Entrance to the Mines") then
        dawnHoldEntranceLore
      else
      // Dawnhold lore:
      if this.currentLocation.fullDescription.contains("Dawnhold Mines") then
        dawnHoldLore
      else
      // Baldur lore:
      if this.currentLocation.fullDescription.contains("Baldur's Lair") then
        baldurLore
      else
      // Otherwise returns the basic lore that is the same as in Fern:
        fernLore
    else
      "You can't do that while in battle!"


  /** Next lines are used for battle system. */
  // Player's Attack damage range:
  private var playerLowestAtt = 1 // this is also used for player rest hp recovery.
  private var playerHighestAtt = 5
  // Player's HP:
  private var maxHP = 100
  private var currentHP = 100
  private def playerHP = min(currentHP, maxHP)
  // Player's experience point system:
  var level = 1 // also used in adventure.scala, which is why it's not a private var.
  private var exp = 0
  private var previousLvL = 1
  // Enemy to fight:
  private var enemyInSight = ""


  // level up method used in adventure class.
  def levelUp() =
    // level 2
    if 50 <= this.exp && this.exp < 120 && level != 2 then
      this.level = 2
      this.previousLvL = 1
      this.maxHP += 8
      this.currentHP = this.maxHP
      this.playerLowestAtt += 3
      this.playerHighestAtt += 3
    // level 3
    else if 120 <= this.exp && this.exp < 200 && level != 3 then
      this.level = 3
      this.previousLvL = 2
      this.maxHP += 10
      this.currentHP = this.maxHP
      this.playerLowestAtt += 4
      this.playerHighestAtt += 4
    // level 4
    else if 200 <= this.exp && this.exp < 300 && level != 4 then
      this.level = 4
      this.previousLvL = 3
      this.maxHP += 12
      this.currentHP = this.maxHP
      this.playerLowestAtt += 5
      this.playerHighestAtt += 5
    // level 5
    else if 300 <= this.exp && this.exp < 420 && level != 5 then
      this.level = 5
      this.previousLvL = 4
      this.maxHP += 14
      this.currentHP = this.maxHP
      this.playerLowestAtt += 6
      this.playerHighestAtt += 6
    // level 6
    else if 420 <= this.exp && this.exp < 550 && level != 6 then
      this.level = 6
      this.previousLvL = 5
      this.maxHP += 16
      this.currentHP = this.maxHP
      this.playerLowestAtt += 7
      this.playerHighestAtt += 7
    // level 7
    else if 550 <= this.exp && this.exp < 700 && level != 7 then
      this.level = 7
      this.previousLvL = 6
      this.maxHP += 18
      this.currentHP = this.maxHP
      this.playerLowestAtt += 8
      this.playerHighestAtt += 8
    // level 8
    else if 700 <= this.exp && this.exp < 900 && level != 8 then
      this.level = 8
      this.previousLvL = 7
      this.maxHP += 20
      this.currentHP = this.maxHP
      this.playerLowestAtt += 9
      this.playerHighestAtt += 9
    // level 9
    else if 900 <= this.exp && this.exp < 1200 && level != 9 then
      this.level = 9
      this.previousLvL = 8
      this.maxHP += 25
      this.currentHP = this.maxHP
      this.playerLowestAtt += 10
      this.playerHighestAtt += 10
    // final level: level 10
    else if this.exp >= 1200 && level != 10 then
      this.level = 10
      this.previousLvL = 9
      this.maxHP += 30
      this.currentHP = this.maxHP
      this.playerLowestAtt += 15
      this.playerHighestAtt += 15


  def examine(enemy: String) =
    if canGo == 0 then
      if this.currentLocation.fullDescription.contains(enemy) then
        val enemyList = this.currentLocation.readEnemy(enemy)
        for enemies <- enemyList do
          this.enemyStatus.put(enemies.enemy, enemies)
          enemyInSight = enemy
        s"You examine the enemy ${enemy}." +
        s"\n\n${enemyStatus(enemy).details}"
      else s"There is no such enemy to examine."
    else
      "You can't do that while in battle!"


  def playerStatus() =
    s"Player:" +
    s"\n\nPlayer's attack damage: $playerLowestAtt - $playerHighestAtt." +
    s"\nPlayer's HP: $currentHP/$maxHP" +
    (if this.level == 10 then s"\nPlayer's LVL: $level (MAX)" else s"\nPlayer's LVL: $level") +
    (if this.exp == 1000 then s"\nPlayer's XP: ${min(this.exp, 1000)} (MAX)" else s"\nPlayer's XP: ${min(this.exp, 1000)}") +
    s"\n\n${inventory()}"


  // For battle later:
  def rest(): String =
    if canGo == 0 then
      if maxHP-currentHP > playerLowestAtt then
        currentHP = min(maxHP, playerHP + playerLowestAtt)
        s"You rest for a while.\n\nRecovered $playerLowestAtt HP." +
        s"\n\nBetter get a move on, though."
      else
        currentHP = maxHP
        s"You rest for a while.\n\nRecovered to MAX HP." +
        s"\n\nBetter get a move on, though."
    else
      val enemyAtt = Random.between(enemyStatus(enemyInSight).lowestDamage, enemyStatus(enemyInSight).highestDamage)
      currentHP = max(0, playerHP - enemyAtt)
      if maxHP-currentHP > playerLowestAtt then
        currentHP = min(maxHP, playerHP + playerLowestAtt)
        s"You rest for a while.\n\nRecovered $playerLowestAtt HP." +
        s"\n\nHowever, the $enemyInSight attacked you and dealt $enemyAtt damage."
      else
        val beforemaxHP = currentHP
        currentHP = maxHP
        s"You rest for a while.\n\nRecovered ${maxHP-beforemaxHP} HP." +
        s"\n\nBetter get a move on, though." +
        s"\n\nHowever, the $enemyInSight attacked you and dealt $enemyAtt damage."


  // variable for attack, if 0 can't attack, if 1 can attack. Used in attack and battle method.
  var canAttack = 0
  // Used for boss music:
  var bossBattle = 0
  def battle(enemy: String) =
    if this.currentLocation.fullDescription.contains(enemy) then
      val enemyList = this.currentLocation.readEnemy(enemy)
      for enemies <- enemyList do
        this.enemyStatus.put(enemies.enemy, enemies)
        canAttack = 1
        canGo = 1
        enemyInSight = enemy
        if enemy == "baldur" then
          bossBattle = 1
      s"${enemyStatus(enemy).details}" +
      s"\n\n${playerStatus()}"
    else s"Enemy does not exist or is dead."


  def attack(): String =
    if canAttack == 1 then
      val playerAtt = Random.between(playerLowestAtt, playerHighestAtt)
      if this.currentLocation.fullDescription.contains(enemyInSight) then
        val enemyList = this.currentLocation.readEnemy(enemyInSight)
        for enemies <- enemyList do
          this.enemyStatus.put(enemies.enemy, enemies)
        canGo = 1
        val enemyAtt = Random.between(enemyStatus(enemyInSight).lowestDamage, enemyStatus(enemyInSight).highestDamage)
        enemyStatus(enemyInSight).injure(playerAtt)
          if enemyStatus(enemyInSight).currentHealth <= 0 then
            this.currentLocation.killMonster(enemyInSight)
            this.currentLocation.addItem(enemyStatus(enemyInSight).item)
            canGo = 0
            canAttack = 0
            this.exp += enemyStatus(enemyInSight).xp
            var levelBefore = this.level
            levelUp()
            if levelBefore != this.level then
              return s"You leveled up!" +
                s"\n\n$enemyInSight is dead. " +
                s"\n\nYou earned: ${enemyStatus(enemyInSight).xp} xp." +
                s"\n\n$enemyInSight dropped: ${enemyStatus(enemyInSight).item}"
            else
              return s"$enemyInSight is dead. " +
                s"\n\nYou earned: ${enemyStatus(enemyInSight).xp} xp." +
                s"\n\n$enemyInSight dropped: ${enemyStatus(enemyInSight).item}"
        currentHP = max(0, playerHP - enemyAtt)
        s"You attack the $enemyInSight and dealt $playerAtt damage." +
        s"\n\nThe $enemyInSight attacks you and deals ${enemyAtt} damage." +
        s"\n\n${enemyStatus(enemyInSight).details}" +
        s"\n\n${playerStatus()}"
      else
        enemyInSight = ""
        s"Enemy does not exist or is dead."
    else
      s"You have to battle first."



/** Music is in AdventureGUI.scala, lore in Player.scala, WelcomeMessage etc. in Adventure.scala. */

  /** Value for help: */
  // Command "help":
  val help1 =
    "Type help for general knowledge, help1 for commands, help2 for more commands." +
    "\n\nType lore to see the lore about the current location pop up. The lore may be about yourself or the location you are currently residing in. " +
    "\n\nType lore in Fernflower Village to see the important details you need to know to beat the game. " +
    "\n\n(Music in the game were made by Michael Chen, one of the two devs. Please turn sounds on.)" +
    "\n\n(help, help1 & help2 do not use a turn)" +
    "\n\nGame ends once you have demonic orb."

  // Command "help1":
  val help2 =
    "Type help for general knowledge, help1 for commands, help2 for more commands." +
    "\n\ndrop x (drops the item x)" +
    "\n\nexamine x (examine the item x in player's inventory)" +
    "\n\nget x (takes the item x from the area)" +
    "\n\ngo x (according to the exits available, you can go to the location x)" +
    "\n\nhelp (general information) (Does not waste a turn)" +
    "\n\nhelp1 (overworld commands) (Does not waste a turn)" +
    "\n\nhelp2 (more overworld commands & battle commands) (Does not waste a turn)" +
    "\n\ninspect x (inspects the enemy x)" +
    "\n\nlore (different lore is displayed in different zones) (Does not waste a turn)" +
    "\n\nmap (displays the map of the world) (Does not waste a turn)" +
    "\n\ntalk x (talk to the NPC x)" +
    "\n\nuse x (use the item x)" +
    "\n\n(Music in the game were made by Michael Chen, one of the two devs. Please turn sounds on.)" +
    "\n\n(help, help1 & help2 do not use a turn)"

  // Command "help2":
  val help3 =
    "Type help for general knowledge, help1 for commands, help2 for more commands." +
    "\n\nattack or \"a\" (attacks an enemy while in battle state)" +
    "\n\nbattle x (goes into battle state against the enemy x)" +
    "\n(Most of the overworld commands do not work while in battle!)" +
    "\n\ninventory (displays player's inventory) (Does not waste a turn)" +
    "\n\nquit (quits the game)" +
    "\n\nrest or \"r\" (rests for HP recovery. Recovers Player's lowest Attack a mount of HP for a given turn) (Recommended in the beginning!)" +
    "\n\nstatus (checks player's status such as leve, attack power and HP) (Does not waste a turn)" +
    "\n\nxp (checks xp required to level up) (Does not waste a turn)" +
    "\n\n(Music in the game where made by Michael Chen, one of the two devs. Please turn sounds on.)" +
    "\n\n(help, help1 &help2 do not use a turn)"

  val xpInfo =
    "Level 2 = 50 xp" +
    "\nLevel 3 = 120 xp" +
    "\nLevel 4 = 200 xp" +
    "\nLevel 5 = 300 xp" +
    "\nLevel 6 = 420 xp" +
    "\nLevel 7 = 550 xp" +
    "\nLevel 8 = 700 xp" +
    "\nLevel 9 = 900 xp" +
    "\nLevel 10 = 1200 xp"

/** NPC dialogue: */
  // Elva The Druid:
  def elvaTalk =
    if inventory().contains("purple slime liquid") &&
      inventory().contains("blue slime liquid") &&
      inventory().contains("green slime liquid") &&
      inventory().contains("fatty bones") &&
      inventory().contains("skinny bones") &&
      inventory().contains("rough bones") &&
      inventory().contains("orc hide") then
      this.currentLocation.addItem(Item("sword", "a steel sword that adds 10 attack to the player."))
      this.itemsPickedUp -= "purple slime liquid"
      this.itemsPickedUp -= "blue slime liquid"
      this.itemsPickedUp -= "green slime liquid"
      this.itemsPickedUp -= "fatty bones"
      this.itemsPickedUp -= "rough bones"
      this.itemsPickedUp -= "orc hide"
      this.itemsPickedUp -= "skinny bones"
      "\n\nOh hoh hoh, now that is a surprise. You have the ingredients." +
      "\n\n Give them to me :)" +
      "\n\nOh, but you need something in return!" +
      "\n\nHere you go, I dropped a sword. Go on and get it."
    else
      "\n\nOh, an energetic villager... perhaps an adventurer. " +
      "\n\nGo on, the forest is yours to explore." +
      "\n\nWould you like to help and get me some purple, blue and green slime liquids? I also need fatty bones, rough bones, skinny bones and some orc hide. Do not ask why. I will reward you a sword for your deeds!"

  // gold fairy:
  def goldFairyTalk =
    if inventory().contains("frost wand") then
      this.currentLocation.addItem(Item("golden amulet", "A golden amulet that blesses the player with 20 HP"))
      this.itemsPickedUp -= "frost wand"
      s"\n\nThank you traveler! Have the golden amulet as a thanks!"
    else
      s"\n\nHello traveler from Fern. I have a task for you. Can you defeat and bring me the frost wand from my archnemesis frost fairy? " +
        s"\n\nI will reward you properly! I swear!"


  // Walden the Chief of Snowhold Town:
  def waldenTalk =
    if inventory().contains("dawnhold key") then
      "\n\nYou are welcome here, but make sure to defeat Baldur as you have the only key to the mines. " +
      s"(You already have the key)"
    else if level >= 5 then
      this.currentLocation.addItem(Item("dawnhold key", "Key to the mines."))
      "\n\nA Fern blood here of all places. You seem to want the dawnhold key? Go on, take it. " +
      "\n\nWrite get dawnhold key to pick up the key."
    else
      "\n\nCome talk to me once you are strong enough."


/** MAP */
  val worldMap = Pic("assets/Book of Fern.jpg")

/** Lore for each zone: */
  def fernLore =
    "You are a energetic villager from Fernflower, that wishes to become an adventurer. " +
    "\n\nYou have practiced the way of fighting and the village chief sees you to be trustworthy and ready to know about the information regarding " +
    "Baldur the Balrog that has awoken deep below the Dawnhold Mines. As you hear of this, you feel an ominous energy coming from south east. " +
    "\n\nWrite map to check out the look of the world." +
    "\n\nYou feel an ominous energy. " +
    "\n\n(Secure the demonic orb to beat the game)" +
    s"\n\n(At turn count 500 balrog gets freed from the mines. Complete the game by then!)"
  
  def ramiaLore =
    "One of the books in Fernflower Village says that Ramia was a beautiful and wise druid that had mantained the woods after wood elves have gone extinct." +
    "\n\nThe center of the forest used to be occupied by the elves. These days many peaceful creatures have made it their home." +
    "\n\nThe western side of the forest is filled with weak creatures" +
    "\n\nThe eastern side of the forest is filled with stronger creatures." +
    "\n\nYou feel the ominous energy getting stronger."

  def passageLore =
    "You are on your way to the Clifflands. " +
    "It is quite high up in the mountains. " +
    "\n\nA town resides in the Clifflands. Seriously though, why build a town up there?" +
    "\n\nYou feel the ominous energy getting stronger."

  def clifflandsLore =
    "The west of the clifflands is quite mild temperature. However the east is really cold." +
    "\n\nSomewhere in the Clifflands lies two fairies." +
    "\n\nYou feel the ominous energy getting stronger."

  def freezingPeakLore =
    "Why are you here in such a cold part of the Clifflands?" +
    "\n\nHome of the frost fairy." +
    "\n\nYou feel the ominous energy getting stronger."

  def goldPeakLore =
    "Beautiful part of the clifflands. It is not cold here. It is perhaps " +
      "not named Gold Peak because there is gold here, but because of the gorgeous view. " +
    "\n\nYou can see Fernflower Village far in the south." +
    "\n\nYou feel the ominous energy getting stronger."
    
  def snowHoldLore =
    "A town full of people. Men have long beards. Women look beautiful yet strong. A place where the strong lives, perhaps?" +
    "\n\nThe Chief Walden is surprised that someone who is an outsider managed to come here. You can read it from his face." +
    "\n\nThe Chief Walden knows about the situation in the mines. " +
    "\n\n(Tip: Talk to Walden)"

  def dawnHoldEntranceLore =
    "The entrance to the mines. You need use the dawnhold key to open the gate to enter the mines."

  def dawnHoldLore =
    "The Dawnhold Mines. A one hell of a ominous place filled with monsters of different kind. Somewhere here is the way to baldur's lair." +
    "\n\nFeels like a labyrinth. Hard to navigate!" +
    "\n\nThere should also be some crazy treasures hiding."

  def baldurLore =
    "Baldur the Balrog. One of the twelve demons of Sylvia." +
    "\n\nBe prepared before fighting this boss!"


  /** Returns a brief description of the player’s state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name

end Player

