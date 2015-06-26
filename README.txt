LAUNCHING BY ECLIPSE
Game manager can be started by right clicking on GameServer.java in the package it.polimi.ingsw.cg_30.gamemanager and selecting Run As -> Java Aplication.
Game client can be started by adding two run configurations; both configurations use the same  it.polimi.ingsw.cg_30.gameclient.GameClient as main class The first argument can be either GUI(or gui, or Gui, or... it's case insensitive) in order to play by the GUI or CLI (or cli, Cli,...) in order to start the CLI.
No arguments means GUI by default.
While playing by CLI you can also move to GUI with the "switch" command. Take in mind that doing after the start of a match will hide you the map.

Both client versions are stateful. If you close he client and connect again with the same NickName you will be back into the match.

USING JAR FILES
Along with maven build execution two .jar files will be generated into target directory.
For the server: just doubleclick cg_30-0.0.1-SNAPSHOT-GameManager.jar or launch it by terminal if you would like to see logging messages.
For the player: doubleclick cg_30-0.0.1-SNAPSHOT-GameClient and the GUI will be shown afte some seconds. For the CLI version start this jar from commanf line anf pass "CLI" as first argument.
WATNING!!
If Java complains about missing classes into JAR just delete target directory and let maven build run at least other two times to ensure all classes to be written into the jar.

LOGIN
For local testing purpose using localhost as Hostname.
The socket port is 22222; the rmi port is 9090.

CLI CONVENTIONS
[] stands for optional arguments
<> stands for required arguments

CLI CONNECTION PROMPT
Usage: [socket|rmi] <hostname> <port>

socket		play over socket based connection
rmi		play over RMI based connection

hostname	the hostname or IP address of the server
port		TCP port for server connection

CLI JOIN PROMPT
Usage: <nickname> [mapname [partyname]]

nickname	your nickname of choice

mapname		the name of the xone where you would like to play
		make sure the map is present on the server otherwise you wont
		be able to start a match.
		default: galvani,
		also available in default server package:
			galilei, galvani, aram, fermi

partyname	use this field to create or enter a party where 
		only who know this party name can enter (useful to play with
		your friends while there are much connected players into the
		server).

CLI EFTAIOS PROMPT
While plying CLI version of the game a player can use the following commands:


move <sectorCoordinates>
attack
draw
noise <sectorCoordinates>
useitem <itemname>
discard <itemname>
turnover
chat <message to party>
chat all <message to all>
chat player <nickName> <message to player>
switch

sectorCoordinates	written using this convention XY, where X can be both a
			char or number and Y is a number like shown in original
			EFTAIOS zones.

itemname		can be one of the EFTAIOS standard items
			(see game manual for the full list)

examples		move J01
			move j1
			move J 1
			move j 000001
			useitem teleport
			discard spotlight
			chat Hey!
			chat player Luca Hello Luca!
			chat all Hi all!

EFTAIOS XML MAP EDITOR
Our git repository contains a jar package with a standalone application
which is able yo edit or show eftaios zones saved in our format.
Default output is map.xml.
The first comman line argument specifies file name to be load or saved with.
Must be in the same directory (or classpath).