The game 'Book of Fern' was made as a project from Aalto University 2022 Programming 1 Course by Michael Chen & Taulant Smakiqi. The project depends on the following Apache 2.0 licensed libraries:

Akka-Actor 2.6.18
Scala swing 3.0.0
Scala 3.3.0
Scala parser combinators 2.1.1

...and the following libraries without known licences

O1-Library (Juha Sorva, Aalto University)
SMCL (Aleksi Lukkarinen, Aalto University)

Music in the game was made and owned by Michael Chen & the repository is licensed with MIT.



Launch the game by opening 'AdventureGUI' located in o1.adventure/ui/AdventureGUI



Guidebook / Manual:

- The game is entirely beatable in 500 turns, even in 200 turns. Strategy & trial and error are needed to beat the game.

- You start the game at Fernflower Village. Write help (or lore in Fernflower Village) for instructions.
	(Tells you to defeat Baldur within a certain a mount of turns)

- Write lore for lore if you are interested. Write map to check the look of the world.

- You explore Ramia Woods and level/gear your character up. Elva has a task for you (not mandatory) (Makes it hell of a lot easier though)

- TIP: Use Magic (MP) sparingly.

- Once you are level 3 or above, you can enter the Clifflands.

- In the Clifflands, you have a quest from gold fairy (not mandatory)

- Once you are level 5 or above, you can talk to Walden in the Snowhold Town for the dawnhold key.

- Go to the Dawnhold Mines (Entrance) and type command: use dawnhold key

- You open the door to the mines. Enter by typing: go mines

- In the Dawnhold Mines you can level yourself up to level 10 (MAX LEVEL IN GAME)

- The mines also contains treasures from monsters and secret areas.

- Once you find the boss room, you can challenge the baldur. It is adviced to be over level 8 before challeging.

- You beat the game, once you have defeated baldur and picked up his demonic orb!

(IF PLAYER's HP is 0 or turn count 500 is reached => GAME OVER!)



List of commands are written down below in alphabetical order:

x = ""
* means can be used in battle
' means does not use a turn

-* battle x or b x (goes into battle state against the enemy x)
-* attack or a (attacks an enemy while in battle state)
-* magic or m (attacks an enemy with magic while in battle state). Consumes 4 MP
- drop x (drops the item x)
- examine x (examines the enemy x)
- get x (takes the item x from the area)
- go x or g x (according to the "exits available", you can go to the location x)
-*' help (general information)
-*' help1 (overworld commands)
-*' help2 (more overworld commands & battle commands)
- inspect x (inspects the item x in player's inventory)
-*' inventory (displays player's inventory)
-' lore (different lore is displayed in different zones)
-' map (displays the map of the world)
-* quit (quits the game)
-* rest or r (rests for HP recovery. Recovers Player's lowest Attack a mount of HP for a given turn)
-*' status or s (checks player's status such as leve, attack power and HP)
- talk x (talk to the NPC x)
- use x (use the item x)
-*' xp (checks xp required to level up)



Good Luck!