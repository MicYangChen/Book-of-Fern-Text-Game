package o1.adventure

class NPC(val name: String, val description: String):

  /** Returns a short textual representation of the NPC (its name, that is). */
  override def toString = this.name

end NPC
