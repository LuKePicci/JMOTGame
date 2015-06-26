USING ECLIPSE
Game manager can be started by right clicking on GameServer.java in the package it.polimi.ingsw.cg_30.gamemanager and selecting Run As -> Java Aplication.
Game client can be started by adding two run configurations; both configurations use the same  it.polimi.ingsw.cg_30.gameclient.GameClient as main class, but the program arguments must be GUI(or gui, or Gui, or... it's case unsensitive) in order to start the GUI, CLI (or cli, Cli,...) in order to start the CLI.
After starting the CLI it is possible to move to GUI by writing the "switch" command before the match begins.

USING JAR FILES
In target directory two .jar files can be found.
The cg_30-0.0.1-SNAPSHOT-GameManager files must be started in order to activate the server and one cg_30-0.0.1-SNAPSHOT-GameClient file must be launched for each player.
COMPLETARE E CORREGGERE!!!

LOGIN
For local testing purpose using localhost as Hostname.
The socket port is 22222; the rmi port is 9090.

CLI LOGIN CONVENTIONS
The first line:
"socket Hostname port" or "Hostname port" for socket
"rmi Hostname port" for rmi
The second line:
"playerName mapName" forplaying in the map mapName
"playerName" for playing in the default map (Galvani) CAMBIARE IN GALILEI!!!
"playerName mapName partyName" for playing as private party partyName in the map mapName 

CLI INPUT COMMAND
When using the CLI version of the game a player can use the following commands:
1)move sectorCoordinates(written using this convention XY, where X is char and Y is int)
2)attack
3)draw
4)noise sectorCoordinates(written using this convention XY, where X is char and Y is int)
5)useitem itemName
6)discard itemName
7)turnover
8)chat COMPLETARE!!!

Commands explanation:
1)Moves to the sector
2)Attacks
3)Draws a sector card
4)Makes noise in the sector
5)Uses the item
6)Discards the item
7)Ends the turn
8)COMPLETARE!!!

NOTE
The FileNotFoundException launched at joining moment is launched because the client is trying to resume a possible unfinished previous match. It's an expected behaviour.