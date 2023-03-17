package o1.adventure

import o1.*
import scala.math.*
import scala.util.Random

class Enemy(val enemy: String, val health: Int, val lowestDamage: Int, val highestDamage: Int, val item: Item, val xp: Int):

  var currentHealth = health

  def details = s"${this.enemy}: \n\n${this.enemy}'s attack damage: ${this.lowestDamage} - ${this.highestDamage}\nHP: ${this.currentHealth}/$health"

  def injure(amount: Int) =
    this.currentHealth = this.currentHealth - amount
    "HP: " + max(this.currentHealth, 0)

  override def toString = s"${this.enemy}: \n\n${this.enemy}'s attack damage: ${this.lowestDamage} - ${this.highestDamage}\nHP: ${this.currentHealth}/$health"

end Enemy
