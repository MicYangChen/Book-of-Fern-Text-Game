package o1.adventure

import scala.collection.mutable.Map

/** The class `Area` represents locations in a text adventure game world. A game world
  * consists of areas. In general, an “area” can be pretty much anything: a room, a building,
  * an acre of forest, or something completely different. What different areas have in
  * common is that players can be located in them and that they can have exits leading to
  * other, neighboring areas. An area also has a name and a description.
  * @param name         the name of the area
  * @param description  a basic description of the area (typically not including information about items) */
class Area(var name: String, var description: String):

  private val neighbors = Map[String, Area]()
  private var itemsInArea = Map[String, Item]()
  private var enemiesInArea = Map[String, Enemy]()
  private var npcInArea = Map[String, NPC]()

  /** Returns the area that can be reached from this area by moving in the given direction. The result
    * is returned in an `Option`; `None` is returned if there is no exit in the given direction. */
  def neighbor(direction: String) = this.neighbors.get(direction)

  /** Adds an exit from this area to the given area. The neighboring area is reached by moving in
    * the specified direction from this area. */
  def setNeighbor(direction: String, neighbor: Area) =
    this.neighbors += direction -> neighbor

  /** Adds exits from this area to the given areas. Calling this method is equivalent to calling
    * the `setNeighbor` method on each of the given direction–area pairs.
    * @param exits  contains pairs consisting of a direction and the neighboring area in that direction
    * @see [[setNeighbor]] */
  def setNeighbors(exits: Vector[(String, Area)]) =
    this.neighbors ++= exits


  /** Returns a multi-line description of the area as a player sees it. This includes a basic
    * description of the area as well as information about exits and items. If there are no
    * items present, the return value has the form "DESCRIPTION\n\nExits available:
    * DIRECTIONS SEPARATED BY SPACES". If there are one or more items present, the return
    * value has the form "DESCRIPTION\nYou see here: ITEMS SEPARATED BY SPACES\n\nExits available:
    * DIRECTIONS SEPARATED BY SPACES". The items and directions are listed in an arbitrary order. */
  def fullDescription =
    val itemList =
      if this.itemsInArea.size > 1 then
        "\nYou see here: " + this.itemsInArea.keys.mkString(", ")
      else "\nYou see here: " + this.itemsInArea.keys.mkString(" ")
    val npcList  = "\n\nNPCs here: " + this.npcInArea.keys.mkString(" ")
    val enemyList =
      if this.enemiesInArea.size > 1 then
        "\n\nEnemies here: " + this.enemiesInArea.keys.mkString(", ")
      else "\n\nEnemies here: " + this.enemiesInArea.keys.mkString(" ")
    val exitList = "\n\nExits available: " + this.neighbors.keys.mkString(" ")
    if itemsInArea.isEmpty && enemiesInArea.isEmpty && npcInArea.isEmpty then
      this.description + exitList
    else
    if itemsInArea.isEmpty && enemiesInArea.isEmpty then
      this.description + npcList + exitList
    else
    if itemsInArea.isEmpty && npcInArea.isEmpty then
      this.description + enemyList + exitList
    else
    if enemiesInArea.isEmpty && npcInArea.isEmpty then
      this.description + itemList + exitList
    else
    if itemsInArea.isEmpty then
      this.description + npcList + enemyList
    else
    if npcInArea.isEmpty then
      this.description + itemList + enemyList
    else
    if enemiesInArea.isEmpty then
      this.description + itemList + npcList
    else
      this.description + itemList + enemyList + exitList


  def addItem(item: Item): Unit =
    itemsInArea += item.name -> item

  def contains(itemName: String): Boolean =
    itemsInArea.contains(itemName)

  def removeItem(itemName: String): Option[Item]=
    itemsInArea.remove(itemName)
    
  def addNPC(npc: NPC): Unit =
    npcInArea += npc.name -> npc
    
  def readEnemy(monsterName: String): Option[Enemy]=
    enemiesInArea.get(monsterName)
  
  def addMonster(monster: Enemy): Unit =
    enemiesInArea += monster.enemy -> monster

  def killMonster(monsterName: String): Unit =
    enemiesInArea.remove(monsterName)
  
  
  /** Returns a single-line description of the area for debugging purposes. */
  override def toString = this.name + ": " + this.description.replaceAll("\n", " ").take(150)

end Area


