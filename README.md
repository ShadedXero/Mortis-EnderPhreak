If you run a server with competitive elements, a common problem, especially with the introduction of shulkers, is the loss of valuable items to ender chests. A player can kill another player, loot their body, and put their valuables in an echest. Those items are effectively lost forever unless the player with the echest decides to bring them out. Rare items with lore or practical valuable can be lost forever simply because a player with a full enderchest decides to quit the server. Enderphreak seeks to solve this in an intuitive and unique manner by allowing players to "hack" enderchests of their opponents.

	When a player wants to use an enderchest, they first must set a pin/password for their chest. This pin will correspond with configurable noises that get played audibly whenever a player opens their enderchest in the future. Another player can listen to the noises, translate them back to numbers, and use them to hack into the ender chest and retrieve items. Enderchests are no longer a 100% secure method of storage and a black hole for loot.
	
	However, this plugin doesn't make enderchests worthless either. Players can use info security measures to make sure no one listens in to their pin. Furthermore, there are many customization options to influence what a player can actually take during a successful hack in order to balance hacking to your server's needs!

Commands/Permissions:

command: /enderphreak open

permission: enderphreak.open

command: /enderphreak set

permission: enderphreak.set

usage: /enderphreak set <pass>
	
usage: /enderphreak set <newpass> <oldpass>
	
command: /enderphreak tone
	
permission: enderphreak.tone
	
usage: /enderphreak tone <numbers>
	
command: /enderphreak pass
	
permission: enderphreak.open
	
usage: /enderphreak pass <pass>
	
command: /enderphreak override
	
permission: enderphreak.override
	
usage: /enderphreak override <player_name>
	
command: /enderphreak overridereset
	
permission: enderphreak.override.reset
	
usage: /enderphreak overridereset <player_name>
	
command: /enderphreak hack
	
permission: enderphreak.hack
	
usage: /enderphreak hack <player_name>
	
usage: /enderphreak hack <player_name> <pass>
	

Additional notes: be sure to have player enderchests be empty before installing the plugin, as there is a chance that their enderchest can be overwritten/lost.
