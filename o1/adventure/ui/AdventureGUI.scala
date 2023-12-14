package o1.adventure.ui

import o1.adventure
import o1.*

import scala.swing.*
import scala.swing.event.*
import javax.swing.UIManager
import scala.collection.mutable.Map
import o1.adventure.Adventure
import o1.util.pass
import scala.util.Random

import scala.io.Source
import java.awt.{Dimension, Insets, Point}
import scala.language.adhocExtensions // enable extension of Swing classes

////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it’s not necessary
// that you understand or even look at the code in this file.
//////////////////////////////////////////////////////////////

/** The singleton object `AdventureGUI` represents a GUI-based version of the Adventure
  * game application. The object serves as a possible entry point for the game app, and can
  * be run to start up a user interface that operates in a separate window. The GUI reads
  * its input from a text field and displays information about the game world in uneditable
  * text areas.
  *
  * **NOTE TO STUDENTS: In this course, you don’t need to understand how this object works
  * or can be used, apart from the fact that you can use this file to start the program.**
  *
  * @see [[AdventureTextUI]] */
object AdventureGUI extends SimpleSwingApplication:
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)

  private var previousLocation = ""

  def top = new MainFrame:

    /** Access to the application’s internal logic: */

    val game = Adventure()
    val player = game.player
    var playerLocation = player.location


    /** Components: */

    val locationInfo = new TextArea(7, 80):
      editable = false
      wordWrap = true
      lineWrap = true
    val turnOutput = new TextArea(20, 80):
      editable = false
      wordWrap = true
      lineWrap = true
    val input = new TextField(40):
      minimumSize = preferredSize
    this.listenTo(input.keys)
    val turnCounter = Label()
    var playMusic = play("")
    var displayLore = ""


    /** Events: */

    this.reactions += {
      case keyEvent: KeyPressed =>
        if keyEvent.source == this.input && keyEvent.key == Key.Enter && !this.game.isOver then
          val command = this.input.text.trim
          if command.nonEmpty then
            this.input.text = ""
            this.playTurn(command)
    }

    /** Layout: */

    this.contents = new GridBagPanel:
      import scala.swing.GridBagPanel.Anchor.*
      import scala.swing.GridBagPanel.Fill
      layout += Label("Location:") -> Constraints(0, 0, 1, 1, 0, 1, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += Label("Command:")  -> Constraints(0, 1, 1, 1, 0, 0, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += Label("Events:")   -> Constraints(0, 2, 1, 1, 0, 0, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += turnCounter        -> Constraints(0, 3, 2, 1, 0, 0, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += locationInfo       -> Constraints(1, 0, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, Insets(5, 5, 5, 5), 0, 0)
      layout += input              -> Constraints(1, 1, 1, 1, 1, 0, NorthWest.id, Fill.None.id, Insets(5, 5, 5, 5), 0, 0)
      layout += turnOutput         -> Constraints(1, 2, 1, 1, 1, 1, SouthWest.id, Fill.Both.id, Insets(5, 5, 5, 5), 0, 0)

    /** Menu: */
    this.menuBar = new MenuBar:
      contents += new Menu("Program"):
        val quitAction = Action("Quit")( dispose() )
        contents += MenuItem(quitAction)

    /** Set up the GUI’s initial state: */
    this.title = game.title
    this.updateInfo(this.game.welcomeMessage)
    this.location = Point(50, 50)
    this.minimumSize = Dimension(200, 200)
    this.pack()
    this.input.requestFocusInWindow()
    fern.play(KeepRepeating)
    previousLocation = "Fernflower Village"

    
    def playTurn(command: String) =
      val turnReport =
        this.game.playTurn(command)
      val musicReport = ""
      if this.player.hasQuit then
        this.dispose()
      else
        this.updateInfo(turnReport)
        this.input.enabled = !this.game.isOver
        this.updateInfoMusic()

    def updateInfo(info: String) =
      if !this.game.isOver then
        this.turnOutput.text = info
      else
        this.turnOutput.text = info + "\n\n" + this.game.goodbyeMessage
      this.locationInfo.text = this.player.location.fullDescription
      this.turnCounter.text = "Turns played: " + this.game.turnCount

    /** Play the music in this location: */
    def locationNow(text: String) =
      this.player.location.fullDescription.contains(text)

    val locationMusic = Map[String, Sound]()

    locationMusic("Fernflower Village") = fern
    locationMusic("Ramia Woods") = ramia
    locationMusic("Passage to Cliffs") = passage
    locationMusic("Clifflands") = clifflands
    locationMusic("Snowhold Town") = snowhold
    locationMusic("Peak") = peak
    locationMusic("Entrance to the Mines") = dawnholdStart
    locationMusic("Dawnhold Mines") = dawnhold
    locationMusic("Baldur's Lair") = dawnholdBossRoom

    private var battleEnabled = 1
    private var bossBattleEnabled = 1
   // private var currentBattle = battle()
    /** Next lines of codes will return what to be played: */
    def updateInfoMusic() =
      // val battle1 = shuffleBattle(Random.nextInt(shuffleBattle.size)) Toimii joskus
      for (location, sound) <- locationMusic do
        if game.isComplete then
          baldur.stop()
          battle1.stop()
          sound.stop()
          win.play(1)
        else if this.game.isOver then
          baldur.stop()
          battle1.stop()
          sound.stop()
        else if player.canAttack == 1 then
          if battleEnabled != 1 then
            if player.bossBattle == 1 then
              if bossBattleEnabled == 1 then
                baldur.play(KeepRepeating)
                bossBattleEnabled = 0
                previousLocation = ""
            else
              battle1.play(KeepRepeating)
              battleEnabled = 1
              previousLocation = ""
          sound.stop()
        else if player.canAttack == 0 then
          this.playMusic =
            battle1.stop()
            baldur.stop()
            //currentBattle = battle()
            if locationNow(location) then
              if previousLocation == location && battleEnabled == 0 then
                pass
              else
                sound.play(KeepRepeating)
                previousLocation = location
            else
              sound.stop()
          battleEnabled = 0


  end top


/** Music is in AdventureGUI.scala, lore in Player.scala, WelcomeMessage etc. in Adventure.scala. */


  /** Music for each zone: */
  val fern =
    Sound("o1/adventure/ui/Fern.wav",100)

  val ramia =
    Sound("o1/adventure/ui/ramia.wav",100)

  val passage =
    Sound("o1/adventure/ui/passage.wav",100)

  val clifflands =
    Sound("o1/adventure/ui/clifflands.wav",100)

  val snowhold =
    Sound("o1/adventure/ui/snowhold.wav")

  val peak =
    Sound("o1/adventure/ui/Peak.wav")

  val dawnholdStart =
    Sound("o1/adventure/ui/dawnhold entrance.wav")

  val dawnholdBossRoom =
    Sound("o1/adventure/ui/dawnhold bossroom.wav",100)

  val dawnhold =
    Sound("o1/adventure/ui/Dawnhold Mines.wav")

  val baldur =
    Sound("o1/adventure/ui/baldur theme.wav")
    
  val win =
    Sound("o1/adventure/ui/win.wav")

  val battle1 =
    Sound("o1/adventure/ui/battle.wav")

  // Joskus ei toimi :( val shuffleBattle = Vector(Sound("o1/adventure/ui/battle.wav"), Sound("o1/adventure/ui/battle.wav"))


  // Enable this code to work even under the -language:strictEquality compiler option:
  private given CanEqual[Component, Component] = CanEqual.derived
  private given CanEqual[Key.Value, Key.Value] = CanEqual.derived

end AdventureGUI

